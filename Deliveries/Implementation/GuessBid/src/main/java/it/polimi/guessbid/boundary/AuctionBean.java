/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.polimi.guessbid.boundary;

import it.polimi.guessbid.control.AuctionController;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 *
 * @author Mirjam
 */
@Named
@RequestScoped
public class AuctionBean {
    @EJB
    AuctionController am;
    
    public List getAllAuctions(){
        return am.getAllAuctions();
                
    }
    
}
