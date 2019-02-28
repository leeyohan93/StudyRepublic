/**
 * 
 */
package org.mohajo.studyrepublic.repository;

import java.util.Calendar;
import java.util.Date;
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
	
	public default Predicate makePredicate(String searchType, String keyword, String searchPeriod) {

		BooleanBuilder builder = new BooleanBuilder();

		QInquireBoard inquireBoard = QInquireBoard.inquireBoard;
		
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
		
		builder.and(inquireBoard.status.eq(0));

		if(searchType ==null && searchPeriod == null) {
			System.out.println("null실행");
			return builder;
		}
		
		switch(searchType){
		case "all":
			builder.and(inquireBoard.title.like("%" + keyword +"%")).or(inquireBoard.content.like("%"+ keyword+ "%"));
			builder.and(inquireBoard.status.eq(0));
			System.out.println("all실행");
			break;
		case "title":
			builder.and(inquireBoard.title.like("%" + keyword +"%"));
			System.out.println("title실행");
			break;
		case "content":
			builder.and(inquireBoard.content.like("%" + keyword + "%"));
			System.out.println("content실행");
			break;
		case "writer":
			builder.and(inquireBoard.member.nickname.like("%" + keyword + "%"));
			System.out.println("writer실행");
			break;
		}
		
		switch(searchPeriod) {
		case "allDay":
			System.out.println("allDay실행");
			builder.and(inquireBoard.status.eq(0));
			break;
		case "week":
			builder.and(inquireBoard.date.between(weekDay, today));
			System.out.println("week실행");
			break;
		case "month":
			builder.and(inquireBoard.date.between(monthDay, today));
			System.out.println("month실행");
			break;
		case "sMonth":
			builder.and(inquireBoard.date.between(sixMonthDay, today));
			System.out.println("sMonth실행");
			break;
		}



		return builder;

	}
	
	@Query(value="select f from InquireBoard f where f.inquireBoardId=:be")
	InquireBoard findByinquireBoardId(@Param("be") int beforeInquireBoard);


	@Query(value = "select * from member m join inquireboard i on m.id=i.id where m.id=:id and i.commentstep = 0",nativeQuery=true)
	List<InquireBoard>findInquireBoardById(String id);
	
	//이전글
	@Query(value = "select * from inquireboard f where f.inquireboard_id <:fi  order by inquireboard_id desc limit 1", nativeQuery=true)
	InquireBoard beForeBoard(@Param("fi")int inquireBoardId);

	//다음글
	@Query(value = "select * from inquireboard f where f.inquireboard_id >:fi limit 1", nativeQuery=true)
	InquireBoard beAfterBoard(@Param("fi")int inquireBoardId);


//	//첨부파일 숨김
//	@Query(value="select count(f.originName) from InquireboardFile f where f.inquireBoardId =:fi")
//	int fileCount(@Param("fi")int inquireBoardId);
//	



}
