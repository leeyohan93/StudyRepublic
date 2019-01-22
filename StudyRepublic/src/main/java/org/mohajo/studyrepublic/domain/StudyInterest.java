package org.mohajo.studyrepublic.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;

/**
 * @author	이미연
 * @since	2019. 1. 22.
 * @version	
 * - 기능 설명 1
 */
@Data
@Entity
public class StudyInterest {

	@Id
	private int StudyInterestId;
	
	@Column
	private String studyID;
	
	@ManyToOne
	@JoinColumn(name = "INTEREST_2_CODE")
	private Interest2CD interest2code;
	

}
