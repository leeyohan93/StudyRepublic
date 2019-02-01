
package org.mohajo.studyrepublic.repository;


import java.util.List;
import java.util.Map;

import org.mohajo.studyrepublic.domain.Board;
import org.mohajo.studyrepublic.domain.FreeBoard;
import org.mohajo.studyrepublic.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * @author 윤성호
 * @since 2019.01.22
 * @version
 * - AdminCommentRepository추가
 * 
 */

public interface MemberRepository extends JpaRepository<Member, String>{
	
	@Query(value = "select * from StudyRepublic.member order by StudyRepublic.member.registration_date", nativeQuery=true)
	List <Member> findAll();	// 기존에 내장되어있던 findAll() 메서드를 오버라이드함. (가입일 기준으로 오름차순 하기 위함.)
	
	@Query(value = "select count(*) from member where id = :id", nativeQuery = true)
	int checkId(String id);
	
	@Query(value = "select count(*) from member where nickname = :nickname", nativeQuery = true)
	int checkNick(String nickname);

	@Query(value = "select * from recommend_tutor_member",nativeQuery=true)
	List<Member> getRecommendTutorMember();
	
	/*@Query(value = "select m.id, f.title,f.date,f.likecount, f.hit from (select * from member where id=:id)m, freeboard f where m.id=f.id", nativeQuery=true)
	List<FreeBoard> findAllBoardById(String id);*/
	
	/*@Query(value="select m.id, f.title,f.date,f.likecount, f.hit from (select * from member where id=:id) m, freeboard f where m.id=f.id ;"+"union select m.id, q.title,q.date,q.likecount, q.hit from (select * from member where id=:id) m, inquireboard q where m.id=q.id " + "union select m.id, r.title,r.date,r.likecount, r.hit from (select * from member where id=:id) m, requestboard r where m.id=r.id",nativeQuery=true)
	List<Board> findAllBoardById(String id);
*/

}

	
