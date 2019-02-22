package org.mohajo.studyrepublic.domain;
/**
 * @author 김준석
 * @since 2019.01.23
 * @version 0.0
 * -
 * 
 * 
 */
import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;


@Data
@MappedSuperclass
public class Message implements Serializable {
	private static final long serialversionUID = 1L;
	
	@Column(name="send_id", nullable=false)
	private String sendId;
	
	@Column(name="receive_id", nullable=false)
	private String receiveId;
	
	@Column(name="message_content", nullable=false)
	private String messageContent;
	
	@Column(name="message_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date messageDate = new Date();
	
	@Column(name="message_delete")
	private String messageDelete="0";
	

}
