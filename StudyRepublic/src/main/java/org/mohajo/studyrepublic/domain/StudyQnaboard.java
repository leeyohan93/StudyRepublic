package org.mohajo.studyrepublic.domain;

import java.sql.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @author 신상용
 * @since 2019.01.22
 * @version
 * StudyQnaboard domain 클래스 추가
 */

@Data
@Entity
@Table(name = "study_qnaboard")
public class StudyQnaboard extends StudyBoard{

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "study_qnaboard_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int studyQnaboardId;
	
	@OneToMany
	private List<StudyNoticeboardFile> studyQnaboardFile;
	@OneToMany
	private List<StudyNoticeboardReply> studyQnaboardReply;
}
