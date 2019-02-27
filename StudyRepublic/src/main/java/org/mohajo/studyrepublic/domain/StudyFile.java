/**
 * 
 */
package org.mohajo.studyrepublic.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;


@Data
@Entity
@Table(name = "study_file", schema = "StudyRepublic")
public class StudyFile implements Serializable {

	private static final long serialVersionUID = 1L;
	
	   @Id 
	   @GeneratedValue(strategy=GenerationType.IDENTITY)
	   private int studyFileId;
	   
	   private String studyId;
	   
	   @ManyToOne
	   @JoinColumn(name = "studyId", insertable=false, updatable=false)
	   private Study study;
	   
	   private String studyfileOriginname;
	   
	   private String studyfileSavename;
	   
	   private String studyfileParturl;
	   
	   private String studyfileFullurl;
		
}
