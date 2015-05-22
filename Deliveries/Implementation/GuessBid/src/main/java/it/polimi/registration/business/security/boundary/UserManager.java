/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package it.polimi.registration.business.security.boundary;

import com.sun.jna.Callback;
import it.polimi.registration.business.security.entity.Group;
import it.polimi.registration.business.security.entity.User;
import java.security.Principal;
import java.util.logging.Level;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import org.eclipse.persistence.exceptions.DatabaseException;
import static org.glassfish.admin.rest.client.utils.RestClientLogging.logger;

/**
 *
 * @author miglie
 */
@Stateless
public class UserManager {
    
    @PersistenceContext
            EntityManager em;
    
    @Inject
            Principal principal;
    
    public void save(User user) {
        user.setCredit(100);
        user.setGroupName(Group.USERS);
        em.persist(user);
        try {
            em.flush();
        } catch (PersistenceException e) { //usually in case of duplicate email
            throw e;
        }catch ( Exception e) {
           throw e;
        }
    }
    
    
    public void unregister() {
        em.remove(getLoggedUser());
    }
    
    public User getLoggedUser() {
        return (User) em.createNamedQuery("User.findByEmail").setParameter("email", principal.getName()).getSingleResult();
        //return em.find(User.class, principal.getName());
        // return em.createQuery("from user WHERE email=:email", User.class).setParameter("email",  principal.getName()).getSingleResult();
    }
    
}
