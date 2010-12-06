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
@Table(name = "user")
@NamedQueries({
    @NamedQuery(name = "User.findAll", query = "SELECT u FROM User u"),
    @NamedQuery(name = "User.findById", query = "SELECT u FROM User u WHERE u.id = :id"),
    @NamedQuery(name = "User.findByUserId", query = "SELECT u FROM User u WHERE u.userId = :userId"),
    @NamedQuery(name = "User.findByUserName", query = "SELECT u FROM User u WHERE u.userName = :userName"),
    @NamedQuery(name = "User.findBySex", query = "SELECT u FROM User u WHERE u.sex = :sex"),
    @NamedQuery(name = "User.findByWhenCreated", query = "SELECT u FROM User u WHERE u.whenCreated = :whenCreated"),
    @NamedQuery(name = "User.findByTimeOfBirth", query = "SELECT u FROM User u WHERE u.timeOfBirth = :timeOfBirth")})
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "user_id")
    private String userId;
    @Column(name = "user_name")
    private String userName;
    @Column(name = "sex")
    private Integer sex;
    @Basic(optional = false)
    @Column(name = "when_created")
    @Temporal(TemporalType.TIMESTAMP)
    private Date whenCreated;
    @Column(name = "time_of_birth")
    @Temporal(TemporalType.DATE)
    private Date timeOfBirth;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private Collection<UserGroup> userGroupCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private Collection<RessiGroupMember> ressiGroupMemberCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private Collection<ChannelMembers> channelMembersCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private Collection<EventLog> eventLogCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private Collection<GroupMembers> groupMembersCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private Collection<Message> messageCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private Collection<UserMessageCount> userMessageCountCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private Collection<LocationLog> locationLogCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private Collection<Channel> channelCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private Collection<Friend> friendCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user1")
    private Collection<Friend> friendCollection1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private Collection<Activity> activityCollection;

    public User() {
    }

    public User(Integer id) {
        this.id = id;
    }

    public User(Integer id, Date whenCreated) {
        this.id = id;
        this.whenCreated = whenCreated;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Date getWhenCreated() {
        return whenCreated;
    }

    public void setWhenCreated(Date whenCreated) {
        this.whenCreated = whenCreated;
    }

    public Date getTimeOfBirth() {
        return timeOfBirth;
    }

    public void setTimeOfBirth(Date timeOfBirth) {
        this.timeOfBirth = timeOfBirth;
    }

    public Collection<UserGroup> getUserGroupCollection() {
        return userGroupCollection;
    }

    public void setUserGroupCollection(Collection<UserGroup> userGroupCollection) {
        this.userGroupCollection = userGroupCollection;
    }

    public Collection<RessiGroupMember> getRessiGroupMemberCollection() {
        return ressiGroupMemberCollection;
    }

    public void setRessiGroupMemberCollection(Collection<RessiGroupMember> ressiGroupMemberCollection) {
        this.ressiGroupMemberCollection = ressiGroupMemberCollection;
    }

    public Collection<ChannelMembers> getChannelMembersCollection() {
        return channelMembersCollection;
    }

    public void setChannelMembersCollection(Collection<ChannelMembers> channelMembersCollection) {
        this.channelMembersCollection = channelMembersCollection;
    }

    public Collection<EventLog> getEventLogCollection() {
        return eventLogCollection;
    }

    public void setEventLogCollection(Collection<EventLog> eventLogCollection) {
        this.eventLogCollection = eventLogCollection;
    }

    public Collection<GroupMembers> getGroupMembersCollection() {
        return groupMembersCollection;
    }

    public void setGroupMembersCollection(Collection<GroupMembers> groupMembersCollection) {
        this.groupMembersCollection = groupMembersCollection;
    }

    public Collection<Message> getMessageCollection() {
        return messageCollection;
    }

    public void setMessageCollection(Collection<Message> messageCollection) {
        this.messageCollection = messageCollection;
    }

    public Collection<UserMessageCount> getUserMessageCountCollection() {
        return userMessageCountCollection;
    }

    public void setUserMessageCountCollection(Collection<UserMessageCount> userMessageCountCollection) {
        this.userMessageCountCollection = userMessageCountCollection;
    }

    public Collection<LocationLog> getLocationLogCollection() {
        return locationLogCollection;
    }

    public void setLocationLogCollection(Collection<LocationLog> locationLogCollection) {
        this.locationLogCollection = locationLogCollection;
    }

    public Collection<Channel> getChannelCollection() {
        return channelCollection;
    }

    public void setChannelCollection(Collection<Channel> channelCollection) {
        this.channelCollection = channelCollection;
    }

    public Collection<Friend> getFriendCollection() {
        return friendCollection;
    }

    public void setFriendCollection(Collection<Friend> friendCollection) {
        this.friendCollection = friendCollection;
    }

    public Collection<Friend> getFriendCollection1() {
        return friendCollection1;
    }

    public void setFriendCollection1(Collection<Friend> friendCollection1) {
        this.friendCollection1 = friendCollection1;
    }

    public Collection<Activity> getActivityCollection() {
        return activityCollection;
    }

    public void setActivityCollection(Collection<Activity> activityCollection) {
        this.activityCollection = activityCollection;
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
        if (!(object instanceof User)) {
            return false;
        }
        User other = (User) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "servicemonitor.entity.User[id=" + id + "]";
    }

}
