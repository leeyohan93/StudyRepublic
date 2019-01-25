package org.mohajo.studyrepublic.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import lombok.Getter;
import lombok.Setter;

/**
 * @author 신상용
 * @since 2019.01.22
 * @version
 * StudyBoardFile domain Parent 클래스 추가
 */

@Getter
@Setter
@MappedSuperclass
public class StudyBoardFile implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Column(name = "filenumber")
	private int fileNumber;
	@Column(name = "originname")
	private String originName;
	@Column(name = "savename")
	private String saveName;
}
