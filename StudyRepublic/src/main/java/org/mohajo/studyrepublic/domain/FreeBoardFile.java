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
 * -FreeBoardFile 도메인 추가
 */

@Getter
@Setter
@ToString
@Entity
@Table(name = "freeboard_file")
public class FreeBoardFile extends File{
	
	@Id
	@GeneratedValue
	@Column(name = "freeboard_file_id")
	private int freeBoardFileId;

	@Column(name ="freeboard_id")
	private int freeBoardId;
	
	@Column(name = "originname")
	protected String originName;
	@Column(name = "savename")
	protected String saveName;
	@Column(name = "uploadpath")
	private String uploadPath;
	@Column(name= "parturl")
	private String partUrl;
	@Column(name= "fullurl")
	private String fullUrl;
	
	


}
