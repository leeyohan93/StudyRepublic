package org.mohajo.studyrepublic.security;

import javax.servlet.http.HttpSession;

import org.mohajo.studyrepublic.domain.Member;
import org.mohajo.studyrepublic.member.MemberController;
import org.mohajo.studyrepublic.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import lombok.extern.java.Log;


@Service
@Log
public class UserDetatilsServiceImpl implements UserDetailsService {

	@Autowired
	MemberRepository mr;

	@Autowired
	MemberController membercontroller;
	
	public static String status = "일반";
	
	public static String socketId;


	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	
		ServletRequestAttributes servletRequestAttribute = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpSession session = servletRequestAttribute.getRequest().getSession(true); //세션 강제생성.
		
		
		status = mr.findById(username).get().getMemberStatusCD().getMemberStatusCode();
		
		Member member = mr.findById(username).get();       
		socketId = member.getId();
		
		System.out.println("회원상태: " + status);
		
		if(status.equals("P")) {
			System.out.println("정지된 회원입니다");
			return null  ;
		} else if (status.equals("E")) {
			System.out.println("탈퇴한 회원입니다.");
			return null;
		} 
		else {
				membercontroller.createSession(session, member);	// 세션호출
				
					return
					mr.findById(username)
					.filter(m -> m != null)
					.map(m -> new MemberSecurity(m)).get();
					
		}

		

	}
	

}
