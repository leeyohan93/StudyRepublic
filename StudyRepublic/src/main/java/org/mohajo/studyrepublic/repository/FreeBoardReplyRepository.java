/**
 * 
 */
package org.mohajo.studyrepublic.repository;

import java.util.List;

import org.mohajo.studyrepublic.domain.FreeBoard;
import org.mohajo.studyrepublic.domain.FreeBoardReply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * @author 윤원식
 * @since 2019. 1. 23.
 * @version
 * -FreeBoardReplyRepository클래스 추가
 */

public interface FreeBoardReplyRepository extends JpaRepository<FreeBoardReply, Integer> {

	@Query(value="select count(f.freeBoardReplyId) from FreeBoardReply f where f.freeBoardId =:fbi")
	public int replyCount(@Param("fbi")int freeBoardId);

}
