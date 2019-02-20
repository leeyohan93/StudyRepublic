package org.mohajo.studyrepublic.member;
import org.mohajo.studyrepublic.domain.Member;

/**
 * 
 */

/**
 * @author 윤성호
 * @since 2019.01.22
 * @version
 * -기능1 추가
 * -기능2 추가
 * -기능3 추가
 */

public interface MemberService {

	Member findMember(String id);
	
	int CheckId(String id);
	
}
