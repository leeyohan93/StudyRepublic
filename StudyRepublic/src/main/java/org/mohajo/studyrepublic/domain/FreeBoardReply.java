/**
 * 
 */
package org.mohajo.studyrepublic.domain;

import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;

/**
 * @author 윤원식
 * @since 2019. 1. 23.
 * @version
 * -FreeBoardReply 도메인 추가
 */

@Getter
@Setter
@ToString
@Entity
@Table(name = "freeboard_reply")
public class FreeBoardReply extends Reply{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "freeboard_reply_id")
	private int freeBoardReplyId;

	@Column(name = "freeboard_id")
	private int freeBoardId;
}
