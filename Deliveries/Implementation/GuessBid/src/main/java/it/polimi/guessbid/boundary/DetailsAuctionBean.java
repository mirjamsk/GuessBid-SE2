/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.polimi.guessbid.boundary;

import it.polimi.guessbid.control.AuctionController;
import it.polimi.guessbid.control.UserController;
import it.polimi.guessbid.entity.Auction;
import java.io.IOException;
import java.util.Calendar;
import java.util.Objects;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Mirjam
 */
@ManagedBean(name="detailsAuctionBean")
@ViewScoped
public class DetailsAuctionBean {

    @EJB
    AuctionController am;
    @EJB
    UserController um;
    
    int id;
    Auction auction;

    public Auction getAuction() {
        return auction;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void loadAuction() throws IOException {
        Auction auctionRes = am.getAuctionById(id);
        if (auctionRes == null) {
            //ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
            //ec.redirect(ec.getRequestContextPath() + "/user/404.xhtml");
            
        } else {
            this.auction = auctionRes;
        }
    }

    public String getSellerName() {
        return auction.getSellerId().getUsername();
    }

    public boolean loggedUserIsSeller() {
         return Objects.equals(um.getLoggedUser().getUserId(), this.auction.getSellerId().getUserId());
    }

    public boolean isAuctionFinished() {
        return Calendar.getInstance().getTime().after(new java.util.Date(this.auction.getEndTime().getTime()));
    }

}
