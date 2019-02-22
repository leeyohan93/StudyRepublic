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
 * -RequestBoardFile 도메인 추가
 */

@Getter
@Setter
@ToString
@Entity
@Table(name = "requestboard_file")
public class RequestBoardFile extends File{
	
	@Id
	@GeneratedValue
	@Column(name = "requestboard_file_id")
	private int requestBoardFileId;
	@Column(name ="requestboard_id")
	private int requestBoardId;
	
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
