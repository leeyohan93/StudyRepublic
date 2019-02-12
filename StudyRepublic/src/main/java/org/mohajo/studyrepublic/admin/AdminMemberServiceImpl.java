/**
 * 
 */
package org.mohajo.studyrepublic.admin;

import java.util.List;

import org.mohajo.studyrepublic.domain.Member;
import org.mohajo.studyrepublic.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 이요한
 * @since 2019. 2. 7.
 * @version 0.0
 * -기능 설명1
 */
@Service("AdminMemberService")
public class AdminMemberServiceImpl implements AdminMemberService {

	@Autowired
	MemberRepository memberRepository;
	
	@Override
	public List<Member> getMemberList() {
		return memberRepository.findAll();
	}
}
