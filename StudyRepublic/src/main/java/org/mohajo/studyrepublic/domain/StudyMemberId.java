package org.mohajo.studyrepublic.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Data;

/**
 * @author	이미연
 * @since	2019. 1. 22.
 * @version	0.0
 * - 스터디원 상세 테이블 복합키 클래스
 */
@Data
@Embeddable
public class StudyMemberId implements Serializable {

	@Column(name="study_id")
	private String studyId;
	@Column(name="id")
	private String id;
	
}
