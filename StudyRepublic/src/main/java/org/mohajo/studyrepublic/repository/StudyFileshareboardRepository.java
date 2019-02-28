package org.mohajo.studyrepublic.repository;

import java.util.List;

import org.mohajo.studyrepublic.domain.QStudyFileshareboard;
import org.mohajo.studyrepublic.domain.QStudyNoticeboard;
import org.mohajo.studyrepublic.domain.StudyFileshareboard;
import org.mohajo.studyrepublic.domain.StudyNoticeboard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;

public interface StudyFileshareboardRepository extends JpaRepository<StudyFileshareboard, Integer>, QuerydslPredicateExecutor<StudyFileshareboard>{
	
	public default Predicate makePredicate(String studyId) {
		
		BooleanBuilder builder = new BooleanBuilder();
		QStudyFileshareboard studyfileshareboard = QStudyFileshareboard.studyFileshareboard;
		builder.and(studyfileshareboard.studyId.like(studyId));

		return builder; 
	}
	
	@Query(value = "select *\r\n" + 
			"from study_fileshareboard\r\n" + 
			"where study_id = :studyId and status = 0\r\n" + 
			"order by study_fileshareboard_id desc", nativeQuery = true)
	List<StudyFileshareboard> findFileshareboardListByStudyId(@Param(value="studyId") String studyId);

	@Query(value="select *\r\n" + 
			"from study_qnaboard left join study_member using (study_id, id)\r\n" + 
			"where study_id=:studyId and number=:number and id=:userId and (study_member_status_code = :power1 or study_member_status_code= :power2)"
			+ "and status = :status", nativeQuery=true)
	StudyFileshareboard findFileshareboardByStudyIdAndNumberAndStatus(@Param(value="studyId") String studyId, @Param(value="number") int number,
			@Param(value="userId") String userId, @Param(value="power1") String power1, @Param(value="power2") String power2,
			@Param(value="status") int status);
}
