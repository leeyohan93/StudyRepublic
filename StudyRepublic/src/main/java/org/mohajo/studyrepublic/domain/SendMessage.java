package org.mohajo.studyrepublic.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;

@Entity
@Table(name="message_send") //, schema = "StudyRepublic" )
@Data
public class SendMessage extends Message implements Serializable{
	private static final long serialVersionUID= 1L;
	
	@Id
	@Column(name="message_send_id" , nullable=false)
	private int messageSendid;
	

	

}
