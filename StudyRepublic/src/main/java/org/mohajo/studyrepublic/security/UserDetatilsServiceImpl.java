package org.mohajo.studyrepublic.security;

import org.hibernate.Hibernate;
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
	
	
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		

	return
		mr.findById(username)
		.filter(m -> m != null)
		.map(m -> new MemberSecurity(m)).get();
		
//	Hibernate.initialize();

	}
}
