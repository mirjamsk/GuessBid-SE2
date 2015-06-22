/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.polimi.guessbid.boundary;

import it.polimi.guessbid.control.BidController;
import it.polimi.guessbid.control.UserController;
import it.polimi.guessbid.util.Code;
import java.util.Calendar;
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
    UserController uc;
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

        if (Calendar.getInstance().getTime().after(new java.util.Date(ab.getAuction().getEndTime().getTime()))) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Unfortunately, the auction has finshed", "Unfortunately, the auction has finshed"));
            return;
        }
        value = value.trim();
        int integerPlaces = value.indexOf('.');
        if (integerPlaces != -1) {
            int decimalPlaces = value.length() - integerPlaces - 1;
            if (decimalPlaces > 2) {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Please enter a positive number with up to 2 decimal places", "NaN"));
                return;
            }
        }
        float bid;
        try {
            bid = Float.parseFloat(value);
            if (bid <= 0) {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Please enter a positive number with up to 2 decimal places", "NaN"));
                return;
            }
            int res = bc.save(uc.getLoggedUser(), ab.getAuction(), bid);
            if (res == Code.BID_SUCCESSFULLY_PLACED) {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, bid + " bid succesfully placed", "Placed bid"));
            } else if (res == Code.INSUFICIENT_CREDIT) {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Insufficient credit", "Insufficient credit"));
            } else {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Something went wrong", "Something went wrong"));
            }

        } catch (NumberFormatException e) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "The input is not a number", "NaN"));
        }

    }

}
