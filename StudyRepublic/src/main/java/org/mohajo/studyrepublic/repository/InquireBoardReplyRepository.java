/**
 * 
 */
package org.mohajo.studyrepublic.repository;

import org.mohajo.studyrepublic.domain.InquireBoardReply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * @author 윤원식
 * @since 2019. 1. 23.
 * @version
 * -InquireBoardReplyRepository클래스 추가
 */

public interface InquireBoardReplyRepository extends JpaRepository<InquireBoardReply, Integer> {
  
	@Query(value="select count(f.inquireBoardReplyId) from InquireBoardReply f where f.inquireBoardId =:ibi")
	public int replyCount(@Param("ibi")int inquireBoardId);
}
