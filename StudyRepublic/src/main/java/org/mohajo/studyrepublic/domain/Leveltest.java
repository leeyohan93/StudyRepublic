package org.mohajo.studyrepublic.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;

/**
 * @author	이미연
 * @since	2019. 1. 22.
 * @version	0.0
 * - 레벨테스트 DTO
 */
@Data
@Entity
public class Leveltest implements Serializable {

/*	@EmbeddedId
	private LeveltestId leveltestId;*/
	
	@Id
	private int leveltestId;
	
	@Column/*(insertable=false, updatable=false)*/
	private int questionNumber;
	
	@Column/*(insertable=false, updatable=false)*/
	private String studyId;
	
	@ManyToOne
	@JoinColumn(name = "LEVELTEST_TYPE_CODE", nullable = false)
	private LeveltestTypeCD leveltestTypeCode;
	
	@Column(nullable = false)
	private String content;
	
	@Column
	private String choice;
	
	@Column(nullable = false)
	private String correctAnswer;
	
}
