/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.polimi.guessbid.control;

/**
 *
 * @author Mirjam
 */
import it.polimi.guessbid.entity.Auction;
import it.polimi.guessbid.entity.Notification;
import it.polimi.guessbid.entity.User;
import it.polimi.guessbid.entity.WinningBid;
import java.util.Date;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerConfig;
import javax.ejb.TimerService;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Startup
@Singleton
public class OutcomeNotificationTimer {

    @Resource
    private TimerService timerService;

    @PersistenceContext
    EntityManager em;

    @EJB
    UserController uc;

    @EJB
    BidController bc;

    public void setTimer(Auction auction) {
        TimerConfig tc = new TimerConfig(auction, true);
        Timer timer = timerService.createSingleActionTimer(auction.getEndTime(), tc);
    }

    @Timeout
    public void programmaticTimeout(Timer timer) {
        Auction auction = (Auction) timer.getInfo();
        generateOutcomeNotifications(auction);
        System.out.println("TimerBean: timeout occurred. " + auction.getName());
    }

    private void generateOutcomeNotifications(Auction auction) {

        WinningBid bid = null;
        try {
             bid = (WinningBid) em.createNamedQuery("WinningBid.findByBidAuctionId").setParameter("auctionId", auction.getAuctionId()).getSingleResult();
        } catch (Exception e) {
            
        }
        if (bid == null) {
            generateNoWinnerNotification(auction);
        } else {
            auction.setWinningBidId(bc.getBidById(bid.getBidId()));
            User bidder =  uc.getUserById(bid.getBidderId());
            // bidder credit substract
            // dswhat if not enough money
            // generate looser notifs
            User seller  =  auction.getSellerId();

            generateOutcomeNotification(auction, bidder, "Congrads! You won");
            generateOutcomeNotification(auction, seller, "Your auction has finished. The winning user is " + bidder.getEmail());
        }

    }

    private void generateNoWinnerNotification(Auction auction) {
        Notification notif = new Notification();
        notif.setDescription("Sorry, looks like nobody won your auction");
        notif.setNAuctionId(auction);
        notif.setNUserId(auction.getSellerId());
        notif.setIsOutcome(true);
        notif.setRank(0);
        em.persist(notif);
        try {
            em.flush();
        } catch (Exception e) {
            throw e;
        }
    }

    private void generateOutcomeNotification(Auction auction, User user, String description) {
        Notification notif = new Notification();
        notif.setDescription(description);
        notif.setNAuctionId(auction);
        notif.setNUserId(user);
        notif.setIsOutcome(true);
        notif.setRank(1);
        em.persist(notif);
        try {
            em.flush();
        } catch (Exception e) {
            throw e;
        }
    }

}
