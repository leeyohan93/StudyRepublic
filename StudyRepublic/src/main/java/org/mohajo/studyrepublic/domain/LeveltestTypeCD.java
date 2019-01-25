package org.mohajo.studyrepublic.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * @author	이미연
 * @since	2019. 1. 22.
 * @version	0.0
 * - 레벨테스트 유형 관련 코드 테이블 (ex. 객관식)
 */
@Data
@Entity
@Table(name = "leveltest_type_cd")
public class LeveltestTypeCD implements Serializable {

	@Id
	private String leveltestTypeCode;
	
	private String codeValueEnglish;
	
	@Column(nullable = false)
	private String codeValueKorean;
	
}
