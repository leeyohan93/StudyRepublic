/**
 * 
 */
package org.mohajo.studyrepublic.repository;

import org.mohajo.studyrepublic.domain.InquireBoard;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author 윤원식
 * @since 2019. 1. 23.
 * @version
 * -InquireBoardRepository 클래스 추가
 */

public interface InquireBoardRepository extends JpaRepository<InquireBoard, Integer> {

}
