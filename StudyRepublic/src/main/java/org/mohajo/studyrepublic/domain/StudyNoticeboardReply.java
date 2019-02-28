package org.mohajo.studyrepublic.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import lombok.Data;

/**
 * @author 신상용
 * @since 2019.01.22
 * @version StudyNoticeboardReply domain 클래스 추가
 */

@Data
@Entity
@Table(name = "study_noticeboard_reply")
public class StudyNoticeboardReply/* extends StudyBoardReply implements Serializable*/ {

	@Id
	@Column(name = "study_noticeboard_reply_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int studyNoticeboardReplyId;
	
	@Column(name = "study_noticeboard_id")
	private int studyNoticeboardId;
	
	@Column(name = "study_id"/*, insertable=false, updatable=false*/)
	private String studyId;
	@Column(name = "id"/*, insertable=false, updatable=false*/)
	private String id;
	@Column
	private String content;
	@Temporal(TemporalType.TIMESTAMP)
	@Column
	private Date date = new Date();
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
