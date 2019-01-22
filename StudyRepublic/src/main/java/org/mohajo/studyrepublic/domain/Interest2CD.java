package org.mohajo.studyrepublic.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

//임시테이블
/**
 * @author	이미연
 * @since	2019. 1. 22.
 * @version	
 * - 기능 설명 1
 */
@Data
@Entity
@Table(name = "Interest_2_CD", schema = "StudyRepublic")
public class Interest2CD {
	
	@Id
	private String interest2Code;
	

}
