/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servicemonitor.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.SessionScoped;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.NotSupportedException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import servicemonitor.entity.*;

/**
 *
 * @author ktuomain
 */
@SessionScoped
public class RessiGroupDao implements Serializable {

    @PersistenceContext(unitName = "ServiceMonitorServerPU")
    private EntityManager em;
    @Resource
    UserTransaction utx;

    public Collection<RessiGroup> findAllGroups() {
        return (Collection<RessiGroup>) em.createNamedQuery("RessiGroup.findAll").getResultList();
    }

    public RessiGroup getOrCreateGroup(String groupName) {
        RessiGroup result;

        try {
            result = (RessiGroup) em.createNamedQuery("RessiGroup.findByName").setParameter("name", groupName).getSingleResult();
        } catch (Exception ex) {
            result = new RessiGroup();
            result.setName(groupName);
        }

        return result;
    }

    public void deleteGroup(String groupName) {
        try {
            utx.begin();
            RessiGroup group = (RessiGroup) em.createNamedQuery("RessiGroup.findByName").setParameter("name", groupName).getSingleResult();
            em.remove(group);
            utx.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public Collection<User> findAllUsers() {
        return (Collection<User>) em.createNamedQuery("User.findAll").getResultList();
    }

    public void saveGroup(RessiGroup group) {
        try {
            utx.begin();
            em.persist(group);
            utx.commit();
        } catch (Exception ex) {
            Logger.getLogger(RessiGroupDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
//    public RessiGroupDao() {
//        em = javax.persistence.Persistence.createEntityManagerFactory("ServiceMonitorServerPU").createEntityManager();
//    }
}
