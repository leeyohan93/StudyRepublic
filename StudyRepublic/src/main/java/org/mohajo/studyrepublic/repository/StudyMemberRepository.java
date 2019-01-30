package org.mohajo.studyrepublic.repository;

import java.util.List;

import org.mohajo.studyrepublic.domain.StudyMember;
import org.mohajo.studyrepublic.domain.StudyMemberId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @author	이미연
 * @since	2019. 1. 24.
 * @version	0.0
 * - 기능 설명 1
 */
public interface StudyMemberRepository extends JpaRepository<StudyMember, StudyMemberId> {

	@Query(value = "select sm from StudyMember sm where sm.id = ?1 and study_member_status_code in ('LE', 'ME')")
	List<StudyMember> findStudyActivityByStudyMemberId(StudyMemberId studyMemberId);

	@Query(value = "select sm from StudyMember sm where sm.id = ?1 and study_member_status_code = 'LE'")
	List<StudyMember> findTutorStudyActivityByStudyMemberId(StudyMemberId studyMemberId);
	

}
