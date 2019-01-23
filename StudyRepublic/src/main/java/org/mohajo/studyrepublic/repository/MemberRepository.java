package org.mohajo.studyrepublic.repository;


import java.util.List;

import org.mohajo.studyrepublic.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @author 이요한
 * @since 2019.01.22
 * @version
 * - AdminCommentRepository추가
 * 
 */

public interface MemberRepository extends JpaRepository<Member, String>{
	
	@Query(value = "select * from StudyRepublic.member order by StudyRepublic.member.registration_date", nativeQuery=true)
	List <Member> findAll();	// 기존에 내장되어있던 findAll() 메서드를 오버라이드함. (가입일 기준으로 오름차순 하기 위함.)
	
}
