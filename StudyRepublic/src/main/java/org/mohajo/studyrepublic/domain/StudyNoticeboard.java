package org.mohajo.studyrepublic.domain;

import java.sql.Date;
import java.sql.Time;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

/**
 * @author 신상용
 * @since 2019.01.22
 * @version
 * StudyNoticeboard domain 클래스 추가
 */

@Getter
@Setter
@Entity
@Table(name = "study_noticeboard")
public class StudyNoticeboard extends StudyBoard{

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
}
