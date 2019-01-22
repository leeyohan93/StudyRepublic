package org.mohajo.studyrepublic.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;



@Data
@Entity
@Table(name = "tutor", schema = "StudyRepublic")
public class Tutor implements Serializable {

	private static final long serialVersionUID = 1L;

	
	@Id 
	private String tutor_number;

	private String EDUCATION_CODE;
	
	private String introduction;
	
	@OneToOne
	@JoinColumn(name = "id")
	private Member member;

	
	
}
