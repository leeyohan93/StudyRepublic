package org.mohajo.studyrepublic.repository;

import org.mohajo.studyrepublic.domain.Study;
import org.mohajo.studyrepublic.domain.StudyMember;
import org.mohajo.studyrepublic.domain.StudyMemberId;
import org.mohajo.studyrepublic.domain.StudyNoticeboard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

/**
 * @author	이미연
 * @since	2019. 1. 24.
 * @version	0.0
 * - 기능 설명 1
 */
public interface StudyMemberRepository extends JpaRepository<StudyMember, StudyMemberId>, QuerydslPredicateExecutor<StudyMember> {

}
