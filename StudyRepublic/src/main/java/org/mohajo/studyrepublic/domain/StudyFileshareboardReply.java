package org.mohajo.studyrepublic.domain;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;


/**
 * @author 신상용
 * @since 2019.01.22
 * @version
 * StudyFileshareboardReply domain 클래스 추가
 */

@Data
@Entity
@Table(name = "study_fileshareboard_reply")
public class StudyFileshareboardReply extends StudyBoardReply{

	
	@Id
	@Column(name = "study_fileshareboard_reply_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int studyFileshareboardReplyId;

	@Column(name = "study_fileshareboard_id")
	private StudyFileshareboard studyfileshareboardId;
}

