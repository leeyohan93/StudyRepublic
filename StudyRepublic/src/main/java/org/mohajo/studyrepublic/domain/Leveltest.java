package org.mohajo.studyrepublic.domain;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.mohajo.studyrepublic.domain.code.LeveltestTypeCD;
import org.mohajo.studyrepublic.domain.id.LeveltestId;

import lombok.Data;

/**
 * @author	이미연
 * @since	2019. 1. 22.
 * @version	
 * - 기능 설명 1
 */
@Data
@Entity
public class Leveltest {

	@EmbeddedId
	private LeveltestId leveltestId;
	
	@ManyToOne
	@JoinColumn(name = "LEVELTEST_TYPE_CODE")
	private LeveltestTypeCD leveltestTypeCode;
	
	@Column
	private String content;
	
	@Column
	private String choice;
	
	@Column
	private String answer;
	
}
