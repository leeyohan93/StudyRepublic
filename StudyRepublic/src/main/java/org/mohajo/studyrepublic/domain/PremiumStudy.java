package org.mohajo.studyrepublic.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;

import lombok.Data;

/**
 * @author	이미연
 * @since	2019. 1. 22.
 * @version	
 * - 기능 설명 1
 */
@Data
@Entity
public class PremiumStudy extends Study {

//	@Column
	@JoinColumn(name = "study_id")
	private StudyPrice price;
	
}
