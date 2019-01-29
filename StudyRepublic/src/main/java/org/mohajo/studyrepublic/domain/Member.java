package org.mohajo.studyrepublic.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/*import org.springframework.security.core.authority.SimpleGrantedAuthority;*/


import lombok.Data;



@Data
@Entity
@Table(name = "member", schema = "StudyRepublic")
public class Member implements Serializable {



/*	public Member(String username, String string, List<SimpleGrantedAuthority> asList) {

	}*/

	private static final long serialVersionUID = 1L;
	
	public Member() {
		super(); 
}

	
	@Id 
	private String id;

	@Column(nullable = false)
	private String password;
	
	@Column(nullable = false)
	private String name;
	
	@Column(nullable = false)
	private String gender;
	
/*	@Temporal(TemporalType.DATE)*/	// 년, 월, 일 형식으로 출력하겠다. DATE를 TIMESTAMP로 바꾸면 시, 분, 초 까지 출력.
	@Column(nullable = false)
	private String birth;	// 파라미터를 @ModelAttribute로 한번에 받기 위함. birth를 Date로 적으면 에러남.... DB상에는 DATE로 저장함.
	/*private Date birth;   */                                                    
	
	@Column(nullable = false)
	private String nickname;
	
	@Column(nullable = false)
	private String email;
	
	@Column(nullable = false)
	private String phonenumber;
	
	@Column(nullable = false)
	private int visibility;
	
	@Column(nullable = false)
	private String profileOriginName;
	
	@Column(nullable = false)
	private String profileSaveName;
	
	@Temporal(TemporalType.DATE)	// 년, 월, 일 형식으로 출력하겠다. DATE를 TIMESTAMP로 바꾸면 시, 분, 초 까지 출력.
	@Column(nullable = false)
	private Date registrationDate = new Date();	// 자동으로 현재 날짜로 가입일을 초기화.
/*	private java.sql.Date date2 = new java.sql.Date(visibility, visibility, visibility);*/
	
	@ManyToOne
	@JoinColumn(name = "MEMBER_STATUS_CODE")
	private MemberStatusCD memberStatusCD = new MemberStatusCD("N");
	
	@ManyToOne
	@JoinColumn(name = "GRADE_CODE")
	private GradeCD gradeCD = new GradeCD("N");
	
	
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinColumn(name = "member")	
	private List <MemberRoles> roles;
	
	public void setRoles(List<MemberRoles> roles) {		// set메서드 재정의함.N으로 초기값 주기 위함.	// 등급이 업데이트 되면 어떻게될까.. 등급을 업데이트 하는 것인가 같은 아이디에 등급을 하나 더 추가하는 것인가.. securityConfig에서 hasRole을 두 개 다 가질 수 있을 것인가.... 
		MemberRoles memberroles = new MemberRoles();
		memberroles.setRoleName("N");
		this.roles.add(memberroles);
	}
	
	@OneToMany(cascade=CascadeType.ALL)
	@JoinColumn(name = "id")
	private List <InterestLocation> interestlocation;
/*	public void setInterestlocation(String location) {		// set메서드 재정의함.N으로 초기값 주기 위함.	// 등급이 업데이트 되면 어떻게될까.. 등급을 업데이트 하는 것인가 같은 아이디에 등급을 하나 더 추가하는 것인가.. securityConfig에서 hasRole을 두 개 다 가질 수 있을 것인가.... 
		InterestLocation il = new InterestLocation();
		il.setInterestLocation(location);
		this.interestlocation.add(il);
	}*/

	
}