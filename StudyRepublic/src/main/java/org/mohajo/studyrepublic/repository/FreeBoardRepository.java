/**
 * 
 */
package org.mohajo.studyrepublic.repository;


import java.security.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.mohajo.studyrepublic.domain.FreeBoard;
import org.mohajo.studyrepublic.domain.FreeBoardReply;
import org.mohajo.studyrepublic.domain.QFreeBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;

import lombok.extern.java.Log;

/**
 * @author 윤원식
 * @since 2019. 1. 23.
 * @version
 * -FreeBoardRepository 클래스 추가
 */

public interface FreeBoardRepository extends JpaRepository<FreeBoard, Integer>, QuerydslPredicateExecutor<FreeBoard>{


	public default Predicate makePredicate(String searchType, String keyword, String searchPeriod) {

		
		System.out.println("=====================================================");
		BooleanBuilder builder = new BooleanBuilder();

		QFreeBoard freeboard = QFreeBoard.freeBoard;

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
		
		builder.and(freeboard.status.eq(0));
		

		if(searchType ==null && searchPeriod == null) {
			System.out.println("null실행");
			return builder;
		}
		
		switch(searchType){
		case "all":
			builder.and(freeboard.title.like("%" + keyword +"%")).or(freeboard.content.like("%"+ keyword+ "%"));
			builder.and(freeboard.status.eq(0));
			System.out.println("all실행");
			break;
		case "title":
			builder.and(freeboard.title.like("%" + keyword +"%"));
			builder.and(freeboard.status.eq(0));
			System.out.println("title실행");
			break;
		case "content":
			builder.and(freeboard.content.like("%" + keyword + "%"));
			builder.and(freeboard.status.eq(0));
			System.out.println("content실행");
			break;
		case "writer":
			builder.and(freeboard.member.nickname.like("%" + keyword + "%"));
			builder.and(freeboard.status.eq(0));
			System.out.println("writer실행");
			break;
		}
		
		switch(searchPeriod) {
		case "allDay":
			System.out.println("allDay실행");
			builder.and(freeboard.status.eq(0));
			break;
		case "week":
			builder.and(freeboard.date.between(weekDay, today));
			builder.and(freeboard.status.eq(0));
			System.out.println("week실행");
			break;
		case "month":
			builder.and(freeboard.date.between(monthDay, today));
			builder.and(freeboard.status.eq(0));
			System.out.println("month실행");
			break;
		case "sMonth":
			builder.and(freeboard.date.between(sixMonthDay, today));
			builder.and(freeboard.status.eq(0));
			System.out.println("sMonth실행");
			break;
		}



		return builder;
	}
	

		

	@Query(value = "select * from member m join freeboard f on m.id=f.id where m.id=:id", nativeQuery=true)
	List<FreeBoard> findFreeBoardById(String id);

    //이전글
	@Query(value = "select * from freeboard f where f.freeboard_id <:fi order by freeboard_id desc limit 1", nativeQuery=true)
	FreeBoard beForeBoard(@Param("fi")int freeBoardId);

	//다음글
	@Query(value = "select * from freeboard f where f.freeboard_id >:fi limit 1", nativeQuery=true)
	FreeBoard beAfterBoard(@Param("fi")int freeBoardId);
    
	
	//첨부파일 숨김
	@Query(value="select count(f.originName) from FreeBoardFile f where f.freeBoardId =:fi")
	int fileCount(@Param("fi")int freeBoardId);



}
