/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.polimi.guessbid.entity;

import java.io.Serializable;
import java.util.Collection;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Mirjam
 */
@Entity
@Table(name = "bid")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Bid.findAll", query = "SELECT b FROM Bid b"),
    @NamedQuery(name = "Bid.countBidsOfAuction", query = "SELECT COUNT(b) FROM Bid b WHERE b.bidAuctionId = :auction "),
    @NamedQuery(name = "Bid.findByBidId", query = "SELECT b FROM Bid b WHERE b.bidId = :bidId"),
    @NamedQuery(name = "Bid.findByAmount", query = "SELECT b FROM Bid b WHERE b.amount = :amount"),
    @NamedQuery(name = "Bid.findBiddersOfAuction", query = "SELECT DISTINCT(b.bidderId) FROM Bid b WHERE b.bidAuctionId = :auctionId"),
    @NamedQuery(name = "Bid.findBiddersOfAuctionExceptOne", query = "SELECT DISTINCT(b.bidderId) FROM Bid b WHERE b.bidAuctionId = :auctionId and b.bidderId != :bidderId "),
    @NamedQuery(name = "Bid.findByTimestamp", query = "SELECT b FROM Bid b WHERE b.timestamp = :timestamp")})
public class Bid implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "bid_id")
    private Integer bidId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "amount")
    private float amount;
    @Basic(optional = false)

    @Column(name = "timestamp")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;
    @JoinColumn(name = "bid_auction_id", referencedColumnName = "auction_id")
    @ManyToOne(optional = false)
    private Auction bidAuctionId;
    @JoinColumn(name = "bidder_id", referencedColumnName = "user_id")
    @ManyToOne(optional = false)
    private User bidderId;
    @OneToMany(mappedBy = "winningBidId")
    private Collection<Auction> auctionCollection;

    public Bid() {
    }

    public Bid(Integer bidId) {
        this.bidId = bidId;
    }

    public Bid(Integer bidId, float amount, Date timestamp) {
        this.bidId = bidId;
        this.amount = amount;
        this.timestamp = timestamp;
    }

    public Integer getBidId() {
        return bidId;
    }

    public void setBidId(Integer bidId) {
        this.bidId = bidId;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public Auction getBidAuctionId() {
        return bidAuctionId;
    }

    public void setBidAuctionId(Auction bidAuctionId) {
        this.bidAuctionId = bidAuctionId;
    }

    public User getBidderId() {
        return bidderId;
    }

    public void setBidderId(User bidderId) {
        this.bidderId = bidderId;
    }

    @XmlTransient
    public Collection<Auction> getAuctionCollection() {
        return auctionCollection;
    }

    public void setAuctionCollection(Collection<Auction> auctionCollection) {
        this.auctionCollection = auctionCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (bidId != null ? bidId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Bid)) {
            return false;
        }
        Bid other = (Bid) object;
        if ((this.bidId == null && other.bidId != null) || (this.bidId != null && !this.bidId.equals(other.bidId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "it.polimi.registration.business.security.entity.Bid[ bidId=" + bidId + " ]";
    }

}
