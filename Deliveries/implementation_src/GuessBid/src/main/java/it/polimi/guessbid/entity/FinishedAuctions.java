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
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
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
@Table(name = "finished_auctions")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FinishedAuctions.findAll", query = "SELECT f FROM FinishedAuctions f"),
    @NamedQuery(name = "FinishedAuctions.findByAuctionId", query = "SELECT f FROM FinishedAuctions f WHERE f.auctionId = :auctionId"),
    @NamedQuery(name = "FinishedAuctions.findBySellerId", query = "SELECT f FROM FinishedAuctions f WHERE f.sellerId = :sellerId"),
    @NamedQuery(name = "FinishedAuctions.findByName", query = "SELECT f FROM FinishedAuctions f WHERE f.name = :name"),
    @NamedQuery(name = "FinishedAuctions.findByEndTime", query = "SELECT f FROM FinishedAuctions f WHERE f.endTime = :endTime"),
    @NamedQuery(name = "FinishedAuctions.findByWinningBidId", query = "SELECT f FROM FinishedAuctions f WHERE f.winningBidId = :winningBidId"),
    @NamedQuery(name = "FinishedAuctions.findByTimestamp", query = "SELECT f FROM FinishedAuctions f WHERE f.timestamp = :timestamp")})
public class FinishedAuctions implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "auction_id")
    private int auctionId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "seller_id")
    private int sellerId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "name")
    private String name;
    @Lob
    @Size(max = 16777215)
    @Column(name = "description")
    private String description;
    @Basic(optional = false)
    @NotNull
    @Column(name = "end_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endTime;
    @Column(name = "winning_bid_id")
    private Integer winningBidId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "timestamp")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "category")
    private String category;
    
    
    
    public FinishedAuctions() {
    }
    
    public int getAuctionId() {
        return auctionId;
    }
    
    public void setAuctionId(int auctionId) {
        this.auctionId = auctionId;
    }
    
    public int getSellerId() {
        return sellerId;
    }
    
    public void setSellerId(int sellerId) {
        this.sellerId = sellerId;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public Date getEndTime() {
        return endTime;
    }
    
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
    
    public Integer getWinningBidId() {
        return winningBidId;
    }
    
    public void setWinningBidId(Integer winningBidId) {
        this.winningBidId = winningBidId;
    }
    
    public Date getTimestamp() {
        return timestamp;
    }
    
    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
    
    public String getCategory() {
        return category;
    }
    
    public void setCategory(String category) {
        this.category = category;
    }
    
}
