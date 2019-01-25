/**
 * 
 */
package org.mohajo.studyrepublic.repository;

import org.mohajo.studyrepublic.domain.RequestBoardFile;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author 윤원식
 * @since 2019. 1. 23.
 * @version
 * -RequestBoardFileRepository클래스 추가
 */

public interface RequestBoardFileRepository extends JpaRepository<RequestBoardFile, Integer> {

}
