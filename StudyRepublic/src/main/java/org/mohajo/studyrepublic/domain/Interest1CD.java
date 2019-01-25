package org.mohajo.studyrepublic.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "interest_1_cd", schema = "StudyRepublic")
public class Interest1CD {

	@Id
	@Column(name = "INTEREST_1_CODE")
	private String interest1Code;

	
	@Column(name = "CODE_VALUE_ENGLISH")
	private String codeValueEnglish;
	
	@Column(name = "CODE_VALUE_KOREAN")
	private String codeValueKorean;

}
