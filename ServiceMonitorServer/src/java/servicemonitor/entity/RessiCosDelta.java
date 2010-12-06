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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author ktuomain
 */
@Entity
@Table(name = "ressi_cos_delta")
@NamedQueries({
    @NamedQuery(name = "RessiCosDelta.findAll", query = "SELECT r FROM RessiCosDelta r"),
    @NamedQuery(name = "RessiCosDelta.findById", query = "SELECT r FROM RessiCosDelta r WHERE r.id = :id"),
    @NamedQuery(name = "RessiCosDelta.findByPrevMaxId", query = "SELECT r FROM RessiCosDelta r WHERE r.prevMaxId = :prevMaxId")})
public class RessiCosDelta implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @Column(name = "prev_max_id")
    private long prevMaxId;

    public RessiCosDelta() {
    }

    public RessiCosDelta(Long id) {
        this.id = id;
    }

    public RessiCosDelta(Long id, long prevMaxId) {
        this.id = id;
        this.prevMaxId = prevMaxId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getPrevMaxId() {
        return prevMaxId;
    }

    public void setPrevMaxId(long prevMaxId) {
        this.prevMaxId = prevMaxId;
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
        if (!(object instanceof RessiCosDelta)) {
            return false;
        }
        RessiCosDelta other = (RessiCosDelta) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "servicemonitor.entity.RessiCosDelta[id=" + id + "]";
    }

}
