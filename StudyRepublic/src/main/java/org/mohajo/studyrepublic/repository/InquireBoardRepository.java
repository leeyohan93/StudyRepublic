/**
 * 
 */
package org.mohajo.studyrepublic.repository;

import java.util.List;

import org.mohajo.studyrepublic.domain.InquireBoard;
import org.mohajo.studyrepublic.domain.QInquireBoard;
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
 * -InquireBoardRepository 클래스 추가
 */


public interface InquireBoardRepository extends JpaRepository<InquireBoard, Integer>, QuerydslPredicateExecutor<InquireBoard> {
	
	public default Predicate makePredicate(String searchType, String keyword) {

		BooleanBuilder builder = new BooleanBuilder();

		QInquireBoard inquireBoard = QInquireBoard.inquireBoard;
		
		builder.and(inquireBoard.status.eq(0));

		if(searchType ==null ) {
			return builder;
		}

		switch(searchType){
		case "all":
			builder.and(inquireBoard.title.like("%" + keyword +"%")).or(inquireBoard.content.like("%"+ keyword+ "%"));
			break;
		case "title":
			builder.and(inquireBoard.title.like("%" + keyword +"%"));
			break;
		case "content":
			builder.and(inquireBoard.content.like("%" + keyword + "%"));
			break;
		case "writer":
			builder.and(inquireBoard.member.nickname.like("%" + keyword + "%"));
            break;
		}

		
		return builder;
	}
	
	@Query(value="select f from InquireBoard f where f.inquireBoardId=:be")
	InquireBoard findByinquireBoardId(@Param("be") int beforeInquireBoard);


	@Query(value = "select * from member m join inquireboard i on m.id=i.id where m.id=:id",nativeQuery=true)
	List<InquireBoard>findInquireBoardById(String id);
	
	//이전글
	@Query(value = "select * from inquireboard f where f.inquireboard_id <:fi limit 1", nativeQuery=true)
	InquireBoard beForeBoard(@Param("fi")int inquireBoardId);

	//다음글
	@Query(value = "select * from inquireboard f where f.inquireboard_id >:fi limit 1", nativeQuery=true)
	InquireBoard beAfterBoard(@Param("fi")int inquireBoardId);


//	//첨부파일 숨김
//	@Query(value="select count(f.originName) from InquireboardFile f where f.inquireBoardId =:fi")
//	int fileCount(@Param("fi")int inquireBoardId);
//	



}
