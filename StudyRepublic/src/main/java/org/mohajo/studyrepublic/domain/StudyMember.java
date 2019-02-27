package org.mohajo.studyrepublic.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
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
		private StudyMemberId studyMemberId = new StudyMemberId();
		
		@Column(insertable=false, updatable=false)
		private String id;
		
		@Column(insertable=false, updatable=false)
		private String studyId;
		
		/**
		 * @author 신상용
		 * CasCadeType.All로 인해서 StudyMember에서  StudyMemberStatusCD 값을 바꾸는 행위가, studyMemberStatusCD 테이블의 변경으로 이어지면서 Constraint가 적용됨.
		 * StudyMember 테이블의 변경이 StudyMemberStatusCD 테이블로의 변경이 이뤄질 필요가 없기 때문에 해당 부분 주석 처리함.
		 */
		@ManyToOne/*(cascade=CascadeType.ALL) */
		@JoinColumn(name = "study_member_status_code", nullable=false)
		private StudyMemberStatusCD studyMemberStatusCode/* = new StudyMemberStatusCD("WA", "")*/;
		
		@Column(nullable=false)
		private Date enrollDate = new Date();
		
		private Date exitDate;
		
//		@MapsId("id")
//		// org.hibernate.id.IdentifierGenerationException: attempted to assign id from null one-to-one property [org.mohajo.studyrepublic.domain.StudyMember.member]
//		// @ManyToOne
//		@ManyToOne(fetch=FetchType.LAZY)
//		@JoinColumn(name = "id", updatable=false, insertable=false)
		
		@MapsId("id")
		@ManyToOne
		@JoinColumn(name="id", updatable=false, insertable=false)
		private Member member;
		
//		@MapsId("studyId")
//		@ManyToOne 	
//		@JoinColumn(name="studyId")
//		private StudyView studyView;
		
		@MapsId("studyId")
		@ManyToOne
		@JoinColumn(name="studyId", updatable=false, insertable=false)
		private Study study;
		
		/*@OneToMany(mappedBy="studyMember")
		private List<StudyNoticeboard> studyNoticeboard;
		
		@OneToMany(mappedBy="studyMember")
		private List<StudyFileshareboard> studyFileshareboard;
		
		@OneToMany(mappedBy="studyMember")
		private List<StudyQnaboard> studyQnaboard;*/
		
		/* Caused by: org.hibernate.AnnotationException: A Foreign key refering org.mohajo.studyrepublic.domain.StudyMember from org.mohajo.studyrepublic.domain.LeveltestResponse has the wrong number of column. should be 2 */
//		@MapsId("id")
		@OneToMany
		@JoinColumns({
			@JoinColumn(name = "id", referencedColumnName = "id", updatable=false, insertable=false),
			@JoinColumn(name = "studyId", referencedColumnName = "studyId", updatable=false, insertable=false)
		})
		private List<LeveltestResponse> leveltestResponse;
}

