package org.mohajo.studyrepublic.repository;

import org.mohajo.studyrepublic.domain.Study;
import org.mohajo.studyrepublic.domain.StudyPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

/**
 * @author	이미연
 * @since	2019. 2. 26.
 * @version	0.0
 * - 기능 설명 1
 */
public interface StudyPriceRepository extends JpaRepository<StudyPrice, String> {


}