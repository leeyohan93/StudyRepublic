package org.mohajo.studyrepublic.domain;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;

/**
 * @author	이미연
 * @since	2019. 1. 22.
 * @version	0.0
 * - 레벨테스트 응답 DTO
 */
@Data
@Entity
public class LeveltestResponse implements Serializable {

	@EmbeddedId
	private LeveltestResponseId leveltestResponseId;
	
/*//	@Id
//	private String leveltestResponseId;
	
//	@Column
//	private String leveltestId;
*/	
	@Column(insertable=false, updatable=false)
	private String studyId;
	
	@Column(insertable=false, updatable=false)
	private String id;
	
	@Column
	private String submitAnswer;
	
	@Column
	private int isCorrect;

	
	
//	@MapsId("leveltestId")
//	@ManyToOne
//	@JoinColumns({
//		@JoinColumn(name="questionNumber"),
//		@JoinColumn(name="studyId")
//	})
	@ManyToOne
	@JoinColumn(name = "leveltestId", nullable = false, insertable=false, updatable=false)
	private Leveltest leveltest;
	
/*//	@MapsId("id")
	@ManyToOne
	@JoinColumn(name = "id", referencedColumnName = "id", updatable=false, insertable=false)
	private Member member;*/
	
/*//	@MapsId("studyMemberId")
	@ManyToOne
	@JoinColumns({
		@JoinColumn(name="studyId", insertable=false, updatable=false, referencedColumnName="studyId"),
		@JoinColumn(name="id", insertable=false, updatable=false, referencedColumnName="id")
	})
	private StudyMember studyMember;*/
}
