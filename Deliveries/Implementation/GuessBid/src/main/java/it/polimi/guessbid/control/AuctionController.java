/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package it.polimi.guessbid.control;

import it.polimi.guessbid.entity.Auction;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import static org.eclipse.persistence.platform.database.oracle.plsql.OraclePLSQLTypes.Int;

/**
 *
 * @author Mirjam
 */
@Stateless
public class AuctionController {
    @PersistenceContext
            EntityManager em;
    
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


}