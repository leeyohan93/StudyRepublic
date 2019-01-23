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
 * -InquireBoard 도메인 추가
 */


@Getter
@Setter
@ToString
@Entity
@Table(name = "inquireboard")
public class InquireBoard extends Board{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "inquireboard_id")
	private int inquireBoardId;
	private int hide;
	@Column(name = "commentgroup")
	private int commentGroup;
	@Column(name = "commentstep")
	private int commentStep;
	@Column(name = "commentindent")
	private int commentIndent;

}
