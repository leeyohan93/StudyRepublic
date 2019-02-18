package org.mohajo.studyrepublic.main;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.mohajo.studyrepublic.domain.QStudy;
import org.mohajo.studyrepublic.domain.QStudyInterest;
import org.mohajo.studyrepublic.domain.QStudyLocation;
import org.mohajo.studyrepublic.domain.Study;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;

/**
 * @author 이요한
 * @since 2019. 1. 27.
 * @version 0.0 -기능 설명1
 */
public class MainPredicate {

	public static Predicate searchStudy(Study studyInfo, String searchDate, String[] location, String[] interest) {

		BooleanBuilder builder = new BooleanBuilder();

		QStudy study = QStudy.study;
		QStudyLocation studyLocation = QStudyLocation.studyLocation;
		QStudyInterest studyInterest = QStudyInterest.studyInterest;

		if (!studyInfo.getName().equals("")) {
			builder.and(study.name.contains(studyInfo.getName()));
		}
		if (!studyInfo.getTypeCode().getTypeCode().equals("All")) {
			builder.and(study.typeCode.typeCode.eq(studyInfo.getTypeCode().getTypeCode() + ""));
		}
		if (!studyInfo.getOnoffCode().getOnoffCode().equals("All")) {
			builder.and(study.onoffCode.onoffCode.eq(studyInfo.getOnoffCode().getOnoffCode() + ""));
		}
		if (!studyInfo.getLevelCode().getLevelCode().equals("All")) {
			builder.and(study.levelCode.levelCode.eq(studyInfo.getLevelCode().getLevelCode() + ""));
		}

		if (!searchDate.equals("")) {
			Date searchStartDate = null;
			Date searchEndDate = null;
			
			try {
				searchStartDate = new SimpleDateFormat("yyyy-MM-dd").parse(searchDate.substring(0, 10));
			} catch (ParseException e) {
				e.printStackTrace();
			}

			if (searchDate.length() <= 10) {
				builder.and(study.startDate.gt(searchStartDate));
			} else {
				try {
					searchEndDate = new SimpleDateFormat("yyyy-MM-dd")
							.parse(searchDate.substring(searchDate.length() - 10, searchDate.length()));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				builder.and(study.startDate.between(searchStartDate, searchEndDate));
			}
		}
		
		if (location!=null) {
			builder.and(study.studyLocation.any().interestLocation.in(location));
		}
		
		if (interest!=null) {
			builder.and(study.studyInterest.any().interest2code.interest2Code.in(interest));
		}
		
		builder.and(study.studyStatusCode.studyStatusCode.notIn("C","D"));
		
		System.out.println(builder.toString());
		return builder;
	}
}
