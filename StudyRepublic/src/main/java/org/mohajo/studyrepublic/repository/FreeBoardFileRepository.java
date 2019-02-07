/**
 * 
 */
package org.mohajo.studyrepublic.repository;

import java.util.List;

import org.mohajo.studyrepublic.domain.Board;
import org.mohajo.studyrepublic.domain.FreeBoardFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * @author 윤원식
 * @since 2019. 1. 23.
 * @version
 * -FileBoardFileRepository클래스 추가
 */

public interface FreeBoardFileRepository extends JpaRepository<FreeBoardFile, Integer> {
	
}
