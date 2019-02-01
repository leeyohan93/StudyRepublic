/**
 * 
 */
package org.mohajo.studyrepublic.repository;

import java.util.List;

import org.mohajo.studyrepublic.domain.InquireBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @author 윤원식
 * @since 2019. 1. 23.
 * @version
 * -InquireBoardRepository 클래스 추가
 */

public interface InquireBoardRepository extends JpaRepository<InquireBoard, Integer> {
	@Query(value = "select * from member m join inquireboard i on m.id=i.id where m.id=:id",nativeQuery=true)
	List<InquireBoard>findInquireBoard(String id);
}
