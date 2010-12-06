/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package servicemonitor.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author ktuomain
 */
@Entity
@Table(name = "user_message_count")
@NamedQueries({
    @NamedQuery(name = "UserMessageCount.findAll", query = "SELECT u FROM UserMessageCount u"),
    @NamedQuery(name = "UserMessageCount.findById", query = "SELECT u FROM UserMessageCount u WHERE u.id = :id"),
    @NamedQuery(name = "UserMessageCount.findByDay", query = "SELECT u FROM UserMessageCount u WHERE u.day = :day"),
    @NamedQuery(name = "UserMessageCount.findByMessageCount", query = "SELECT u FROM UserMessageCount u WHERE u.messageCount = :messageCount"),
    @NamedQuery(name = "UserMessageCount.findByCumulativeCount", query = "SELECT u FROM UserMessageCount u WHERE u.cumulativeCount = :cumulativeCount")})
public class UserMessageCount implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @Column(name = "day")
    @Temporal(TemporalType.DATE)
    private Date day;
    @Basic(optional = false)
    @Column(name = "message_count")
    private long messageCount;
    @Basic(optional = false)
    @Column(name = "cumulative_count")
    private long cumulativeCount;
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private User user;

    public UserMessageCount() {
    }

    public UserMessageCount(Long id) {
        this.id = id;
    }

    public UserMessageCount(Long id, Date day, long messageCount, long cumulativeCount) {
        this.id = id;
        this.day = day;
        this.messageCount = messageCount;
        this.cumulativeCount = cumulativeCount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDay() {
        return day;
    }

    public void setDay(Date day) {
        this.day = day;
    }

    public long getMessageCount() {
        return messageCount;
    }

    public void setMessageCount(long messageCount) {
        this.messageCount = messageCount;
    }

    public long getCumulativeCount() {
        return cumulativeCount;
    }

    public void setCumulativeCount(long cumulativeCount) {
        this.cumulativeCount = cumulativeCount;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UserMessageCount)) {
            return false;
        }
        UserMessageCount other = (UserMessageCount) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "servicemonitor.entity.UserMessageCount[id=" + id + "]";
    }

}
