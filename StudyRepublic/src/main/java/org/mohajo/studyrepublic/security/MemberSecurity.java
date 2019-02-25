package org.mohajo.studyrepublic.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.mohajo.studyrepublic.domain.Member;
import org.mohajo.studyrepublic.domain.MemberRoles;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import lombok.Data;



@Data
public class MemberSecurity extends User {

/*	public MemberSecurity(String username, String password, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
	}*/

	private static final String ROLE_PREFIX = "ROLE_";
	
	private Member member;
	private String name;
	
	
	public MemberSecurity(String username, String password, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
	}
	
	public MemberSecurity(Member member) {
		super(member.getId(), member.getPassword(), makeGrantedAuthority(member.getRoles()));
		this.member = member;
	}
	

	
	private static List<GrantedAuthority> makeGrantedAuthority(List<MemberRoles> roles) {
		List <GrantedAuthority> list = new ArrayList<>();	
		roles.forEach(role -> list.add(new SimpleGrantedAuthority(ROLE_PREFIX + role.getRoleName())));
		return list;
		
	}
	
	




	
	
	
}
