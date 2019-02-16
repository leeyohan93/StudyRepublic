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
	
	@Query(value = "select sm from StudyMember sm where sm.id = ?1 and study_member_status_code in ('LE', 'ME') order by study.endDate DESC")
	List<StudyMember> findStudyActivityById(String id);
//	List<StudyMember> findStudyActivityByStudyMemberId(StudyMemberId studyMemberId);

	@Query(value = "select sm from StudyMember sm where sm.id = ?1 and study_member_status_code = 'LE'")
	List<StudyMember> findTutorActivityById(String id);
//	List<StudyMember> findTutorActivityByStudyMemberId(StudyMemberId studyMemberId);

	
	/*스터디가 일반스터디이고 진행중이며, 스터디 리더거나, 멤버일때 스터디 정보 가져오기 */
	@Query(value="select * from (select * from study_member where id=:id AND (study_member_status_code = 'ME' || study_member_status_code = 'LE')) a1 join study s1 using (study_id) where s1.study_status_code='O'",nativeQuery=true)
	List<StudyMember> findActivityById(String id);
	
	/*스터디가 일반스터디이고 진행중이며, 스터디 리더거나, 멤버일때 스터디 정보 가져오기(카운터방식으로) */
	@Query(value="select count(*) from (select * from study_member where id=:id AND (study_member_status_code = 'ME' || study_member_status_code = 'LE')) a1 join study s1 using (study_id) where  type_code='B' and s1.study_status_code='G'",nativeQuery=true)
	int studycount(String id); 
	
	@Query(value="select * from study_member sm join study s on sm.study_id=s.study_id where sm.id= :id",nativeQuery=true)
	List<StudyMember> findBasicByList(String id);
	
	/*일반스터디 스터디 정보가져오기(스터디테이블사용)*/
	@Query(value="select * from study_member sm left outer join study_view sv on sm.study_id = sv.study_id where sm.id = :id and sv.type_code='B' order by start_date DESC",nativeQuery=true)
	List<StudyMember> findbasicStudylist(String id);
	
	/*프리미엄스터디  스터디 정보가져오기(스터디테이블사용)*/
	@Query(value="select * from study_member sm left outer join study_view sv on sm.study_id = sv.study_id where sm.id = :id and sv.type_code='P' order by start_date DESC",nativeQuery=true)
	List<StudyMember> findPremiumStudylist(String id);
	/*스터디 정보 가져오는데 일반스터디 이고 진행중인 스터디 상위 1개 가져오기(가져오는갯수 수정가능) 마이페이지 메인에서 사용 */
	@Query(value="select * from study_member sm left outer join study_view sv on sm.study_id = sv.study_id where sm.study_member_status_code in ('LE', 'ME') and sm.id = :id  and sv.type_code = 'B' AND sv.STUDY_STATUS_CODE='G' order by enroll_date DESC limit 1",nativeQuery=true)
	List<StudyMember> findstudyall(String id);
	/*스터디 정보 가져오는데 프리미엄 이고 진행중인 스터디 상위 1개 가져오기(가져오는갯수 수정가능) 마이페이지 메인에서 사용 */
	@Query(value="select * from study_member sm left outer join study_view sv on sm.study_id = sv.study_id where sm.study_member_status_code in ('LE', 'ME') and sm.id = :id  and sv.type_code = 'P' AND sv.STUDY_STATUS_CODE='G' order by enroll_date DESC limit 1",nativeQuery=true)
	List<StudyMember> findstudyall2(String id);
	/*위에꺼에 맴버상태넣지않은것*/
	@Query(value="select * from study_member sm left outer join study_view sv on sm.study_id = sv.study_id where sm.id = :id",nativeQuery=true)
	List<StudyMember> findStudyMemberStatus(String id);

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

	StudyMember findByStudyIdAndId(String studyId, String userId);

	//테스트 시작.
	@Query(value="select sm from StudyMember sm where sm.studyId = ?1")
	List<StudyMember> selectByStudyId(String string);
	//테스트 끝.
	
	@Query(value="select *\r\n" + 
			"from study_member left join member using (id)\r\n" + 
			"where (study_member_status_code = :code1 or study_member_status_code = :code2) and study_id = :studyId", nativeQuery=true)
	List<StudyMember> findStudyMemberLEMEbyStudyIdANDStudyStatusCode(@Param(value="studyId") String studyId, @Param(value="code1") String code1, @Param(value="code2") String code2);

	@Query(value="select *\r\n" + 
			"from study_member left join member using (id)\r\n" + 
			"where study_member_status_code = :code1 and study_id = :studyId", nativeQuery=true)
	List<StudyMember> finStudyMemberWAbyStudyIdANDStudyStatusCode(@Param(value="studyId") String studyId, @Param(value="code1") String code1);
	
	@Query( value = "select  *from study_member sm left join study s using (study_id)where id=:id and (sm.study_member_status_code = 'ME'  or sm.study_member_status_code = 'LE')", nativeQuery = true)
	List <StudyMember> joinedstudymember(String id);
	
	/*@Query(value = "")*/
	
}

