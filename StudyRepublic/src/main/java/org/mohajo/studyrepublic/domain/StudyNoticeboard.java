package org.mohajo.studyrepublic.domain;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;
import lombok.ToString;

/**
 * @author 신상용
 * @since 2019.01.22
 * @version
 * StudyNoticeboard domain 클래스 추가
 */

@Data
@Entity
@ToString
@Table(name = "study_noticeboard")
public class StudyNoticeboard extends StudyBoard implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "study_noticeboard_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int studyNoticeboardId;
	@Column(name = "starttime")
	private Time startTime;
	@Column(name = "endtime")
	private Time endTime;
	@Column
	private Date day;
	@Column
	private String location;
	
	@OneToMany
	@JoinColumn(name="study_noticeboard_id")
	private List<StudyNoticeboardFile> studyNoticeboardFile;
	
	@OneToMany
	@JoinColumn(name="study_noticeboard_id")
	private List<StudyNoticeboardReply> studyNoticeboardReply;

/**
 * 작성자: 이미연
 * 작성일: 2019-01-30
 * 참고사이트:	https://medium.com/@SlackBeck/%EC%A4%91%EC%B2%A9%EB%90%9C-fk-foreign-key-%EB%A5%BC-jpa%EB%A1%9C-%EC%97%B0%EA%B4%80-%EA%B4%80%EA%B3%84-%EB%A7%A4%ED%95%91-%ED%95%98%EA%B8%B0-216ba5f2b8ed
 * 참조 엔티티는 읽기전용이므로 insert, update 불가함에 주의
 */
//	@ManyToOne
//	@JoinColumns({
//			@JoinColumn(name="study_id", referencedColumnName = "studyId", insertable = false, updatable = false),
//			@JoinColumn(name="id", referencedColumnName = "id", insertable = false, updatable = false)
//	})
//	private StudyMember studyMember;
	
	
}
