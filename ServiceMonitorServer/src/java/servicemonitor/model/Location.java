/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package servicemonitor.model;

import java.util.Date;

/**
 *
 * @author ktuomain
 */
public class Location {
    private Date updatedAt = null;
    private Double longitude = null;
    private String label = null;
    private Double latitude = null;
    private Double accuracy = null;

    public String toString() {
        return
            "[updatedAt: " + updatedAt + ", " +
            "longitude: " + longitude + ", " +
            "label; " + label + ", " +
            "latitude: " + latitude + ", " +
            "accuracy: " + accuracy + "]";
    }

    public boolean isOK() {
        return (latitude != null) && (longitude != null);
    }

    public Double getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(Double accuracy) {
        this.accuracy = accuracy;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Date getUpdatedAt() {
        //TODO: FIXME
        //return (updatedAt != null) ? updatedAt : "n/a";
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
