/**
 * 
 */
package org.mohajo.studyrepublic.repository;


import java.util.List;

import org.mohajo.studyrepublic.domain.FreeBoard;
import org.mohajo.studyrepublic.domain.QFreeBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;

/**
 * @author 윤원식
 * @since 2019. 1. 23.
 * @version
 * -FreeBoardRepository 클래스 추가
 */

public interface FreeBoardRepository extends JpaRepository<FreeBoard, Integer>, QuerydslPredicateExecutor<FreeBoard>{


	public default Predicate makePredicate(String searchType, String keyword, String searchPeriod) {

		BooleanBuilder builder = new BooleanBuilder();

		QFreeBoard freeboard = QFreeBoard.freeBoard;

		if(searchType ==null ) {
			return builder;
		}


		switch(searchType){
		case "all":
			builder.and(freeboard.title.like("%" + keyword +"%")).or(freeboard.content.like("%"+ keyword+ "%"));
			break;
		case "title":
			builder.and(freeboard.title.like("%" + keyword +"%"));
			break;
		case "content":
			builder.and(freeboard.content.like("%" + keyword + "%"));
			break;
		case "writer":
			builder.and(freeboard.id.like("%" + keyword + "%"));
            break;
		}

		
		return builder;
	}
	
	@Query(value = "select * from member m join freeboard f on m.id=f.id where m.id=:id", nativeQuery=true)
	List<FreeBoard> findFreeBoardById(String id);
	
	
	@Query(value = "select * from freeboard f where :fi > f.freeboard_id limit 1", nativeQuery=true)
	FreeBoard beForeBoard(@Param("fi")int freeBoardId);
	
	@Query(value = "select * from freeboard f where f.freeboard_id >:fi limit 1", nativeQuery=true)
	FreeBoard beAfterBoard(@Param("fi")int freeBoardId);






}
