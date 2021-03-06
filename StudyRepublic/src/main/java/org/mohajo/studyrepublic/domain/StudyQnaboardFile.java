package org.mohajo.studyrepublic.domain;

import java.io.Serializable;
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
public class StudyQnaboardFile implements Serializable /*extends StudyBoardFile*/{

	@Id
	@Column(name = "study_qnaboard_file_id")
	private int studyQnaboardFileId;
	
	@Column(name = "study_qnaboard_id")
	private int studyQnaboardId;
	
	@Column(name = "filenumber")
	private int fileNumber;
	@Column(name = "originname")
	private String originName;
	@Column(name = "savename")
	private String saveName;
}
