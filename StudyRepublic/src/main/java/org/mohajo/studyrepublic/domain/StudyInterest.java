package org.mohajo.studyrepublic.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SecondaryTable;
import javax.persistence.Table;

import lombok.Data;

/**
 * @author	이미연
 * @since	2019. 1. 22.
 * @version	0.0
 * - 스터디 분야 DTO
 */
@Data
@Entity
public class StudyInterest implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int StudyInterestId;
	
	@ManyToOne
	@JoinColumn(name = "INTEREST_2_CODE")
	private Interest2CD interest2code;
	

}
