/**
 * 
 */
package org.mohajo.studyrepublic.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * @author 윤성호
 * @since 2019.01.22
 * @version
 * -기능1 추가
 * -기능2 추가
 * -기능3 추가
 */

public class LoginCheckInterceptor extends HandlerInterceptorAdapter {


	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String dest = request.getParameter("dest");
		
		if (dest != null) {
			request.getSession().setAttribute("dest",dest);
		}
		return super.preHandle(request,response,handler);
	}

	

}
