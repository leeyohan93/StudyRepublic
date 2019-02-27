package org.mohajo.studyrepublic.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.mohajo.studyrepublic.domain.LeveltestResponse;
import org.mohajo.studyrepublic.domain.LeveltestResponseId;
import org.mohajo.studyrepublic.domain.MemberInterest;
import org.mohajo.studyrepublic.domain.Tutor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * @author	이미연
 * @since	2019. 1. 24.
 * @version	0.0
 * - 기능 설명 1
 */
//public interface LeveltestResponseRepository extends JpaRepository<LeveltestResponse, String>{
public interface MemberInterestRepository extends JpaRepository<MemberInterest, Integer>{

	@Query(value = "select * from member_interest where id = :id", nativeQuery = true)
	List <MemberInterest> findMemberInterest(String id);
	
	@Modifying
	@Transactional
	@Query(value = "delete from member_interest where id = :id", nativeQuery = true)
	void deleteInterest(String id);

}
