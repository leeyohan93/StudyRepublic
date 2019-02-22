
package org.mohajo.studyrepublic.repository;


import java.util.List;

import org.mohajo.studyrepublic.domain.AnalyticsDTO;
import org.mohajo.studyrepublic.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


/**
 * @author 윤성호
 * @since 2019.01.22
 * @version
 * - AdminCommentRepository추가
 * 
 */

public interface MemberRepository extends JpaRepository<Member, String>, QuerydslPredicateExecutor<Member> {
	
	@Query(value = "select * from StudyRepublic.member order by StudyRepublic.member.registration_date", nativeQuery=true)
	List <Member> findAll();	// 기존에 내장되어있던 findAll() 메서드를 오버라이드함. (가입일 기준으로 오름차순 하기 위함.)
	
	@Query(value = "select count(*) from member where id = :id", nativeQuery = true)
	int checkId(String id);
	
	@Query(value = "select count(*) from member where nickname = :nickname", nativeQuery = true)
	int checkNick(String nickname);
	
/*	@Query (value = "SELECT * FROM member where MEMBER_STATUS_CODE != 'P' AND MEMBER_STATUS_CODE != 'E' AND id = :id", nativeQuery = true)
	Member findMember(String id);*/
	
	@Query (value = "SELECT * FROM member where id = :id", nativeQuery = true)
	Member findMember(String id);
	
	@Query(value = "select * from recommend_tutor_member",nativeQuery=true)
	List<Member> getRecommendTutorMember();
	
	/*@Query(value = "select m.id, f.title,f.date,f.likecount, f.hit from (select * from member where id=:id)m, freeboard f where m.id=f.id", nativeQuery=true)
	List<FreeBoard> findAllBoardById(String id);*/
	
	/*@Query(value="select m.id, f.title,f.date,f.likecount, f.hit from (select * from member where id=:id) m, freeboard f where m.id=f.id ;"+"union select m.id, q.title,q.date,q.likecount, q.hit from (select * from member where id=:id) m, inquireboard q where m.id=q.id " + "union select m.id, r.title,r.date,r.likecount, r.hit from (select * from member where id=:id) m, requestboard r where m.id=r.id",nativeQuery=true)
	List<Board> findAllBoardById(String id);
*/
	
	@Query(value = "select count(*) from member where email=:email", nativeQuery = true)
	int checkemail(String email);
	
	@Query(value="select count(*) from member where id=:id and password = :password", nativeQuery = true)
	int checkpassword(String id,String password);
	
	/*회원탈퇴 버튼 클릭시 회원상태코드 변경*/
	@Transactional
	@Modifying
	@Query(value="update member set member_status_code='E' where id = :id", nativeQuery = true)
	int memberExit(String id);

	@Modifying
	@Query(value="update member set password = :password where id = :id", nativeQuery = true)
	void saveById(String password);
	
	@Transactional
	@Modifying
	@Query(value="update member set profile_origin_name = :profileOriginName, profile_save_name = :profileSaveName where id = :id", nativeQuery = true)
	int changeProfile(String profileOriginName, String profileSaveName,String id);
	
	@Query(value = "select * from member where id in (:selectedId)",nativeQuery=true)
	List<Member> getSelectedMember(String[] selectedId);
	
	@Query(value = "select * from member where  grade_code=:gradeCode",nativeQuery=true)
	List<Member> getTutorMember(String gradeCode);
	
	@Query(value = "SELECT MID(registration_date,1,7) period, COUNT(*) as count FROM member GROUP BY period order by period desc",nativeQuery=true)
	List<Object[]> getMemberCount();
	@Query(value = "SELECT MID(registration_date,1,7) period, COUNT(*) as count FROM member where grade_code='T' GROUP BY period order by period desc;",nativeQuery=true)
	List<Object[]> getTutorCount();
	
	@Query(value = "SELECT MID(registration_date,1,4) period, COUNT(*) as count FROM member GROUP BY period order by period desc",nativeQuery=true)
	List<Object[]> getMemberCountYear();
	@Query(value = "SELECT MID(registration_date,1,4) period, COUNT(*) as count FROM member where grade_code='T' GROUP BY period order by period desc;",nativeQuery=true)
	List<Object[]> getTutorCountYear();

}

	
