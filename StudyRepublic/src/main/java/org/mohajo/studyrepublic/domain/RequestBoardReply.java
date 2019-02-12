/**
 * 
 */
package org.mohajo.studyrepublic.domain;

import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Getter;

/**
 * @author 윤원식
 * @since 2019. 1. 23.
 * @version
 * -requestBoardReply 도메인 추가
 */

@Getter
@Setter
@ToString
@Entity
@Table(name = "requestboard_reply")
public class RequestBoardReply extends Reply{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "requestboard_reply_id")
	private int requestBoardReplyId;

	protected String id;
	protected String content;
	@CreationTimestamp
	protected Timestamp date;
	@Column(name = "replygroup")
	protected int replyGroup;
	@Column(name = "replystep")
	protected int replyStep;


	@Column(name = "requestboard_id")
	private int requestBoardId;

	@ManyToOne
	@JoinColumn(name="id",insertable=false, updatable=false)
	protected Member member;
}
