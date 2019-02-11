package org.mohajo.studyrepublic.domain;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;

import lombok.Data;
<<<<<<< HEAD
import lombok.ToString;

/**
 * @author	이미연
 * @since	2019. 1. 22.
 * @version	0.0
 * - 스터디원 상세 DTO
 */
@Data
@Entity
@ToString(exclude="studyNoticeboard, studyNoticeboardReply")
public class StudyMember implements Serializable {

		@EmbeddedId
		private StudyMemberId studyMemberId;
		
		@Column(insertable=false, updatable=false)
		private String id;
		
		@Column(insertable=false, updatable=false)
		private String studyId;
		
		@ManyToOne(cascade=CascadeType.ALL) 
		@JoinColumn(name = "study_member_status_code")
		private StudyMemberStatusCD studyMemberStatusCode;
		
		private Date enrollDate;
		private Date exitDate;
		
		@MapsId("id")
		@ManyToOne 
		@JoinColumn(name = "id")
		private Member member;
		
		@MapsId("studyId")
		@ManyToOne 	
		@JoinColumn(name="studyId")
		private Study study;
		
		/*@OneToMany(mappedBy="studyMember")
		private List<StudyNoticeboard> studyNoticeboard;*/
		/*@OneToMany(mappedBy="studyMember")
		private List<StudyFileshareboard> studyFileshareboard;
		@OneToMany(mappedBy="studyMember")
		private List<StudyQnaboard> studyQnaboard;*/
		
		/*@OneToMany(mappedBy="studyReplyMember")
		private List<StudyNoticeboardReply> studyNoticeboardReply;*/
		/*@OneToMany(mappedBy="studyReplyMember")
		private List<StudyFileshareboardReply> studyFileshareboardReply;
		@OneToMany(mappedBy="studyReplyMember")
		private List<StudyQnaboardReply> studyQnaboardReply;*/
=======

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
		
		@Column(insertable=false, updatable=false)
		private String id;
		
		@Column(insertable=false, updatable=false)
		private String studyId;
		
		@ManyToOne(cascade=CascadeType.ALL) 
		@JoinColumn(name = "study_member_status_code")
		private StudyMemberStatusCD studyMemberStatusCode;
		
		private Date enrollDate;
		private Date exitDate;
		
		@MapsId("id")
		@ManyToOne 
		@JoinColumn(name = "id")
		private Member member;
		
		@MapsId("studyId")
		@ManyToOne 	
		@JoinColumn(name="studyId")
		private Study study;
		
		@OneToMany(mappedBy="studyMember")
		private List<StudyNoticeboard> studyNoticeboard;
		
		@OneToMany(mappedBy="studyMember")
		private List<StudyFileshareboard> studyFileshareboard;
		
		@OneToMany(mappedBy="studyMember")
		private List<StudyQnaboard> studyQnaboard;
		
>>>>>>> refs/heads/Sungho

}
