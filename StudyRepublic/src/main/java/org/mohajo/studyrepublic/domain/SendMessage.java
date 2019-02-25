package org.mohajo.studyrepublic.domain;
/**
 * @author 김준석
 * @since 2019.01.23
 * @version 0.0
 * -@GeneratedValue 추가
 * 
 * 
 */
import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;

@Entity
@Table(name="message_send")
@Data
public class SendMessage extends Message implements Serializable{
	private static final long serialVersionUID= 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="message_send_id" , nullable=false)
	private int messageSendId;
	
	/*	@Column(name="send_nickname", nullable=true)
	private String send_nickname;
	
	@Column(name="receive_nickname", nullable=true)
	private String receive_nickname;
	
	 */
	

}
