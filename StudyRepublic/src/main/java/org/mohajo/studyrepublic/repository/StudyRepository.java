package org.mohajo.studyrepublic.repository;

import java.util.List;

import org.mohajo.studyrepublic.domain.Member;
import org.mohajo.studyrepublic.domain.Study;
import org.mohajo.studyrepublic.domain.StudyMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @author	이미연
 * @since	2019. 1. 22.
 * @version	0.0
 * - 기능 설명 1
 */
public interface StudyRepository extends JpaRepository<Study, String> {
	
	@Query(value ="select * from (select * from study_member where id= :member AND (study_member_status_code = 'ME' || study_member_status_code = 'LE')) a1 join study s1"
			+ " using (study_id) where s1.study_status_code='G'", nativeQuery=true)
	List<Study> findByMemberId(Member member);
	

}
