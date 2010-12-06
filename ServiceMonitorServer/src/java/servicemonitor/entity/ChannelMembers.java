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
@Table(name = "channel_members")
@NamedQueries({
    @NamedQuery(name = "ChannelMembers.findAll", query = "SELECT c FROM ChannelMembers c"),
    @NamedQuery(name = "ChannelMembers.findById", query = "SELECT c FROM ChannelMembers c WHERE c.id = :id"),
    @NamedQuery(name = "ChannelMembers.findByBeginAt", query = "SELECT c FROM ChannelMembers c WHERE c.beginAt = :beginAt"),
    @NamedQuery(name = "ChannelMembers.findByEndAt", query = "SELECT c FROM ChannelMembers c WHERE c.endAt = :endAt")})
public class ChannelMembers implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "begin_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date beginAt;
    @Column(name = "end_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endAt;
    @JoinColumn(name = "channel_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Channel channel;
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private User user;

    public ChannelMembers() {
    }

    public ChannelMembers(Integer id) {
        this.id = id;
    }

    public ChannelMembers(Integer id, Date beginAt) {
        this.id = id;
        this.beginAt = beginAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getBeginAt() {
        return beginAt;
    }

    public void setBeginAt(Date beginAt) {
        this.beginAt = beginAt;
    }

    public Date getEndAt() {
        return endAt;
    }

    public void setEndAt(Date endAt) {
        this.endAt = endAt;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
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
        if (!(object instanceof ChannelMembers)) {
            return false;
        }
        ChannelMembers other = (ChannelMembers) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "servicemonitor.entity.ChannelMembers[id=" + id + "]";
    }

}
