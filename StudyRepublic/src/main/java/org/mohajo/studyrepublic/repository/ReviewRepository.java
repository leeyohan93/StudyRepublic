package org.mohajo.studyrepublic.repository;

import java.util.List;

import org.mohajo.studyrepublic.domain.Review;
import org.mohajo.studyrepublic.domain.StudyMemberId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @author	이미연
 * @since	2019. 1. 24.
 * @version	0.0
 * - 기능 설명 1
 */
public interface ReviewRepository extends JpaRepository<Review, Integer> {

	@Query(value = "select r from Review r where r.studyId = ?1 and r.id <> ?2")
	public List<Review> findByStudyId(StudyMemberId studyMemberId);
//	public List<Review> findByStudyId(String studyId, String id);
	
	//상용님 try
//	@Query(value = "select * from review r where r.studyId = :studyId and r.id not 'admin123'", nativeQuery=true)
//	public List<Review> findByStudyId2(@Param("studyId") String str);
}
