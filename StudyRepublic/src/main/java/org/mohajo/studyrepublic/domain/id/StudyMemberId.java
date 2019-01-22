package org.mohajo.studyrepublic.domain.id;

import java.io.Serializable;
import java.lang.reflect.Member;

import javax.persistence.Embeddable;
import javax.persistence.Id;

import org.mohajo.studyrepublic.domain.Study;

/**
 * @author	이미연
 * @since	2019. 1. 22.
 * @version	
 * - 기능 설명 1
 */
@Embeddable
public class StudyMemberId implements Serializable {

//	@Id
//	org.springframework.beans.factory.BeanCreationException: Error creating bean with name 'entityManagerFactory' defined in class path resource [org/springframework/boot/autoconfigure/orm/jpa/HibernateJpaConfiguration.class]: Invocation of init method failed; nested exception is org.hibernate.AnnotationException: org.mohajo.studyrepublic.domain.id.StudyMemberId must not have @Id properties when used as an @EmbeddedId
	private Study studyId;
	
//	@Id
	private Member member;
	
}
