/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package it.polimi.guessbid.boundary;

import it.polimi.guessbid.control.CategoryController;
import it.polimi.guessbid.entity.Category;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.model.SelectItem;
import javax.inject.Named;

/**
 *
 * @author Mirjam
 */
@Named
@RequestScoped
public class CategoryBean {
    @EJB
            CategoryController cm;
    private SelectItem[] categoryOptions;
    
    public CategoryBean() {
        categoryOptions = createFilterOptions(cm.getAllCategories());
    }
    
    public SelectItem[] getCategoryOptions(){
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
