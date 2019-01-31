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
@Table(name = "tutor_interest", schema = "StudyRepublic")
public class TutorInterest {

	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "tutor_interest_id")
	private int tutorInterestId;
	
/*	@ManyToOne
	@JoinColumn(name = "tutor_number")
	private Tutor tutor;*/
	
	@ManyToOne
	@JoinColumn(name = "INTEREST_2_CODE")
	private Interest2CD interest2CD;
}
