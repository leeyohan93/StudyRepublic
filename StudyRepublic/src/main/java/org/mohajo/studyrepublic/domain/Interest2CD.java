package org.mohajo.studyrepublic.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "interetst_2_cd", schema = "StudyRepublic")
public class Interest2CD {

	
	@Id
	@Column(name = "INTEREST_2_CODE")
	private String interest2Code;
	
	@Column(name = "CODE_VALUE_ENGLISH")
	private String codeValueEnglish;
	
	@Column(name = "CODE_VALUE_KOREAN")
	private String codeValueKorean;
	
	@ManyToOne
	@JoinColumn(name = "Interest_1_CODE")
	private Interest1CD interest1cd;
}
