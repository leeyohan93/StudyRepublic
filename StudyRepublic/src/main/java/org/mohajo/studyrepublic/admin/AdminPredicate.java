/**
 * 
 */
package org.mohajo.studyrepublic.admin;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.mohajo.studyrepublic.domain.QMember;
import org.mohajo.studyrepublic.domain.QStudy;
import org.mohajo.studyrepublic.domain.QStudyInterest;
import org.mohajo.studyrepublic.domain.QStudyLocation;
import org.mohajo.studyrepublic.domain.Study;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;

/**
 * @author 이요한
 * @since 2019. 2. 12.
 * @version 0.0
 * -기능 설명1
 */
public class AdminPredicate {
	
	public static Predicate selectedMember(String[] selectedId) {

		BooleanBuilder builder = new BooleanBuilder();
		QMember member = QMember.member;

		if (selectedId!=null) {
			builder.and(member.id.in(selectedId));
		}
		
		System.out.println(builder.toString());
		return builder;
	}
	
	public static Predicate searchMember(String[] grade,String[] status,String searchKey,String searchValue) {

		BooleanBuilder builder = new BooleanBuilder();
		QMember member = QMember.member;
		
		if(grade != null) {
			builder.and(member.gradeCD.gradeCode.in(grade));
		}
		
		if(status != null) {
			builder.and(member.memberStatusCD.memberStatusCode.in(status));
		}
		if(!(searchValue.equals(""))) {
			switch(searchKey) {
			case "id":
				builder.and(member.id.eq(searchValue));
				break;
			case "name":
				builder.and(member.name.eq(searchValue));
				break;
			case "nickname":
				builder.and(member.nickname.eq(searchValue));
			}
		}
			
		
		return builder;
	}
	
	public static Predicate searchTutor(String searchKey,String searchValue) {

		BooleanBuilder builder = new BooleanBuilder();
		QMember member = QMember.member;
		
		if(!(searchValue.equals(""))) {
			switch(searchKey) {
			case "id":
				builder.and(member.id.eq(searchValue));
				break;
			case "name":
				builder.and(member.name.eq(searchValue));
				break;
			case "nickname":
				builder.and(member.nickname.eq(searchValue));
			}
		}
		builder.and(member.gradeCD.gradeCode.eq("W"));
			
		
		return builder;
	}
	
	public static Predicate searchStudy(String[] type, String[] status,String[] onoff,String searchKey, String searchValue) {

		BooleanBuilder builder = new BooleanBuilder();
		QStudy study = QStudy.study;
		
		if(type != null) {
			builder.and(study.typeCode.typeCode.in(type));
		}
		
		if(status != null) {
			builder.and(study.studyStatusCode.studyStatusCode.in(status));
		}
		
		if(onoff != null) {
			builder.and(study.onoffCode.onoffCode.in(onoff));
		}
		if(!(searchValue.equals(""))) {
			switch(searchKey) {
			case "studyId":
				builder.and(study.studyId.contains(searchValue));
				break;
			case "name":
				builder.and(study.name.contains(searchValue));
				break;
			case "leader":
				builder.and(study.member.id.eq(searchValue));
			}
		}
			
		
		return builder;
	}

}
