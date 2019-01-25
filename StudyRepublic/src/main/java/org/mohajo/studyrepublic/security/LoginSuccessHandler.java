/**
 * 
 */
package org.mohajo.studyrepublic.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

/**
 * @author 윤성호
 * @since 2019.01.22
 * @version
 * -기능1 추가
 * -기능2 추가
 * -기능3 추가
 */

public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

	@Override
	protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response) {
		Object dest = request.getSession().getAttribute("dest");
		String nextURL = null;
		if(dest != null) {
			request.getSession().removeAttribute("dest");
			nextURL =(String) dest;
		} else {
			nextURL = super.determineTargetUrl(request, response);
			
		}
		return nextURL;
	}
	
	
}
