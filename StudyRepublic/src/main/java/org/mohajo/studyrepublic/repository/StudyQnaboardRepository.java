package org.mohajo.studyrepublic.repository;

import org.mohajo.studyrepublic.domain.QStudyQnaboard;
import org.mohajo.studyrepublic.domain.StudyNoticeboard;
import org.mohajo.studyrepublic.domain.StudyQnaboard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;

public interface StudyQnaboardRepository extends JpaRepository<StudyQnaboard, Integer>, QuerydslPredicateExecutor<StudyQnaboard>{

	
	public default Predicate makePredicate(String studyId) {
		
		BooleanBuilder builder = new BooleanBuilder();
		QStudyQnaboard studyqnaboard = QStudyQnaboard.studyQnaboard;
		
		builder.and(studyqnaboard.studyId.like(studyId));
		
		return builder; 
	}
	
	@Query(value="select *\r\n" + 
			"from study_qnaboard left join study_member using (study_id, id)\r\n" + 
			"where study_id=:studyId and number=:number and id=:userId and (study_member_status_code = :power1 or study_member_status_code= :power2)"
			+ "and status = :status", nativeQuery=true)
	StudyQnaboard findQnaboardByStudyIdAndNumberAndStatus(@Param(value="studyId") String studyId, @Param(value="number") int number,
			@Param(value="userId") String userId, @Param(value="power1") String power1, @Param(value="power2") String power2,
			@Param(value="status") int status);
}
