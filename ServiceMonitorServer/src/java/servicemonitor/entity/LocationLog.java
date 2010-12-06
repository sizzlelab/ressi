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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "location_log")
@NamedQueries({
    @NamedQuery(name = "LocationLog.findAll", query = "SELECT l FROM LocationLog l"),
    @NamedQuery(name = "LocationLog.findByLocationId", query = "SELECT l FROM LocationLog l WHERE l.locationId = :locationId"),
    @NamedQuery(name = "LocationLog.findByWhenCreated", query = "SELECT l FROM LocationLog l WHERE l.whenCreated = :whenCreated"),
    @NamedQuery(name = "LocationLog.findByLatitude", query = "SELECT l FROM LocationLog l WHERE l.latitude = :latitude"),
    @NamedQuery(name = "LocationLog.findByLongitude", query = "SELECT l FROM LocationLog l WHERE l.longitude = :longitude"),
    @NamedQuery(name = "LocationLog.findByAltitude", query = "SELECT l FROM LocationLog l WHERE l.altitude = :altitude")})
public class LocationLog implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "location_id")
    private Long locationId;
    @Basic(optional = false)
    @Column(name = "when_created")
    @Temporal(TemporalType.TIMESTAMP)
    private Date whenCreated;
    @Basic(optional = false)
    @Column(name = "latitude")
    private double latitude;
    @Basic(optional = false)
    @Column(name = "longitude")
    private double longitude;
    @Column(name = "altitude")
    private Double altitude;
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private User user;

    public LocationLog() {
    }

    public LocationLog(Long locationId) {
        this.locationId = locationId;
    }

    public LocationLog(Long locationId, Date whenCreated, double latitude, double longitude) {
        this.locationId = locationId;
        this.whenCreated = whenCreated;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }

    public Date getWhenCreated() {
        return whenCreated;
    }

    public void setWhenCreated(Date whenCreated) {
        this.whenCreated = whenCreated;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public Double getAltitude() {
        return altitude;
    }

    public void setAltitude(Double altitude) {
        this.altitude = altitude;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (locationId != null ? locationId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LocationLog)) {
            return false;
        }
        LocationLog other = (LocationLog) object;
        if ((this.locationId == null && other.locationId != null) || (this.locationId != null && !this.locationId.equals(other.locationId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "servicemonitor.entity.LocationLog[locationId=" + locationId + "]";
    }

}
