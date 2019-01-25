package org.mohajo.studyrepublic.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "tutor_file", schema = "StudyRepublic")
public class TutorFile {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "tutor_file_id")
	private int tutorFileId;
	
	@ManyToOne
	@JoinColumn(name = "tutor_number")
	private Tutor tutor;
	
	@Column(name = "tutorfile_originname")
	private String tutorfileOriginname;
	
	@Column(name = "tutorfile_savename")
	private String tutorfileSavename;

}
