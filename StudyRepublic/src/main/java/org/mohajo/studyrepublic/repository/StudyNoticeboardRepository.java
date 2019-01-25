package org.mohajo.studyrepublic.repository;

import java.util.List;

import org.mohajo.studyrepublic.domain.StudyNoticeboard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface StudyNoticeboardRepository extends JpaRepository<StudyNoticeboard, Integer>, QuerydslPredicateExecutor<StudyNoticeboard>{

	@Query(value = "select * from study_noticeboard left join study_noticeboard_reply using (study_noticeboard_id)", nativeQuery=true)
	List<StudyNoticeboard> joinReply();
}
