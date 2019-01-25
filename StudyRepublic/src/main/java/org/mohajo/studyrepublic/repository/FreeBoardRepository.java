/**
 * 
 */
package org.mohajo.studyrepublic.repository;

import org.mohajo.studyrepublic.domain.FreeBoard;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author 윤원식
 * @since 2019. 1. 23.
 * @version
 * -FreeBoardRepository 클래스 추가
 */

public interface FreeBoardRepository extends JpaRepository<FreeBoard, Integer> {

}
