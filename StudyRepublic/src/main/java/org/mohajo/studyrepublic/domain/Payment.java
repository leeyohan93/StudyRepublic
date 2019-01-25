package org.mohajo.studyrepublic.domain;

import java.io.Serializable;
import java.lang.reflect.Member;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;

/**
 * @author	이미연
 * @since	2019. 1. 22.
 * @version	0.0
 * - 결제내역 DTO
 */
@Data
@Entity
public class Payment implements Serializable {

	@Id
	private String paymentId;
	
	@JoinColumn(name = "id", nullable = false)
	private String id;
	
	@JoinColumn(name = "study_id", nullable = false)
	private String studyId;
	
	@Column(nullable = false)
	private int totalAmount;
	
	@Column
	private Date paymentDate;
	
	@Column
	private String tid;
}
