package org.mohajo.studyrepublic.domain;


import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

import org.mohajo.studyrepublic.domain.code.LeveltestTypeCD;
import org.mohajo.studyrepublic.domain.id.LeveltestResponseId;

import lombok.Data;

/**
 * @author	이미연
 * @since	2019. 1. 22.
 * @version	
 * - 기능 설명 1
 */
@Data
@Entity
public class LeveltestResponse {

	@EmbeddedId
	private LeveltestResponseId leveltestResponseId;

	@Column
	private String submitAnswer;
	
	@Column
	private int isCorrect;
}
