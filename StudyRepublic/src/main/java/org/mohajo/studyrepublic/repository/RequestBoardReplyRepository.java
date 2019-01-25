/**
 * 
 */
package org.mohajo.studyrepublic.repository;

import org.mohajo.studyrepublic.domain.RequestBoardReply;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author 윤원식
 * @since 2019. 1. 23.
 * @version
 * -RequestBoardReplyRepository클래스 추가
 */

public interface RequestBoardReplyRepository extends JpaRepository<RequestBoardReply, Integer> {

}
