package org.mohajo.studyrepublic.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "interst_1_cd", schema = "StudyRepublic")
public class Interest1CD {

	@Id
	@Column(name = "INTEREST_1_CODE")
	private String interest1Code;
}
