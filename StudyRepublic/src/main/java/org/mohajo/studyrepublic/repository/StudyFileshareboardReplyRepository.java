package org.mohajo.studyrepublic.repository;

import java.util.List;

import org.mohajo.studyrepublic.domain.StudyFileshareboardReply;
import org.mohajo.studyrepublic.domain.StudyQnaboardReply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StudyFileshareboardReplyRepository extends JpaRepository<StudyFileshareboardReply, Integer>{
	
	@Query(value="select *\r\n" + 
			"from study_fileshareboard_reply\r\n" + 
			"where study_fileshareboard_id = :fileshareboardId\r\n" + 
			"order by replygroup asc", nativeQuery=true)
	List<StudyFileshareboardReply> findStudyFileshareReplyByStudyFileshareIdOrderbyGroup(@Param(value = "fileshareboardId") int studyFileshareBoardId);
	
	@Query(value="select *\r\n" + 
			"from study_fileshareboard_reply\r\n" + 
			"where study_fileshareboard_id = :fileshareboardId\r\n" + 
			"order by replygroup desc limit 1", nativeQuery=true)
	StudyFileshareboardReply findStudyFileshareboardReplyByStudyFileshareIdOrderbyGroupDecsLimit1(@Param(value="fileshareboardId") int studyFileshareBoardId);

	@Query(value="select *\r\n" + 
			"from study_fileshareboard_reply\r\n" + 
			"where id = :userId and study_fileshareboard_reply_id = :studyFileshareboardReplyId", nativeQuery=true)
	StudyFileshareboardReply findByIdAndStudyFileshareboardReplyId(@Param(value="userId") String userId,
			@Param(value="studyFileshareboardReplyId") int studyFileshareboardReplyId);
}
