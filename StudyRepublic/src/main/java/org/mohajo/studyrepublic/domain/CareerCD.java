package org.mohajo.studyrepublic.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "career_cd", schema = "StudyRepublic")
public class CareerCD {

	@Id
	@Column(name = "CAREER_CODE")
	private String careerCode;
	
}
