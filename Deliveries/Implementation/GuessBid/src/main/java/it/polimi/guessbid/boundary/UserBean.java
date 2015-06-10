/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.polimi.guessbid.boundary;

import it.polimi.guessbid.control.UserController;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author miglie
 */
@Named
@RequestScoped
public class UserBean{

    @EJB
    UserController um;
    
    
    public UserBean() {
    }
    
    public String getUsername() {
        return um.getLoggedUser().getUsername();
    }
    
    public int getUserId() {
        return um.getLoggedUser().getUserId();
    }
    
    public float getCredit() {
        return um.getLoggedUser().getCredit();
    }
}
