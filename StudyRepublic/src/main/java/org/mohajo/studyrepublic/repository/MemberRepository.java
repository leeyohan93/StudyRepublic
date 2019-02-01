
package org.mohajo.studyrepublic.repository;


import java.util.List;
import java.util.Map;

import org.mohajo.studyrepublic.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @author 윤성호
 * @since 2019.01.22
 * @version
 * - AdminCommentRepository추가
 * 
 */

public interface MemberRepository extends JpaRepository<Member, String>{
	
	@Query(value = "select * from StudyRepublic.member order by StudyRepublic.member.registration_date", nativeQuery=true)
	List <Member> findAll();	// 기존에 내장되어있던 findAll() 메서드를 오버라이드함. (가입일 기준으로 오름차순 하기 위함.)
	
	@Query(value = "select count(*) from member where id = :id", nativeQuery = true)
	int checkId(String id);
	
	@Query(value = "select count(*) from member where nickname = :nickname", nativeQuery = true)
	int checkNick(String nickname);

	@Query(value = "select * from recommend_tutor_member",nativeQuery=true)
	List<Member> getRecommendTutorMember();

}

	
