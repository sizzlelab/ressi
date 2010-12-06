/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package servicemonitor.entity;

import java.io.Serializable;
import java.util.Collection;
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

/**
 *
 * @author ktuomain
 */
@Entity
@Table(name = "ressi_group")
@NamedQueries({
    @NamedQuery(name = "RessiGroup.findAll", query = "SELECT r FROM RessiGroup r"),
    @NamedQuery(name = "RessiGroup.findById", query = "SELECT r FROM RessiGroup r WHERE r.id = :id"),
    @NamedQuery(name = "RessiGroup.findByName", query = "SELECT r FROM RessiGroup r WHERE r.name = :name")})
public class RessiGroup implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ressiGroup")
    private Collection<RessiGroupMember> ressiGroupMemberCollection;

    public RessiGroup() {
    }

    public RessiGroup(Long id) {
        this.id = id;
    }

    public RessiGroup(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Collection<RessiGroupMember> getRessiGroupMemberCollection() {
        return ressiGroupMemberCollection;
    }

    public void setRessiGroupMemberCollection(Collection<RessiGroupMember> ressiGroupMemberCollection) {
        this.ressiGroupMemberCollection = ressiGroupMemberCollection;
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
        if (!(object instanceof RessiGroup)) {
            return false;
        }
        RessiGroup other = (RessiGroup) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "servicemonitor.entity.RessiGroup[id=" + id + "]";
    }

}
