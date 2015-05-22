/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package it.polimi.registration.gui.security;

import it.polimi.registration.business.security.boundary.UserManager;
import it.polimi.registration.business.security.entity.User;
import java.util.logging.Level;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import static org.glassfish.admin.rest.client.utils.RestClientLogging.logger;

/**
 *
 * @author miglie
 */
@Named
@RequestScoped
public class RegistrationBean {
    
    @EJB
    private UserManager um;
    
    private User user;
    private String rawpassword;
    
    public RegistrationBean() {
    }
    
    public User getUser() {
        if (user == null) {
            user = new User();
        }
        return user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
    
    public void setRawpassword(String rawpassword) {
        this.rawpassword = rawpassword;
    }
    
    public String getRawpassword() {
        return rawpassword;
    }
    public String register() {
        user.setCredit(100);
        um.save(user);
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        try {
            request.login(user.getEmail(), this.rawpassword);
            return "/user/home";
        } catch (ServletException e) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Automatic Login Failed after registration","Automatic Login Failed after registratio"));
            logger.log(Level.SEVERE,"Automatic Login Failed after registratio");
            return "";
        }
    }
    
}
