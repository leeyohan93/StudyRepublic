/**
 * 
 */
package org.mohajo.studyrepublic.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

/**
 * @author 윤성호
 * @since 2019.01.22
 * @version
 * -기능1 추가
 * -기능2 추가
 * -기능3 추가
 */


@Data
@Entity
@Table(name = "tutor_uploadfile", schema = "StudyRepublic")
public class TutorUploadFile {

	   @Id 
	   @GeneratedValue(strategy=GenerationType.IDENTITY)
	   @Column(name = "fileId")
	   private int fileId;
	   
/*	   @ManyToOne
	   @JoinColumn(name = "tutor_number__")
	   private int tutor_number;*/
	   
	   @Column(name = "fileName")
	   private String fileName;
	   
	   @Column(name = "fileOriName")
	   private String fileOriName;
	   
	   @Column(name = "fileUrl")
	   private String fileUrl;
		
}
