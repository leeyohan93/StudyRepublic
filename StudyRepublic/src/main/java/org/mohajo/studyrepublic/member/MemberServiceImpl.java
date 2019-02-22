/**
 * 
 */
package org.mohajo.studyrepublic.member;

import org.mohajo.studyrepublic.domain.Member;
import org.mohajo.studyrepublic.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 윤성호
 * @since 2019.01.22
 * @version
 * -기능1 추가
 * -기능2 추가
 * -기능3 추가
 */

@Service("MemberService")
public class MemberServiceImpl implements MemberService {

	@Autowired
	MemberRepository memberrepository;
	/* (non-Javadoc)
	 * @see org.mohajo.studyrepublic.member.MemberService#findByMember(java.lang.String)
	 */
	@Override
	public Member findMember(String id) {
		System.out.println("memberrepository : " + memberrepository);
		// TODO Auto-generated method stub
		return memberrepository.findMember(id);
	}
	/* (non-Javadoc)
	 * @see org.mohajo.studyrepublic.member.MemberService#CheckId(java.lang.String)
	 */
	@Override
	public int CheckId(String id) {
		// TODO Auto-generated method stub
		return memberrepository.checkId(id);
	}

}
