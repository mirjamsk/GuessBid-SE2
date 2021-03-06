/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package it.polimi.guessbid.boundary;

import it.polimi.guessbid.control.AuctionController;
import it.polimi.guessbid.control.UserController;
import it.polimi.guessbid.entity.ActiveAuctions;
import it.polimi.guessbid.entity.Auction;
import it.polimi.guessbid.entity.Category;
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
public class ListAuctionsBean{
    
    
    @EJB
    AuctionController am;
        @EJB
    UserController uc;
    private  SelectItem[] categoryOptions;
    
    private LazyDataModel lazyModel;
    private LazyDataModel lazyOwnModel;

    
    public ListAuctionsBean() {
                this.lazyModel = new LazyDataModel(){
                    @Override
                    public List load(int first, int pageSize, String sortField, SortOrder sortOrder, Map filters) {
                        //List<Auction> lazyAuctions = am.findActiveAuctions(first, first + pageSize);
                        List<ActiveAuctions> lazyAuctions = am.findActiveAuctions(first, first + pageSize, sortField, sortOrder, filters);
                        
                        this.setRowCount(am.getTotalFilterdRowsCntAA());
                        return lazyAuctions; //To change body of generated methods, choose Tools | Templates.
                    }
                };
                
               this.lazyOwnModel = new LazyDataModel(){
                    @Override
                    public List load(int first, int pageSize, String sortField, SortOrder sortOrder, Map filters) {
                        filters.put("sellerId", uc.getLoggedUser());
                        List<Auction> lazyAuctions = am.findAuctions(first, first + pageSize, sortField, sortOrder, filters);
                        
                        this.setRowCount(am.getTotalFilterdRowsCntA());
                        return lazyAuctions; //To change body of generated methods, choose Tools | Templates.
                    }
                };
    }
    
    
    public LazyDataModel getLazyModel() {
        return this.lazyModel;
    }
      
    
    public LazyDataModel getLazyOwnModel() {
        return this.lazyOwnModel;
    }
    
    
    
    public SelectItem[] getCategoryOptions(){
        if( categoryOptions == null) categoryOptions = createFilterOptions(am.getCategoryOptions());

        return categoryOptions;
   }
    
    private SelectItem[] createFilterOptions(List categoryOptions) {
        SelectItem[] options = new SelectItem[categoryOptions.size() + 1];
        
        options[0] = new SelectItem("", "Select");
        for(int i = 0; i < categoryOptions.size(); i++) {
            String type = ((Category)categoryOptions.get(i)).getType();
            options[i + 1] = new SelectItem(type, type);
        }
        
        return options;
    }
    

    
}
