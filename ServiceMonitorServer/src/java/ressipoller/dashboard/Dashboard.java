/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ressipoller.dashboard;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import servicemonitor.entity.LocationLog;
import servicemonitor.entity.Metalog;

/**
 *
 * @author ktuomain
 */

//@SessionScoped
public class Dashboard implements Serializable {
    //private javax.persistence.EntityManager entityManager;
//    @PersistenceContext(unitName="ServiceMonitorServerPU")
    private EntityManager entityManager;

    public void connect() {
        try {
            entityManager = javax.persistence.Persistence.createEntityManagerFactory("ServiceMonitorServerPU").createEntityManager();
  //          entityManager.getTransaction().begin();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void disconnect() {
        entityManager.close();
    }

//    public Dashboard() {
//    }

    public TimeSeriesDataset getUserCounts(Date from, Date to) {
        return null;
    }

    public TimeSeriesDataset getFriendCounts(Date from, Date to, String userId) {
        return null;
    }

    public List<servicemonitor.entity.Metalog> getMetalogs() {
        return (List<Metalog>) entityManager.createQuery("SELECT m FROM Metalog m").getResultList();
    }

    public DateTimeSeriesDataset getActivitySum() {
        DateTimeSeriesDataset result = new DateTimeSeriesDataset();

        List list = entityManager.createNativeQuery("select activity_date, name, count(distinct user_id) from activity,cv_application where cv_application.application_id=activity.application_id group by 1,2 order by 1,2").getResultList();
        for (Object i : list) {
            Object[] arr = (Object[]) i;
            java.sql.Date date = (java.sql.Date) arr[0];
            String appName = (String) arr[1];
//            java.math.BigDecimal count = (java.math.BigDecimal) arr[2];
            Long count = (Long) arr[2];


            result.addDatapoint(date, appName, count.doubleValue());
        }

        return result;
    }

    public DateTimeSeriesDataset getServiceEventSum() {
        DateTimeSeriesDataset result = new DateTimeSeriesDataset();

        List list = entityManager.createNativeQuery("select activity_date, name, sum(actions) from activity,cv_application where cv_application.application_id=activity.application_id group by 1,2 order by 1,2").getResultList();
        for (Object i : list) {
            Object[] arr = (Object[]) i;
            java.sql.Date date = (java.sql.Date) arr[0];
            String appName = (String) arr[1];
            java.math.BigDecimal count = (java.math.BigDecimal) arr[2];
//            Long count = (Long) arr[2];

            result.addDatapoint(date, appName, count.doubleValue());
        }

        return result;
    }

    public LocationDataset getUserLocations(Date time) {
        LocationDataset result = new LocationDataset();

//        entityManager.crea
//        List<LocationLog> arr_loc = (List<LocationLog>) entityManager.createQuery("SELECT l FROM LocationLog l").getResultList();
        //FIXME
        List<LocationLog> arr_loc = (List<LocationLog>) entityManager.createQuery("SELECT l FROM LocationLog l").getResultList();

        Iterator i = arr_loc.iterator();
        LocationLog loc;

        Set<Integer> allReadyListed = new HashSet();
      
        while (i.hasNext()) {
            loc = (LocationLog) i.next();
            //FIXME: checkkaa toimiiko
//            if (allReadyListed.contains(loc.getUserId().getId()))
            if (allReadyListed.contains(loc.getUser().getId()))
                continue;
//            allReadyListed.add(loc.getUserId().getId());
            allReadyListed.add(loc.getUser().getId());

            result.latitudes.add(loc.getLatitude());
            result.longitudes.add(loc.getLongitude());
            result.time.add(loc.getWhenCreated());
            result.ids.add(loc.getUser().getUserName());
        }

        return result;
    }

    public LocationDataset getUserRoute(Date from, Date to, String userId) {
        return null;
    }
}
