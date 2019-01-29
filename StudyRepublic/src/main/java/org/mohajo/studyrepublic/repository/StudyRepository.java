package org.mohajo.studyrepublic.repository;

import java.util.List;

import org.mohajo.studyrepublic.domain.Study;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

/**
 * @author	이미연
 * @since	2019. 1. 22.
 * @version	0.0
 * - 기능 설명 1
 */
public interface StudyRepository extends JpaRepository<Study, String>,QuerydslPredicateExecutor<Study> {

	@Query(value="select * from popular_study where TYPE_CODE='P' order by rand() limit 2",nativeQuery=true)
	public List<Study> findPrStudyBytypeCode();
	
	@Query(value="select * from study natural join study_interest where interest_2_code in ('P02','P08') "
			+ "and STUDY_STATUS_CODE ='O' order by rand() limit 2",nativeQuery=true)
	public List<Study> findBsStudyBytypeCode();
	
	
	
	
}
