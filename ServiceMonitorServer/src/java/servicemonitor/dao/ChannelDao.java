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
public class ChannelDao {
    //@PersistenceContext
    private EntityManager em;

    public Collection<Channel> findAllChannels() {
        return (Collection<Channel>) em.createNamedQuery("Channel.findAll").getResultList();
    }

    public ChannelDao() {
        em = javax.persistence.Persistence.createEntityManagerFactory("ServiceMonitorServerPU").createEntityManager();
    }
}
