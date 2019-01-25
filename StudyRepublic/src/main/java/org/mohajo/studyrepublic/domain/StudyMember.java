package org.mohajo.studyrepublic.domain;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

import lombok.Data;

/**
 * @author	이미연
 * @since	2019. 1. 22.
 * @version	0.0
 * - 스터디원 상세 DTO
 */
@Data
@Entity
public class StudyMember implements Serializable {

		@EmbeddedId
		private StudyMemberId studyMemberId;
		
		@ManyToOne
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

}
