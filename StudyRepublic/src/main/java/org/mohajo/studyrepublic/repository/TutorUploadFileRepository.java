package org.mohajo.studyrepublic.repository;



import java.util.List;

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

	@Query(value = "select * from tutor_uploadfile where id = :id", nativeQuery = true)
	List<TutorUploadFile> findByTutorUploadFile(String id);
	
	@Query(value = "select * from tutor_uploadfile where tutorfile_Url = :tutorfileUrl", nativeQuery = true)
	TutorUploadFile findByTutorUploadPreviewFile(String tutorfileUrl);
	
	
}
