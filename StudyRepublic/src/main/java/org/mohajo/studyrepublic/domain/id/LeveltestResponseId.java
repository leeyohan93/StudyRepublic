package org.mohajo.studyrepublic.domain.id;

import java.io.Serializable;

import javax.persistence.Embeddable;

/**
 * @author	이미연
 * @since	2019. 1. 22.
 * @version	
 * - 기능 설명 1
 */
@Embeddable
public class LeveltestResponseId implements Serializable {	//org.springframework.beans.factory.BeanCreationException: Error creating bean with name 'entityManagerFactory' defined in class path resource [org/springframework/boot/autoconfigure/orm/jpa/HibernateJpaConfiguration.class]: Invocation of init method failed; nested exception is javax.persistence.PersistenceException: [PersistenceUnit: default] Unable to build Hibernate SessionFactory; nested exception is org.hibernate.MappingException: Composite-id class must implement Serializable: org.mohajo.studyrepublic.domain.id.LeveltestResponseId

//	private Leveltest leveltest;	//org.springframework.beans.factory.BeanCreationException: Error creating bean with name 'entityManagerFactory' defined in class path resource [org/springframework/boot/autoconfigure/orm/jpa/HibernateJpaConfiguration.class]: Invocation of init method failed; nested exception is javax.persistence.PersistenceException: [PersistenceUnit: default] Unable to build Hibernate SessionFactory; nested exception is org.hibernate.MappingException: Could not determine type for: org.mohajo.studyrepublic.domain.Leveltest, at table: leveltest_response, for columns: [org.hibernate.mapping.Column(leveltest)]
	private LeveltestId leveltestId;
//	private Member member;
	
}
