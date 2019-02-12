package org.mohajo.studyrepublic.domain;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;

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
		
//		@MapsId("studyId")
//		@ManyToOne 	
//		@JoinColumn(name="studyId")
//		private StudyView studyView;
		
		@MapsId("studyId")
		@ManyToOne 	
		@JoinColumn(name="studyId")
		private Study study;
		
/*		@OneToMany(mappedBy="studyMember")
		private List<StudyNoticeboard> studyNoticeboard;
		
		@OneToMany(mappedBy="studyMember")
		private List<StudyFileshareboard> studyFileshareboard;
		
		@OneToMany(mappedBy="studyMember")
		private List<StudyQnaboard> studyQnaboard;*/
		
		/* Caused by: org.hibernate.AnnotationException: A Foreign key refering org.mohajo.studyrepublic.domain.StudyMember from org.mohajo.studyrepublic.domain.LeveltestResponse has the wrong number of column. should be 2 */
		@MapsId("id")
		@OneToMany
		@JoinColumns({
			@JoinColumn(name = "id"/*, referencedColumnName = "id", updatable=false, insertable=false*/),
			@JoinColumn(name = "studyId")
		})
		private List<LeveltestResponse> leveltestResponse;
		

}
