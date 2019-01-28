package org.mohajo.studyrepublic.domain;

import java.sql.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @author 신상용
 * @since 2019.01.22
 * @version
 * StudyFileshareboard domain 클래스 추가
 */

@Data
@Entity
@Table(name = "study_fileshareboard")
public class StudyFileshareboard extends StudyBoard{

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "study_fileshareboard_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int studyFileshareboardId;
	
	@OneToMany
	private List<StudyNoticeboardFile> studyFileshareboardFile;
	@OneToMany
	private List<StudyNoticeboardReply> studyFileshareboardReply;
	
}
