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
import it.polimi.guessbid.entity.Bid;
import it.polimi.guessbid.entity.Notification;
import it.polimi.guessbid.entity.User;
import it.polimi.guessbid.entity.WinningBid;
import java.util.Date;
import java.util.List;
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
import javax.persistence.Query;

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
        Bid winningBid = findWinningBid(auction);

        if (winningBid == null) {
            generateNoWinnerNotification(auction);
            generateLoserNotifications(auction);
        } else {
            User seller = uc.getUserById(auction.getSellerId().getUserId());
            User bidder = uc.getUserById(winningBid.getBidderId().getUserId());

            updateCredit(seller, bidder, winningBid.getAmount());
            auction.setWinningBidId(winningBid);

            generateOutcomeNotification(auction, bidder, "Congrads! You won");
            generateOutcomeNotification(auction, seller, "Your auction has finished. The winning user is " + bidder.getEmail());
            generateLoserNotifications(auction, bidder);
        }
    }

    private Bid findWinningBid(Auction auction) {
        Bid winningBid = null;

        try {//find winning bid
            WinningBid b = (WinningBid) em.createNamedQuery("WinningBid.findByBidAuctionId").setParameter("auctionId", auction.getAuctionId()).getSingleResult();
            winningBid = bc.getBidById(b.getBidId());
        } catch (Exception e) {
        }
        if (winningBid != null) { //if winning bid exsists check if bidder has enough credit left
            User bidder = uc.getUserById(winningBid.getBidderId().getUserId());
            if (bidder.getCredit() < winningBid.getAmount()) { //if enough credit
                generateOutcomeNotification(auction, bidder, "Insufficient credit");
                winningBid = findSuccessionalWinningBid(auction);
            }
        }
        return winningBid;
    }
    private Bid findSuccessionalWinningBid(Auction auction) {
        Bid winningBid = null;
        int rank = 2;
        int bidsNb = bc.countBidsOfAuction(auction);
        boolean foundWinner = false;
        
        Bid prevBid = getBidAtAuctionRank(rank-1, auction.getAuctionId());
        Bid currBid = getBidAtAuctionRank(rank, auction.getAuctionId());
        Bid nextBid = null;
        User currUser = getUserAtAuctionRank(rank, auction.getAuctionId());
        
        while(!foundWinner && rank < bidsNb){
            nextBid = getBidAtAuctionRank(rank+1, auction.getAuctionId());
            if(currUser.getCredit() >= currBid.getAmount() && 
                    currBid.getAmount() != prevBid.getAmount() &&
                    currBid.getAmount() != nextBid.getAmount()){
                winningBid = currBid;
                foundWinner = true;
            }
            rank++;
            prevBid = currBid;
            currBid = nextBid;
            currUser = getUserAtAuctionRank(rank, auction.getAuctionId());
        }
        
        if (!foundWinner && 
                currUser.getCredit() >= currBid.getAmount() && 
                currBid.getAmount() != prevBid.getAmount()){
            winningBid = currBid;
        }

        return winningBid;
    }
    
    
   public User getUserAtAuctionRank(int rank, int auctionId) {
        Query query = em.createNamedStoredProcedureQuery("GET_USERID_AT_AUCTION_RANK");
        query.setParameter("arg_rank", rank);
        query.setParameter("arg_auction_id", auctionId);
        List results = query.getResultList();
        int userId = -1;
        if (!results.isEmpty()) {
            userId = (int) ((Object[]) results.get(0))[0];
        }
        return uc.getUserById(userId);
    }
   
      public Bid getBidAtAuctionRank(int rank, int auctionId) {
        Query query = em.createNamedStoredProcedureQuery("GET_BIDID_AT_AUCTION_RANK");
        query.setParameter("arg_rank", rank);
        query.setParameter("arg_auction_id", auctionId);
        List results = query.getResultList();
        int bidId = -1;
        if (!results.isEmpty()) {
            bidId = (int) ((Object[]) results.get(0))[0];
        }
        return bc.getBidById(bidId);
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

    public void generateLoserNotifications(Auction auction, User winner) {
        Query query = em.createNamedQuery("Bid.findBiddersOfAuctionExceptOne");
        query.setParameter("auctionId", auction);
        query.setParameter("bidderId", winner);

        List<User> results = query.getResultList();
        if (!results.isEmpty()) {
            for (User bidder : results) {
                generateOutcomeNotification(auction, bidder, "Unfortunately, you lost this one");
            }
        }

    }

    public void generateLoserNotifications(Auction auction) {
        Query query = em.createNamedQuery("Bid.findBiddersOfAuction");
        query.setParameter("auctionId", auction);

        List<User> results = query.getResultList();
        if (!results.isEmpty()) {
            for (User bidder : results) {
                generateOutcomeNotification(auction, bidder, "Unfortunately, you lost this one");
            }
        }

    }

    private void updateCredit(User seller, User bidder, float amount) {
        bidder.setCredit(bidder.getCredit() - amount);
        seller.setCredit(seller.getCredit() + amount);
    }



}
