package org.mohajo.studyrepublic.repository;


import java.util.List;

import org.mohajo.studyrepublic.domain.Tutor;
import org.mohajo.studyrepublic.domain.TutorCareer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * @author 윤성호
 * @since 2019.01.22
 * @version
 * - AdminCommentRepository추가
 * 
 */

public interface TutorCareerRepository extends JpaRepository<TutorCareer, Integer>{

/*	@Query(value = "select * from tutor where id = :id", nativeQuery = true)
	Tutor findByTutor(String id);*/
	
	@Query(value = "select * from tutor_career where tutor_number = :tutor_number", nativeQuery = true)
	List<TutorCareer> selectedtutorcareer(int tutor_number);
	
}
