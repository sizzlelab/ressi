/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package servicemonitor.entity;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author ktuomain
 */
@Entity
@Table(name = "event_log")
@NamedQueries({
    @NamedQuery(name = "EventLog.findAll", query = "SELECT e FROM EventLog e"),
    @NamedQuery(name = "EventLog.findById", query = "SELECT e FROM EventLog e WHERE e.id = :id"),
    @NamedQuery(name = "EventLog.findByTransactionId", query = "SELECT e FROM EventLog e WHERE e.transactionId = :transactionId"),
    @NamedQuery(name = "EventLog.findByParentEvent", query = "SELECT e FROM EventLog e WHERE e.parentEvent = :parentEvent"),
    @NamedQuery(name = "EventLog.findByEventParameters", query = "SELECT e FROM EventLog e WHERE e.eventParameters = :eventParameters"),
    @NamedQuery(name = "EventLog.findByWhenCreated", query = "SELECT e FROM EventLog e WHERE e.whenCreated = :whenCreated")})
public class EventLog implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @Column(name = "transaction_id")
    private long transactionId;
    @Column(name = "parent_event")
    private BigInteger parentEvent;
    @Column(name = "event_parameters")
    private String eventParameters;
    @Basic(optional = false)
    @Column(name = "when_created")
    @Temporal(TemporalType.TIMESTAMP)
    private Date whenCreated;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "eventLog")
    private EventAnnotation eventAnnotation;
    @JoinColumn(name = "event_name", referencedColumnName = "event_name_id")
    @ManyToOne(optional = false)
    private CvEventName cvEventName;
    @JoinColumn(name = "event_category", referencedColumnName = "event_category_id")
    @ManyToOne(optional = false)
    private CvEventCategory cvEventCategory;
    @JoinColumn(name = "application_id", referencedColumnName = "application_id")
    @ManyToOne(optional = false)
    private CvApplication cvApplication;
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private User user;
    @JoinColumn(name = "session_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private UserSession userSession;

    public EventLog() {
    }

    public EventLog(Long id) {
        this.id = id;
    }

    public EventLog(Long id, long transactionId, Date whenCreated) {
        this.id = id;
        this.transactionId = transactionId;
        this.whenCreated = whenCreated;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(long transactionId) {
        this.transactionId = transactionId;
    }

    public BigInteger getParentEvent() {
        return parentEvent;
    }

    public void setParentEvent(BigInteger parentEvent) {
        this.parentEvent = parentEvent;
    }

    public String getEventParameters() {
        return eventParameters;
    }

    public void setEventParameters(String eventParameters) {
        this.eventParameters = eventParameters;
    }

    public Date getWhenCreated() {
        return whenCreated;
    }

    public void setWhenCreated(Date whenCreated) {
        this.whenCreated = whenCreated;
    }

    public EventAnnotation getEventAnnotation() {
        return eventAnnotation;
    }

    public void setEventAnnotation(EventAnnotation eventAnnotation) {
        this.eventAnnotation = eventAnnotation;
    }

    public CvEventName getCvEventName() {
        return cvEventName;
    }

    public void setCvEventName(CvEventName cvEventName) {
        this.cvEventName = cvEventName;
    }

    public CvEventCategory getCvEventCategory() {
        return cvEventCategory;
    }

    public void setCvEventCategory(CvEventCategory cvEventCategory) {
        this.cvEventCategory = cvEventCategory;
    }

    public CvApplication getCvApplication() {
        return cvApplication;
    }

    public void setCvApplication(CvApplication cvApplication) {
        this.cvApplication = cvApplication;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public UserSession getUserSession() {
        return userSession;
    }

    public void setUserSession(UserSession userSession) {
        this.userSession = userSession;
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
        if (!(object instanceof EventLog)) {
            return false;
        }
        EventLog other = (EventLog) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "servicemonitor.entity.EventLog[id=" + id + "]";
    }

}
