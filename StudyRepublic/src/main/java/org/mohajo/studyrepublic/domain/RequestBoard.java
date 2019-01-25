/**
 * 
 */
package org.mohajo.studyrepublic.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author 윤원식
 * @since 2019. 1. 23.
 * @version
 * -requestBoard 도메인 추가
 */


@Getter
@Setter
@ToString
@Entity
@Table(name = "requestboard")
public class RequestBoard extends Board{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "requestboard_id")
	private int requestBoardId;

}
