package org.mohajo.studyrepublic.domain;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

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
@ToString(exclude="studyReplyMember")
@MappedSuperclass
public class StudyBoardReply implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Column(name = "study_id", insertable=false, updatable=false)
	private String studyId;
	@Column(name = "id", insertable=false, updatable=false)
	private String id;
	@Column
	private String content;
	@Column
	private Date date;
	@Column(name = "replygroup")
	private int replyGroup;
	@Column(name = "replystep")
	private int replyStep;
	
	@NotFound(action=NotFoundAction.IGNORE)
	@ManyToOne(optional=false)
	@JoinColumns({
		@JoinColumn(name="study_id", referencedColumnName="studyId", insertable=false, updatable=false),
		@JoinColumn(name="id", referencedColumnName="id", insertable=false, updatable=false)
	})
	private StudyMember studyReplyMember;
}
