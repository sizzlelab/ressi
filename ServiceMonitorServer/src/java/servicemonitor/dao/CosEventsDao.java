/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servicemonitor.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import servicemonitor.entity.CosEvents;

/**
 *
 * @author ktuomain
 */
public class CosEventsDao {

//    @PersistenceContext
    private EntityManager entityManager;
    private Query queryWeekly;
    private Query queryFromToDate;

    public CosEventsDao() {
        init();
    }

    private void init() {
        if (entityManager == null) {
            entityManager = javax.persistence.Persistence.createEntityManagerFactory("ServiceMonitorServerPU").createEntityManager();
        }

        if (queryWeekly == null) {
            queryWeekly = entityManager.createQuery("SELECT c FROM CosEvents c");
        }
        if (queryFromToDate == null) {
            queryFromToDate = entityManager.createQuery("SELECT c FROM CosEvents c WHERE c.createdAt >= :fromDate AND c.createdAt <= :toDate");
        }
    }

    public Collection<CosEvents> getFromToDate(Date fromDate, Date toDate) {
        init();
        queryFromToDate.setMaxResults(1049);
        queryFromToDate.setParameter("fromDate", fromDate);
        queryFromToDate.setParameter("toDate", toDate);
        return queryFromToDate.getResultList();
    }

    public Collection<CosEvents> findEvents(Date fromDate, Date toDate, String action, String semanticEventId, String userId, String appId, String sessionId, String returnValue, Integer resultLimit) {
        init();
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery();
        Root<CosEvents> e = cq.from(CosEvents.class);
        List<Predicate> restrictions = new ArrayList();

        if (fromDate != null) {
            restrictions.add(cb.greaterThan(e.<Date>get("createdAt"), fromDate));
        }
        if (fromDate != null) {
            restrictions.add(cb.lessThan(e.<Date>get("createdAt"), toDate));
        }
        if (action != null) {
            restrictions.add(cb.like(e.<String>get("action"), action));
        }
        if (semanticEventId != null) {
            restrictions.add(cb.like(e.<String>get("semanticEventId"), semanticEventId));
        }
        if (userId != null) {
            restrictions.add(cb.like(e.<String>get("userId"), userId));
        }
        if (appId != null) {
            restrictions.add(cb.like(e.<String>get("appId"), appId));
        }
        if (sessionId != null) {
            restrictions.add(cb.like(e.<String>get("sessionId"), sessionId));
        }
        if (returnValue != null) {
            restrictions.add(cb.like(e.<String>get("returnValue"), returnValue));
        }
        cq.where(cb.and(restrictions.toArray(new Predicate[]{})));
        if (resultLimit != null) {
            return entityManager.createQuery(cq).setMaxResults(resultLimit).getResultList();
        } else {
            return entityManager.createQuery(cq).getResultList();
        }
    }

    public Collection<CosEvents> findEvents(Date fromDate, Date toDate, String action, String semanticEventId, String userId, String appId, String sessionId, String returnValue) {
        return findEvents(fromDate, toDate, action, semanticEventId, userId, appId, sessionId, returnValue, null);
    }

    public Collection<CosEvents> getCosEventsWeekly() {
        init();
        queryWeekly.setMaxResults(1049);
        Collection<CosEvents> data = queryWeekly.getResultList();
        /*
        for (Object entity : data) {
        entityManager.refresh(entity);
        }
         */
        return data;
    }
}
