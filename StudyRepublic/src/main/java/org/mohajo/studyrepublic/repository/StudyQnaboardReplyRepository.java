package org.mohajo.studyrepublic.repository;

import java.util.List;

import org.mohajo.studyrepublic.domain.StudyQnaboardReply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StudyQnaboardReplyRepository extends JpaRepository<StudyQnaboardReply, Integer>{
	
	@Query(value="select *\r\n" + 
			"from study_qnaboard_reply\r\n" + 
			"where study_qnaboard_id = :qnaboardId\r\n" + 
			"order by replygroup asc", nativeQuery=true)
	List<StudyQnaboardReply> findStudyQnaboardReplyByStudyQnaBoardIdOrderbyGroup(@Param(value = "qnaboardId") int qnaboardId);
	
	@Query(value="select *\r\n" + 
			"from study_qnaboard_reply\r\n" + 
			"where study_qnaboard_id = :qnaboardId\r\n" + 
			"order by replygroup desc limit 1", nativeQuery=true)
	StudyQnaboardReply findStudyQnadboardReplyByStudyQnaBoardIdOrderbyGroupDecsLimit1(@Param(value="qnaboardId") int qnaboardId);

}
