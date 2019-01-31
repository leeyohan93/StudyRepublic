package org.mohajo.studyrepublic.repository;

import org.mohajo.studyrepublic.domain.StudyFileshareboard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface StudyFileshareboardRepository extends JpaRepository<StudyFileshareboard, Integer>, QuerydslPredicateExecutor<StudyFileshareboard>{

}
