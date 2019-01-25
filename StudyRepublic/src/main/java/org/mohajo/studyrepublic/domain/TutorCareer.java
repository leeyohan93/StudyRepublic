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
@Table(name = "tutor_career", schema = "StudyRepublic")
public class TutorCareer {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "tutor_career_id")
	private int tutorCareerId;
	
	@ManyToOne
	@JoinColumn(name = "tutor_number")
	private Tutor tutor;
	
	@ManyToOne
	@JoinColumn(name = "CAREER_CODE")
	private CareerCD careerCD;
	
}
