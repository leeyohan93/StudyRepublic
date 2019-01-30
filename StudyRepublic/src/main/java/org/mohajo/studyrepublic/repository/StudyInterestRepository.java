/**
 * 
 */
package org.mohajo.studyrepublic.repository;

import java.util.List;

import org.mohajo.studyrepublic.domain.Study;
import org.mohajo.studyrepublic.domain.StudyInterest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @author 이요한
 * @since 2019. 1. 29.
 * @version 0.0
 * -기능 설명1
 */
public interface StudyInterestRepository extends JpaRepository<StudyInterest, Integer> {
	


}
