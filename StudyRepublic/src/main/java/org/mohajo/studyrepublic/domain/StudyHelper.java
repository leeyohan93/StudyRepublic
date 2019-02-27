package org.mohajo.studyrepublic.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;

/**
 * @author	이미연
 * @since	2019. 2. 19.
 * @version	0.0
 * - Study 객체의 Date 타입 값을 @ModelAttribute 로 받기 위한 클래스
 */
@Data
public class StudyHelper implements Serializable {

//	@Temporal(TemporalType.DATE)					// 년, 월, 일 형식으로 출력하겠다. DATE를 TIMESTAMP로 바꾸면 시, 분, 초 까지 출력.
//	@Column(nullable = false)
//	private Date startDate = new Date();			//시작일
	private String startDateStr;
	
//	@Temporal(TemporalType.DATE)					// 년, 월, 일 형식으로 출력하겠다. DATE를 TIMESTAMP로 바꾸면 시, 분, 초 까지 출력.
//	@Column
//	private Date endDate = new Date();				//종료일
	private String endDateStr;
	
//	@Temporal(TemporalType.TIME)
//	@Column(nullable = false)
//	private Date startTime;							//시작시각
	private String startTimeStr;
	
//	@Temporal(TemporalType.TIME)
//	@Column(nullable = false)
//	private Date endTime;							//종료시각
	private String endTimeStr;
}
