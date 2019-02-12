package org.mohajo.studyrepublic.repository;

import java.util.Date;
import java.util.List;

import org.mohajo.studyrepublic.domain.Member;
import org.mohajo.studyrepublic.domain.QStudy;
import org.mohajo.studyrepublic.domain.Study;
import org.mohajo.studyrepublic.domain.StudyView;
import org.mohajo.studyrepublic.domain.TypeCD;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;


/**
 * @author	이미연
 * @since	2019. 2. 8.
 * @version	0.0
 * - 기능 설명 1
 */
public interface StudyViewRepository extends JpaRepository<StudyView, String>, QuerydslPredicateExecutor<StudyView> {

	@Query(value = "select * from study_view sv where sv.type_code = ?1 and sv.study_status_code not in ('C', 'D')", nativeQuery=true)
	public Page<StudyView> selectValidStudyViewByTypeCode(String typeCode, Pageable paging);

	@Query(value = "select * from study_view sv where sv.study_id = ?1", nativeQuery=true)
	public StudyView selectStudyViewByStudyId(String studyId);
	
}