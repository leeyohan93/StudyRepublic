/**
 * 
 */
package org.mohajo.studyrepublic.repository;

import java.util.List;

import org.mohajo.studyrepublic.domain.RequestBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @author 윤원식
 * @since 2019. 1. 23.
 * @version
 * -RequestBoardRepository 클래스 추가
 */

public interface RequestBoardRepository extends JpaRepository<RequestBoard, Integer> {
	@Query(value="select * from member m join requestboard r on m.id=r.id where m.id=:id",nativeQuery=true)
	List<RequestBoard> findRequestBoardById(String id);
}
