package org.mohajo.studyrepublic.domain;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

public class TutorInterest {

	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "tutor_interest_id")
	private int tutorInterestId;
	
	@ManyToOne
	@JoinColumn(name = "tutor_number")
	private Tutor tutor;
	
	@ManyToOne
	@JoinColumn(name = "interest_2_cd")
	private Interest2CD interest2cd;
}
