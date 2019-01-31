
package org.mohajo.studyrepublic.repository;


import java.util.List;

import org.mohajo.studyrepublic.domain.MemberRoles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @author 윤성호
 * @since 2019.01.22
 * @version
 * - AdminCommentRepository추가
 * 
 */

public interface MemberRolesRepository extends JpaRepository<MemberRoles, String>{
	
		@Query(value = "select * from member_roles where member = :id", nativeQuery = true)
		List<MemberRoles> findByRole(String id);
	
}
