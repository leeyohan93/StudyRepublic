package org.mohajo.studyrepublic.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;




import lombok.Data;

@Data
@Entity
@Table(name = "grade_cd", schema = "StudyRepublic")
public class GradeCD implements Serializable {

	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "GRADE_CODE")
	private String gradeCode;

	@Column(name = "CODE_VALUE_ENGLISH")
	private String codeValueEnglish;

	@Column(name = "CODE_VALUE_KOREAN")
	private String codeValueKorean;

	public GradeCD(String gradeCode) {
		super();
		this.gradeCode = gradeCode;
	}

	public GradeCD() {
		super();
	}


	
	
	
	

}
