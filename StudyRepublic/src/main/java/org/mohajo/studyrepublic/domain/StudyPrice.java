package org.mohajo.studyrepublic.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

/**
 * @author	이미연
 * @since	2019. 1. 22.
 * @version	0.0
 * - 스터디 가격 DTO
 */

@Data
@Entity
public class StudyPrice implements Serializable {
	
	@Id
	private String studyId;
	private int price;
	
}
