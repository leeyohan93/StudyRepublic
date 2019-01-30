package org.mohajo.studyrepublic.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;

@Data
@Entity
@Table(name = "interest_location", schema = "StudyRepublic")
public class InterestLocation {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "interest_location_id", unique = true, nullable = false)
	private int interestLocationId;
	
	@Column(name = "interest_location")
	String interestLocation;
	
	
/*	@ManyToOne
	@JoinColumn(name = "id")
	Member member;*/
	
	
}
