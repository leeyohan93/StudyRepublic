package org.mohajo.studyrepublic.repository;

import java.util.List;

import org.mohajo.studyrepublic.domain.StudyMember;
import org.mohajo.studyrepublic.domain.StudyMemberId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

/**
 * @author	이미연
 * @since	2019. 1. 24.
 * @version	0.0
 * - 기능 설명 1
 */
public interface StudyMemberRepository extends JpaRepository<StudyMember, StudyMemberId>, QuerydslPredicateExecutor<StudyMember>{
	
	@Query(value = "select sm from StudyMember sm where sm.id = ?1 and study_member_status_code in ('LE', 'ME')")
	List<StudyMember> findStudyActivityById(String id);
//	List<StudyMember> findStudyActivityByStudyMemberId(StudyMemberId studyMemberId);

	@Query(value = "select sm from StudyMember sm where sm.id = ?1 and study_member_status_code = 'LE'")
	List<StudyMember> findTutorActivityById(String id);
//	List<StudyMember> findTutorActivityByStudyMemberId(StudyMemberId studyMemberId);

	//noticeboard에서 클릭한 글과 관련된 사람의 정보를 가져오는 쿼리
	@Query(value="select *\r\n" + 
			"from (((study_noticeboard left join study_member using (study_id, id))\r\n" + 
			"left join study using (study_id))\r\n" + 
			"left join member using (id))\r\n" + 
			"left join study_member_status_cd using (study_member_status_code)\r\n" + 
			"where study.study_id = :studyId AND member.id = :id AND study_noticeboard.number=:number", nativeQuery=true)
	StudyMember findNoticeboardMemberByStudyIdANDIdANDNumber(@Param(value="studyId") String studyId, @Param(value="id") String id, @Param(value="number") int number);
	
	@Query(value="select nickname,id, content, date, replygroup, replystep, profile_save_name\r\n" + 
			"from (select snr.id, snr.content, snr.date, snr.replygroup, snr.replystep\r\n" + 
			"from study_noticeboard sn left join study_noticeboard_reply snr using (study_noticeboard_id, study_id)\r\n" + 
			"where study_id=:studyId and number=:number) as result join member using (id)\r\n" + 
			"order by replygroup asc, replystep asc", nativeQuery=true)
	List<StudyMember> findReplyMemberByStudyIdANDNumber(@Param(value="studyId") String studyId, @Param(value="number") int number);
	
	/*
	 * 심각한 문제를 일으킨 부분. select * 은 되지만, 특정 칼럼을 뽑아올 경우 SQL 에러가 발생함. 실제로 SQL에서는 문제가 없음.
	 * @Query(value = "select study_noticeboard.number, day, title, location, status\r\n" + 
			"from (((study_noticeboard left join study_member using (study_id, id))\r\n" + 
			"left join study using (study_id))\r\n" + 
			"left join member using (id))\r\n" + 
			"left join study_member_status_cd using (study_member_status_code)\r\n" + 
			"where study_id = :studyId AND status = 0\r\n" + 
			"order by number desc", nativeQuery = true)
	List<StudyMember> findNoticeboardListByStudyId(@Param String studyId);*/
	@Query(value="select *\r\n" + 
			"from (\r\n" + 
			"select *\r\n" + 
			"from study_member left join study_qnaboard using (study_id, id)\r\n" + 
			"where study_id = \"bb00001\" and status = 0) result\r\n" + 
			"left join member using (id)\r\n" + 
			"order by study_qnaboard_id desc", nativeQuery=true)
	List<StudyMember> findQnaboardInfoByStudyId(@Param(value="studyId") String studyId);
}
