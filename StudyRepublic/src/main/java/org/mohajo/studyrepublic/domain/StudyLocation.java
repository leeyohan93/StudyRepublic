package org.mohajo.studyrepublic.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

/**
 * @author	이미연
 * @since	2019. 1. 22.
 * @version	
 * - 기능 설명 1
 */
@Data
@Entity
public class StudyLocation {

	@Id
	private int interestLocationId;
	
	@Column
	private String studyId;
	
	@Column
	private String interestLocation;
	
	
	
	
}
