
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servicemonitor.dao;

import java.util.Collection;
import javax.persistence.EntityManager;
import servicemonitor.entity.*;

/**
 *
 * @author ktuomain
 */
public class UserDao {
 //   @PersistenceContext
    private EntityManager em;

    public Collection<User> findAllUsers() {
        return (Collection<User>) em.createNamedQuery("User.findAll").getResultList();
    }

    public UserDao() {
        em = javax.persistence.Persistence.createEntityManagerFactory("ServiceMonitorServerPU").createEntityManager();
    }
}
