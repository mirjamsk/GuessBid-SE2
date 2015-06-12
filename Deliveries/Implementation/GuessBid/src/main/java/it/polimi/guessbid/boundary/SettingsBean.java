/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.polimi.guessbid.boundary;

/**
 *
 * @author Mirjam
 */
import it.polimi.guessbid.control.UserController;
import it.polimi.guessbid.entity.User;
import it.polimi.guessbid.util.Code;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

@ManagedBean
public class SettingsBean {

    @EJB
    UserController uc;

    String newUsername;
    String newEmail;

    public String getNewEmail() {
        return newEmail;
    }

    public void setNewEmail(String newEmail) {
        this.newEmail = newEmail;
    }

    public String getNewUsername() {
        return newUsername;
    }

    public void setNewUsername(String newUsername) {
        this.newUsername = newUsername;
    }

    public User getLoggedUser() {
        return uc.getLoggedUser();
    }

    public String updateUsername() {
        int res = uc.updateUsername(getLoggedUser(), newUsername);
        if (res == Code.USERNAME_SUCCESSFULLY_CHANGED) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Username changed"));
            //redirect to auciton
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Something went wrong"));
        }
        newUsername = "";
        return null;
    }

    public String updateEmail() {
        int res = uc.updateEmail(getLoggedUser(), newEmail.trim());
        if (res == Code.EMAIL_SUCCESSFULLY_CHANGED) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Email changed"));
        } else if (res == Code.NEW_EMAIL_SAME_AS_OLD) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("New email can't be the same as the old one"));
        } else if (res == Code.DUPLICATE_EMAIL) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("User with this email already exists"));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Something went wrong"));
        }
        newEmail = "";
        return null;
    }

}
