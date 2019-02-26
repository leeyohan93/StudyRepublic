/**
 * 
 */
package org.mohajo.studyrepublic.repository;

import java.util.List;

import org.mohajo.studyrepublic.domain.QRequestBoard;
import org.mohajo.studyrepublic.domain.RequestBoard;
import org.mohajo.studyrepublic.domain.RequestBoardFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;

/**
 * @author 윤원식
 * @since 2019. 1. 23.
 * @version
 * -RequestBoardFileRepository클래스 추가
 */

public interface RequestBoardFileRepository extends JpaRepository<RequestBoardFile, Integer> {

	
	@Query(value = "select * from requestboard_file where fullurl = :fullUrl", nativeQuery = true)
	RequestBoardFile findByfullUrl(String fullUrl);
}
