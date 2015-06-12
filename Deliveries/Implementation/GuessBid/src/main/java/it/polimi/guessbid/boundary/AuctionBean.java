/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.polimi.guessbid.boundary;

/**
 *
 * @author Mirjam
 */
import it.polimi.guessbid.control.AuctionController;
import it.polimi.guessbid.control.UserController;
import it.polimi.guessbid.entity.Auction;
import it.polimi.guessbid.entity.Category;
import it.polimi.guessbid.util.Code;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

@ManagedBean
public class AuctionBean {

    @EJB
    AuctionController ac;
    @EJB
    UserController uc;

    private String name;
    private String category;
    private String description;
    private Date endTime;
    private SelectItem[] categoryOptions;
    //private final Date currentDate = new Date(new Date().getTime() + 3600 * 1000); // current date plus 1hour
    private final Date currentDate = new Date(); // current date plus 1hour

    public Date getCurrentDate() {
        return currentDate;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public SelectItem[] getCategoryOptions() {
        if (categoryOptions == null) {
            categoryOptions = createFilterOptions(ac.getCategoryOptions());
        }
        return categoryOptions;
    }

    private SelectItem[] createFilterOptions(List categoryOptions) {
        SelectItem[] options = new SelectItem[categoryOptions.size() + 1];

        options[0] = new SelectItem("", "Select");
        for (int i = 0; i < categoryOptions.size(); i++) {
            String type = ((Category) categoryOptions.get(i)).getType();
            options[i + 1] = new SelectItem(type, type);
        }

        return options;
    }

    public void create() {

        int res = ac.create(uc.getLoggedUser(), name, description, category, endTime);
        if (res == Code.AUCTION_SUCCESSFULLY_CREATED) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Auction created"));
            //redirect to auciton
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Something went wrong"));
        }
    }



}
