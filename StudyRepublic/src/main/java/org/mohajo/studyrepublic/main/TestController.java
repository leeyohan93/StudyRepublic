/**
 * 
 */
package org.mohajo.studyrepublic.main;

import java.util.List;

import org.mohajo.studyrepublic.domain.Study;
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

	@RequestMapping("/search")
	public void search(Study study,Model model,String searchDate,String[] location, String[] interest){
		
		System.out.println(study.toString());
		System.out.println(searchDate);
		
		System.out.println(searchDate.substring(0, 10));
		System.out.println(searchDate.substring(searchDate.length()-10, searchDate.length()));
		
		
	}
}
