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
@Table(name = "user_session")
@NamedQueries({
    @NamedQuery(name = "UserSession.findAll", query = "SELECT u FROM UserSession u"),
    @NamedQuery(name = "UserSession.findById", query = "SELECT u FROM UserSession u WHERE u.id = :id"),
    @NamedQuery(name = "UserSession.findByCosSessionId", query = "SELECT u FROM UserSession u WHERE u.cosSessionId = :cosSessionId")})
public class UserSession implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Column(name = "cos_session_id")
    private String cosSessionId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userSession")
    private Collection<EventLog> eventLogCollection;

    public UserSession() {
    }

    public UserSession(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCosSessionId() {
        return cosSessionId;
    }

    public void setCosSessionId(String cosSessionId) {
        this.cosSessionId = cosSessionId;
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
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UserSession)) {
            return false;
        }
        UserSession other = (UserSession) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "servicemonitor.entity.UserSession[id=" + id + "]";
    }

}
