package org.mohajo.studyrepublic.domain;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import lombok.Data;
import lombok.ToString;

/**
 * @author 신상용
 * @since 2019.01.22
 * @version
 * StudyBoard domain Parent 클래스 추가
 */

@Data
@MappedSuperclass
public class StudyBoard implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Column(name = "study_id", insertable=false, updatable=false)
	private String studyId;
	@Column
	private int number;
	@Column
	private String title;
	@Column(nullable = false)
	private String content;
	@Column
	private Date date;
	@Column(insertable=false, updatable=false)
	private String id;
	@Column
	private int status;
	
	@NotFound(action=NotFoundAction.IGNORE)
	@ManyToOne(optional=false)
	@JoinColumns({
		@JoinColumn(name="study_id", insertable=false, updatable=false),
		@JoinColumn(name="id", insertable=false, updatable=false)
	})
	private StudyMember studyMember;
}
