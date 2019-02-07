package org.mohajo.studyrepublic.domain;

import java.io.Serializable;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SecondaryTable;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;
import lombok.ToString;

/**
 * @author	이미연
 * @since	2019. 1. 22.
 * @version	
 * - 스터디 DTO 의 기초 클래스
 * - 초기값, 제약조건 추가 필요함
 */
@Data
@ToString(exclude = "review")
@Entity
@Table(name = "study", schema = "StudyRepublic")
@SecondaryTable(name="popular_study")
public class Study implements Serializable {

	@Id
	private String studyId;							//스터디 코드
	
	@Column(nullable = false)
	private String name;							//스터디명
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "leader_id", nullable = false)
	private Member member;
	
	@ManyToOne
	@JoinColumn(name = "TYPE_CODE", nullable = false)
	private TypeCD typeCode;						//유형코드 (B/P)
	
	@ManyToOne
	@JoinColumn(name = "ONOFF_CODE", nullable = false)
	private OnoffCD onoffCode;						//방식코드 (O/F/B)
	
	@ManyToOne
	@JoinColumn(name = "STUDY_STATUS_CODE", nullable = false)
	private StudyStatusCD studyStatusCode;			//상태코드 (O/G/F/D/C)
	
	@ManyToOne
	@JoinColumn(name = "LEVEL_CODE", nullable = false)
	private LevelCD levelCode;						//레벨코드 (L/M/H)
	
	@Temporal(TemporalType.DATE)					// 년, 월, 일 형식으로 출력하겠다. DATE를 TIMESTAMP로 바꾸면 시, 분, 초 까지 출력.
	@Column(nullable = false)
	private Date startDate = new Date();			//시작일
	
	@Temporal(TemporalType.DATE)					// 년, 월, 일 형식으로 출력하겠다. DATE를 TIMESTAMP로 바꾸면 시, 분, 초 까지 출력.
	@Column
	private Date endDate = new Date();				//종료일
	
	@ManyToOne
	@JoinColumn(name = "DAY_CODE", nullable = false)
	private DayCD dayCode;							//요일코드 (0~6)
	
	@Column
	private int studyCount;							//총 회차
	
	@Temporal(TemporalType.TIME)
	@Column(nullable = false)
	private Date startTime;							//시작시각
	
	@Temporal(TemporalType.TIME)
	@Column(nullable = false)
	private Date endTime;							//종료시각
	
	@Column(nullable = false)
	private int enrollCapacity = 5;						//정원
	
	@Column(nullable = false)
	private int enrollActual = 1;						//현재인원
	
	@Column(nullable = false)
	private String introduction;					//소개
	
	@Column
	private int hasLeveltest = 0;						//레벨테스트 유무 (0/1)
	
	@Column
	private Timestamp disbandDate;						//해체일
	
	@Column(nullable = false)
	private Timestamp postDate = new java.sql.Timestamp(new Date().getTime());	//게시일
	
	@OneToMany
	@JoinColumn(name = "study_id", nullable = false)
	private List<StudyInterest> studyInterest;			//분야
	
	@OneToMany
	@JoinColumn(name = "studyId")
	private List<Review> review;
	
	///////////	아래는 Study 클래스를 상속하여 분리시킬 수도 있음 //////////////////////
	
	@OneToOne
	@JoinColumn(name = "study_id")
	private StudyPrice price;							//가격
	
	@OneToMany
	@JoinColumn(name = "study_id")
	private List<StudyLocation> studyLocation;			//지역
	
	/**
	 * Add sangyong.shin
	 */
	/**
	 * @author	이미연
	 * - 정상 실행 됐으나, merge 후 에러 남.
	 */
	/*@OneToMany(mappedBy="study")
	private List<StudyMember> studyMember;*/
	
	
}
