
package org.mohajo.studyrepublic.repository;


import java.util.List;

import javax.transaction.Transactional;

import org.mohajo.studyrepublic.domain.MemberRoles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
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
		
		@Query(value = "select * from member_roles where member = :id", nativeQuery = true)
		MemberRoles findByT(String id);
		

		@Modifying
		@Transactional
		@Query(value = "delete from member_roles where member = :id && role_name = 'W'", nativeQuery = true)
		void deleteTutorWait(String id);
		
		@Modifying
		@Transactional
		@Query(value = "delete from member_roles where member = :id && role_name = 'N'", nativeQuery = true)
		void deleteNormal(String id);
		
		@Modifying
		@Transactional
		@Query(value = "delete from member_roles where member = :id && role_name = 'T'", nativeQuery = true)
		void deleteTutor(String id);
}
