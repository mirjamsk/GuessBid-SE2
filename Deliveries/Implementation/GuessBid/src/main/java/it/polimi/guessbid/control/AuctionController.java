/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package it.polimi.guessbid.control;

import it.polimi.guessbid.entity.ActiveAuctions;
import it.polimi.guessbid.entity.Auction;
import java.util.List;
import java.util.Map;
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
    private int totalFilterdRowsCnt = 0;
    
    private List categoryOptions;
    
    public Auction getAuctionById(int id){
        Auction auction = em.find(Auction.class, id);
        return auction;
    }
 
    
    public List getCategoryOptions() {
        if( categoryOptions == null)  categoryOptions = getAllCategories();
        return categoryOptions;
    }
    
    private List getAllCategories(){
        return em.createNamedQuery("Category.findAll").getResultList();
    }
    public int getTotalFilterdRowsCnt() {
        return totalFilterdRowsCnt;
    }
    
    public List getAllAuctions(){
        return em.createNamedQuery("Auction.findAll").getResultList();
    }
    public List getAllActiveAuctions(){
        return em.createNamedQuery("ActiveAuctions.findAll").getResultList();
    }
    
    public int countAllActiveAuctions(){
        Long res = (Long) em.createNamedQuery("ActiveAuctions.countAll").getSingleResult();
        return  res.intValue() ;
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
    
    public List<ActiveAuctions> findActiveAuctions(int start, int end, String sortField, SortOrder sortOrder, Map filters) {
        CriteriaQuery<ActiveAuctions> cq =  getFilteredQuery( sortField,  sortOrder, filters);
        TypedQuery<ActiveAuctions> query = em.createQuery(cq);
        this.totalFilterdRowsCnt = query.getResultList().size();
        
        query.setMaxResults(end - start);
        query.setFirstResult(start);
        return query.getResultList();
    }
    private CriteriaQuery<ActiveAuctions> getFilteredQuery( String sortField, SortOrder sortOrder, Map filters){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<ActiveAuctions> cq = cb.createQuery(ActiveAuctions.class);
        Root<ActiveAuctions> auction = cq.from(ActiveAuctions.class);
        Predicate predicateCategory = null;
        Predicate predicatesGlobal = null;
        
        if (filters != null){
            if ( filters.containsKey("globalFilter")){
                predicatesGlobal = cb.or(
                        cb.like(auction.get("name"), "%"+filters.get("globalFilter")+"%"),
                        cb.like(auction.get("description"),  "%"+filters.get("globalFilter")+"%"));
            }
            
            if ( filters.containsKey("category") ){
                predicateCategory = cb.equal(auction.get("category"), filters.get("category"));
            }
            
            if( predicateCategory != null && predicatesGlobal != null ){
                cq.where(cb.and( predicateCategory, predicatesGlobal));
            }else if( predicateCategory != null ){
                cq.where( predicateCategory);
            }else if( predicatesGlobal != null ){
                cq.where( predicatesGlobal);
            }
        }
        
        if (sortField != null){
            if(sortOrder.equals(SortOrder.ASCENDING))
                cq.orderBy(cb.asc(auction.get(sortField)));
            else
                cq.orderBy(cb.desc(auction.get(sortField)));
        }else{
             cq.orderBy(cb.desc(auction.get("timestamp")));
        }
        
        return cq;
    }
    
    
    
}