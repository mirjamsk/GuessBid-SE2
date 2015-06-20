/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package it.polimi.guessbid.boundary;

import it.polimi.guessbid.control.NotificationController;
import it.polimi.guessbid.control.UserController;
import it.polimi.guessbid.entity.Notification;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

/**
 *
 * @author Mirjam
 */
@Named
@RequestScoped
public class ListNotificationsBean{
    
    
    @EJB
    NotificationController nc;
    @EJB
    UserController um;
    
    private LazyDataModel lazyModel;
    
    public ListNotificationsBean() {
                this.lazyModel = new LazyDataModel(){
                    @Override
                    public List load(int first, int pageSize, String sortField, SortOrder sortOrder, Map filters) {
                        //List<Auction> lazyAuctions = am.findActiveAuctions(first, first + pageSize);
                        List<Notification> lazyNotifications = nc.findUserNotifications(um.getLoggedUser(), first, first + pageSize);
                        
                        this.setRowCount(nc.getTotalRowsCnt());
                        return lazyNotifications; //To change body of generated methods, choose Tools | Templates.
                    }
                };
    }
    
    public LazyDataModel getLazyModel() {
        return this.lazyModel;
    }
   
}
