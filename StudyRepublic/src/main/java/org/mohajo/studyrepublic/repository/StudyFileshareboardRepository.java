package org.mohajo.studyrepublic.repository;

import org.mohajo.studyrepublic.domain.QStudyFileshareboard;
import org.mohajo.studyrepublic.domain.QStudyNoticeboard;
import org.mohajo.studyrepublic.domain.StudyFileshareboard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;

public interface StudyFileshareboardRepository extends JpaRepository<StudyFileshareboard, Integer>, QuerydslPredicateExecutor<StudyFileshareboard>{
	
	public default Predicate makePredicate(String studyId) {
		
		BooleanBuilder builder = new BooleanBuilder();
		QStudyFileshareboard studyfileshareboard = QStudyFileshareboard.studyFileshareboard;
		builder.and(studyfileshareboard.studyId.like(studyId));

		return builder; 
	}

}
