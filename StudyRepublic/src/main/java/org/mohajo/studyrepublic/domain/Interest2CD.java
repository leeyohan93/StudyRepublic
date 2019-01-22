package org.mohajo.studyrepublic.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "interetst_2_cd", schema = "StudyRepublic")
public class Interest2CD {

	
	@Id
	@Column(name = "INTEREST_2_CODE")
	private String interest2Code;
}
