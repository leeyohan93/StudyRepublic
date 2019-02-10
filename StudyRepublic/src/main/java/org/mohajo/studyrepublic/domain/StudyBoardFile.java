package org.mohajo.studyrepublic.domain;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author 신상용
 * @since 2019.01.22
 * @version
 * StudyBoardFile domain Parent 클래스 추가
 */

@Data
@MappedSuperclass
public class StudyBoardFile implements Serializable{
	
	@Column(name = "filenumber")
	private int fileNumber;
	@Column(name = "originname")
	private String originName;
	@Column(name = "savename")
	private String saveName;
}
