/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package servicemonitor.entity;

import java.io.Serializable;
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

/**
 *
 * @author ktuomain
 */
@Entity
@Table(name = "ressi_group_member")
@NamedQueries({
    @NamedQuery(name = "RessiGroupMember.findAll", query = "SELECT r FROM RessiGroupMember r"),
    @NamedQuery(name = "RessiGroupMember.findById", query = "SELECT r FROM RessiGroupMember r WHERE r.id = :id")})
public class RessiGroupMember implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private User user;
    @JoinColumn(name = "group_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private RessiGroup ressiGroup;

    public RessiGroupMember() {
    }

    public RessiGroupMember(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public RessiGroup getRessiGroup() {
        return ressiGroup;
    }

    public void setRessiGroup(RessiGroup ressiGroup) {
        this.ressiGroup = ressiGroup;
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
        if (!(object instanceof RessiGroupMember)) {
            return false;
        }
        RessiGroupMember other = (RessiGroupMember) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "servicemonitor.entity.RessiGroupMember[id=" + id + "]";
    }

}
