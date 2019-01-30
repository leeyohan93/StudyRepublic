package org.mohajo.studyrepublic.tutor;

import java.util.List;

import org.mohajo.studyrepublic.domain.CareerCD;
import org.mohajo.studyrepublic.domain.EducationCD;
import org.mohajo.studyrepublic.domain.Interest1CD;
import org.mohajo.studyrepublic.domain.Interest2CD;
import org.mohajo.studyrepublic.domain.Tutor;
import org.mohajo.studyrepublic.repository.CareerCDRepository;
import org.mohajo.studyrepublic.repository.EducationCDRepository;
import org.mohajo.studyrepublic.repository.Interest1CDRepository;
import org.mohajo.studyrepublic.repository.Interest2CDRepository;
import org.mohajo.studyrepublic.repository.TutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TutorController {

	@Autowired
	TutorRepository tutorrepository;
	@Autowired
	CareerCDRepository careerCDRepository;
	@Autowired
	EducationCDRepository educationCDRepository;
	@Autowired
	Interest1CDRepository interest1CDRepository;
	@Autowired
	Interest2CDRepository interest2CDRepository;
	
	
	@RequestMapping("/tutor")
	public String tutorInfo(Model model) {
		
		List <Tutor> tutor = tutorrepository.findAll();
		model.addAttribute("tutor", tutor);
		
		return "tutor/tutor_info";
		
	}
	
	@RequestMapping("/signupTutor")
	public String signupTutor(Model model) {
		
		List <CareerCD> careercd = careerCDRepository.findAll();
		model.addAttribute("careercd", careercd);
		
		List <EducationCD> educationcd = educationCDRepository.findAll();
		model.addAttribute("educationcd", educationcd);
		
		List <Interest1CD> interest1cd = interest1CDRepository.findAll();
		model.addAttribute("interest1cd", interest1cd);
		
		List <Interest2CD> interest2cd = interest2CDRepository.findAll();
		model.addAttribute("interest2cd", interest2cd);
		
		List <Interest2CD> Pinterest2cd = interest2CDRepository.Pinterest2List();
		model.addAttribute("pinterest2cd", Pinterest2cd);
		
		List <Interest2CD> Dinterest2cd = interest2CDRepository.Dinterest2List();
		model.addAttribute("dinterest2cd", Dinterest2cd);
		
		List <Interest2CD> Winterest2cd = interest2CDRepository.Winterest2List();
		model.addAttribute("winterest2cd", Winterest2cd);
		
		List <Interest2CD> Ninterest2cd = interest2CDRepository.Ninterest2List();
		model.addAttribute("ninterest2cd", Ninterest2cd);
		
				
		return "tutor/tutor_signuptutor";
		
	}
	
}
