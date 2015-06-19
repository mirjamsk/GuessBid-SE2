/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.polimi.guessbid.control;

import it.polimi.guessbid.entity.Auction;
import it.polimi.guessbid.entity.Bid;
import it.polimi.guessbid.entity.Notification;
import it.polimi.guessbid.entity.User;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Mirjam
 */
@Stateless
public class RankNotificationController {

    @PersistenceContext
    EntityManager em;

    public void generateNotification(Bid bid) {
        int rank = getCurrentUserRank(bid.getBidderId().getUserId(), bid.getBidAuctionId().getAuctionId());
        if (rank != -1) {
            generateNotification(bid.getBidderId(), bid.getBidAuctionId(), rank);
        }
        System.out.println(rank + " notif");

        //generate for all users changed notifs
        generateChangeRankNotification(bid);
    }

    public void generateNotification(User user, Auction auction, int rank) {
        // ignores multiple results
        Notification notif = new Notification();
        notif.setDescription("Your new rank is ");
        notif.setIsOutcome(false);
        notif.setNAuctionId(auction);
        notif.setNUserId(user);
        notif.setRank(rank);
        em.persist(notif);
        try {
            em.flush();
        } catch (Exception e) {
            throw e;
        }

    }

    public int getCurrentUserRank(int userId, int auctionId) {
        Query query = em.createNamedStoredProcedureQuery("GET_USER_AUCTION_RANKING");
        query.setParameter("arg_user_id", userId);
        query.setParameter("arg_auction_id", auctionId);
        List results = query.getResultList();
        return getInt(results);
    }

    private int getInt(List results) {
        Long rankL = -1L;
        Double rankD = -1.0;
        int rankI = -1;
        try {
            if (!results.isEmpty()) {
                rankL = (Long) ((Object[]) results.get(0))[0];
            }
        } catch (Exception e) {
            try {
                if (!results.isEmpty()) {
                    rankD = (Double) ((Object[]) results.get(0))[0];
                }
            } catch (Exception e2) {
                try {
                    if (!results.isEmpty()) {
                        rankI = (int) ((Object[]) results.get(0))[0];
                    }
                } catch (Exception e3) {
                }
            }
        }
        if (rankL != -1) return rankL.intValue();
        else if (rankD != -1) return rankD.intValue();
        else return rankI;
    }

    public void generateChangeRankNotification(Bid bid) {
        Query query = em.createNamedQuery("Bid.findBiddersOfAuctionExceptOne");
        query.setParameter("auctionId", bid.getBidAuctionId());
        query.setParameter("bidderId", bid.getBidderId());

        List<User> results = query.getResultList();
        if (!results.isEmpty()) {
            for (User u : results) {
                System.out.println(u.getUsername());
                Query q1 = em.createNamedQuery("Notification.findUserNewestRankNotif");
                q1.setParameter("user", u);
                q1.setParameter("auction", bid.getBidAuctionId());
                q1.setMaxResults(1);
                List res1 = q1.getResultList();
                if (!res1.isEmpty()) {
                    int oldRank;
                    oldRank = (int) res1.get(0);
                    int newRank = getCurrentUserRank(u.getUserId(), bid.getBidAuctionId().getAuctionId());
                    if (oldRank != newRank) {
                        generateNotification(u, bid.getBidAuctionId(), newRank);
                    }

                }
            }

        }

    }
    //generate for all users changed notifs

}
