/**
 * 
 */
package org.mohajo.studyrepublic.domain;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author 윤원식
 * @since 2019. 1. 23.
 * @version
 * -FreeBoard 도메인 추가
 */


@Getter
@Setter
@ToString
@Entity
@Table(name = "freeboard")
public class FreeBoard extends Board{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "freeboard_id")
	private int freeBoardId;
	



}
