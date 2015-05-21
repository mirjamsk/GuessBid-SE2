/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.polimi.registration.business.security.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Mirjam
 */
@Entity
@Table(name = "winning_bid")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "WinningBid.findAll", query = "SELECT w FROM WinningBid w"),
    @NamedQuery(name = "WinningBid.findByBidId", query = "SELECT w FROM WinningBid w WHERE w.bidId = :bidId"),
    @NamedQuery(name = "WinningBid.findByBidderId", query = "SELECT w FROM WinningBid w WHERE w.bidderId = :bidderId"),
    @NamedQuery(name = "WinningBid.findByBidAuctionId", query = "SELECT w FROM WinningBid w WHERE w.bidAuctionId = :bidAuctionId"),
    @NamedQuery(name = "WinningBid.findByAmount", query = "SELECT w FROM WinningBid w WHERE w.amount = :amount"),
    @NamedQuery(name = "WinningBid.findByTimestamp", query = "SELECT w FROM WinningBid w WHERE w.timestamp = :timestamp")})
public class WinningBid implements Serializable {
    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Column(name = "bid_id")
    private int bidId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "bidder_id")
    private int bidderId;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "bid_auction_id")
    private int bidAuctionId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "amount")
    private float amount;
    @Basic(optional = false)
    @NotNull
    @Column(name = "timestamp")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;

    public WinningBid() {
    }

    public int getBidId() {
        return bidId;
    }

    public void setBidId(int bidId) {
        this.bidId = bidId;
    }

    public int getBidderId() {
        return bidderId;
    }

    public void setBidderId(int bidderId) {
        this.bidderId = bidderId;
    }

    public int getBidAuctionId() {
        return bidAuctionId;
    }

    public void setBidAuctionId(int bidAuctionId) {
        this.bidAuctionId = bidAuctionId;
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
    
}
