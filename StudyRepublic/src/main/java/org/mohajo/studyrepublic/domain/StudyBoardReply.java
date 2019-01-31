package org.mohajo.studyrepublic.domain;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.MappedSuperclass;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author 신상용
 * @since 2019.01.22
 * @version
 * StudyBoardReply domain Parent 클래스 추가
 */

@Data
@ToString
@MappedSuperclass
public class StudyBoardReply implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Column(name = "study_id")
	private String studyId;
	@Column
	private String id;
	@Column
	private String content;
	@Column
	private Date date;
	@Column(name = "replygroup")
	private int replyGroup;
	@Column(name = "replystep")
	private int replyStep;
}
