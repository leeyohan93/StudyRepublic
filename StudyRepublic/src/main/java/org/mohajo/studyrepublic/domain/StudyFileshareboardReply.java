package org.mohajo.studyrepublic.domain;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

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
public class StudyFileshareboardReply/* extends StudyBoardReply*/{

	
	@Id
	@Column(name = "study_fileshareboard_reply_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int studyFileshareboardReplyId;

	@Column(name = "study_fileshareboard_id")
	private StudyFileshareboard studyfileshareboardId;
	
	@Column(name = "study_id", insertable=false, updatable=false)
	private String studyId;
	@Column(name = "id", insertable=false, updatable=false)
	private String id;
	@Column
	private String content;
	@Column
	private Date date;
	@Column(name = "replygroup")
	private int replyGroup;
	@Column(name = "replystep")
	private int replyStep;
	
	@NotFound(action=NotFoundAction.IGNORE)
	@ManyToOne(optional=false)
	@JoinColumns({
		@JoinColumn(name="study_id", referencedColumnName="studyId", insertable=false, updatable=false),
		@JoinColumn(name="id", referencedColumnName="id", insertable=false, updatable=false)
	})
	private StudyMember studyReplyMember;
}

