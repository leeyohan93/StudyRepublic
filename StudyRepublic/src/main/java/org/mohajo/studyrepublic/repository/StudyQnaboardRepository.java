package org.mohajo.studyrepublic.repository;

import org.mohajo.studyrepublic.domain.QStudyQnaboard;
import org.mohajo.studyrepublic.domain.StudyNoticeboard;
import org.mohajo.studyrepublic.domain.StudyQnaboard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;

public interface StudyQnaboardRepository extends JpaRepository<StudyQnaboard, Integer>, QuerydslPredicateExecutor<StudyQnaboard>{

	
	public default Predicate makePredicate(String studyId) {
		
		BooleanBuilder builder = new BooleanBuilder();
		QStudyQnaboard studyqnaboard = QStudyQnaboard.studyQnaboard;
		
		builder.and(studyqnaboard.studyId.like(studyId));
		
		return builder; 
	}
}
