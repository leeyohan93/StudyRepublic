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
 * - 스터디원 상태 관련 코드 테이블 (ex. 승인대기)
 */
@Data
@Entity
@Table(name = "study_member_status_cd")
public class StudyMemberStatusCD implements Serializable {

	@Id
	private String studyMemberStatusCode;
	
	private String codeValueEnglish;
	
	@Column(nullable = false)
	private String codeValueKorean;
	
}
