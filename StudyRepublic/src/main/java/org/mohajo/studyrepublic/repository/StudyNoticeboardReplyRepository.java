package org.mohajo.studyrepublic.repository;

import java.util.List;

import org.mohajo.studyrepublic.domain.StudyNoticeboardReply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StudyNoticeboardReplyRepository extends JpaRepository<StudyNoticeboardReply, Integer>{

	@Query(value="select *\r\n" + 
			"from study_noticeboard left join study_noticeboard_reply using (study_noticeboard_id)\r\n" + 
			"where study_noticeboard.study_id=:studyId and number=:number\r\n" + 
			"order by replygroup asc, replystep asc", nativeQuery=true)
	List<StudyNoticeboardReply> findNoticeboardReplyByStudyIdANDNumber(@Param(value="studyId") String studyId, @Param(value="number") int number);
	
	@Query(value="select *\r\n" + 
			"from study_noticeboard_reply\r\n" + 
			"where study_noticeboard_id = :noticeboardId\r\n" + 
			"order by replygroup asc", nativeQuery=true)
	List<StudyNoticeboardReply> findStudyNoticeReplyByStudyNoticeIdOrderbyGroup(@Param(value = "noticeboardId") int studyNoticeBoardId);
	
	@Query(value="select *\r\n" + 
			"from study_noticeboard_reply\r\n" + 
			"where study_noticeboard_id = :noticeboardId\r\n" + 
			"order by replygroup desc limit 1", nativeQuery=true)
	StudyNoticeboardReply findStudyNoticeboardReplyByStudyNoticeIdOrderbyGroupDecsLimit1(@Param(value="noticeboardId") int studyNoticeBoardId);
	
	@Query(value="select *\r\n" + 
			"from study_noticeboard_reply\r\n" + 
			"where id = :userId and study_noticeboard_reply_id = :studyNoticeboardReplyId", nativeQuery=true)
	StudyNoticeboardReply findByIdAndStudyNoticeboardReplyId(@Param(value="userId") String userId,
			@Param(value="studyNoticeboardReplyId") int studyNoticeboardReplyId);
}
