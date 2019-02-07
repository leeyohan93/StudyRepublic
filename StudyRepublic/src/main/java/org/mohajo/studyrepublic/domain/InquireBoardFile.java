/**
 * 
 */
package org.mohajo.studyrepublic.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author 윤원식
 * @since 2019. 1. 23.
 * @version
 * -InquireBoardFile 도메인 추가
 */

@Getter
@Setter
@ToString
@Entity
@Table(name = "inquireboard_file")
public class InquireBoardFile extends File{
	
	@Id
	@GeneratedValue
	@Column(name = "inquireboard_file_id")
	private int inquireBoardFileId;
	@Column(name ="inquireboard_id")
	private int inquireBoardId;
	
	@Column(name = "filenumber")
	protected int fileNumber;
	@Column(name = "originname")
	protected String originName;
	@Column(name = "savename")
	protected String saveName;
	


}
