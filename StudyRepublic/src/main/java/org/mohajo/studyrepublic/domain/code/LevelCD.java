package org.mohajo.studyrepublic.domain.code;

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
public class LevelCD {

	@Id
	private String levelCode;
	
	@Column
	private String codeValueEnglish;
	
	@Column	
	private String codeValueKorean;
	
}
