package org.mohajo.studyrepublic.repository;

import java.util.List;

import org.mohajo.studyrepublic.domain.QStudyNoticeboard;
import org.mohajo.studyrepublic.domain.QStudyQnaboard;
import org.mohajo.studyrepublic.domain.StudyMember;
import org.mohajo.studyrepublic.domain.StudyNoticeboard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;

public interface StudyNoticeboardRepository extends JpaRepository<StudyNoticeboard, Integer>, QuerydslPredicateExecutor<StudyNoticeboard>{

//	@Query(value = "select * from study_noticeboard left join study_noticeboard_reply using (study_noticeboard_id)", nativeQuery=true)
//	List<StudyNoticeboard> joinReply();
	
	/*@Query(value = "select * from study_member as sm where sm.study_id=:study_id&& sm.id=:id", nativeQuery=true)
	String checkIdStudyId(@Param("study_id") String study_id, @Param("id") String id);*/
	
//	@Query(value = "select sn from StudyNoticeboard as sn where sn.studyId=:studyId and sn.number=:noticeboardNumber")
/*	@Query(value = "select sn from StudyNoticeboard as sn where sn.studyId=:rbc.studyId and sn.number=:rbc.number")
	StudyNoticeboard findByNoticeboardNumberAndStudyId(StudyNoticeboard rbc); 이거 안됨*/
	@Query(value = "select *\r\n" + 
			"from study_noticeboard\r\n" + 
			"where study_id = :studyId and status = 0\r\n" + 
			"order by study_noticeboard_id desc", nativeQuery = true)
	List<StudyNoticeboard> findNoticeboardListByStudyId(@Param(value="studyId") String studyId);
	
	//스터디 id, 글번호를 바탕으로 글 내용, 덧글 추출
	@Query(value = "select *\r\n" + 
			"from study_noticeboard left join study_noticeboard_reply using (study_noticeboard_id)\r\n" + 
			"where study_noticeboard.study_id=:studyId and number=:number\r\n" + 
			"order by replygroup asc, replystep asc", nativeQuery=true)
	StudyNoticeboard findNoticeboardByStudyIdANDNumber(@Param(value="studyId") String studyId, @Param(value="number") int number);
	

	public default Predicate makePredicate(String studyId) {
		BooleanBuilder builder = new BooleanBuilder();

		QStudyNoticeboard studynoticeboard = QStudyNoticeboard.studyNoticeboard;

		builder.and(studynoticeboard.studyId.like(studyId));

		return builder; 
	}
}
