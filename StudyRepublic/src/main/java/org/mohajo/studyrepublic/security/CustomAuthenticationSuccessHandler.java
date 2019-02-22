/**
 * 
 */
package org.mohajo.studyrepublic.security;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mohajo.studyrepublic.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;


@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

/*@Autowired
private MemberRepository mr;*/
	
/*@Autowired	
private MemberRepository memberrepository;*/
	

	private static MemberRepository mr;
	
	@Autowired
	private static void setRepository(MemberRepository memberrepository) {
		mr = memberrepository;
	}

private String loginidname;
private String defaultUrl;


public String getLoginidname() {
	return loginidname;
}

public void setLoginidname(String loginidname) {
	this.loginidname = loginidname;
}

public String getDefaultUrl() {
	return defaultUrl;
}

public void setDefaultUrl(String defaultUrl) {
	this.defaultUrl = defaultUrl;
}


public CustomAuthenticationSuccessHandler() {
	this.loginidname = "하이";
	this.defaultUrl = "index";
}


	@Inject
	MemberRepository mr2;


	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		
		
			System.out.println("멤버레퍼짓토리: " + mr);
			String loginId = request.getParameter("username");
			System.out.println(mr2 + "22");
			
		resultRedirectStrategy(request, response, authentication);

		
			
/*			MemberService memberservice = new MemberService();
			Member member = memberservice.member(loginId);
		
			
			System.out.println("멤버서비스" + memberservice);
			System.out.println(member);
			*/
			
/*			System.out.println(mr);	
			Member member = mr.findById(loginId).get();			
			System.out.println(member);*/
			
/*			MemberController membercontroller = new MemberController();			
			Member member = membercontroller.member(loginId);
			
			System.out.println("로그인 유저 아이디: " + loginId);
			System.out.println("멤버좀 나와라:" + member);*/
					
			request.getRequestDispatcher(defaultUrl).forward(request, response);

	}

	protected void resultRedirectStrategy(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        
		System.out.println("멤버레퍼짓토리: " + mr);
		String loginId = request.getParameter("username");
		System.out.println(mr2 + "22");
	}
	
}
