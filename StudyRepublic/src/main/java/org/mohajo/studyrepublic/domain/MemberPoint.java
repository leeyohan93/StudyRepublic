
package org.mohajo.studyrepublic.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "member_point", schema = "StudyRepublic")
public class MemberPoint implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public MemberPoint() {
		super(); 
}


	   @Id 
	   @GeneratedValue(strategy=GenerationType.IDENTITY)
	   @Column(name = "pointid")
	   private int pointid;
	   
	   @OneToOne
	   @JoinColumn(name = "id") 
	   private Member member;
   
	   @Column(name = "point")
	   private int point = 0;

	
	

	



	
}