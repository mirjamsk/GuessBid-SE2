/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package it.polimi.guessbid.boundary;

import it.polimi.guessbid.control.AuctionController;
import it.polimi.guessbid.entity.Auction;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.model.SelectItem;
import javax.inject.Named;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

/**
 *
 * @author Mirjam
 */
@Named
@RequestScoped
public class AuctionBean{


    @EJB
    AuctionController am;

    private LazyDataModel lazyModel;
    
        public AuctionBean() {
        int rowCount = 0;      
        this.lazyModel = new LazyDataModel(){
            @Override
            public List load(int first, int pageSize, String sortField, SortOrder sortOrder, Map filters) {
                List<Auction> lazyAuctions = am.findActiveAuctions(first, first + pageSize);
                this.setRowCount(am.countAllActiveAuctions());
                return lazyAuctions; //To change body of generated methods, choose Tools | Templates.
            }
        };
        //lazyModel.setRowCount(10);
       
    }

    
      public LazyDataModel getLazyModel() {
        return this.lazyModel;
    }
    
}
