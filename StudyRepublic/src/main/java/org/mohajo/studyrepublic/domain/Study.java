package org.mohajo.studyrepublic.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.mohajo.studyrepublic.domain.code.DayCD;
import org.mohajo.studyrepublic.domain.code.LevelCD;
import org.mohajo.studyrepublic.domain.code.OnOffCD;
import org.mohajo.studyrepublic.domain.code.StudyStatusCD;
import org.mohajo.studyrepublic.domain.code.TypeCD;

import lombok.Data;

/**
 * @author	이미연
 * @since	2019. 1. 22.
 * @version	
 * - 스터디 클래스드의 기초 클래스
 */
@Data
@Entity
@Table(name = "study", schema = "StudyRepublic")
public class Study implements Serializable {

	@Id
	private String studyId;							//스터디 코드
	
	@Column
	private String name;							//스터디명
	
	@Column
	private String leaderId;						//리더 아이디
	
	@ManyToOne
	@JoinColumn(name = "TYPE_CODE")
	private TypeCD typeCode;						//유형코드 (B/P)
	
	@ManyToOne
	@JoinColumn(name = "ONOFF_CODE")
	private OnOffCD onOffCode;						//방식코드 (O/F/B)
	
	@ManyToOne
	@JoinColumn(name = "STUDY_STATUS_CODE")
	private StudyStatusCD studyStatusCode;			//상태코드 (O/G/F/D/C)
	// = "O"
	
	@ManyToOne
	@JoinColumn(name = "LEVEL_CODE")
	private LevelCD levelCode;						//레벨코드 (L/M/H)
	// = "M"
	
	@Column
	@Temporal(TemporalType.DATE)					// 년, 월, 일 형식으로 출력하겠다. DATE를 TIMESTAMP로 바꾸면 시, 분, 초 까지 출력.
	private Date startDate;							//시작일
	
	@Column
	@Temporal(TemporalType.DATE)					// 년, 월, 일 형식으로 출력하겠다. DATE를 TIMESTAMP로 바꾸면 시, 분, 초 까지 출력.
	private Date endDate;							//종료일
	
	@ManyToOne
	@JoinColumn(name = "DAY_CODE")
	private DayCD dayCode;							//요일코드 (0~6)
	
	@Column
	private int studyCount;							//총 회차
	
	@Column
	private long startTime;							//시작시각
	
	@Column
	private long endTime;							//종료시각
	
	@Column
	private int enrollCapacity;						//정원
	
	@Column
	private int enrollActual;						//현재인원
	
	@Column
	private String introduction;					//소개
	
	@Column
	private int hasLeveltest;						//레벨테스트 유무 (0/1)
	
	@Column
	private Date disbandDate;						//해체일
	
	@Column
	private java.sql.Date postDate = new java.sql.Date(new java.util.Date().getTime());	//게시일
	
//	@OneToMany
//	@JoinColumn(name = "study_interest_id")
//	private StudyInterest studyInterest;			//분야
	
//	@ManyToOne
////	@JoinColumn(name = "id")						//될 것인가 이것이..	//org.springframework.beans.factory.BeanCreationException: Error creating bean with name 'entityManagerFactory' defined in class path resource [org/springframework/boot/autoconfigure/orm/jpa/HibernateJpaConfiguration.class]: Invocation of init method failed; nested exception is org.hibernate.AnnotationException: @OneToOne or @ManyToOne on org.mohajo.studyrepublic.domain.Study.studyMembers references an unknown entity
//	@JoinColumns({
//		@JoinColumn(name = "study_id")
//		,@JoinColumn(name = "id")
//	})
//	private List<StudyMember> studyMembers;	
	
//	@OneToMany
//	@JoinColumns({
//		@JoinColumn(name = "question_number", referencedColumnName = "question_number")
//		,@JoinColumn(name = "study_id", referencedColumnName = "study_id")
//	})
//	private List<Leveltest> leveltests;			//org.springframework.beans.factory.BeanCreationException: Error creating bean with name 'entityManagerFactory' defined in class path resource [org/springframework/boot/autoconfigure/orm/jpa/HibernateJpaConfiguration.class]: Invocation of init method failed; nested exception is org.hibernate.AnnotationException: Unable to map collection org.mohajo.studyrepublic.domain.Study.leveltests
	
	@OneToMany
//	@JoinColumn(name = "review_number")	//org.springframework.beans.factory.BeanCreationException: Error creating bean with name 'entityManagerFactory' defined in class path resource [org/springframework/boot/autoconfigure/orm/jpa/HibernateJpaConfiguration.class]: Invocation of init method failed; nested exception is org.hibernate.DuplicateMappingException: Table [review] contains physical column name [review_number] referred to by multiple physical column names: [review_number], [reviewNumber]
	@JoinColumn(name = "reviewNumber")	//review 클래스의 PK 변수명과 동일하게 바꿈.
	private List<Review> reviews;		

	
	
}
