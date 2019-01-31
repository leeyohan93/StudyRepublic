package org.mohajo.studyrepublic.repository;

import java.util.List;

import org.mohajo.studyrepublic.domain.StudyMember;
import org.mohajo.studyrepublic.domain.StudyNoticeboard;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import com.querydsl.core.types.Predicate;

public interface StudyNoticeboardRepository extends JpaRepository<StudyNoticeboard, Integer>, QuerydslPredicateExecutor<StudyNoticeboard>{

//	@Query(value = "select * from study_noticeboard left join study_noticeboard_reply using (study_noticeboard_id)", nativeQuery=true)
//	List<StudyNoticeboard> joinReply();
	
	/*@Query(value = "select * from study_member as sm where sm.study_id=:study_id&& sm.id=:id", nativeQuery=true)
	String checkIdStudyId(@Param("study_id") String study_id, @Param("id") String id);*/
}
