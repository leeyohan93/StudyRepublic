package org.mohajo.studyrepublic.repository;



import java.util.List;

import org.mohajo.studyrepublic.domain.StudyFile;
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

public interface StudyFileRepository extends JpaRepository<StudyFile, Integer>{

	@Query(value = "select * from study_file where id = :id", nativeQuery = true)
	List<StudyFile> findByStudyFile(String id);
	
	@Query(value = "select * from study_file where studyfile_fullurl = :studyfileFullUrl", nativeQuery = true)
	StudyFile findByStudyFilePreview(String studyfileFullUrl);
	
	
}
