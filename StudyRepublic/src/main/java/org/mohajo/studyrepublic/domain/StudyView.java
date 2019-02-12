package org.mohajo.studyrepublic.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;
import lombok.ToString;


/**
 * @author	이미연
 * - 뷰 테이블 연결
 */
@Data
@ToString(exclude = "review")
@Entity
@Table(name = "study_view"/*, schema = "StudyRepublic"*/)
@DiscriminatorValue("STUDYVIEW")
public class StudyView extends Study implements Serializable {
	
//	@Transient
//	@Column(name="date_diff", table="study_view", insertable=false, updatable=false)
	private int dateDiff;
	
//	@Transient
	@Temporal(TemporalType.TIME)
//	@Column(name="time_diff", table="study_view", insertable=false, updatable=false)
	private Date timeDiff;
	
//	@Transient
//	@Column(name="review_count", table="study_view", insertable=false, updatable=false)
	private int reviewCount;

//	@Transient
//	@Column(name="average_score", table="study_view", insertable=false, updatable=false)
	private float averageScore;
	
}
