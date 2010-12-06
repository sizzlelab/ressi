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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author ktuomain
 */
@Entity
@Table(name = "event_annotation")
@NamedQueries({
    @NamedQuery(name = "EventAnnotation.findAll", query = "SELECT e FROM EventAnnotation e"),
    @NamedQuery(name = "EventAnnotation.findById", query = "SELECT e FROM EventAnnotation e WHERE e.id = :id"),
    @NamedQuery(name = "EventAnnotation.findByDescription", query = "SELECT e FROM EventAnnotation e WHERE e.description = :description")})
public class EventAnnotation implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @Column(name = "description")
    private String description;
    @JoinColumn(name = "id", referencedColumnName = "id", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private EventLog eventLog;

    public EventAnnotation() {
    }

    public EventAnnotation(Long id) {
        this.id = id;
    }

    public EventAnnotation(Long id, String description) {
        this.id = id;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public EventLog getEventLog() {
        return eventLog;
    }

    public void setEventLog(EventLog eventLog) {
        this.eventLog = eventLog;
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
        if (!(object instanceof EventAnnotation)) {
            return false;
        }
        EventAnnotation other = (EventAnnotation) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "servicemonitor.entity.EventAnnotation[id=" + id + "]";
    }

}
