package org.mohajo.studyrepublic.repository;


import org.mohajo.studyrepublic.domain.Tutor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @author 윤성호
 * @since 2019.01.22
 * @version
 * - AdminCommentRepository추가
 * 
 */

public interface TutorRepository extends JpaRepository<Tutor, Integer>{

	@Query(value = "select * from tutor where id = :id", nativeQuery = true)
	Tutor findByTutor(String id);
	
}
