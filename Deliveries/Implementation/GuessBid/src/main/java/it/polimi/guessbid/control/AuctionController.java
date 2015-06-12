/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.polimi.guessbid.control;

import it.polimi.guessbid.entity.ActiveAuctions;
import it.polimi.guessbid.entity.Auction;
import it.polimi.guessbid.entity.User;
import it.polimi.guessbid.util.Code;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.primefaces.model.SortOrder;

/**
 *
 * @author Mirjam
 */
@Stateless
public class AuctionController {

    @PersistenceContext
    EntityManager em;
    @EJB
    OutcomeNotificationController onc;
    
    private int totalFilterdRowsCntAA = 0;
    private int totalFilterdRowsCntA = 0;


    private List categoryOptions;

    public int create(User seller, String name, String description, String category, Date endTime) {
        Auction auction = new Auction();

        auction.setSellerId(seller);
        auction.setName(name);
        auction.setDescription(description);
        auction.setCategory(category);
        auction.setEndTime(endTime);

        em.persist(auction);
        try {
            em.flush();
            onc.setOutcomeTimer(auction);
            //oc.generateNotification(auction);
            return Code.AUCTION_SUCCESSFULLY_CREATED;
        } catch (Exception e) {
            return Code.ERROR;
        }

    }

    public Auction getAuctionById(int id) {
        Auction auction = em.find(Auction.class, id);
        return auction;
    }

    public List getCategoryOptions() {
        if (categoryOptions == null) {
            categoryOptions = getAllCategories();
        }
        return categoryOptions;
    }

    private List getAllCategories() {
        return em.createNamedQuery("Category.findAll").getResultList();
    }

    public int getTotalFilterdRowsCntAA() {
        return totalFilterdRowsCntAA;
    }
    public int getTotalFilterdRowsCntA() {
        return totalFilterdRowsCntA;
    }

    public List getAllAuctions() {
        return em.createNamedQuery("Auction.findAll").getResultList();
    }

    public List getAllActiveAuctions() {
        return em.createNamedQuery("ActiveAuctions.findAll").getResultList();
    }

    public int countAllActiveAuctions() {
        Long res = (Long) em.createNamedQuery("ActiveAuctions.countAll").getSingleResult();
        return res.intValue();
    }

    public List<Auction> findAuctions(int start, int end) {
        TypedQuery<Auction> query = em.createNamedQuery("Auction.findAll", Auction.class);
        query.setMaxResults(end - start);
        query.setFirstResult(start);
        return query.getResultList();
        // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public List<Auction> findActiveAuctions(int start, int end) {
        TypedQuery<Auction> query = em.createNamedQuery("ActiveAuctions.findAll", Auction.class);
        query.setMaxResults(end - start);
        query.setFirstResult(start);
        return query.getResultList();
    }
    
    public List<Auction> findAuctions(int start, int end, String sortField, SortOrder sortOrder, Map filters) {
        CriteriaQuery<Auction> cq = getFilteredQueryAuctions(sortField, sortOrder, filters);
        TypedQuery<Auction> query = em.createQuery(cq);
        this.totalFilterdRowsCntA = query.getResultList().size();

        query.setMaxResults(end - start);
        query.setFirstResult(start);
        return query.getResultList();
    }


    public List<ActiveAuctions> findActiveAuctions(int start, int end, String sortField, SortOrder sortOrder, Map filters) {
        CriteriaQuery<ActiveAuctions> cq = getFilteredQueryActiveAuctions(sortField, sortOrder, filters);
        TypedQuery<ActiveAuctions> query = em.createQuery(cq);
        this.totalFilterdRowsCntAA = query.getResultList().size();

        query.setMaxResults(end - start);
        query.setFirstResult(start);
        return query.getResultList();
    }

    private CriteriaQuery<ActiveAuctions> getFilteredQueryActiveAuctions(String sortField, SortOrder sortOrder, Map filters) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<ActiveAuctions> cq = cb.createQuery(ActiveAuctions.class);
        Root<ActiveAuctions> auction = cq.from(ActiveAuctions.class);
        Predicate predicateCategory = null;
        Predicate predicatesGlobal = null;

        if (filters != null) {
            if (filters.containsKey("globalFilter")) {
                predicatesGlobal = cb.or(
                        cb.like(auction.get("name"), "%" + filters.get("globalFilter") + "%"),
                        cb.like(auction.get("description"), "%" + filters.get("globalFilter") + "%"));
            }

            if (filters.containsKey("category")) {
                predicateCategory = cb.equal(auction.get("category"), filters.get("category"));
            }

            if (predicateCategory != null && predicatesGlobal != null) {
                cq.where(cb.and(predicateCategory, predicatesGlobal));
            } else if (predicateCategory != null) {
                cq.where(predicateCategory);
            } else if (predicatesGlobal != null) {
                cq.where(predicatesGlobal);
            }
        }

        if (sortField != null) {
            if (sortOrder.equals(SortOrder.ASCENDING)) {
                cq.orderBy(cb.asc(auction.get(sortField)));
            } else {
                cq.orderBy(cb.desc(auction.get(sortField)));
            }
        } else {
            cq.orderBy(cb.desc(auction.get("timestamp")));
        }

        return cq;
    }
    
    private CriteriaQuery<Auction> getFilteredQueryAuctions(String sortField, SortOrder sortOrder, Map filters) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Auction> cq = cb.createQuery(Auction.class);
        Root<Auction> auction = cq.from(Auction.class);
        Predicate predicateCategory = null;
        Predicate predicateUserId = null;
        Predicate predicatesGlobal = null;
        Predicate predicateTemp = null;

        if (filters != null) {
            if (filters.containsKey("globalFilter")) {
                predicatesGlobal = cb.or(
                        cb.like(auction.get("name"), "%" + filters.get("globalFilter") + "%"),
                        cb.like(auction.get("description"), "%" + filters.get("globalFilter") + "%"));
            }

            if (filters.containsKey("category")) {
                predicateCategory = cb.equal(auction.get("category"), filters.get("category"));
            }
            if (filters.containsKey("sellerId")) {
                predicateUserId = cb.equal(auction.get("sellerId"), filters.get("sellerId"));
            }

            if (predicateCategory != null && predicatesGlobal != null) {
                predicateTemp = cb.and(predicateCategory, predicatesGlobal);
            } else if (predicateCategory != null) {
                predicateTemp = predicateCategory;
            } else if (predicatesGlobal != null) {
                predicateTemp = predicatesGlobal;
            }
            if (predicateUserId != null) {
                if (predicateTemp == null) {
                    predicateTemp = predicateUserId;
                } else {
                    predicateTemp = cb.and(predicateTemp, predicateUserId);
                }
            }
            if (predicateTemp != null) {
                cq.where(predicateTemp);
            }
        }

        if (sortField != null) {
            if (sortOrder.equals(SortOrder.ASCENDING)) {
                cq.orderBy(cb.asc(auction.get(sortField)));
            } else {
                cq.orderBy(cb.desc(auction.get(sortField)));
            }
        } else {
            cq.orderBy(cb.desc(auction.get("timestamp")));
        }

        return cq;
    }

    public void delete(Auction auction) {
        Auction a = em.merge(auction);
        em.remove(a);
    }

}
