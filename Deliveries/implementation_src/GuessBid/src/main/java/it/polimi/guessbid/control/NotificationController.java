/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package it.polimi.guessbid.control;

import it.polimi.guessbid.entity.Notification;
import it.polimi.guessbid.entity.User;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Mirjam
 */
@Stateless
public class NotificationController {
    @PersistenceContext
    EntityManager em;
    private int totalRowsCnt = 10;
    

    public int getTotalRowsCnt() {
        return totalRowsCnt;
    }
    
    public int countUserNotifications(User u){
        Long res = (Long) em
                .createNamedQuery("Notification.countByUser")
                .setParameter("user", u)
                .getSingleResult();

        return  res.intValue() ;
    }
    
    public List<Notification> findUserNotifications(User u, int start, int end) {
        TypedQuery<Notification> query = em.createNamedQuery("Notification.findByUser", Notification.class);
        query.setParameter("user", u);
        query.setMaxResults(end - start);
        query.setFirstResult(start);
        this.totalRowsCnt = countUserNotifications(u);
        return query.getResultList();
    }
    
}