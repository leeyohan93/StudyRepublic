/**
 * 
 */
package org.mohajo.studyrepublic.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mohajo.studyrepublic.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

/**
 * @author 윤성호
 * @since 2019.01.22
 * @version
 * -기능1 추가
 * -기능2 추가
 * -기능3 추가
 */
@Service
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {


	private String loginidname;
	private String defaultUrl;
	
/*	@Autowired
	MemberRepository memberrepository;*/
	
	@Autowired
	MemberRepository mr;

	public CustomLoginSuccessHandler() {
		super();
		this.loginidname = "김치";
		this.defaultUrl = "/index";
	}



	/**
	 * @return the loginidname
	 */
	public String getLoginidname() {
		return loginidname;
	}



	/**
	 * @param loginidname the loginidname to set
	 */
	public void setLoginidname(String loginidname) {
		this.loginidname = loginidname;
	}



	/**
	 * @return the defaultUrl
	 */
	public String getDefaultUrl() {
		return defaultUrl;
	}



	/**
	 * @param defaultUrl the defaultUrl to set
	 */
	public void setDefaultUrl(String defaultUrl) {
		this.defaultUrl = defaultUrl;
	}


	
	
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
	
		String loginid = request.getParameter("username");

		System.out.println("로그인 시도 아이디:" + loginid.toString());
		
		System.out.println(loginid instanceof String);
		
//		System.out.println(memberrepository.findMember(loginid) == null);
		
		System.out.println(mr.checkId(loginid) + "숫자");

	/*	System.out.println(memberrepository.findById(loginid));*/
/*		Member member = memberrepository.findMember(request.getParameter("username"));
		System.out.println("member: " + member);*/

		
/*		String status = memberrepository.findById(loginid).get().getMemberStatusCD().getMemberStatusCode();
		System.out.println("상태:" +status);	*/
		
//		System.out.println(memberrepository.findById(loginid).get().getMemberStatusCD().getMemberStatusCode()==null);	
		
		
		
/*		switch (status) {
			case "P" :
				setExceptionmsgname("정지된 계정입니다.");
				this.exceptionmsgname = "정지된 계정입니다.";	
				break;
			case "E" :
				setExceptionmsgname("탈퇴된 계정입니다.");
				this.exceptionmsgname = "탈퇴된 계정입니다.";	
			default :
				break;
		} 
		
		request.setAttribute("exceptionmsgname", exceptionmsgname);*/

		
		request.setAttribute("loginidname", loginid);
		request.getRequestDispatcher(defaultUrl).forward(request, response);

	}
	

 
}
