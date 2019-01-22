package org.mohajo.studyrepublic.domain;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.mohajo.studyrepublic.domain.id.StudyMemberId;

import lombok.Data;

/**
 * @author	이미연
 * @since	2019. 1. 22.
 * @version	
 * - 기능 설명 1
 */
@Data
@Entity
public class Review {

	@Id
	private int reviewNumber;
	
	@Column
	private String studyId;
	
//	@Column
//	@JoinColumns({
//		@JoinColumn(name = "study_id", referencedColumnName = "study_id")
//		,@JoinColumn(name = "id", referencedColumnName = "id")
//	})
	@Column
//	private StudyMember studyMember;	//org.springframework.beans.factory.BeanCreationException: Error creating bean with name 'entityManagerFactory' defined in class path resource [org/springframework/boot/autoconfigure/orm/jpa/HibernateJpaConfiguration.class]: Invocation of init method failed; nested exception is javax.persistence.PersistenceException: [PersistenceUnit: default] Unable to build Hibernate SessionFactory; nested exception is org.hibernate.MappingException: Could not determine type for: org.mohajo.studyrepublic.domain.StudyMember, at table: review, for columns: [org.hibernate.mapping.Column(study_member)]
	private StudyMemberId studyMemberId;	//org.springframework.beans.factory.BeanCreationException: Error creating bean with name 'entityManagerFactory' defined in class path resource [org/springframework/boot/autoconfigure/orm/jpa/HibernateJpaConfiguration.class]: Invocation of init method failed; nested exception is javax.persistence.PersistenceException: [PersistenceUnit: default] Unable to build Hibernate SessionFactory; nested exception is org.hibernate.MappingException: Could not determine type for: java.lang.reflect.Member, at table: review, for columns: [org.hibernate.mapping.Column(member)]
	
	@Column
	private String content;
	
	@Column
	private float score;
	
	@Column
	private Date registerDate;
	
}
