/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package it.polimi.guessbid.util;

import java.util.Date;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Mirjam
 */
@FacesConverter("RemainingTime")
public class RemainingTime implements Converter{
    
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        return value ;
    }
    
    
    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if ( value == null ) {
            return null;
        }
        // value must be of the type that can be cast to a String.
        try {
            Date endingDate = (Date)value;
            Date currentDate = new Date();
            long diff = endingDate.getTime() - currentDate.getTime();
            int diffDays = (int) diff / (24 * 60 * 60 * 1000);
            int diffHours = ((int)diff % (24 * 60 * 60 * 1000))/(60*60*1000);
            int diffMinutes = ((int)diff % (60*60*1000))/(60*1000);

            String timeRemaining = "";
            if (diffDays > 0){
                timeRemaining += String.valueOf(diffDays);
                timeRemaining += diffDays == 1 ?  " day " : " days ";
            }
            if (diffHours > 0){
                timeRemaining += String.valueOf(diffHours);
                timeRemaining += diffHours == 1 ?  " hour " : " hours ";
            }
            
            timeRemaining += diffMinutes > 0 ? String.valueOf(diffMinutes) + " min " : "";
            
            timeRemaining = timeRemaining.equals("")? "Auction ended "+ endingDate.toString().replace(" CEST", "") : timeRemaining+ "left";
            
            return timeRemaining ;
        } catch (ClassCastException ce) {
            return null;
        }
        
    }
    
}
