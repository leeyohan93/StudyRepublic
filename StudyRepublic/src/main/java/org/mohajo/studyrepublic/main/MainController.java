/**
 * 
 */
package org.mohajo.studyrepublic.main;

import java.util.List;

import org.mohajo.studyrepublic.domain.CareerCD;
import org.mohajo.studyrepublic.domain.EducationCD;
import org.mohajo.studyrepublic.domain.Interest1CD;
import org.mohajo.studyrepublic.domain.Interest2CD;
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
		
		for(int i=0; i<premiumStudy.size(); i++) {
			model.addAttribute("popularPremiumStudy"+i,premiumStudy.get(i));
		}
		
		for(int i=0; i<basicStudy.size(); i++) {
			model.addAttribute("popularBasicStudy"+i,basicStudy.get(i));
		}
		
		model.addAttribute("recommendTutorMember", mainService.getRecommendTutorMember());
		model.addAttribute("premiumPopularTag", mainService.getPremiumPopularTag());
		model.addAttribute("basicPopularTag", mainService.getBasicPopularTag());
		
		model.addAttribute("interest1cd", mainService.getInterest1Code());
		model.addAttribute("interest2cd", mainService.getInterest2Code());
		model.addAttribute("pinterest2cd", mainService.getPInterest2Code());
		model.addAttribute("dinterest2cd", mainService.getDInterest2Code());
		model.addAttribute("winterest2cd", mainService.getWInterest2Code());
		model.addAttribute("ninterest2cd", mainService.getNInterest2Code());
		
		
		
	}
}
