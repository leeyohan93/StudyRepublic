package org.mohajo.studyrepublic.repository;


import org.mohajo.studyrepublic.domain.Tutor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @author 이요한
 * @since 2019.01.22
 * @version
 * - AdminCommentRepository추가
 * 
 */

public interface TutorRepository extends JpaRepository<Tutor, Integer>{

	@Query(value = "select t from Tutor t where t.id = ?1")
	public Tutor findByMemberId(String id);
	
	

}
