package org.mohajo.studyrepublic.repository;

import java.util.Date;
import java.util.List;

import org.mohajo.studyrepublic.domain.Member;
import org.mohajo.studyrepublic.domain.PopularStudy;
import org.mohajo.studyrepublic.domain.QStudy;
import org.mohajo.studyrepublic.domain.Study;
import org.mohajo.studyrepublic.domain.TypeCD;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;

/**
 * @author	이미연
 * @since	2019. 1. 22.
 * @version	0.0
 * - 기능 설명 1
 */
public interface StudyRepository extends JpaRepository<Study, String>, QuerydslPredicateExecutor<Study> {

	/**	
	*	@author	이미연
	*/

	//페이징
	public default Predicate makePredicate(String type, String keyword) {
		
		BooleanBuilder builder = new BooleanBuilder();
		QStudy study = QStudy.study;
		//검색에 필요한 타입, 키워드를 이용해서 쿼리를 생성 (p.269)
		
		return builder;
	}

//	@Query(value = "select sv from StudyView sv where sv.typeCode = ?1 and sv.studyStatusCode not in ('C', 'D')")
////	@Query(value = "select s.* from study_view s where s.typeCode = ?1 and s.studyStatusCode not in ('C', 'D')", nativeQuery=true)
////	public List<Study> findValidStudyByTypeCode(TypeCD typeCode, Pageable paging);
//	public Page<StudyView> findValidStudyByTypeCode(TypeCD typeCode, Pageable paging);
	//쿼리문 안에는 Study 클래스에 설정한 TypeCD 타입 변수명을 사용한다.
		//@Query(value = "select s from (select s from Study s where s.typeCode = ?1) where s.studyStatusCode in ('C', 'D')")
		//위 테스트 해보고, 성능 비교
	//OrderBy 를 등록일, 시작일로 구분
	
	/*select avg(score) from review r where study_id = "BO00001";*/
	@Query(value = "select avg(score) from Review r where r.studyId = ?1")
	public float getAverageScore(String studyId);
	
	@Query(value = "select s from Study s where s.startDate between ?1 and ?2")
	public List<Study> getByStartDates(Date s1, Date s2);
	
	
	/**
	*	@author	이요한
	*/
	@Query(value="select * from popular_study where TYPE_CODE='P' order by rand() limit 2",nativeQuery=true)
//	public List<Study> findPrStudyBytypeCode();
	public List<PopularStudy> findPrStudyBytypeCode();
	
	@Query(value="select * from study where study_id in (select distinct study_id from study natural join study_interest where interest_2_code in (?1) and TYPE_CODE = 'B' and STUDY_STATUS_CODE ='O') order by rand() limit 2;",nativeQuery=true)
//	public List<Study> findBsStudyBytypeCode();
	public List<PopularStudy> findBsStudyBytypeCode(String[] popularTag);
	

// 스터디 뷰 테이블 관계 설정 후 에러. 주석 처리함. BY 이미연
//	@Query(value ="select * from (select * from study_member where id= :member AND (study_member_status_code = 'ME' || study_member_s+tatus_code = 'LE')) a1 join study s1"
//			+ " using (study_id) where s1.study_status_code='G'", nativeQuery=true)
//	List<Study> findByMemberId(Member member);
// 준석님 코드 테스트 --> 실패
/*	@Query(value ="select * from study s1 join (select * from study_member where id= :member AND (study_member_status_code = 'ME' || study_member_s+tatus_code = 'LE')) a1"
	+ " using (study_id) where s1.study_status_code='G'", nativeQuery=true)
	List<Study> findByMemberId(Member member);*/
	
	@Query(value = "select s from Study s where s.studyId in ?1")
	List<Study> getSelectedStudy(String[] selectedId);

	@Query(value = "select if(number<100000, concat(prefix, lpad(number, 5, 0)), null) as new_study_id from (select substring(study_id, 1, 2) as prefix, substring(study_id, 3)+1 as number from study where type_code = :typeCode and onoff_code = :onoffCode order by study_id desc limit 1) sample", nativeQuery=true)
	public String getNewStudyId(String typeCode, String onoffCode);

	@Modifying	//https://winmargo.tistory.com/208, https://docs.spring.io/spring-data/jpa/docs/current/reference/html/, 안 써도 되나? --> Modifying Annotation을 추가하지 않는 경우 Not supported for DML operations 에러가 발생한다고 함. (https://www.slipp.net/questions/95)
	@Query(value = "update Study s set s.enrollActual = s.enrollActual + 1 where studyId = ?1")
	public void plusEnrollActual(String studyId);
	
	@Query(value = "SELECT MID(post_date,1,7) period, COUNT(*) as count FROM study GROUP BY period order by period desc;",nativeQuery=true)
	List<Object[]> getStudyCount();
	@Query(value = "SELECT MID(post_date,1,4) period, COUNT(*) as count FROM study GROUP BY period order by period desc;",nativeQuery=true)
	List<Object[]> getStudyCountYear();
	

}