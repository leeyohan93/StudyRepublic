package org.mohajo.studyrepublic.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "member_interest", schema = "StudyRepublic")
public class MemberInterest {

	@Id
	@Column(name = "member_interest_id")
	private int memberInterestId;
	
	/*
	 * 수정 작성:	이미연
	 * 수정 사유:	회원 정보와 관심분야 한 번에 조회하기 위함
	 */
//	@ManyToOne
//	@JoinColumn(name = "member")
//	private Member member;
	private String id;
	
	@ManyToOne
	@JoinColumn(name = "INTEREST_2_CODE")
	private Interest2CD interest2cd;
	
	
}
