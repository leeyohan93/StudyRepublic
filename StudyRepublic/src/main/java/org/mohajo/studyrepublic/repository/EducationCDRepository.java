package org.mohajo.studyrepublic.repository;




import java.util.List;

import org.mohajo.studyrepublic.domain.EducationCD;
import org.mohajo.studyrepublic.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


/**
 * @author 윤성호
 * @since 2019.01.24
 * @version
 * - AdminCommentRepository추가
 * 
 */

public interface EducationCDRepository extends JpaRepository<EducationCD, String>{
	
	@Query(value = "select * from education_cd order by StudyRepublic.education_cd.SEQUENCENUM", nativeQuery=true)
	List <EducationCD> findAll();	// 기존에 내장되어있던 findAll() 메서드를 오버라이드함. (가입일 기준으로 오름차순 하기 위함.)
	
	
}
