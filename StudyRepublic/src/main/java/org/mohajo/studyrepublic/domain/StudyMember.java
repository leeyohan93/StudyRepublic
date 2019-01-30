package org.mohajo.studyrepublic.domain;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

import org.springframework.stereotype.Component;

import lombok.Data;

/**
 * @author	이미연
 * @since	2019. 1. 22.
 * @version	0.0
 * - 스터디원 상세 DTO
 */
@Data
@Entity
@Component
public class StudyMember implements Serializable {

		@EmbeddedId
		private StudyMemberId studyMemberId;
		
		@ManyToOne(cascade=CascadeType.ALL) 
		@JoinColumn(name = "study_member_status_code")
		private StudyMemberStatusCD studyMemberStatusCode;
		
		@Column
		private Date enrollDate;
		
		@Column
		private Date exitDate;
		
		@MapsId("id")
		@ManyToOne 
		@JoinColumn(name = "id")
		private Member member;

		//급하게 추가한 코드. 에러 나면 주석 처리하기.
		//duplicate field name 에러 없이 처리하는 방법 확인할 것
//		@ManyToOne(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
		@MapsId("studyId")
		@ManyToOne 
		@JoinColumn(name="studyId")
		private Study study;
}
