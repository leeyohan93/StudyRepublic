package org.mohajo.studyrepublic.admin;

import java.util.List;

import org.mohajo.studyrepublic.domain.Member;

/**
 * @author 이요한
 * @since 2019. 2. 7.
 * @version 0.0
 * -기능 설명1
 */
public interface AdminMemberService {

	List<Member> getMemberList();

	List<Member> changeGrade(String[] selectedId, String changeGrade);

	void sendEmailMessage(String[] to, String subject, String text);

	List<Member> pauseMember(String[] selectedId);

	List<Member> unpauseMember(String[] selectedId);

	List<Member> exitMember(String[] selectedId);

	void changePassword(String memberId, String memberPhonenumber);

	List<Member> getSearchMember(String[] grade, String[] status, String searchKey, String searchValue);

}
