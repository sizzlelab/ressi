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
@Table(name = "service_events")
@NamedQueries({
    @NamedQuery(name = "ServiceEvents.findAll", query = "SELECT s FROM ServiceEvents s"),
    @NamedQuery(name = "ServiceEvents.findById", query = "SELECT s FROM ServiceEvents s WHERE s.id = :id"),
    @NamedQuery(name = "ServiceEvents.findByUserId", query = "SELECT s FROM ServiceEvents s WHERE s.userId = :userId"),
    @NamedQuery(name = "ServiceEvents.findByApplicationId", query = "SELECT s FROM ServiceEvents s WHERE s.applicationId = :applicationId"),
    @NamedQuery(name = "ServiceEvents.findBySessionId", query = "SELECT s FROM ServiceEvents s WHERE s.sessionId = :sessionId"),
    @NamedQuery(name = "ServiceEvents.findByIpAddress", query = "SELECT s FROM ServiceEvents s WHERE s.ipAddress = :ipAddress"),
    @NamedQuery(name = "ServiceEvents.findByAction", query = "SELECT s FROM ServiceEvents s WHERE s.action = :action"),
    @NamedQuery(name = "ServiceEvents.findByReturnValue", query = "SELECT s FROM ServiceEvents s WHERE s.returnValue = :returnValue"),
    @NamedQuery(name = "ServiceEvents.findByHttpUserAgent", query = "SELECT s FROM ServiceEvents s WHERE s.httpUserAgent = :httpUserAgent"),
    @NamedQuery(name = "ServiceEvents.findByRequestUri", query = "SELECT s FROM ServiceEvents s WHERE s.requestUri = :requestUri"),
    @NamedQuery(name = "ServiceEvents.findByHttpReferer", query = "SELECT s FROM ServiceEvents s WHERE s.httpReferer = :httpReferer"),
    @NamedQuery(name = "ServiceEvents.findBySemanticEventId", query = "SELECT s FROM ServiceEvents s WHERE s.semanticEventId = :semanticEventId"),
    @NamedQuery(name = "ServiceEvents.findByCreatedAt", query = "SELECT s FROM ServiceEvents s WHERE s.createdAt = :createdAt"),
    @NamedQuery(name = "ServiceEvents.findByUpdatedAt", query = "SELECT s FROM ServiceEvents s WHERE s.updatedAt = :updatedAt")})
public class ServiceEvents implements Serializable {
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
    @Column(name = "session_id")
    private String sessionId;
    @Column(name = "ip_address")
    private String ipAddress;
    @Column(name = "action")
    private String action;
    @Lob
    @Column(name = "parameters")
    private String parameters;
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
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    public ServiceEvents() {
    }

    public ServiceEvents(Integer id) {
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

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ServiceEvents)) {
            return false;
        }
        ServiceEvents other = (ServiceEvents) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "servicemonitor.entity.ServiceEvents[id=" + id + "]";
    }

}
