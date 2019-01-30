package org.mohajo.studyrepublic.repository;

import java.util.List;

import org.mohajo.studyrepublic.domain.Review;
import org.mohajo.studyrepublic.domain.StudyMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * @author	이미연
 * @since	2019. 1. 24.
 * @version	0.0
 * - 기능 설명 1
 */
public interface ReviewRepository extends JpaRepository<Review, Integer> {

////	@Query(value = "select s from Study s where s.typeCode = ?1 and s.studyStatusCode not in ('C', 'D')")
////	@Query(value = "select r from Review r where studyMemberId = ?1")	//값 못 가져옮
////	List<Review> findByStudyMemberId(String studyId);
//	@Query(value = "select r from Review r where studyMember = ?1")	//값 못 가져옮
//	List<Review> findByStudyMemberId(StudyMember studyMember);
//	@Query(value = "select r from Review r where r.studyId = ?1")
//	public List<Review> findByStudyId(StudyMember studyMember);
//	@Query(value = "select r from Review r where r.studyId = ?1")
//	public List<Review> findAllByStudyId(String studyId);
	
	@Query(value = "select r from Review r where r.studyId = ?1 and r.id <> ?2")
	public List<Review> findByStudyId(String studyId, String id);
	
	@Query(value = "select * from review r where r.studyId = :studyId and r.id not 'admin123'", nativeQuery=true)
	public List<Review> findByStudyId2(@Param("studyId") String str);
}
