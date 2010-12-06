/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package servicemonitor.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Date;
import java.util.Set;

/**
 *
 * @author kmtuomai
 */
public class User implements Serializable {
    private String id = "";

    private Sex sex;

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    String name;
    Set<User> friends = new HashSet();

    public User(String name) {
        this.name = name;
    }

    public Set<User> getFriends() {
        return friends;
    }

    public void setFriends(Set<User> friends) {
        this.friends = friends;
    }

    public void setFriends(User... friends) {
        this.friends = new HashSet(Arrays.asList(friends));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    Date timeOfBirth = new Date();

    public Date getTimeOfBirth() {
        return timeOfBirth;
    }

    public void setTimeOfBirth(Date timeOfBirth) {
        this.timeOfBirth = timeOfBirth;
    }

}
