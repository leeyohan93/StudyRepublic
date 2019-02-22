package org.mohajo.studyrepublic.security;

import org.hibernate.Hibernate;
import org.mohajo.studyrepublic.domain.Member;
import org.mohajo.studyrepublic.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.java.Log;


@Service
@Log
public class UserDetatilsServiceImpl implements UserDetailsService {

	@Autowired
	MemberRepository mr;


	
	public static String status = "일반";
	
	


	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	
		
		
		status = mr.findById(username).get().getMemberStatusCD().getMemberStatusCode();
			
			
		System.out.println("회원상태: " + status);
		
		if(status.equals("P")) {
			System.out.println("정지된 회원입니다");
			return null  ;
		} else if (status.equals("E")) {
			System.out.println("탈퇴한 회원입니다.");
			return null;
		} 
		else {
					return
					mr.findById(username)
					.filter(m -> m != null)
					.map(m -> new MemberSecurity(m)).get();
		}
/*		return
		mr.findMember(username)
		.filter(m -> m != null)
		.map(m -> new MemberSecurity(m)).get();*/

		
//	Hibernate.initialize();

	}
	

}
