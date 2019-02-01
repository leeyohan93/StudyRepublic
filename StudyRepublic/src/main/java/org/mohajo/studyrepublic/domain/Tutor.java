package org.mohajo.studyrepublic.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;



@Data
@Entity
@Table(name = "tutor", schema = "StudyRepublic")
public class Tutor implements Serializable {

   private static final long serialVersionUID = 1L;

   
   
   @Id 
   @GeneratedValue(strategy=GenerationType.IDENTITY)
   @Column(name = "tutor_number")
   private int tutorNumber;

   @ManyToOne
   @JoinColumn(name = "EDUCATION_CODE")
   private EducationCD educationCD;
   
   @Column
   private String introduction;
   
   @OneToOne
   @JoinColumn(name = "id")
   private Member member;
   
   @OneToMany(cascade=CascadeType.ALL)
   @JoinColumn(name = "tutor_number")
   private List <TutorCareer> tutorcareer;
   
   @OneToMany(cascade=CascadeType.ALL)
   @JoinColumn(name = "tutor_number_")
   private List <TutorInterest> tutorinterest;   
   
}