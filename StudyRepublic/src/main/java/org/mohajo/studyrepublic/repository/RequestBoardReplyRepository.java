/**
 * 
 */
package org.mohajo.studyrepublic.repository;

import java.util.List;

import org.mohajo.studyrepublic.domain.FreeBoardReply;
import org.mohajo.studyrepublic.domain.RequestBoardReply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * @author 윤원식
 * @since 2019. 1. 23.
 * @version
 * -RequestBoardReplyRepository클래스 추가
 */

public interface RequestBoardReplyRepository extends JpaRepository<RequestBoardReply, Integer> {

	@Query(value="select count(f.requestBoardReplyId) from RequestBoardReply f where f.requestBoardId =:rbi")
	public int replyCount(@Param("rbi")int requestBoardId);
	
	//댓글 리스트 출력
	@Query(value="select * from requestboard_reply where requestboard_id =:f1 order by replygroup asc", nativeQuery=true)
	 List<RequestBoardReply> findRequestBoardReply(@Param("f1")int requestBoardId);
	
}
