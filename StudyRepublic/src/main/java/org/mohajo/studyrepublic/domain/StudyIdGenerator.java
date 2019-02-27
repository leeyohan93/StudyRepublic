package org.mohajo.studyrepublic.domain;

import java.io.Serializable;

import lombok.Data;

/**
 * @author	이미연
 * @since	2019. 2. 27.
 * @version	
 * - 스터디 DTO 의 기초 클래스
 * - 초기값, 제약조건 추가 필요함
 */
@Data
public class StudyIdGenerator implements Serializable {

	private String prefix;
	private double counter;
	
	
}
