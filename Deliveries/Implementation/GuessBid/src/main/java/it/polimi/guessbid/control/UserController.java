/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.polimi.guessbid.control;

import it.polimi.guessbid.entity.Group;
import it.polimi.guessbid.entity.User;
import it.polimi.guessbid.util.Code;
import java.security.Principal;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

/**
 *
 * @author miglie
 */
@Stateless
public class UserController {

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
        } catch (Exception e) {
            throw e;
        }
    }

    public void unregister() {
        em.remove(getLoggedUser());
    }

    public User getLoggedUser() {
        return getUserByEmail(principal.getName());
        //return em.find(User.class, principal.getName());
        // return em.createQuery("from user WHERE email=:email", User.class).setParameter("email",  principal.getName()).getSingleResult();
    }

    public User getUserById(int id) {
        User u = null;
        try {
            u = em.find(User.class, id);;

        } catch (Exception e) {
        }
        return u;
    }

    public User getUserByEmail(String email) {
        User u = null;
        try {
            u = (User) em.createNamedQuery("User.findByEmail").setParameter("email", email).getSingleResult();

        } catch (Exception e) {
        }
        return u;
    }

    public int updateUsername(User user, String username) {
        try {
            user.setUsername(username);
            em.merge(user);
            em.flush();
            return Code.USERNAME_SUCCESSFULLY_CHANGED;
        } catch (Exception e) {
            return Code.ERROR;
        }
    }

    public int updateEmail(User user, String email) {
        if (user.getEmail().equals(email)) {
            return Code.NEW_EMAIL_SAME_AS_OLD;
        }
        if (getUserByEmail(email) != null) {
            return Code.DUPLICATE_EMAIL;
        }
        try {
            user.setEmail(email);
            em.merge(user);

            return Code.EMAIL_SUCCESSFULLY_CHANGED;
        } catch (Exception e) {
            return Code.ERROR;
        }
    }

}
