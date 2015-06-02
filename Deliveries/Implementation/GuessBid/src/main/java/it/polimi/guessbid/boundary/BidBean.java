/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.polimi.guessbid.boundary;

import it.polimi.guessbid.control.BidController;
import it.polimi.guessbid.control.UserController;
import it.polimi.guessbid.util.Code;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author Mirjam
 */
@ManagedBean
@ViewScoped
public class BidBean {

    @EJB
    UserController um;
    @EJB
    BidController bc;

    @ManagedProperty(value = "#{detailsAuctionBean}")
    private DetailsAuctionBean ab;

    //must povide the setter method
    public void setAb(DetailsAuctionBean ab) {
        this.ab = ab;
    }

    String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        FacesContext context = FacesContext.getCurrentInstance();

        float bid;
        try {
            bid = Float.parseFloat(value);
            int res = bc.save(um.getLoggedUser(), ab.getAuction(), bid);
            if (res == Code.BID_SUCCESSFULLY_PLACED) {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, bid +" bid succesfully placed", "Placed bid"));
            } else if (res == Code.INSUFICIENT_CREDIT) {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Insufficient credit", "Insufficient credit"));
            }

        } catch (NumberFormatException e) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "The input is not a number", "NaN"));
        }

    }

}
