/**
 * 
 */
package org.mohajo.studyrepublic.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.aspectj.apache.bcel.generic.SwitchBuilder;
import org.mohajo.studyrepublic.admin.AdminMemberServiceImpl;
import org.mohajo.studyrepublic.domain.Member;
import org.mohajo.studyrepublic.member.MemberController;
import org.mohajo.studyrepublic.member.MemberServiceImpl;
import org.mohajo.studyrepublic.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * @author 윤성호
 * @since 2019.01.22
 * @version -기능1 추가 -기능2 추가 -기능3 추가
 */

@ComponentScan
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.security.web.authentication.AuthenticationFailureHandler#
	 * onAuthenticationFailure(javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse,
	 * org.springframework.security.core.AuthenticationException)
	 */
	
	@Autowired
	MemberRepository memberrepository;
	

	

	private String loginidname;
	private String loginpasswdname;
	private String loginredirectname;
/*	private String goodbyeMSG;
	private String pauseMSG;*/
	private String exceptionmsgname;
	private String defaultFailureUrl;

	public CustomAuthenticationFailureHandler() {
		this.loginidname = "김치";
		this.loginpasswdname = "감자";
		this.loginredirectname = "index";
		this.exceptionmsgname = "아이디 또는 패스워드가 일치하지 않습니다.";
/*		this.goodbyeMSG = "본 계정은 탈퇴한 계정입니다.";
		this.pauseMSG = "본 계정은 정지된 계정입니다.";*/
		this.defaultFailureUrl = "/login";
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
	 * @return the loginpasswdname
	 */
	public String getLoginpasswdname() {
		return loginpasswdname;
	}

	/**
	 * @param loginpasswdname the loginpasswdname to set
	 */
	public void setLoginpasswdname(String loginpasswdname) {
		this.loginpasswdname = loginpasswdname;
	}

	/**
	 * @return the loginredirectname
	 */
	public String getLoginredirectname() {
		return loginredirectname;
	}

	/**
	 * @param loginredirectname the loginredirectname to set
	 */
	public void setLoginredirectname(String loginredirectname) {
		this.loginredirectname = loginredirectname;
	}

	/**
	 * @return the goodbyeMSG
	 */
/*	public String getGoodbyeMSG() {
		return goodbyeMSG;
	}

	*//**
	 * @param goodbyeMSG the goodbyeMSG to set
	 *//*
	public void setGoodbyeMSG(String goodbyeMSG) {
		this.goodbyeMSG = goodbyeMSG;
	}

	*//**
	 * @return the pauseMSG
	 *//*
	public String getPauseMSG() {
		return pauseMSG;
	}

	*//**
	 * @param pauseMSG the pauseMSG to set
	 *//*
	public void setPauseMSG(String pauseMSG) {
		this.pauseMSG = pauseMSG;
	}*/

	/**
	 * @return the defaultFailureUrl
	 */
	public String getDefaultFailureUrl() {
		return defaultFailureUrl;
	}

	/**
	 * @return the exceptionmsgname
	 */
	public String getExceptionmsgname() {
		return exceptionmsgname;
	}

	/**
	 * @param exceptionmsgname the exceptionmsgname to set
	 */
	public void setExceptionmsgname(String exceptionmsgname) {
		this.exceptionmsgname = exceptionmsgname;
	}

	/**
	 * @param defaultFailureUrl the defaultFailureUrl to set
	 */
	public void setDefaultFailureUrl(String defaultFailureUrl) {
		this.defaultFailureUrl = defaultFailureUrl;
	}
	
	
	 AdminMemberServiceImpl service = new AdminMemberServiceImpl();
	 MemberServiceImpl mi = new MemberServiceImpl();
	 MemberRepository memberRepository;
	 
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		// TODO Auto-generated method stub
		
		
		String loginid = request.getParameter("username");
		
		System.out.println("로그인 시도 아이디 : " + loginid);

		String memberStauts = UserDetatilsServiceImpl.status;
		
		System.out.println("현재 계정의 멤버 현재 상태는  " + memberStauts + " 상태입니다.");
		
		
		switch (memberStauts) {
			case "P" :
				setExceptionmsgname("정지된 계정입니다.");
				this.exceptionmsgname = "현 계정은 정지된 계정입니다.";	
				break;
			case "E" :
				setExceptionmsgname("탈퇴된 계정입니다.");
				this.exceptionmsgname = "현 계정은 탈퇴된 계정입니다.";	
			default :
				break;
		} 
		

		request.setAttribute("loginidname", loginid);
		request.setAttribute("exceptionmsgname", exceptionmsgname);
		request.getRequestDispatcher(defaultFailureUrl).forward(request, response);
	}


}
