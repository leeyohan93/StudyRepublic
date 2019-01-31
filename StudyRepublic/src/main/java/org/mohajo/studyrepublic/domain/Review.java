package org.mohajo.studyrepublic.domain;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToOne;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import lombok.Data;

/**
 * @author	이미연
 * @since	2019. 1. 22.
 * @version	0.0
 * - 리뷰 DTO
 */
@Data
@Entity
public class Review implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int reviewNumber;
	
	@NotFound(action=NotFoundAction.IGNORE)
	@OneToOne
	@JoinColumns({
		@JoinColumn(name = "studyId", updatable=false, insertable=false),
		@JoinColumn(name = "id", updatable=false, insertable=false)
	})
	private StudyMember studyMember;

	private String studyId;
	private String id;
	
	@Column(nullable = false)
	private String content;
	
	@Column(nullable = false)
	private float score;
	
	
}
