/**
 * 
 */
package org.mohajo.studyrepublic.main;

import java.util.List;

import org.mohajo.studyrepublic.domain.Study;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author 이요한
 * @since 2019. 1. 23.
 * @version 0.0
 * -MainController 생성
 */
@Controller 
public class MainController {
	
	@Autowired
	MainService mainService;
	
	@RequestMapping("/index")
	public void index(Model model) {
		List<Study> premiumStudy = mainService.getPopularPremiumStudy();
		List<Study> basicStudy = mainService.getPopularBasicStudy();
		
		model.addAttribute("recommendTutorMember", mainService.getRecommendTutorMember());
		
		for(int i=0; i<premiumStudy.size(); i++) {
			model.addAttribute("popularPremiumStudy"+i,premiumStudy.get(i));
		}
		
		for(int i=0; i<basicStudy.size(); i++) {
			model.addAttribute("popularBasicStudy"+i,basicStudy.get(i));
		}
		
		
	}
}
