/*

https://wiki.tkk.fi/display/otasizzle/RESSI%20-%20log%20specification%20plan
 *
 * Tässä vähän ajatuksia:

Metalog (system)

 * Pollaamalla COSia
 * Metalog-taulu: time, # males, # females, # private channels, # public channels, # open groups, # closed groups
 * GroupMembership-taulu: group id, user id, begin date, end date
o Käytännössä sama kuin Aggregate Login Group-taulu, joten ei tarvita tähän erillistä taulua?

Aggregate Log (user)

 * Erillisinä tauluina
o Friend: user id, friend id, begin date, end date
+ Tästä saadaan: friend list, friend count tiettynä ajanhetkenä
o Channel: user id, channel id, private/public, begin date, end date
+ Tästä saadaan: number of private/public channels
o Message: user id, message id, written/read, channel?
+ Tästä saadaan: number of messages written/read
o Group: user id, group id, begin date, end date
+ Tästä saadaan: group list, number of groups, members of a group
o Activity: user id, date, actions
+ Käyttäjän päivän aikana tekemät tapahtumat, actions > 0
+ Tästä saadaan: number of active days total (kaikki rivit), in row (peräkkäiset päivät), list of active days, "activity number" per day
o User: user id, user name, gender, time of birth
 * Selvitettävää
o Number of messages written to each group channel
+ Message-tauluun channel kenttä?
 * Validity: begin date < date < end date
o Kaikki jäsenyyttä kuvastavat tiedot: friends, channels, groups
 *

 */
package servicemonitor.timer;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.ejb.Singleton;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import javax.transaction.UserTransaction;
import org.json.JSONObject;
import org.json.JSONTokener;
import servicemonitor.model.*;
import servicemonitor.probe.*;
import servicemonitor.entity.*;

/**
 *
 * @author ktuomain
 */
//TransactionManagement(TransactionManagementType.BEAN)
@Singleton
@TransactionManagement(TransactionManagementType.BEAN)
public class IncrementalUpdate {
    /*
    private static UserDAO _userDAO = null;
    private static ChannelDao _channelDao = null;
    private static CosConnection cosConnection = null;
     */

    long MAX_BATCH_SIZE = 5000;
    private UserDAO _userDAO = null;
    private ChannelDao _channelDao = null;
    private CosConnection cosConnection = null;
    //private javax.persistence.EntityManager em;

    @PersistenceUnit(unitName = "ServiceMonitorServerPU")
    EntityManagerFactory emf;
    EntityManager em;
    @Resource
    UserTransaction utx;

    @PreDestroy
    public void destroy() {
        if (em.isOpen()) {
            em.close();
        }
    }

    private UserDAO getUserDao() {
        if (_userDAO == null) {
            _userDAO = new UserDAO(cosConnection);
        }
        return _userDAO;
    }

    private ChannelDao getChannelDao() {
        if (_channelDao == null) {
            _channelDao = new ChannelDao(cosConnection);
        }
        return _channelDao;
    }

    @PostConstruct
    public boolean init() {
        try {
            em = emf.createEntityManager();

//           em = javax.persistence.Persistence.createEntityManagerFactory("ServiceMonitorServerPU").createEntityManager();
            calculateUserMessageCounts();

            cosConnection = new CosConnection();

            String cosBaseUrl = System.getProperty("cos.baseurl");
            System.out.println("cos.baseurl=" + cosBaseUrl);
            String cosUser = System.getProperty("cos.user");
            String cosPassword = System.getProperty("cos.password");

            cosConnection.init(cosBaseUrl, cosUser, cosPassword);
            cosConnection.login();

            System.out.println("getUsers()");
            if (_userDAO == null) {
                getUserDao().getUsers(false);
            }

            System.out.println("getChannels()");
            if (_channelDao == null) {
                getChannelDao().getChannels();
            }

        } catch (Throwable ex) {
            ex.printStackTrace();
        }

        return true;
    }

    public void run() {
        if (em == null) {
            return;
        }

        try {
            do {
//            em.getTransaction().begin();
                utx.begin();

                fetchNewDeltaRange();

                deltaUser();
                deltaLocationLog();
                deltaUserGroup();
                deltaChannel();

                saveNewDeltaRange();
//            em.getTransaction().commit();
                utx.commit();
            } while (prevMaxUpdate + (MAX_BATCH_SIZE / 10) < newMaxUpdate);
        } catch (Exception ex) {
            Logger.getLogger(IncrementalUpdate.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void close() {
        if (em != null) {
            //          em.getTransaction().commit();
            //       em.close();

            calculateUserMessageCounts();

            try {
                utx.begin();

                //          em.getTransaction().begin();
                updateStatistics();
                //        em.getTransaction().commit();

                utx.commit();
            } catch (Exception ex) {
                Logger.getLogger(IncrementalUpdate.class.getName()).log(Level.SEVERE, null, ex);
            }

//            em.close();
        }
        em = null;
//        cosConnection.logout();
    }
    /////////////////////////////////////////////////////////////////////////////
    private long prevMaxUpdate = -1, newMaxUpdate = 0;

    private void fetchNewDeltaRange() {
        long oldMax = -1;

        servicemonitor.entity.RessiCosDelta rcd = null;
        try {
            rcd = em.createQuery("SELECT r FROM RessiCosDelta r", servicemonitor.entity.RessiCosDelta.class).getSingleResult();
        } catch (javax.persistence.NoResultException ex) {
            System.out.println("This seems to be first run...");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (rcd != null) {
            oldMax = rcd.getPrevMaxId();
            em.remove(rcd);
        }

        Integer newMax = (Integer) em.createNativeQuery("select max(id) from cos_events").getSingleResult();

        prevMaxUpdate = oldMax;
        newMaxUpdate = newMax;

        long difference = newMax - oldMax;
        System.out.println("Entries left: " + difference);

        if (difference > MAX_BATCH_SIZE) {
            newMaxUpdate = oldMax + MAX_BATCH_SIZE;
        }

        System.out.println("prevMaxUpdate=" + prevMaxUpdate
                + ", newMaxUpdate=" + newMaxUpdate);
    }

    private void saveNewDeltaRange() {
        servicemonitor.entity.RessiCosDelta rcd = new servicemonitor.entity.RessiCosDelta();
        rcd.setPrevMaxId(newMaxUpdate);
        em.persist(rcd);
    }

    /////////////////////////////////////////////////////////////////////////
    public void deltaUser() {
        System.out.println("deltaUser()");

        try {
            Query query = em.createQuery("SELECT c FROM CosEvents c WHERE c.returnValue='201 Created' and c.requestUri='/people' and c.id > :prevMaxUpdate and c.id <= :newMaxUpdate");
            query = query.setParameter("prevMaxUpdate", prevMaxUpdate);
            query = query.setParameter("newMaxUpdate", newMaxUpdate);

            List<servicemonitor.entity.CosEvents> ce =
                    (List<servicemonitor.entity.CosEvents>) query.getResultList();

            System.out.println("Number of cos user creation events = " + ce.size());

            for (servicemonitor.entity.CosEvents i : ce) {
                //{"format":"json","person":{"username":"erarwhilita",
                //            "password":"[FILTERED]","email":"GofatoffHak@thesearchlife.info"},
                //          "action":"create","controller":"people"}
                //saadaan username
                //haetaan userid
                System.out.println(i.getParameters());
                JSONTokener tokener = new JSONTokener(i.getParameters());
                JSONObject jObject = new JSONObject(tokener);

                JSONObject obj = (JSONObject) jObject.get("person");
                String username = obj.getString("username");
                System.out.println("name " + username);

                servicemonitor.model.User smUser = getUserDao().getUserByUsername(username);

                if (smUser != null) {
                    servicemonitor.entity.User user = getUserbyUserId(smUser.getId());
                    user.setWhenCreated(i.getCreatedAt());
                    em.persist(user);
                } else {
                    System.out.println("user doesn't exist anymore: " + username);
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        System.out.println("end of deltaUsers.....................");
    }

    private servicemonitor.entity.User createUser(String userId) {
        servicemonitor.entity.User user = new servicemonitor.entity.User();

        servicemonitor.model.User smUser = getUserDao().getUserById(userId);
        if (smUser == null) {
            System.out.println("createUserDao().getUserById(" + userId + ") --> null");
            return null;
        }

        user.setUserId(userId);
        user.setWhenCreated(new Date()); //TODO: FIXME
        user.setUserName(smUser.getName());

        user.setSex(smUser.getSex().getIntValue());
        user.setTimeOfBirth(smUser.getTimeOfBirth());

        em.persist(user);

        return user;
    }

    public servicemonitor.entity.User getUserbyUserId(String userId) {
        try {
            return (servicemonitor.entity.User) em.createNamedQuery("User.findByUserId").setParameter("userId", userId).getSingleResult();
        } catch (javax.persistence.NoResultException ex) {
            System.out.println("Trying to create user: " + userId);
            return createUser(userId);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

////////////////////////////////////////////////////////////////////////////////
    public void deltaLocationLog() {
        System.out.println("deltaLocationLog()");

        //SELECT * from research_production.cos_events c WHERE parameters like '%latitude%' and  c.request_uri like '/people/%/@location'

        Query query = em.createQuery("SELECT c FROM CosEvents c WHERE c.parameters like '%latitude%' and c.requestUri like '/people/%/@location' and c.id > :prevMaxUpdate and c.id <= :newMaxUpdate");
        query = query.setParameter("prevMaxUpdate", prevMaxUpdate);
        query = query.setParameter("newMaxUpdate", newMaxUpdate);

        List<servicemonitor.entity.CosEvents> ce =
                (List<servicemonitor.entity.CosEvents>) query.getResultList();

        System.out.println("Number of cos location_log events = " + ce.size());

        for (servicemonitor.entity.CosEvents i : ce) {
            try {
                //{"format":"json","person":{"username":"erarwhilita",
                //            "password":"[FILTERED]","email":"GofatoffHak@thesearchlife.info"},
                //          "action":"create","controller":"people"}
                //saadaan username
                //haetaan userid
//                System.out.println("parameters = " + i.getParameters());

                JSONTokener tokener = new JSONTokener(i.getParameters());
                JSONObject jObject = new JSONObject(tokener);

                String userId = jObject.getString("user_id");
                //              System.out.println("user_id = " + userId);

                JSONObject obj;
                try {
                    obj = (JSONObject) jObject.get("location");
//                   System.out.println("OK, new format");
//                     {"format":"json","location":{"label":"Pasila","latitude":"","longitude":""},"action":"update","_method":"put","user_id":"abrFxuARqr3AhYaaWPEYjL","controller":"locations"}
                } catch (org.json.JSONException ex) {
//                    parameters = {"format":"json","latitude":"23.234","action":"update","altitude":"992","vertical_accuracy":"1","user_id":"aeCd5wrNir3BOQab_ZvnhG","controller":"locations","longitude":"21.2309","horizontal_accuracy":"1"}
                    obj = jObject;
                }

                String latitude = obj.getString("latitude");
                String longitude = obj.getString("longitude");

                servicemonitor.entity.User smUser = getUserbyUserId(userId);
                if (smUser == null) {
                    System.out.println("Error: user does not exist?: " + userId);
                    continue;
                }

                if (latitude.equals("")) {
//                    System.out.println("Missing latitude, skipping...");
                    continue;
                }
                if (longitude.equals("")) {
//                    System.out.println("Missing longitude, skipping...");
                    continue;
                }

                servicemonitor.entity.LocationLog locationLog = new servicemonitor.entity.LocationLog();
                locationLog.setLatitude(Double.parseDouble(latitude));
                locationLog.setLongitude(Double.parseDouble(longitude));
                locationLog.setWhenCreated(i.getCreatedAt());
                locationLog.setUser(smUser);

                em.persist(locationLog);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        System.out.println("end of deltaLocationLog.....................");
    }

    ///////////////////////////////////////////////////////////////////////////////////////
    public boolean alreadyExistsUserGroupName(String userGroupName) {
        try {
            Object result = em.createNamedQuery("UserGroup.findByGroupName").setParameter("groupName", userGroupName).getSingleResult();
        } catch (javax.persistence.NoResultException ex) {
            return false;
        }
        return true;
    }

    public void deltaUserGroup() {
        /*
         *Response example:

        {
        "entry": {
        "number_of_members": 1,
        "created_at": "2010-05-14T10:29:36Z",
        "title": "KNjojVAn example group",
        "group_type": "open",
        "id": "cx93Cwx0mr34DhaaWPEYjL",
        "created_by": "cx5m3Wx0mr34DhaaWPEYjL",
        "description": "Groups can have descriptions. This is an example."
        }
        }SELECT * FROM cos_events WHERE return_Value='201 Created' and request_Uri like '/groups%'
         *
         */
        System.out.println("deltaUserGoup()");

        Query query = em.createQuery("SELECT c FROM CosEvents c WHERE c.returnValue='201 Created' and c.requestUri like '/groups%' and c.id > :prevMaxUpdate and c.id <= :newMaxUpdate");
        query = query.setParameter("prevMaxUpdate", prevMaxUpdate);
        query = query.setParameter("newMaxUpdate", newMaxUpdate);

        List<servicemonitor.entity.CosEvents> ce =
                (List<servicemonitor.entity.CosEvents>) query.getResultList();

        System.out.println("Number of cos group creation events = " + ce.size());

        Set<String> groupNames = new HashSet();


        for (servicemonitor.entity.CosEvents i : ce) {
            try {
                //{"format":"json","person":{"username":"erarwhilita",
                //            "password":"[FILTERED]","email":"GofatoffHak@thesearchlife.info"},
                //          "action":"create","controller":"people"}
                //saadaan username
                //haetaan userid
                System.out.println("parameters = " + i.getParameters());

                JSONTokener tokener = new JSONTokener(i.getParameters());
                JSONObject jObject = new JSONObject(tokener);

                JSONObject obj;
                try {
                    obj = (JSONObject) jObject.get("group");
                    jObject = obj;
                } catch (org.json.JSONException ex) {
//                    parameters = {"format":"json","latitude":"23.234","action":"update","altitude":"992","vertical_accuracy":"1","user_id":"aeCd5wrNir3BOQab_ZvnhG","controller":"locations","longitude":"21.2309","horizontal_accuracy":"1"}
//                    obj = jObject;
                }

                String title = jObject.getString("title");
                if (alreadyExistsUserGroupName(title)) {
                    System.out.println("User group already exists, skipping: " + title);
                    continue;
                }

                String userId = i.getUserId();

                System.out.println("Title = " + title);
                System.out.println("created by " + userId);

                servicemonitor.entity.User smUser = getUserbyUserId(userId);
                if (smUser == null) {
                    System.out.println("Error: user does not exist?: " + userId);
                    continue;
                }

                if (!groupNames.contains(title)) {
                    //Prevent inserting same group twice...

                    servicemonitor.entity.UserGroup userGroup = new servicemonitor.entity.UserGroup();
                    userGroup.setWhenCreated(i.getCreatedAt());
                    //      userGroup.setCreatedBy(smUser);
                    userGroup.setUser(smUser);

                    userGroup.setGroupName(title);
                    userGroup.setIsOpenGroup(-1);

                    em.persist(userGroup);
                } else {
                    System.out.println("Error group already exists!: " + title);
                }

                groupNames.add(title);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        System.out.println("end of deltaUserGroup.....................");
    }
///////////////////////////////////////////////////////////////////////////////////////

    public boolean alreadyExistsChannelName(String channelName) {
        try {
            Object result = em.createNamedQuery("Channel.findByChannelName").setParameter("channelName", channelName).getSingleResult();
        } catch (javax.persistence.NoResultException ex) {
            return false;
        }
        return true;
    }

    public boolean alreadyExistsChannelId(String channelId) {
        try {
            Object result = em.createNamedQuery("Channel.findByChannelId").setParameter("channelId", channelId).getSingleResult();
        } catch (javax.persistence.NoResultException ex) {
            return false;
        }
        return true;
    }

    public void deltaChannel() {
        /*
         *Response example:

        {
        "entry": {
        "number_of_members": 1,
        "created_at": "2010-05-14T10:29:36Z",
        "title": "KNjojVAn example group",
        "group_type": "open",
        "id": "cx93Cwx0mr34DhaaWPEYjL",
        "created_by": "cx5m3Wx0mr34DhaaWPEYjL",
        "description": "Groups can have descriptions. This is an example."
        }
        }SELECT * FROM cos_events WHERE return_Value='201 Created' and request_Uri like '/groups%'
         *
         */

//        : parameters = {"format":"json","action":"create","channel":{"channel_type":"public","name":"Ossi feedback","description":"Post here your remarks, opinions,  questions etc. about Ossi"},"controller":"channels"}
        System.out.println("deltaChannel()");

        Query query = em.createQuery("SELECT c FROM CosEvents c WHERE c.returnValue='201 Created' and c.requestUri='/channels' and c.id > :prevMaxUpdate and c.id <= :newMaxUpdate");
        query = query.setParameter("prevMaxUpdate", prevMaxUpdate);
        query = query.setParameter("newMaxUpdate", newMaxUpdate);

        List<servicemonitor.entity.CosEvents> ce =
                (List<servicemonitor.entity.CosEvents>) query.getResultList();

        System.out.println("Number of cos group creation events = " + ce.size());

        Set<String> groupNames = new HashSet();


        for (servicemonitor.entity.CosEvents i : ce) {
            try {
                //{"format":"json","person":{"username":"erarwhilita",
                //            "password":"[FILTERED]","email":"GofatoffHak@thesearchlife.info"},
                //          "action":"create","controller":"people"}
                //saadaan username
                //haetaan userid
                System.out.println("parameters = " + i.getParameters());

                JSONTokener tokener = new JSONTokener(i.getParameters());
                JSONObject jObject = new JSONObject(tokener);

                JSONObject obj = jObject.getJSONObject("channel");

                String name = obj.getString("name");
                if (name == null) {
                    System.out.println("Channel name is null, skipping!");
                    continue;
                }

                if (alreadyExistsChannelName(name)) {
                    System.out.println("Channel already exists, skipping!: " + name);
                    continue;
                }

                servicemonitor.model.Channel modelChannel = getChannelDao().getChannelByName(name);
                if (modelChannel == null) {
                    System.out.println("Model channel == null, skipping: " + name);
                    continue;
                }

                String channelId = modelChannel.getId();

                String userId = i.getUserId();

                System.out.println("Title = " + name);
                System.out.println("created by " + userId);

                servicemonitor.entity.User smUser = getUserbyUserId(userId);
                if (smUser == null) {
                    System.out.println("Error: user does not exist?: " + userId);
                    continue;
                }

                //Prevent inserting same group twice...

                servicemonitor.entity.Channel channel = new servicemonitor.entity.Channel();


                channel.setWhenCreated(i.getCreatedAt());
                channel.setUser(smUser);
                channel.setChannelName(name);
                channel.setChannelId(channelId);

                if (modelChannel.getChannel_type().toString().equals("private")) {
                    channel.setIsPublic(0); //TODO, FIXME
                } else {
                    channel.setIsPublic(1); //TODO, FIXME
                }
                em.persist(channel);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        System.out.println("end of deltaChannel.");
    }

///////////////////////////////////////////////////////////////////////////////////////
    private servicemonitor.entity.CvApplication createCvApplication(String cosApplicationId) {
        servicemonitor.entity.CvApplication cvApplication = new servicemonitor.entity.CvApplication();

        cvApplication.setCosApplicationId(cosApplicationId);

        if (cosApplicationId.equals("a5bYBATler3OHCaaWPEYjL")) {
            cvApplication.setName("naepsy");
        } else if (cosApplicationId.equals("acm-TkziGr3z9Tab_ZvnhG")) {
            cvApplication.setName("kassi");
        } else if (cosApplicationId.equals("aDaJ-iruqr3RIwaaWPEYjL")) {
            cvApplication.setName("mobitrack");
        } else if (cosApplicationId.equals("artUTUxV0r3Pu1aaWPEYjL")) {
            cvApplication.setName("locationserver");
        } else if (cosApplicationId.equals("c2X_1uH0ar3Q2VaaWPEYjL")) {
            cvApplication.setName("elisa-3g-manager");
        } else if (cosApplicationId.equals("cWslSQyIyr3yiraaWPEYjL")) {
            cvApplication.setName("ossi");
        } else if (cosApplicationId.equals("cz5w9ooVGr3REiaaWPEYjL")) {
            cvApplication.setName("zabbix");
        } else if (cosApplicationId.equals("dI54XgoVCr3REiaaWPEYjL")) {
            cvApplication.setName("monitor");
        } else if (cosApplicationId.equals("dmtieyzWyr3QsSaaWPEYjL")) {
            cvApplication.setName("nsm");
        } else if (cosApplicationId.equals("dy6866Gbur3PEzaaWPEYjL")) {
            cvApplication.setName("xformsdb");
        } else {
            System.out.println("Unknown cos_application_id=" + cosApplicationId);
            return null;
        }

        em.persist(cvApplication);
        return cvApplication;
    }

    public servicemonitor.entity.CvApplication getApplicationbyCosApplicationId(String cosApplicationId) {
        try {
            return (servicemonitor.entity.CvApplication) em.createNamedQuery("CvApplication.findByCosApplicationId").setParameter("cosApplicationId", cosApplicationId).getSingleResult();
        } catch (javax.persistence.NoResultException ex) {
            System.out.println("Created application " + cosApplicationId);
            return createCvApplication(cosApplicationId);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public void sqlMetalog() {
        System.out.println("sqlMetalog() starts");
        em.createNativeQuery("delete from metalog").executeUpdate();

        List list = em.createNativeQuery("select date(a.when_created), (select count(*) from user b where b.sex=0 and a.when_created >= b.when_created), (select count(*) from user b where b.sex=1 and a.when_created >= b.when_created), (select count(*) from user b where b.sex=2 and a.when_created >= b.when_created) from user a where not exists (select * from user c where date(a.when_created)=date(c.when_created) and c.when_created > a.when_created) order by 1, 2").getResultList();
        for (Object i : list) {
            Object[] arr = (Object[]) i;
            java.sql.Date date = (java.sql.Date) arr[0];
            long sex0 = (Long) arr[1];
            long sex1 = (Long) arr[2];
            long sex2 = (Long) arr[3];

            Metalog metalog = new Metalog();
            metalog.setCreatedAt(date);
            metalog.setUnknownSexCount((int) sex0);
            metalog.setMaleCount((int) sex1);
            metalog.setFemaleCount((int) sex2);

            metalog.setOpenGroupCount(-1);
            metalog.setPrivateChannelCount(-1);
            metalog.setClosedGroupCount(-1);
            metalog.setPublicChannelCount(-1);

            em.persist(metalog);
        }

        System.out.println("sqlMetalog() ends");
    }

    private java.sql.Date getLastActivityDate() {
        try {
            Object result = em.createNativeQuery("select date(max(activity_date)) from activity").getSingleResult();
            return (java.sql.Date) result;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    private void incrementalActivity(java.sql.Date lastDate) {
        System.out.println("incrementalActivity()");
        Set<String> missingUsers = new HashSet();

        em.createNativeQuery("delete from activity where date(activity_date)>=?").setParameter(1, lastDate).executeUpdate();

        List list = em.createNativeQuery("select date(created_at), user_id, application_id, count(*) from cos_events where user_id is not null and date(created_at) >= ? group by 1,2,3").setParameter(1, lastDate).getResultList();
        for (Object i : list) {
            Object[] arr = (Object[]) i;
            java.sql.Date date = (java.sql.Date) arr[0];
            String user = (String) arr[1];
            String applicationId = (String) arr[2];
            long count = (Long) arr[3];

            if (missingUsers.contains(user)) {
                System.out.println("Skipping missing user: " + user);
                continue;
            }

            servicemonitor.entity.CvApplication cvApplication = getApplicationbyCosApplicationId(applicationId);
            servicemonitor.entity.User userObj = getUserbyUserId(user);
            if (userObj != null && cvApplication != null) {
                if (date == null) {
                    System.out.println("Date on tosiaan null!?");
                } else {
                    Activity activity = new Activity();
                    activity.setActions((int) count);
                    activity.setActivityDate(date);
                    activity.setUser(userObj);
                    activity.setCvApplication(cvApplication);
                    em.persist(activity);
                }
            } else {
                if (userObj == null) {
                    System.out.println("User not found?: " + user);
                    missingUsers.add(user);
                }
                if (cvApplication == null) {
                    System.out.println("Cos_application_id not found?: " + applicationId);
                }
            }
        }
    }

    public void sqlCreateActivity() {
        System.out.println("sqlCreateActivity starts");

        java.sql.Date lastActivity = getLastActivityDate();

        if (lastActivity != null) {
            incrementalActivity(lastActivity);
            return;
        }

        Set<String> missingUsers = new HashSet();

        em.createNativeQuery("delete from activity").executeUpdate();

        List list = em.createNativeQuery("select date(created_at), user_id, application_id, count(*) from cos_events where user_id is not null group by 1,2,3").getResultList();
        for (Object i : list) {
            Object[] arr = (Object[]) i;
            java.sql.Date date = (java.sql.Date) arr[0];
            String user = (String) arr[1];
            String applicationId = (String) arr[2];
            long count = (Long) arr[3];

            if (missingUsers.contains(user)) {
                System.out.println("Skipping missing user: " + user);
                continue;
            }

            servicemonitor.entity.CvApplication cvApplication = getApplicationbyCosApplicationId(applicationId);
            servicemonitor.entity.User userObj = getUserbyUserId(user);
            if (userObj != null && cvApplication != null) {
                if (date == null) {
                    System.out.println("Date on tosiaan null!?");
                } else {
                    Activity activity = new Activity();
                    activity.setActions((int) count);
                    activity.setActivityDate(date);
                    activity.setUser(userObj);
//                  activity.setUserId(userObj);
                    activity.setCvApplication(cvApplication);
//                  activity.setApplicationId(cvApplication);

                    em.persist(activity);
                }
            } else {
                if (userObj == null) {
                    System.out.println("User not found?: " + user);
                    missingUsers.add(user);
                }
                if (cvApplication == null) {
                    System.out.println("Cos_application_id not found?: " + applicationId);
                }
            }
        }

        System.out.println("sqlCreateActivity ends");
    }

    ////////////////////////////////////////////////////////////////////////////
    public void deltaGroupMembers() {
    }

    public void deltaChannelMembers() {
    }

    public void deltaMessage() {
    }

    public void deltaFriend() {
    }

    /////////////////////////////////////////////////////////////////
    public void updateStatistics() {
        System.out.println("updateStatistics()");

        sqlCreateActivity();
        sqlMetalog();

        System.out.println("updateStatistics() done");
    }

    //http://database.in2p3.fr/doc/oracle/Oracle_Application_Server_10_Release_3/web.1013/b28221/usclient005.htm#CIHDDHGB
    private void printMetalog() {
        Query queryMetalog = em.createQuery(
                "SELECT OBJECT(metalog) FROM Metalog metalog");
        Collection logEntries = queryMetalog.getResultList();

        for (Object i : logEntries) {
            System.out.println("log: " + i);
        }
    }

    private void saveMetalog() {
        List<servicemonitor.model.User> users = getUserDao().getUsers();



        int maleCount = 0, femaleCount = 0, unknownSexCount = 0;


        for (servicemonitor.model.User i : users) {
            //  System.out.println("-->" + i);
            if (i.getSex().equals(Sex.MALE)) {
                maleCount++;


            } else if (i.getSex().equals(Sex.FEMALE)) {
                femaleCount++;


            } else {
                unknownSexCount++;


            }
        }
        System.out.println("Uknown sex count = " + unknownSexCount);

        List<servicemonitor.model.Channel> channels = getChannelDao().getChannels();



        int publicChannelCount = 0, privateChannelCount = 0;


        for (servicemonitor.model.Channel i : channels) {
            if (i.getChannel_type().equals(ChannelType.PUBLIC)) {
                publicChannelCount++;


            }
            if (i.getChannel_type().equals(ChannelType.PRIVATE)) {
                privateChannelCount++;


            }
        }

        Metalog metalog = new Metalog();
        metalog.setClosedGroupCount(-1);        //TODO: closedgroupcount
        metalog.setCreatedAt(new Date());
        metalog.setFemaleCount(femaleCount);
        metalog.setMaleCount(maleCount);
        metalog.setUnknownSexCount(unknownSexCount);
        metalog.setOpenGroupCount(-1);           //TODO: opengroupcount
        metalog.setPrivateChannelCount(privateChannelCount);
        metalog.setPublicChannelCount(publicChannelCount);

        em.persist(metalog);

    }
    static boolean msgCountDone = true;
    //

    public void calculateUserMessageCountsOrigOld() {
        if (msgCountDone) {
            return;
        }
        msgCountDone = true;

        Query query = em.createQuery("SELECT u FROM User u");
        List<servicemonitor.entity.User> users =
                (List<servicemonitor.entity.User>) query.getResultList();

//        List<servicemonitor.entity.CosEvents> ce =
//                (List<servicemonitor.entity.CosEvents>) query.getResultList();

        try {
            for (servicemonitor.entity.User i : users) {
                //Too many rows to handle all this in one big transaction...
                //        em.getTransaction().begin();
                utx.begin();

                System.out.println("Calculating message stats for: " + i.getUserName());

                int count = em.createNativeQuery("delete from user_message_count where user_id=?").setParameter(1, i.getId()).executeUpdate();

                count = em.createNativeQuery("insert into user_message_count (day, user_id, message_count, cumulative_count) select date(c.created_at), u.id, count(*), 0 from cos_events c, user u where request_uri like '/channels/%/@messag%' and return_value='201 Created' and u.user_id=c.user_id and c.user_id=? group by 1,2;").setParameter(1, i.getUserId()).executeUpdate();
                System.out.println("Row count = " + count);

//            em.getTransaction().commit();
                utx.commit();
            }
        } catch (Exception ex) {
            Logger.getLogger(IncrementalUpdate.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void calculateUserMessageCounts() {
//        prevMaxUpdate = 1;
//        newMaxUpdate = 1000000;

        System.out.println("calculateUserMessageCounts starts");
        Query query = em.createQuery("SELECT c.userId FROM CosEvents c WHERE c.returnValue='201 Created' and c.requestUri like '/channels/%/%' and c.id > :prevMaxUpdate and c.id <= :newMaxUpdate");
        query = query.setParameter("prevMaxUpdate", prevMaxUpdate);
        query = query.setParameter("newMaxUpdate", newMaxUpdate);

        List<String> users = (List<String>) query.getResultList();
        Set<String> alreadySeen = new HashSet();

        try {
            for (String i : users) {
                //FIXME, distinctillä kyselyssä?
                if (alreadySeen.contains(i)) {
                    continue;
                }
                alreadySeen.add(i);

                //Too many rows to handle all this in one big transaction...
//            em.getTransaction().begin();
                utx.begin();

                System.out.println("Calculating message stats for: " + i);

                int count = em.createNativeQuery("delete from user_message_count where user_id in (select distinct id from user where user_id=?)").setParameter(1, i).executeUpdate();

                count = em.createNativeQuery("insert into user_message_count (day, user_id, message_count, cumulative_count) select date(c.created_at), u.id, count(*), 0 from cos_events c, user u where request_uri like '/channels/%/@messag%' and return_value='201 Created' and u.user_id=c.user_id and c.user_id=? group by 1,2;").setParameter(1, i).executeUpdate();
                System.out.println("Row count = " + count);

//            em.getTransaction().commit();
                utx.commit();
            }
        } catch (Exception ex) {
            Logger.getLogger(IncrementalUpdate.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("calculateUserMessageCounts done");
    }
}
