/**
 * 
 */
package org.mohajo.studyrepublic.domain;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author 윤원식
 * @since 2019. 1. 23.
 * @version
 * -File 기초클래스 추가
 */

@Getter
@Setter
@ToString
@MappedSuperclass
public class File {
	

	@Column(name = "originname")
	protected String originName;
	@Column(name = "savename")
	protected String saveName;
	


}
