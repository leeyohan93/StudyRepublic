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
 * - 스터디 레벨 관련 코드 테이블
 */
@Data
@Entity
@Table(name = "level_cd")
public class LevelCD implements Serializable {

	@Id
	private String levelCode;
	
	private String codeValueEnglish;
	
	@Column(nullable = false)
	private String codeValueKorean;
	
}
