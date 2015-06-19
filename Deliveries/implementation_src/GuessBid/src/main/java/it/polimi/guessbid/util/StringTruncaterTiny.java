/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package it.polimi.guessbid.util;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Mirjam
 */
@FacesConverter("StringTruncaterTiny")
public class StringTruncaterTiny implements Converter{
    
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        String convertedValue = null;
        if ( value == null )  return value;
        return value.length() > 20? value.substring(0, 20) + "..." : value ;
    }
    
    
    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if ( value == null ) {
            return null;
        }
        // value must be of the type that can be cast to a String.
        try {
            String inputValue = (String)value;
            return inputValue.length() > 20? inputValue.substring(0, 20) + "..." : inputValue ;
        } catch (ClassCastException ce) {
            return null;
        }
        
    }
    
}
