package org.mohajo.studyrepublic.domain;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;

import lombok.Data;

/**
 * @author	이미연
 * @since	2019. 1. 22.
 * @version	0.0
 * - 레벨테스트 응답 DTO
 */
@Data
@Entity
public class LeveltestResponse implements Serializable {

	@EmbeddedId
	private LeveltestResponseId leveltestResponseId;

	@Column
	private String submitAnswer;
	
	@Column
	private int isCorrect;
	
}
