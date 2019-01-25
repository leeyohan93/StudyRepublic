package org.mohajo.studyrepublic.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

/**
 * @author 이요한
 * @since 2019.01.22
 * @version
 * - Report 도메인 추가
 * - CODE테이블과 조인설정
 */

@Data
@Entity
@Table(name = "report", schema="StudyRepublic")
public class Report {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)

	private int reportId;
	private String id;
	
	@ManyToOne
	@JoinColumn(name="REPORT_WHY_CODE")
	private ReportWhyCD reportWhyCD;
	
	@ManyToOne
	@JoinColumn(name="REPORT_TYPE_CODE")
	private ReportTypeCD reportTypeCD;
	
	private String target;
	private String content;
	private Date date;

}
