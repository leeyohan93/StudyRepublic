package org.mohajo.studyrepublic.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

import lombok.Data;

/**
 * @author	이미연
 * @since	2019. 1. 22.
 * @version	0.0
 * - 스터디 유형 관련 코드 테이블 (ex. 프리미엄)
 */
@Data
@Entity
@Table(name = "type_cd")
@Component
public class TypeCD implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "type_code")
	private String typeCode;
	
	private String codeValueEnglish;
	
	@Column(nullable = false)
	private String codeValueKorean;

	public TypeCD() {
		super();
	}
	
	public TypeCD(String typeCode) {
		super();
		this.typeCode = typeCode;
	}
	
}
