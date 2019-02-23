package org.mohajo.studyrepublic.security;

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
		
		Member member = mr.findById(username).get();

			
			
		System.out.println("회원상태: " + status);
		
		if(status.equals("P")) {
			System.out.println("정지된 회원입니다");
			return null  ;
		} else if (status.equals("E")) {
			System.out.println("탈퇴한 회원입니다.");
			return null;
		} 
		else {
			System.out.println("진행중?");
			System.out.println("아이디 : " + member.getId());
			System.out.println("비번 : " + member.getPassword());
			System.out.println("권한 : " + member.getRoles());
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
	
/*    @Transactional
    public UserDetails loadUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(
            () -> new ResourceNotFoundException("User", "id", id)
        );

        return UserPrincipal.create(user);
    }*/
	

}
