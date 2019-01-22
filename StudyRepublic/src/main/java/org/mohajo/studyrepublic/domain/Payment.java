package org.mohajo.studyrepublic.domain;

import java.lang.reflect.Member;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
public class Payment {

	@Id
	private String paymentId;
	
//	@OneToMany
//	@JoinColumn(name = "id")
//	private Member member;
	
	@Column
	private Study study;
	
	@Column
	private int totalAmount;
	
	@Column
	private Date paymentDate;
	
	@Column
	private String tid;
}
