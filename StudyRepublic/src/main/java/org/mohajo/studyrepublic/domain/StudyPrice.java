package org.mohajo.studyrepublic.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

/**
 * @author	이미연
 * @since	2019. 1. 22.
 * @version	0.0
 * - 스터디 가격 DTO
 */

@Data
@Entity
public class StudyPrice implements Serializable {
	
	@Id
	private String studyId;
	//외래키가 ID 이면 다음 에러가 나는 모양
	//org.hibernate.id.IdentifierGenerationException: ids for this class must be manually assigned before calling save(): org.mohajo.studyrepublic.domain.StudyPrice
//	@Id
//	@GeneratedValue(strategy = GenerationType.AUTO)
//	private int priceId;
	//Study 쪽에서 StudyPrice 에 nullable = false 설정을 해도 study_id 를 못 찾는다고 함. DB 및 도메인 원복함.
	
//	private String studyId;
	
	@Column(nullable = false)
	private int price;
	
}
