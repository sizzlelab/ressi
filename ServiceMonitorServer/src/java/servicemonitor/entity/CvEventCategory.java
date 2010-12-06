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
@Table(name = "cv_event_category")
@NamedQueries({
    @NamedQuery(name = "CvEventCategory.findAll", query = "SELECT c FROM CvEventCategory c"),
    @NamedQuery(name = "CvEventCategory.findByEventCategoryId", query = "SELECT c FROM CvEventCategory c WHERE c.eventCategoryId = :eventCategoryId"),
    @NamedQuery(name = "CvEventCategory.findByEventCategory", query = "SELECT c FROM CvEventCategory c WHERE c.eventCategory = :eventCategory")})
public class CvEventCategory implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "event_category_id")
    private Integer eventCategoryId;
    @Column(name = "event_category")
    private String eventCategory;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cvEventCategory")
    private Collection<EventLog> eventLogCollection;

    public CvEventCategory() {
    }

    public CvEventCategory(Integer eventCategoryId) {
        this.eventCategoryId = eventCategoryId;
    }

    public Integer getEventCategoryId() {
        return eventCategoryId;
    }

    public void setEventCategoryId(Integer eventCategoryId) {
        this.eventCategoryId = eventCategoryId;
    }

    public String getEventCategory() {
        return eventCategory;
    }

    public void setEventCategory(String eventCategory) {
        this.eventCategory = eventCategory;
    }

    public Collection<EventLog> getEventLogCollection() {
        return eventLogCollection;
    }

    public void setEventLogCollection(Collection<EventLog> eventLogCollection) {
        this.eventLogCollection = eventLogCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (eventCategoryId != null ? eventCategoryId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CvEventCategory)) {
            return false;
        }
        CvEventCategory other = (CvEventCategory) object;
        if ((this.eventCategoryId == null && other.eventCategoryId != null) || (this.eventCategoryId != null && !this.eventCategoryId.equals(other.eventCategoryId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "servicemonitor.entity.CvEventCategory[eventCategoryId=" + eventCategoryId + "]";
    }

}
