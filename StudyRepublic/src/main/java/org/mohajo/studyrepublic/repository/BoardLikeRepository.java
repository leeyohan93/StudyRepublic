/**
 * 
 */
package org.mohajo.studyrepublic.repository;

import java.util.List;

import org.jboss.logging.Param;
import org.mohajo.studyrepublic.domain.BoardLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @author 윤원식
 * @since 2019. 2. 15.
 * @version
 * -기능설명1
 */

public interface BoardLikeRepository extends JpaRepository<BoardLike, Integer> {

   @Query(value = "select * from boardlike b where b.board_writer =:memberId and b.board_id =:freeBoardId", nativeQuery=true)
   BoardLike findBoardLike(int freeBoardId,String memberId);

}
