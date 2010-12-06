/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package servicemonitor.model;

import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;

/**
 *
 * @author ktuomain
 */
public class MetalogRow {
    private Date date;
    private int publicChannelCount, privateChannelCount;

    private Collection<UserDistribution> userDistributions = new LinkedList();

    public void setUserDistributions(Collection<UserDistribution> users) {
        this.userDistributions = users;
    }

    public Collection<UserDistribution> getUserDistributions() {
        return userDistributions;
    }


    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getPrivateChannelCount() {
        return privateChannelCount;
    }

    public void setPrivateChannelCount(int privateChannelCount) {
        this.privateChannelCount = privateChannelCount;
    }

    public int getPublicChannelCount() {
        return publicChannelCount;
    }

    public void setPublicChannelCount(int publicChannelCount) {
        this.publicChannelCount = publicChannelCount;
    }

    public int getChannelCount() {
        return getPublicChannelCount() + getPrivateChannelCount();
    }

    public int getUserCount() {
        return userDistributions.size();
    }
    
}

