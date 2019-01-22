package org.mohajo.studyrepublic.domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

import lombok.Data;

/**
 * @author	이미연
 * @since	2019. 1. 22.
 * @version	
 * - 기능 설명 1
 */
@Data
@Entity
public class OfflineStudy extends Study {

	//테이블 어노테이션 붙여도 되는지?
	
//	@Column	////org.springframework.beans.factory.BeanCreationException: Error creating bean with name 'entityManagerFactory' defined in class path resource [org/springframework/boot/autoconfigure/orm/jpa/HibernateJpaConfiguration.class]: Invocation of init method failed; nested exception is javax.persistence.PersistenceException: [PersistenceUnit: default] Unable to build Hibernate SessionFactory; nested exception is org.hibernate.MappingException: Could not determine type for: java.util.List, at table: study, for columns: [org.hibernate.mapping.Column(locations)]
	@OneToMany
	private List<StudyLocation> locations;
	
}
