package org.mohajo.studyrepublic.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "member_status_cd", schema = "StudyRepublic")
public class MemberStatusCD implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "MEMBER_STATUS_CODE")
	private String memberStatusCode;

	@Column(name = "CODE_VALUE_ENGLISH")
	private String codeValueEnglish;

	@Column(name = "CODE_VALUE_KOREAN")
	private String codeValueKorean;

	public MemberStatusCD(String memberStatusCode) {
		super();
		this.memberStatusCode = memberStatusCode;
	}
	
	public MemberStatusCD(String memberStatusCode,String codevalueKorean) {
		super();
		this.memberStatusCode = memberStatusCode;
		this.codeValueKorean = codevalueKorean;
	}

	public MemberStatusCD() {
		super();
	}


	
	
	
	

}
