/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.polimi.guessbid.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Mirjam
 */
@Entity
@Table(name = "notification")
@XmlRootElement
@NamedStoredProcedureQuery(
        name = "GET_USER_AUCTION_RANKING",
        procedureName = "get_distinct_user_auction_ranking",
        parameters = {
            @StoredProcedureParameter(mode = ParameterMode.IN, name = "arg_user_id", type = Integer.class),
            @StoredProcedureParameter(mode = ParameterMode.IN, name = "arg_auction_id", type = Integer.class)
        })
@NamedQueries({
    @NamedQuery(name = "Notification.findAll", query = "SELECT n FROM Notification n"),
    @NamedQuery(name = "Notification.findByNotificationId", query = "SELECT n FROM Notification n WHERE n.notificationId = :notificationId"),
    @NamedQuery(name = "Notification.findByIsOutcome", query = "SELECT n FROM Notification n WHERE n.isOutcome = :isOutcome"),
    @NamedQuery(name = "Notification.findByTimestamp", query = "SELECT n FROM Notification n WHERE n.timestamp = :timestamp"),
    @NamedQuery(name = "Notification.findUserNewestRankNotif", query = "SELECT n.rank FROM Notification n WHERE n.nAuctionId= :auction and n.nUserId = :user and n.isOutcome = false ORDER BY n.timestamp DESC"),
    @NamedQuery(name = "Notification.findByRank", query = "SELECT n FROM Notification n WHERE n.rank = :rank")})
public class Notification implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "notification_id")
    private Integer notificationId;
    @Lob
    @Size(max = 16777215)
    @Column(name = "description")
    private String description;
    @Basic(optional = false)
    @NotNull
    @Column(name = "is_outcome")
    private boolean isOutcome;
    @Basic(optional = false)
    @Column(name = "timestamp")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;
    @Basic(optional = false)
    @NotNull
    @Column(name = "rank")
    private int rank;
    @JoinColumn(name = "n_auction_id", referencedColumnName = "auction_id")
    @ManyToOne(optional = false)
    private Auction nAuctionId;
    @JoinColumn(name = "n_user_id", referencedColumnName = "user_id")
    @ManyToOne(optional = false)
    private User nUserId;

    public Notification() {
    }

    public Notification(Integer notificationId) {
        this.notificationId = notificationId;
    }

    public Notification(Integer notificationId, boolean isOutcome, Date timestamp, int rank) {
        this.notificationId = notificationId;
        this.isOutcome = isOutcome;
        this.timestamp = timestamp;
        this.rank = rank;
    }

    public Integer getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(Integer notificationId) {
        this.notificationId = notificationId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean getIsOutcome() {
        return isOutcome;
    }

    public void setIsOutcome(boolean isOutcome) {
        this.isOutcome = isOutcome;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public Auction getNAuctionId() {
        return nAuctionId;
    }

    public void setNAuctionId(Auction nAuctionId) {
        this.nAuctionId = nAuctionId;
    }

    public User getNUserId() {
        return nUserId;
    }

    public void setNUserId(User nUserId) {
        this.nUserId = nUserId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (notificationId != null ? notificationId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Notification)) {
            return false;
        }
        Notification other = (Notification) object;
        if ((this.notificationId == null && other.notificationId != null) || (this.notificationId != null && !this.notificationId.equals(other.notificationId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "it.polimi.registration.business.security.entity.Notification[ notificationId=" + notificationId + " ]";
    }

}
