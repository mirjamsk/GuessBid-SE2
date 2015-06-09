/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.polimi.guessbid.control;

import it.polimi.guessbid.entity.Auction;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Mirjam
 */
@Stateless
public class OutcomeNotificationController {

    @EJB
    OutcomeNotificationTimer ont;
 
    public void setOutcomeTimer(Auction auction) {
        ont.setTimer(auction);
    }    
}