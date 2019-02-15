package org.mohajo.studyrepublic.repository;

import java.util.List;

import org.mohajo.studyrepublic.domain.LeveltestResponse;
import org.mohajo.studyrepublic.domain.LeveltestResponseId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @author	이미연
 * @since	2019. 1. 24.
 * @version	0.0
 * - 기능 설명 1
 */
//public interface LeveltestResponseRepository extends JpaRepository<LeveltestResponse, String>{
public interface LeveltestResponseRepository extends JpaRepository<LeveltestResponse, LeveltestResponseId>{

	@Query(value="select lr from LeveltestResponse lr where lr.studyId = ?1")
	List<LeveltestResponse> selectByStudyId(String studyId);

}
