
package org.mohajo.studyrepublic.repository;


import java.util.List;
import java.util.Map;

import org.mohajo.studyrepublic.domain.Member;
import org.mohajo.studyrepublic.domain.MemberPoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @author 윤성호
 * @since 2019.01.22
 * @version
 * - AdminCommentRepository추가
 * 
 */

public interface MemberPointRepository extends JpaRepository<MemberPoint, String>{
	
			@Query(value = "select * from member_point where id = :id", nativeQuery = true) 
			MemberPoint inqueryPoint(String id);

}

	
