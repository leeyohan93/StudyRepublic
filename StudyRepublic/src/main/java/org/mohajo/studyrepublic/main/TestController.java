package org.mohajo.studyrepublic.main;

import java.util.List;

import org.mohajo.studyrepublic.domain.Study;
import org.mohajo.studyrepublic.repository.StudyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 이요한
 * @since 2019. 1. 31.
 * @version 0.0
 * -기능 설명1
 */

@RestController
public class TestController {
	
	@Autowired
	StudyRepository studyRepository;
	
//	@RequestMapping("/search")
	public void search(Study studyInfo,String searchDate,String[] location, String[] interest, Model model){
		
		/*for(Study list:studyRepository.findAll(MainPredicate.searchStudy(studyInfo,searchDate,location,interest))) {
			System.out.println("list = ");
			System.out.println(list);
		}
		
		Pageable paging = PageRequest.of(0, 2, Sort.Direction.DESC, "postDate");
		
		Page<Study> searchResult = studyRepository.findAll(MainPredicate.searchStudy(studyInfo,searchDate,location,interest),paging);
		
		List<Study> searchList = searchResult.getContent();
		
		model.addAttribute("list", list);
		model.addAttribute("typeCode", typeCode);

		return "study/list";*/
		
		// 스터디 검색 사용 테스트 종료, 삭제 예정
		

	}
}
