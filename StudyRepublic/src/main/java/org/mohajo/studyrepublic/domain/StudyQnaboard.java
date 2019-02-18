package org.mohajo.studyrepublic.domain;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import lombok.Data;

/**
 * @author 신상용
 * @since 2019.01.22
 * @version
 * StudyQnaboard domain 클래스 추가
 */

@Data
@Entity
@Table(name = "study_qnaboard")
public class StudyQnaboard extends StudyBoard implements Serializable{

	
	@Id
	@Column(name = "study_qnaboard_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int studyQnaboardId;
	
	/*@Column(name = "study_id")
	private String studyId;
	@Column
	private int number;
	@Column
	private String title;
	@Column(nullable = false)
	private String content;
	@Temporal(TemporalType.TIMESTAMP)
	@Column
	private Date date=new Date();
	@Column
	private String id;
	@Column
	private int status;
	
	@NotFound(action=NotFoundAction.IGNORE)
	@ManyToOne(optional=false)
	@JoinColumns({
		@JoinColumn(name="study_id", referencedColumnName="studyId", insertable=false, updatable=false),
		@JoinColumn(name="id", referencedColumnName="id", insertable=false, updatable=false)
	})
	private StudyMember studyMember;*/
	
	@OneToMany
	@JoinColumn(name="study_qnaboard_id")
	private List<StudyQnaboardFile> studyQnaboardFile;
	
	@OneToMany
	@JoinColumn(name="study_qnaboard_id")
	private List<StudyQnaboardReply> studyQnaboardReply;
}
