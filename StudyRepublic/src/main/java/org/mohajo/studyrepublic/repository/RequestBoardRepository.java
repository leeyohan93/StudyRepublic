/**
 * 
 */
package org.mohajo.studyrepublic.repository;

import java.util.List;

import org.mohajo.studyrepublic.domain.QRequestBoard;
import org.mohajo.studyrepublic.domain.RequestBoard;
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
 * -RequestBoardRepository 클래스 추가
 */

public interface RequestBoardRepository extends JpaRepository<RequestBoard, Integer>, QuerydslPredicateExecutor<RequestBoard>{
	
	public default Predicate makePredicate(String searchType, String keyword) {

		BooleanBuilder builder = new BooleanBuilder();

		QRequestBoard requestBoard = QRequestBoard.requestBoard;
		
		builder.and(requestBoard.status.eq(0));
		
		

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
			builder.and(requestBoard.member.nickname.like("%" + keyword + "%"));
            break;
		}

		
		return builder;
	}
	
	
	@Query(value = "select * from member m join requestboard f on m.id=f.id where m.id=:id", nativeQuery=true)
	List<RequestBoard> findRequestBoardById(String id);
	
	
	@Query(value="select f from RequestBoard f where f.requestBoardId=:be")
	RequestBoard findByrequestBoardId(@Param("be") int beforeRequestBoard);


	//이전글
		@Query(value = "select * from requestBoard f where f.requestBoard_id <:fi limit 1", nativeQuery=true)
		RequestBoard beForeBoard(@Param("fi")int requestBoardId);

		//다음글
		@Query(value = "select * from requestBoard f where f.requestBoard_id >:fi limit 1", nativeQuery=true)
		RequestBoard beAfterBoard(@Param("fi")int requestBoardId);


		//첨부파일 숨김
		@Query(value="select count(f.originName) from RequestBoardFile f where f.requestBoardId =:fi")
		int fileCount(@Param("fi")int requestBoardId);
	

	
	
}
