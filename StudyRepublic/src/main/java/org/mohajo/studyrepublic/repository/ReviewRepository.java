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

	@Query(value = "select * from review r where r.study_id = ?1 and r.id <> ?2",nativeQuery=true)
	public List<Review> findByStudyId(String studyId, String id);
	
	@Query(value="select * from review r where r.study_id in ?1 and r.id=?2",nativeQuery=true)
	public List<Review> findStudyReview(String[] studyList, String id);
	
}
