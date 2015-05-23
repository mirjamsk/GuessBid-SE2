/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.polimi.guessbid.control;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Mirjam
 */
@Stateless
public class CategoryController {
        @PersistenceContext
        EntityManager em;
        
        public List getAllCategories(){
        return em.createNamedQuery("Category.findAll").getResultList();
    }
}
