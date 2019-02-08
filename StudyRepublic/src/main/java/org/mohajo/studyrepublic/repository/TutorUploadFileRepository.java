package org.mohajo.studyrepublic.repository;


import org.mohajo.studyrepublic.domain.Tutor;
import org.mohajo.studyrepublic.domain.TutorUploadFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @author 윤성호
 * @since 2019.01.22
 * @version
 * - AdminCommentRepository추가
 * 
 */

public interface TutorUploadFileRepository extends JpaRepository<TutorUploadFile, Integer>{

	
}
