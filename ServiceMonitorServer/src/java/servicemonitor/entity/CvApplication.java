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
@Table(name = "cv_application")
@NamedQueries({
    @NamedQuery(name = "CvApplication.findAll", query = "SELECT c FROM CvApplication c"),
    @NamedQuery(name = "CvApplication.findByApplicationId", query = "SELECT c FROM CvApplication c WHERE c.applicationId = :applicationId"),
    @NamedQuery(name = "CvApplication.findByCosApplicationId", query = "SELECT c FROM CvApplication c WHERE c.cosApplicationId = :cosApplicationId"),
    @NamedQuery(name = "CvApplication.findByName", query = "SELECT c FROM CvApplication c WHERE c.name = :name")})
public class CvApplication implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "application_id")
    private Integer applicationId;
    @Column(name = "cos_application_id")
    private String cosApplicationId;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cvApplication")
    private Collection<EventLog> eventLogCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cvApplication")
    private Collection<Activity> activityCollection;

    public CvApplication() {
    }

    public CvApplication(Integer applicationId) {
        this.applicationId = applicationId;
    }

    public CvApplication(Integer applicationId, String name) {
        this.applicationId = applicationId;
        this.name = name;
    }

    public Integer getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Integer applicationId) {
        this.applicationId = applicationId;
    }

    public String getCosApplicationId() {
        return cosApplicationId;
    }

    public void setCosApplicationId(String cosApplicationId) {
        this.cosApplicationId = cosApplicationId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Collection<EventLog> getEventLogCollection() {
        return eventLogCollection;
    }

    public void setEventLogCollection(Collection<EventLog> eventLogCollection) {
        this.eventLogCollection = eventLogCollection;
    }

    public Collection<Activity> getActivityCollection() {
        return activityCollection;
    }

    public void setActivityCollection(Collection<Activity> activityCollection) {
        this.activityCollection = activityCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (applicationId != null ? applicationId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CvApplication)) {
            return false;
        }
        CvApplication other = (CvApplication) object;
        if ((this.applicationId == null && other.applicationId != null) || (this.applicationId != null && !this.applicationId.equals(other.applicationId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "servicemonitor.entity.CvApplication[applicationId=" + applicationId + "]";
    }

}
