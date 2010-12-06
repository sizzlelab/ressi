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
import javax.persistence.Lob;
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
@Table(name = "cos_events")
@NamedQueries({
    @NamedQuery(name = "CosEvents.findAll", query = "SELECT c FROM CosEvents c"),
    @NamedQuery(name = "CosEvents.findById", query = "SELECT c FROM CosEvents c WHERE c.id = :id"),
    @NamedQuery(name = "CosEvents.findByUserId", query = "SELECT c FROM CosEvents c WHERE c.userId = :userId"),
    @NamedQuery(name = "CosEvents.findByApplicationId", query = "SELECT c FROM CosEvents c WHERE c.applicationId = :applicationId"),
    @NamedQuery(name = "CosEvents.findByCosSessionId", query = "SELECT c FROM CosEvents c WHERE c.cosSessionId = :cosSessionId"),
    @NamedQuery(name = "CosEvents.findByIpAddress", query = "SELECT c FROM CosEvents c WHERE c.ipAddress = :ipAddress"),
    @NamedQuery(name = "CosEvents.findByAction", query = "SELECT c FROM CosEvents c WHERE c.action = :action"),
    @NamedQuery(name = "CosEvents.findByCreatedAt", query = "SELECT c FROM CosEvents c WHERE c.createdAt = :createdAt"),
    @NamedQuery(name = "CosEvents.findByUpdatedAt", query = "SELECT c FROM CosEvents c WHERE c.updatedAt = :updatedAt"),
    @NamedQuery(name = "CosEvents.findByReturnValue", query = "SELECT c FROM CosEvents c WHERE c.returnValue = :returnValue"),
    @NamedQuery(name = "CosEvents.findByHttpUserAgent", query = "SELECT c FROM CosEvents c WHERE c.httpUserAgent = :httpUserAgent"),
    @NamedQuery(name = "CosEvents.findByRequestUri", query = "SELECT c FROM CosEvents c WHERE c.requestUri = :requestUri"),
    @NamedQuery(name = "CosEvents.findByHttpReferer", query = "SELECT c FROM CosEvents c WHERE c.httpReferer = :httpReferer"),
    @NamedQuery(name = "CosEvents.findBySemanticEventId", query = "SELECT c FROM CosEvents c WHERE c.semanticEventId = :semanticEventId")})
public class CosEvents implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "user_id")
    private String userId;
    @Column(name = "application_id")
    private String applicationId;
    @Column(name = "cos_session_id")
    private String cosSessionId;
    @Column(name = "ip_address")
    private String ipAddress;
    @Column(name = "action")
    private String action;
    @Lob
    @Column(name = "parameters")
    private String parameters;
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
    @Column(name = "return_value")
    private String returnValue;
    @Column(name = "http_user_agent")
    private String httpUserAgent;
    @Column(name = "request_uri")
    private String requestUri;
    @Column(name = "http_referer")
    private String httpReferer;
    @Column(name = "semantic_event_id")
    private String semanticEventId;

    public CosEvents() {
    }

    public CosEvents(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    public String getCosSessionId() {
        return cosSessionId;
    }

    public void setCosSessionId(String cosSessionId) {
        this.cosSessionId = cosSessionId;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getParameters() {
        return parameters;
    }

    public void setParameters(String parameters) {
        this.parameters = parameters;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getReturnValue() {
        return returnValue;
    }

    public void setReturnValue(String returnValue) {
        this.returnValue = returnValue;
    }

    public String getHttpUserAgent() {
        return httpUserAgent;
    }

    public void setHttpUserAgent(String httpUserAgent) {
        this.httpUserAgent = httpUserAgent;
    }

    public String getRequestUri() {
        return requestUri;
    }

    public void setRequestUri(String requestUri) {
        this.requestUri = requestUri;
    }

    public String getHttpReferer() {
        return httpReferer;
    }

    public void setHttpReferer(String httpReferer) {
        this.httpReferer = httpReferer;
    }

    public String getSemanticEventId() {
        return semanticEventId;
    }

    public void setSemanticEventId(String semanticEventId) {
        this.semanticEventId = semanticEventId;
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
        if (!(object instanceof CosEvents)) {
            return false;
        }
        CosEvents other = (CosEvents) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "servicemonitor.entity.CosEvents[id=" + id + "]";
    }

}
