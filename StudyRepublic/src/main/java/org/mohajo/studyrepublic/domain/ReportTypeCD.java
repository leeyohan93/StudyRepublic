package org.mohajo.studyrepublic.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * @author 이요한
 * @since 2019.01.22
 * @version
 * - REPORT_TYPE_CD 도메인 추가
 */

@Data
@Entity
@Table(name = "report_type_cd", schema="StudyRepublic")
public class ReportTypeCD {
	@Id

	private String reportTypeCode;
	
	private String codeValue;	



}
