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
@Table(name = "cv_event_name")
@NamedQueries({
    @NamedQuery(name = "CvEventName.findAll", query = "SELECT c FROM CvEventName c"),
    @NamedQuery(name = "CvEventName.findByEventNameId", query = "SELECT c FROM CvEventName c WHERE c.eventNameId = :eventNameId"),
    @NamedQuery(name = "CvEventName.findByEventName", query = "SELECT c FROM CvEventName c WHERE c.eventName = :eventName")})
public class CvEventName implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "event_name_id")
    private Integer eventNameId;
    @Column(name = "event_name")
    private String eventName;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cvEventName")
    private Collection<EventLog> eventLogCollection;

    public CvEventName() {
    }

    public CvEventName(Integer eventNameId) {
        this.eventNameId = eventNameId;
    }

    public Integer getEventNameId() {
        return eventNameId;
    }

    public void setEventNameId(Integer eventNameId) {
        this.eventNameId = eventNameId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
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
        hash += (eventNameId != null ? eventNameId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CvEventName)) {
            return false;
        }
        CvEventName other = (CvEventName) object;
        if ((this.eventNameId == null && other.eventNameId != null) || (this.eventNameId != null && !this.eventNameId.equals(other.eventNameId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "servicemonitor.entity.CvEventName[eventNameId=" + eventNameId + "]";
    }

}
