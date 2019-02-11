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
@Table(name = "tutor_uploadfile", schema = "StudyRepublic")
public class TutorUploadFile implements Serializable {

	private static final long serialVersionUID = 1L;
	
	   @Id 
	   @GeneratedValue(strategy=GenerationType.IDENTITY)
	   @Column(name = "tutor_file_id")
	   private int tutorFileId;
	   
	   @ManyToOne
	   @JoinColumn(name = "tutor_number__")
	   private Tutor tutor;
	   
	   
	   @Column(name = "tutorfile_originname")
	   private String tutorfileOriginname;
	   
	   @Column(name = "tutorfile_savename")
	   private String tutorfileSavename;
	   
	   @Column(name = "tutorfile_parturl")
	   private String tutorfilePartUrl;
	   
	   @Column(name = "tutorfile_fullurl")
	   private String tutorFileFullUrl;
	   
	   
	   
	   @ManyToOne
	   @JoinColumn(name = "id")
	   private Member member;
	   
		
}
