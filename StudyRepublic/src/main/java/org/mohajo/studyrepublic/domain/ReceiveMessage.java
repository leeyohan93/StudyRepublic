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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name="message_receive")
@Data
public class ReceiveMessage extends Message implements Serializable{
	private static final long serialversionuid = 1l;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="message_receive_id" , nullable=false)
	private int messageReceiveId;
	


}
