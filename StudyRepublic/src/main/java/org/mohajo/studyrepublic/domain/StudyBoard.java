package org.mohajo.studyrepublic.domain;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import lombok.Getter;
import lombok.Setter;

/**
 * @author 신상용
 * @since 2019.01.22
 * @version
 * StudyBoard domain Parent 클래스 추가
 */

@Getter
@Setter
@MappedSuperclass
public class StudyBoard implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Column(name = "study_id")
	private String studyId;
	@Column
	private int number;
	@Column
	private String title;
	@Column
	private String content;
	@Column
	private Date date;
	@Column
	private String id;
	@Column
	private int status;
}
