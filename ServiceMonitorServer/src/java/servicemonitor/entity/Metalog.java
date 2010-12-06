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
@Table(name = "metalog")
@NamedQueries({
    @NamedQuery(name = "Metalog.findAll", query = "SELECT m FROM Metalog m"),
    @NamedQuery(name = "Metalog.findById", query = "SELECT m FROM Metalog m WHERE m.id = :id"),
    @NamedQuery(name = "Metalog.findByCreatedAt", query = "SELECT m FROM Metalog m WHERE m.createdAt = :createdAt"),
    @NamedQuery(name = "Metalog.findByMaleCount", query = "SELECT m FROM Metalog m WHERE m.maleCount = :maleCount"),
    @NamedQuery(name = "Metalog.findByFemaleCount", query = "SELECT m FROM Metalog m WHERE m.femaleCount = :femaleCount"),
    @NamedQuery(name = "Metalog.findByUnknownSexCount", query = "SELECT m FROM Metalog m WHERE m.unknownSexCount = :unknownSexCount"),
    @NamedQuery(name = "Metalog.findByPrivateChannelCount", query = "SELECT m FROM Metalog m WHERE m.privateChannelCount = :privateChannelCount"),
    @NamedQuery(name = "Metalog.findByPublicChannelCount", query = "SELECT m FROM Metalog m WHERE m.publicChannelCount = :publicChannelCount"),
    @NamedQuery(name = "Metalog.findByOpenGroupCount", query = "SELECT m FROM Metalog m WHERE m.openGroupCount = :openGroupCount"),
    @NamedQuery(name = "Metalog.findByClosedGroupCount", query = "SELECT m FROM Metalog m WHERE m.closedGroupCount = :closedGroupCount")})
public class Metalog implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @Basic(optional = false)
    @Column(name = "male_count")
    private int maleCount;
    @Basic(optional = false)
    @Column(name = "female_count")
    private int femaleCount;
    @Basic(optional = false)
    @Column(name = "unknown_sex_count")
    private int unknownSexCount;
    @Basic(optional = false)
    @Column(name = "private_channel_count")
    private int privateChannelCount;
    @Basic(optional = false)
    @Column(name = "public_channel_count")
    private int publicChannelCount;
    @Basic(optional = false)
    @Column(name = "open_group_count")
    private int openGroupCount;
    @Basic(optional = false)
    @Column(name = "closed_group_count")
    private int closedGroupCount;

    public Metalog() {
    }

    public Metalog(Integer id) {
        this.id = id;
    }

    public Metalog(Integer id, Date createdAt, int maleCount, int femaleCount, int unknownSexCount, int privateChannelCount, int publicChannelCount, int openGroupCount, int closedGroupCount) {
        this.id = id;
        this.createdAt = createdAt;
        this.maleCount = maleCount;
        this.femaleCount = femaleCount;
        this.unknownSexCount = unknownSexCount;
        this.privateChannelCount = privateChannelCount;
        this.publicChannelCount = publicChannelCount;
        this.openGroupCount = openGroupCount;
        this.closedGroupCount = closedGroupCount;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public int getMaleCount() {
        return maleCount;
    }

    public void setMaleCount(int maleCount) {
        this.maleCount = maleCount;
    }

    public int getFemaleCount() {
        return femaleCount;
    }

    public void setFemaleCount(int femaleCount) {
        this.femaleCount = femaleCount;
    }

    public int getUnknownSexCount() {
        return unknownSexCount;
    }

    public void setUnknownSexCount(int unknownSexCount) {
        this.unknownSexCount = unknownSexCount;
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

    public int getOpenGroupCount() {
        return openGroupCount;
    }

    public void setOpenGroupCount(int openGroupCount) {
        this.openGroupCount = openGroupCount;
    }

    public int getClosedGroupCount() {
        return closedGroupCount;
    }

    public void setClosedGroupCount(int closedGroupCount) {
        this.closedGroupCount = closedGroupCount;
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
        if (!(object instanceof Metalog)) {
            return false;
        }
        Metalog other = (Metalog) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "servicemonitor.entity.Metalog[id=" + id + "]";
    }

}
