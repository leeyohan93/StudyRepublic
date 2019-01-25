package org.mohajo.studyrepublic.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * @author 이요한
 * @since 2019.01.22
 * @version
 * - AdminCommnet 도메인 추가
 * 
 */

@Data
@Entity
@Table(name = "admin_comment", schema="StudyRepublic")
public class AdminComment {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int adminCommentId;
	
	private String id;
	private String content;
	private Date date;

}
