
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
public class MetalogDao {
    //@PersistenceContext
    private EntityManager em;

    public Collection<Metalog> findAllMetalogs() {
        return (Collection<Metalog>) em.createNamedQuery("Metalog.findAll").getResultList();
    }

    public MetalogDao() {
        em = javax.persistence.Persistence.createEntityManagerFactory("ServiceMonitorServerPU").createEntityManager();
    }
}
