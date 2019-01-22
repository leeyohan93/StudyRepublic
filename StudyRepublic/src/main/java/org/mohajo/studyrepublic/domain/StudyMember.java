package org.mohajo.studyrepublic.domain;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;

import org.mohajo.studyrepublic.domain.code.StudyMemberStatusCD;
import org.mohajo.studyrepublic.domain.id.StudyMemberId;

import lombok.Data;

/**
 * @author	이미연
 * @since	2019. 1. 22.
 * @version	
 * - 기능 설명 1
 */
@Data
@Entity
public class StudyMember {

		@EmbeddedId
		private StudyMemberId studyMemberId;
		
		@Column
		@JoinColumn(name = "study_member_status_code")
		private StudyMemberStatusCD studyMemberStatusCode;
		
		@Column
		private Date enrollDate;
		
		@Column
		private Date exitDate;
	
}
