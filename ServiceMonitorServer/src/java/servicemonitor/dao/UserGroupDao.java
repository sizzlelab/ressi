
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
import servicemonitor.entity.*;

/**
 *
 * @author ktuomain
 */
public class UserGroupDao {
    //@PersistenceContext
    private EntityManager em;

    public Collection<UserGroup> findAllUserGroups() {
        return (Collection<UserGroup>) em.createNamedQuery("UserGroup.findAll").getResultList();
    }

    public UserGroupDao() {
        em = javax.persistence.Persistence.createEntityManagerFactory("ServiceMonitorServerPU").createEntityManager();
    }
}
