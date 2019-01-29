package org.mohajo.studyrepublic.repository;

import java.util.List;

import org.mohajo.studyrepublic.domain.QStudy;
import org.mohajo.studyrepublic.domain.Study;
import org.mohajo.studyrepublic.domain.TypeCD;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
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

	//페이징
	public default Predicate makePredicate(String type, String keyword) {
		
		BooleanBuilder builder = new BooleanBuilder();
		QStudy study = QStudy.study;
		//검색에 필요한 타입, 키워드를 이용해서 쿼리를 생성 (p.269)
		
		return builder;
	}

	@Query(value = "select s from Study s where s.typeCode = ?1 and s.studyStatusCode not in ('C', 'D')")
	public List<Study> findValidStudyByTypeCode(TypeCD typeCode, Pageable paging);
	//@Query(value = "select s from (select s from Study s where s.typeCode = ?1) where s.studyStatusCode in ('C', 'D')")
	//위 테스트 해보고, 성능 비교
	//쿼리문 안에는 Study 클래스에 설정한 TypeCD 타입 변수명을 사용한다.
	//OrderBy 를 등록일, 시작일로 구분
	
}
