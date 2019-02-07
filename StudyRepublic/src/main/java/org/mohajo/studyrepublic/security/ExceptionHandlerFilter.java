/**
 * 
 */
package org.mohajo.studyrepublic.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.web.authentication.rememberme.CookieTheftException;
import org.springframework.web.filter.GenericFilterBean;



/**
 * @author 윤성호
 * @since 2019.01.22
 * @version
 * -기능1 추가
 * -기능2 추가
 * -기능3 추가
 */

public class ExceptionHandlerFilter extends GenericFilterBean {
	
	private String ajaxHaeder = "AJAX";


	public void destroy() {
	  }
	 
	  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
	      throws IOException, ServletException {
	    HttpServletRequest req = (HttpServletRequest) request;
	    HttpServletResponse res = (HttpServletResponse) response;
	 
	    if (isAjaxRequest(req)) {
	      try {
	        chain.doFilter(req, res);
	      } catch (CookieTheftException e) {

	        res.sendError(HttpServletResponse.SC_FORBIDDEN);
	      }
	    } else
	      try {
	        chain.doFilter(req, res);
	      } catch (CookieTheftException e) {
	
	        res.sendRedirect("/");
	      }
	  }
	 
	  private boolean isAjaxRequest(HttpServletRequest req) {
	    return req.getHeader(ajaxHaeder) != null && req.getHeader(ajaxHaeder).equals(Boolean.TRUE.toString());
	  }
	 
	  /**
	   * Set AJAX Request Header (Default is AJAX)
	   * 
	   * @param ajaxHeader
	   */
	  public void setAjaxHaeder(String ajaxHeader) {
	    this.ajaxHaeder = ajaxHeader;
	  }


	
}
