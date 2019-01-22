package org.mohajo.studyrepublic.study;

import java.util.List;

import org.mohajo.studyrepublic.domain.Study;
import org.mohajo.studyrepublic.repository.StudyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author	이미연
 * @since	2019. 1. 22.
 * @version	
 * - 기능 설명 1
 */
public class StudyController {

	@Autowired
	StudyRepository sr;
	
	@RequestMapping("/studyTest")
	public void studyTest(Model model) {
		
		List<Study> studyList = sr.findAll();
		model.addAttribute("studyList", studyList);
		
	}
	
	
	
}
