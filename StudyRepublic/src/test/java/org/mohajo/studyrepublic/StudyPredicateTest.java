package org.mohajo.studyrepublic;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mohajo.studyrepublic.domain.QStudy;
import org.mohajo.studyrepublic.domain.Study;
import org.mohajo.studyrepublic.repository.StudyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import com.querydsl.core.BooleanBuilder;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StudyPredicateTest {

	@Autowired
	StudyRepository sr;
	

	@Test
	public void testPredicate() {
		
		String typeCode = "P";
		
		
		BooleanBuilder builder = new BooleanBuilder();
		
		QStudy study = QStudy.study;
		
		if(typeCode.equals("P")) {
			builder.and(study.name.like("%"+"소개합니다"+"%"));
		}
		
		Pageable pageable = PageRequest.of(0, 10);
				
		Page<Study> result = sr.findAll(builder, pageable);
		
		System.out.println("PAGE SIZE: "+result.getSize());
		System.out.println("TOTAL PAGES: "+result.getTotalPages());
		System.out.println("TOTAL COUNT: "+result.getTotalElements());
		System.out.println("NEXT: "+result.nextPageable());
		
		List<Study> list = result.getContent();

//		list.forEach(b -> System.out.println(b.toString()));	//Error

	}
	
}
