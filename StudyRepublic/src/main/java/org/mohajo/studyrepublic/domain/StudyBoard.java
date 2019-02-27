package org.mohajo.studyrepublic.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

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

/*
 * 해당 클래스는 insert 및 save를 수행할 때 입력되는 데이터를 전달해야하는 class 였지만, MappedSuperclass의 사용으로
 * 부모 클래스의 내용이 Mapping 되지 않아서 해당 클래스의를 상속받아서 운반한 class의 상속받은 데이터가 모두 null 처리 되게 됨.
 * 수정이 가능하지만, 프로젝트 기간이 얼마남지 않은 시점에서 leanning curve를 감당할 수 없어서 어쩔 수 없이 사용을 포기하고 자식 entity로 내용을 이식함.
 * @date 2019.02.16
 */

@Data
@MappedSuperclass
@ToString(exclude="studyMember")
public class StudyBoard implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Column(name = "study_id")
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
	private StudyMember studyMember;
}
