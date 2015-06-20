/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.polimi.guessbid.security;

import it.polimi.guessbid.control.UserController;
import it.polimi.guessbid.entity.User;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author miglie
 */
@Named
@RequestScoped
public class LoginBean {

    @Inject
    private Logger logger;
    @EJB
    UserController uc;

    private String username;
    private String password;

    public LoginBean() {
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String login() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        try {
            User u = uc.getUserByEmail(this.username);
            if (u != null) {
                request.login(String.valueOf(u.getUserId()), this.password);
                return "/user/home?faces-redirect=true";
            }
            else{
                 errorMessage(context);
            }
        } catch (ServletException e) {
            errorMessage(context);

        }
        return null;
    }

    private void errorMessage(FacesContext context) {
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid login", "Login Failed"));
        logger.log(Level.SEVERE, "Login Failed");
    }

    public String logout() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        request.getSession().invalidate();
        logger.log(Level.INFO, "User Logged out");
        return "/index?faces-redirect=true";
    }
}
