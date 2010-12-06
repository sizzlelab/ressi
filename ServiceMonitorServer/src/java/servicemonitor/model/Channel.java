/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package servicemonitor.model;

import java.util.Date;

/**
 *
 * @author ktuomain
 */
public class Channel {
    private String name;
    private ChannelType channel_type;
    private Date created_at, updated_at;
    private String id;
    private long message_count;
    private String owner_name;
    private String owner_id;

    public ChannelType getChannel_type() {
        return channel_type;
    }

    public void setChannel_type(ChannelType channel_type) {
        this.channel_type = channel_type;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getMessage_count() {
        return message_count;
    }

    public void setMessage_count(long message_count) {
        this.message_count = message_count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwner_id() {
        return owner_id;
    }

    public void setOwner_id(String owner_id) {
        this.owner_id = owner_id;
    }

    public String getOwner_name() {
        return owner_name;
    }

    public void setOwner_name(String owner_name) {
        this.owner_name = owner_name;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }

    public String toString() {
        return "Channel: " + getName();
    }
}

