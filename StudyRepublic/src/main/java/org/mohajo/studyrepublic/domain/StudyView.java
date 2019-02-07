//package org.mohajo.studyrepublic.domain;
//
//import java.io.Serializable;
//import java.util.Date;
//
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.Id;
//import javax.persistence.Table;
//import javax.persistence.Temporal;
//import javax.persistence.TemporalType;
//
//import lombok.Data;
//import lombok.ToString;
//
///**
// * @author	이미연
// * @since	2019. 1. 22.
// * @version	
// * - 스터디 DTO 의 기초 클래스
// * - 초기값, 제약조건 추가 필요함
// */
//@Data
//@ToString(exclude = "review")
//@Entity
//@Table(name = "study_view", schema = "StudyRepublic")
//public class StudyView implements Serializable {
//
//	@Id
//	private String studyId;
//	
////	@OneToOne
////	@JoinColumn(name = "studyId")
////	private Study study;
//	
//	@Column(name="date_diff", table="study_view")
//	private int dateDiff;
//	
//	@Temporal(TemporalType.TIME)
//	@Column(name="time_diff", table="study_view")
//	private Date timeDiff;
//	
//	@Column(name="review_count", table="study_view")
//	private int reviewCount;
//
//	@Column(name="average_score", table="study_view")
//	private float averageScore;
//	
//	
//}
