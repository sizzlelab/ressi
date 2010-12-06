/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package servicemonitor.model;

import java.util.Collection;
import java.util.Date;

/**
 *
 * @author ktuomain
 */
public class AggregatelogRow {
    private Collection<User> friends;
    private int publicChannelsCreateCount;
    private int privateChannelsCreateCount;
    private int publicChannelsVisibleCount;
    private int privateChannelsVisibleCount;
    private int messageWrittenCount;
    private int messageReadCount;
    private Sex sex;
    private Date birthDate;
    private Date timestamp;

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public Collection<User> getFriends() {
        return friends;
    }

    public void setFriends(Collection<User> friends) {
        this.friends = friends;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }
    
    public int getMessageReadCount() {
        return messageReadCount;
    }

    public void setMessageReadCount(int messageReadCount) {
        this.messageReadCount = messageReadCount;
    }

    public int getMessageWrittenCount() {
        return messageWrittenCount;
    }

    public void setMessageWrittenCount(int messageWrittenCount) {
        this.messageWrittenCount = messageWrittenCount;
    }

    public int getPrivateChannelsCreateCount() {
        return privateChannelsCreateCount;
    }

    public void setPrivateChannelsCreateCount(int privateChannelsCreateCount) {
        this.privateChannelsCreateCount = privateChannelsCreateCount;
    }

    public int getPrivateChannelsVisibleCount() {
        return privateChannelsVisibleCount;
    }

    public void setPrivateChannelsVisibleCount(int privateChannelsVisibleCount) {
        this.privateChannelsVisibleCount = privateChannelsVisibleCount;
    }

    public int getPublicChannelsCreateCount() {
        return publicChannelsCreateCount;
    }

    public void setPublicChannelsCreateCount(int publicChannelsCreateCount) {
        this.publicChannelsCreateCount = publicChannelsCreateCount;
    }

    public int getPublicChannelsVisibleCount() {
        return publicChannelsVisibleCount;
    }

    public void setPublicChannelsVisibleCount(int publicChannelsVisibleCount) {
        this.publicChannelsVisibleCount = publicChannelsVisibleCount;
    }

    public int getFriendCount() {
        return friends.size();
    }

}

