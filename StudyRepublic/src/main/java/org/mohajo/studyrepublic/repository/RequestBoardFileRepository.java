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

	
	public default Predicate makePredicate(String searchType, String keyword) {

		BooleanBuilder builder = new BooleanBuilder();

		QRequestBoard requestBoard = QRequestBoard.requestBoard;

		if(searchType ==null ) {
			return builder;
		}

		switch(searchType){
		case "all":
			builder.and(requestBoard.title.like("%" + keyword +"%")).or(requestBoard.content.like("%"+ keyword+ "%"));
			break;
		case "title":
			builder.and(requestBoard.title.like("%" + keyword +"%"));
			break;
		case "content":
			builder.and(requestBoard.content.like("%" + keyword + "%"));
			break;
		case "writer":
			builder.and(requestBoard.id.like("%" + keyword + "%"));
            break;
		}

		
		return builder;
	}
	
	@Query(value = "select * from member m join requestboard f on m.id=f.id where m.id=:id", nativeQuery=true)
	List<RequestBoard> findFreeBoardById(String id);
	
	
	@Query(value="select f from RequestBoard f where f.requestBoardId=:be")
	public RequestBoard findByRequestBoardId(@Param("be") int beforeRequestBoard);

}
