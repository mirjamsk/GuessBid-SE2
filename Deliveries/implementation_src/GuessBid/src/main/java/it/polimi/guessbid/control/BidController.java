/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.polimi.guessbid.control;

import it.polimi.guessbid.entity.Auction;
import it.polimi.guessbid.entity.Bid;
import it.polimi.guessbid.entity.User;
import it.polimi.guessbid.util.Code;
import java.util.Date;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author miglie
 */
@Stateless
public class BidController {

    @PersistenceContext
    EntityManager em;

    @EJB
    RankNotificationController nc;

    public int save(User user, Auction auction, float amount) {
        Bid bid = new Bid();
        if (user.getCredit() < amount) {
            return Code.INSUFICIENT_CREDIT;
        }
        bid.setAmount(amount);
        bid.setBidderId(user);
        bid.setBidAuctionId(auction);
        bid.setTimestamp(new Date());
//dont allow same bid??????????
        em.persist(bid);
        try {
            em.flush();
            nc.generateNotification(bid);
            return Code.BID_SUCCESSFULLY_PLACED;
        } catch (Exception e) {
            throw e;
        }

    }

    public Bid getBidById(int id) {
        return em.find(Bid.class, id);
    }

    public int countBidsOfAuction(Auction a){
        Long res = (Long) em
                .createNamedQuery("Bid.countBidsOfAuction")
                .setParameter("auction", a)
                .getSingleResult();

        return  res.intValue() ;
    }
}
