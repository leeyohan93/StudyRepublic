/**
 * 
 */
package org.mohajo.studyrepublic.domain;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author 윤원식
 * @since 2019. 1. 23.
 * @version
 * -Reply 기초클래스 추가
 */

@Getter
@Setter
@ToString
@MappedSuperclass
public class Reply {

	protected String id;
	protected String content;
	@Temporal(TemporalType.TIMESTAMP)
	protected Date date= new Date();
	@Column(name = "replygroup")
	protected int replyGroup;
	@Column(name = "replystep")
	protected int replyStep;
	
}
