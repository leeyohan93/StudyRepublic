package org.mohajo.studyrepublic.repository;




import org.mohajo.studyrepublic.domain.CareerCD;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * @author 윤성호
 * @since 2019.01.24
 * @version
 * - AdminCommentRepository추가
 * 
 */

public interface CareerCDRepository extends JpaRepository<CareerCD, String>{
	

	
}
