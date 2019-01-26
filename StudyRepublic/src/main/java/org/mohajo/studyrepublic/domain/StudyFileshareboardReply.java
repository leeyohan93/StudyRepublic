package org.mohajo.studyrepublic.domain;

import java.sql.Date;
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
import lombok.Getter;
import lombok.Setter;

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

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "study_fileshareboard_reply_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int studyFileshareboardReplyId;
	
/*	@ManyToOne
	@JoinColumn(name = "study_fileshareboard_id")
	private List<StudyFileshareboard> studyfileshareboard;*/

	@ManyToOne
	@JoinColumn(name = "study_fileshareboard_id")
	private StudyFileshareboard studyfileshareboard;
	
	
}

