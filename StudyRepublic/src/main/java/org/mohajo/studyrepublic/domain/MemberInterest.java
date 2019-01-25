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
	
	@ManyToOne
	@JoinColumn(name = "member")
	private Member member;
	
	@ManyToOne
	@JoinColumn(name = "INTEREST_2_CODE")
	private Interest2CD interest2cd;
	
	
}
