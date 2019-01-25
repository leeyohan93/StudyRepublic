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
 * - 리뷰 DTO
 */
@Data
@Entity
public class Review implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int reviewNumber;
	
	@Column(nullable = false)
	private StudyMemberId studyMemberId;

	@Column(nullable = false)
	private String content;
	
	@Column(nullable = false)
	private float score;
	
	
}
