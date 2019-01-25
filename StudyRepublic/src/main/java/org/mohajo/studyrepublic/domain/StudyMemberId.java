package org.mohajo.studyrepublic.domain;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

import org.mohajo.studyrepublic.domain.Member;

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

	private String studyId;
	private String id;
	
}
