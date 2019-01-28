package org.mohajo.studyrepublic.repository;

import org.mohajo.studyrepublic.domain.StudyNoticeboard;
import org.mohajo.studyrepublic.domain.StudyQnaboard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface StudyQnaboardRepository extends JpaRepository<StudyQnaboard, Integer>, QuerydslPredicateExecutor<StudyQnaboard>{

}
