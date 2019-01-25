package org.mohajo.studyrepublic.security;

import org.mohajo.studyrepublic.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.extern.java.Log;


@Service
@Log
public class UserDetatilsServiceImpl implements UserDetailsService {

	@Autowired
	MemberRepository mr;
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		

	return
		mr.findById(username)
		.filter(m -> m != null)
		.map(m -> new MemberSecurity(m)).get();

	}
}
