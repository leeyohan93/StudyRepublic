/**
 * 
 */
package org.mohajo.studyrepublic.repository;

import java.util.Calendar;
import java.util.Date;
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
	
	public default Predicate makePredicate(String searchType, String keyword, String searchPeriod) {

		BooleanBuilder builder = new BooleanBuilder();

		QRequestBoard requestBoard = QRequestBoard.requestBoard;
		
		Date today = new Date();

		Calendar cal = Calendar.getInstance();
		cal.add(cal.DAY_OF_MONTH, -7);
		Date weekDay = cal.getTime();
		cal.add(cal.MONTH, -1);
		Date monthDay = cal.getTime();
		cal.add(cal.MONTH, -6);
		Date sixMonthDay = cal.getTime();

		System.out.println(today);
		System.out.println(weekDay);
		System.out.println(monthDay);
		System.out.println(sixMonthDay);

		System.out.println(searchType + "," + searchPeriod);
		
		builder.and(requestBoard.status.eq(0));
		
		

		if(searchType ==null && searchPeriod == null) {
			System.out.println("null실행");
			return builder;
		}
		
		switch(searchType){
		case "all":
			builder.and(requestBoard.title.like("%" + keyword +"%")).or(requestBoard.content.like("%"+ keyword+ "%"));
			builder.and(requestBoard.status.eq(0));
			System.out.println("all실행");
			break;
		case "title":
			builder.and(requestBoard.title.like("%" + keyword +"%"));
			System.out.println("title실행");
			break;
		case "content":
			builder.and(requestBoard.content.like("%" + keyword + "%"));
			System.out.println("content실행");
			break;
		case "writer":
			builder.and(requestBoard.member.nickname.like("%" + keyword + "%"));
			System.out.println("writer실행");
			break;
		}
		switch(searchPeriod) {
		case "allDay":
			System.out.println("allDay실행");
			builder.and(requestBoard.status.eq(0));
			break;
		case "week":
			builder.and(requestBoard.date.between(weekDay, today));
			System.out.println("week실행");
			break;
		case "month":
			builder.and(requestBoard.date.between(monthDay, today));
			System.out.println("month실행");
			break;
		case "sMonth":
			builder.and(requestBoard.date.between(sixMonthDay, today));
			System.out.println("sMonth실행");
			break;
		}



		return builder;

	}
	
	
	@Query(value = "select * from member m join requestboard f on m.id=f.id where m.id=:id", nativeQuery=true)
	List<RequestBoard> findRequestBoardById(String id);
	
	
	@Query(value="select f from RequestBoard f where f.requestBoardId=:be")
	RequestBoard findByrequestBoardId(@Param("be") int beforeRequestBoard);


	//이전글
		@Query(value = "select * from requestboard f where f.requestboard_id <:fi order by requestboard_id desc limit 1", nativeQuery=true)
		RequestBoard beForeBoard(@Param("fi")int requestBoardId);

		//다음글
		@Query(value = "select * from requestboard f where f.requestboard_id >:fi limit 1", nativeQuery=true)
		RequestBoard beAfterBoard(@Param("fi")int requestBoardId);


		//첨부파일 숨김
		@Query(value="select count(f.originName) from RequestBoardFile f where f.requestBoardId =:fi")
		int fileCount(@Param("fi")int requestBoardId);
	

	
	
}
