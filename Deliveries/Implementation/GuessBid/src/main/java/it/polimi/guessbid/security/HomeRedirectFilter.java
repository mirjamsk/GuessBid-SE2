/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package it.polimi.guessbid.security;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Mirjam
 */
public class HomeRedirectFilter implements Filter {
    
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (((HttpServletRequest) request).getUserPrincipal() != null){
            System.out.println("user si");
            ((HttpServletResponse) response).sendRedirect("./user/home.xhtml");
        }else{
            chain.doFilter(request, response);
        }
    }
    
    @Override
    public void destroy() {
        System.out.println("destroyFilter");
    }
}
