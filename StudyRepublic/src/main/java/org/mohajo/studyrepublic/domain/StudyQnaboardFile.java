package org.mohajo.studyrepublic.domain;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @author 신상용
 * @since 2019.01.22
 * @version
 * StudyQnaboardFile domain 클래스 추가
 */

@Data
@Entity
@Table(name = "study_qnaboard_file")
public class StudyQnaboardFile extends StudyBoardFile{

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "study_qnaboard_file_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int studyQnaboardFileId;
	@Column(name = "study_qnaboard_id")
	private int studyQnaboardId;
}
