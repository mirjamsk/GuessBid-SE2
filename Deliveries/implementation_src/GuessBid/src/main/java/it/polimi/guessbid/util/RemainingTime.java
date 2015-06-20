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
public class RemainingTime implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        return value;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value == null) {
            return null;
        }
        // value must be of the type that can be cast to a String.
        try {
            Date endingDate = (Date) value;
            Date currentDate = new Date();

            double diff = (double) endingDate.getTime() - currentDate.getTime();
            if (diff <= 0){
              return "Auction ended " + endingDate.toString().replace(" CEST", "");
            }
            
            Double diffDays = diff / (24 * 60 * 60 * 1000);
            int diffDaysI = diffDays.intValue();

            Double diffHours = ( diff % (24 * 60 * 60 * 1000)) / (60 * 60 * 1000);
            int diffHoursI = diffHours.intValue();

            Double diffMinutes = ( diff % (60 * 60 * 1000)) / (60 * 1000);
            int diffMinutesI = diffMinutes.intValue();
            Double diffSeconds = ( diff % (60 * 1000)) / (1000);
            int diffSecondsI = diffSeconds.intValue();

            String timeRemaining = "";
            if (diffDaysI > 0) {
                timeRemaining += String.valueOf(diffDaysI);
                timeRemaining += diffDaysI == 1 ? " day " : " days ";
            }
            if (diffHoursI > 0) {
                timeRemaining += String.valueOf(diffHoursI);
                timeRemaining += diffHoursI == 1 ? " hour " : " hours ";
            }
            if (diffMinutesI > 0) {
                timeRemaining += String.valueOf(diffMinutesI)  + " min ";
            }
            if( diffDaysI <=0 && diffHoursI <=0 && 0 <= diffMinutesI && diffMinutesI <= 5){
                timeRemaining += String.valueOf(diffSecondsI)  + " sec ";
            }

            timeRemaining = timeRemaining.equals("") ?  endingDate.toString().replace(" CEST", "") : timeRemaining + "left";

            return timeRemaining;
        } catch (ClassCastException ce) {
            return null;
        }

    }

}
