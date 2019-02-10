package org.mohajo.studyrepublic.domain;

import java.io.Serializable;
import java.util.List;

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
 * @version StudyNoticeboardReply domain 클래스 추가
 */

@Data
@Entity
@Table(name = "study_noticeboard_reply")
public class StudyNoticeboardReply extends StudyBoardReply implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "study_noticeboard_reply_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int studyNoticeboardReplyId;
	
	@Column(name = "study_noticeboard_id")
	private int studyNoticeboardId;
}
