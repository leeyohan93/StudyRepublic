package org.mohajo.studyrepublic.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;

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

	
	@Id
	@Column(name = "study_fileshareboard_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int studyFileshareboardId;
	
	@OneToMany
	@JoinColumn(name="study_fileshareboard_id")
	private List<StudyFileshareboardFile> studyFileshareboardFile;
	
	@OneToMany
	@JoinColumn(name="study_fileshareboard_id")
	private List<StudyFileshareboardReply> studyFileshareboardReply;
	
}
