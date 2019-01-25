package org.mohajo.studyrepublic.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

/**
 * @author	이미연
 * @since	2019. 1. 22.
 * @version	0.0
 * - 스터디 지역 DTO
 */
@Data
@Entity
public class StudyLocation implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int interestLocationId;
	private String interestLocation;
	
}
