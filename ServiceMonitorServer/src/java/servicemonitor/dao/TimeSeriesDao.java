/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servicemonitor.dao;

import java.util.*;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import servicemonitor.entity.UserMessageCount;
import servicemonitor.entity.*;

/**
 *
 * @author ktuomain
 */
public class TimeSeriesDao {
    //@PersistenceContext
    private EntityManager em = javax.persistence.Persistence.createEntityManagerFactory("ServiceMonitorServerPU").createEntityManager();

    public class TimeSeriesData {

        private SortedMap<Date, List<UserDayData>> timeSeries;
        private List<String> userNames;
        private List<String> variableNames;

        public SortedMap<Date, List<UserDayData>> getTimeSeries() {
            return timeSeries;
        }

        public void setTimeSeries(SortedMap<Date, List<UserDayData>> timeSeries) {
            this.timeSeries = timeSeries;
        }

        public List<String> getUserNames() {
            return userNames;
        }

        public void setUserNames(List<String> userNames) {
            this.userNames = userNames;
        }

        public List<String> getVariableNames() {
            return variableNames;
        }

        public void setVariableNames(List<String> variableNames) {
            this.variableNames = variableNames;
        }
    }

    public class UserDayData {

        private List<Double> values = new ArrayList();

        public void addValue(Double value) {
            values.add(value);
        }

        public List<Double> getValues() {
            return values;
        }

        public void setValues(List<Double> values) {
            this.values = values;
        }
    }

    public List<String> getVariableNames() {
        List<String> result = new LinkedList();

        result.add("activity");
        result.add("message.count");
//        result.add("friend.count");
//        result.add("channels.created.count");
//        result.add("channels.member.count");
        result.add("location.count");

        return result;
    }
    
    private Double getUserDayMsgCount(User user, Date date) {
        try {
            UserMessageCount umc;
            Number count = (Number) em.createQuery("SELECT SUM(u.messageCount) FROM UserMessageCount u where u.user=:user_id and u.day<=:day").setParameter("user_id", user).setParameter("day", date).getSingleResult();
            System.out.println("Summa = " + count);
            return count.doubleValue();
        } catch (Exception ex) {
            System.out.println("UserDayMsgCount:");
            ex.printStackTrace();
            return 0.0;
        }
    }

    /*
    private Double getUserDayMsgCount(User user, Date date) {
        try {
            UserMessageCount umc = (UserMessageCount) em.createQuery("SELECT u FROM UserMessageCount u where u.userId=:user_id and u.day<=:day").setParameter("user_id", user).setParameter("day", date).getSingleResult();
            return 1.1;
        } catch (Exception ex) {
            return 0.0;
        }
    }
*/
    

    Double gg = 0.0;

    private Double getUserDayFriendCount(User user, Date date) {
        return 0.0 + gg++;
    }

    private Double getUserLocationCount(User user, Date date) {
        double result = 0.0;

        System.out.println("kukkuu" + date.toString());
        try {

            Number count = (Number) em.createQuery("SELECT COUNT(l.locationId) FROM LocationLog l where l.userId=:user_id and l.whenCreated <= :date").setParameter("user_id", user).setParameter("date", date).getSingleResult();
//                 Number count = (Number) em.createQuery("SELECT COUNT(l.locationId) FROM LocationLog l where l.userId = :user_id").setParameter("user_id", user).getSingleResult();

            result = count.doubleValue();
            System.out.println("LocationSum = " + result);
        } catch (Exception ex) {
            ex.printStackTrace();
            //Nor this user
        }

        return result;
    }

    private Double getUserDayActivity(User user, Date today, Date tomorrow) {
        double result = 0.0;
        /*
        List list = em.createNativeQuery("select actions from activity where user_id = :userId and date(created_at) = date(:date)").setParameter("userId", user.getId()).setParameter("date", date).getResultList();
        for (Object i : list) {
        Object[] arr = (Object[]) i;
        result = (Double) arr[0];
        }
         */

        try {
            Activity activity;
            activity = (Activity) em.createQuery("select a from Activity a where a.activityDate >= :today and a.activityDate < :tomorrow and a.userId = :user_id").setParameter("today", today).setParameter("tomorrow", tomorrow).setParameter("user_id", user).getSingleResult();

            System.out.println("Aktiviteetin alku: " + today);
            System.out.println("Aktiviteetin loppu: " + tomorrow);

//            activity = (Activity) em.createQuery("select a from Activity a where a.activityDate >= :today and a.activityDate < :tomorrow").setParameter("today", today).setParameter("tomorrow", tomorrow).getSingleResult();


            result = activity.getActions();
        } catch (NoResultException ex) {
            //not all users are active each day, this is not an error
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        //            rcd = em.createQuery("SELECT r FROM RessiCosDelta r", servicemonitor.entity.RessiCosDelta.class).getSingleResult();

        System.out.println("activiteetti" + result);

        return result;
    }

    private Double getUserDayValue(User user, Date today, Date tomorrow, String variableName) {
        Double result = 0.0;

//        System.out.println("Variable: " + variableName);

        if (variableName.equals("activity")) {
            result = getUserDayActivity(user, today, tomorrow);
        } else if (variableName.equals("message.count")) {
            result = getUserDayMsgCount(user, today);
        } else if (variableName.equals("location.count")) {
            result = getUserLocationCount(user, today);
        } else if (variableName.equals("friend.count")) {
            result = getUserDayFriendCount(user, today);
        } else {
            //unknown variable!
            System.out.println("Error: Unknown variable: " + variableName);
        }

        return result;
    }

    public TimeSeriesData getSeries(RessiGroup group, Date start, Date end, List<String> variables) {
        TimeSeriesData result = new TimeSeriesData();

        List<String> userNames = new ArrayList();
        if (group.getRessiGroupMemberCollection() != null) {
            for (RessiGroupMember i : group.getRessiGroupMemberCollection()) {
                userNames.add(i.getUser().getUserName());
            }
        }

        SortedMap<Date, List<UserDayData>> timeSeries = new TreeMap();

        Calendar i = Calendar.getInstance();
        Calendar endI = Calendar.getInstance();
        endI.setTime(end);

        for (i.setTime(start); i.before(endI);) {
            Date today = i.getTime();
            List<UserDayData> dayData = new ArrayList();
            timeSeries.put(i.getTime(), dayData);

            i.add(Calendar.DATE, 1);
            Date tomorrow = i.getTime();

            if (group.getRessiGroupMemberCollection() != null) {
                for (RessiGroupMember groupMember : group.getRessiGroupMemberCollection()) {
//                String user = groupMember.getUserId().getUserId();

                    UserDayData udd = new UserDayData();

                    for (String variableName : variables) {
                        udd.addValue(getUserDayValue(groupMember.getUser(), today, tomorrow, variableName));
                    }

                    dayData.add(udd);
                }
            }
        }

        result.setTimeSeries(timeSeries);
        result.setUserNames(userNames);
        result.setVariableNames(variables);

        return result;
    }
}

