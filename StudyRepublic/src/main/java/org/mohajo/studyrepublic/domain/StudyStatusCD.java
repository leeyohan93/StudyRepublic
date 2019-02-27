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
 * - 스터디 진행 상태 관련 코드 테이블
 */
@Data
@Entity
@Table(name = "study_status_cd")
public class StudyStatusCD implements Serializable {
	
	@Id
	private String studyStatusCode;
	
	private String codeValueEnglish;
	
	@Column(nullable = false)
	private String codeValueKorean;
	
	public StudyStatusCD() {
		// TODO Auto-generated constructor stub
	}
	
	public StudyStatusCD(String studyStatusCode, String codeValueKorean){
		this.studyStatusCode = studyStatusCode;
		this.codeValueKorean = codeValueKorean;
	}

}
