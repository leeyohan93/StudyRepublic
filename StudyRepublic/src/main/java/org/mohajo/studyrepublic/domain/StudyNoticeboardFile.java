package org.mohajo.studyrepublic.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

/**
 * @author 신상용
 * @since 2019.01.22
 * @version
 * StudyNoticeboardFile domain 클래스 추가
 */

@Getter
@Setter
@Entity
@Table(name = "study_noticeboard_file")
public class StudyNoticeboardFile extends StudyBoardFile{

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "study_noticeboard_file_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int studyNoticeboardFileId;
	@Column(name = "study_noticeboard_id")
	private int studyNoticeboardId;
}
