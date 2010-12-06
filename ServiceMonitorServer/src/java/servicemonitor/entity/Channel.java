/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package servicemonitor.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author ktuomain
 */
@Entity
@Table(name = "channel")
@NamedQueries({
    @NamedQuery(name = "Channel.findAll", query = "SELECT c FROM Channel c"),
    @NamedQuery(name = "Channel.findById", query = "SELECT c FROM Channel c WHERE c.id = :id"),
    @NamedQuery(name = "Channel.findByChannelName", query = "SELECT c FROM Channel c WHERE c.channelName = :channelName"),
    @NamedQuery(name = "Channel.findByChannelId", query = "SELECT c FROM Channel c WHERE c.channelId = :channelId"),
    @NamedQuery(name = "Channel.findByWhenCreated", query = "SELECT c FROM Channel c WHERE c.whenCreated = :whenCreated"),
    @NamedQuery(name = "Channel.findByIsPublic", query = "SELECT c FROM Channel c WHERE c.isPublic = :isPublic")})
public class Channel implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "channel_name")
    private String channelName;
    @Basic(optional = false)
    @Column(name = "channel_id")
    private String channelId;
    @Basic(optional = false)
    @Column(name = "when_created")
    @Temporal(TemporalType.TIMESTAMP)
    private Date whenCreated;
    @Column(name = "is_public")
    private Integer isPublic;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "channel")
    private Collection<ChannelMembers> channelMembersCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "channel")
    private Collection<Message> messageCollection;
    @JoinColumn(name = "created_by", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private User user;

    public Channel() {
    }

    public Channel(Integer id) {
        this.id = id;
    }

    public Channel(Integer id, String channelName, String channelId, Date whenCreated) {
        this.id = id;
        this.channelName = channelName;
        this.channelId = channelId;
        this.whenCreated = whenCreated;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public Date getWhenCreated() {
        return whenCreated;
    }

    public void setWhenCreated(Date whenCreated) {
        this.whenCreated = whenCreated;
    }

    public Integer getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(Integer isPublic) {
        this.isPublic = isPublic;
    }

    public Collection<ChannelMembers> getChannelMembersCollection() {
        return channelMembersCollection;
    }

    public void setChannelMembersCollection(Collection<ChannelMembers> channelMembersCollection) {
        this.channelMembersCollection = channelMembersCollection;
    }

    public Collection<Message> getMessageCollection() {
        return messageCollection;
    }

    public void setMessageCollection(Collection<Message> messageCollection) {
        this.messageCollection = messageCollection;
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
        if (!(object instanceof Channel)) {
            return false;
        }
        Channel other = (Channel) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "servicemonitor.entity.Channel[id=" + id + "]";
    }

}
