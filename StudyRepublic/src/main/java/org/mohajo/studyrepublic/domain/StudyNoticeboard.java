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
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

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
	
	@OneToMany/*(fetch=FetchType.EAGER)*/
	@JoinColumn(name="study_noticeboard_id")
	private List<StudyNoticeboardFile> studyNoticeboardFile;
	
	@OneToMany/*(fetch=FetchType.EAGER)*/
	@JoinColumn(name="study_noticeboard_id")
	private List<StudyNoticeboardReply> studyNoticeboardReply;
}
