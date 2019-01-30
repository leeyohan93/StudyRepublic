package org.mohajo.studyrepublic.tutor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.mohajo.studyrepublic.domain.CareerCD;
import org.mohajo.studyrepublic.domain.EducationCD;
import org.mohajo.studyrepublic.domain.GradeCD;
import org.mohajo.studyrepublic.domain.Interest1CD;
import org.mohajo.studyrepublic.domain.Interest2CD;
import org.mohajo.studyrepublic.domain.Member;
import org.mohajo.studyrepublic.domain.MemberRoles;
import org.mohajo.studyrepublic.domain.Tutor;
import org.mohajo.studyrepublic.repository.CareerCDRepository;
import org.mohajo.studyrepublic.repository.EducationCDRepository;
import org.mohajo.studyrepublic.repository.Interest1CDRepository;
import org.mohajo.studyrepublic.repository.Interest2CDRepository;
import org.mohajo.studyrepublic.repository.MemberRepository;
import org.mohajo.studyrepublic.repository.MemberRolesRepository;
import org.mohajo.studyrepublic.repository.TutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TutorController {
	
	@Autowired
	MemberRepository memberrepository;
	@Autowired
	MemberRolesRepository memberrolesrepository;
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
	
	@RequestMapping("/tutor/signup")
	public String signupTutor(Model model) {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String id = auth.getName();
		model.addAttribute("id", id);
		
		List <CareerCD> careercd = careerCDRepository.findAll();
		model.addAttribute("careercd", careercd);
		
		List <EducationCD> educationcd = educationCDRepository.findAll();
		model.addAttribute("educationcd", educationcd);
		
		List <Interest1CD> interest1cd = interest1CDRepository.findAll();
		model.addAttribute("interest1cd", interest1cd);
		
		List <Interest2CD> interest2cd = interest2CDRepository.findAll();
		model.addAttribute("interest2cd", interest2cd);
		
		List <Interest2CD> pinterest2cd = interest2CDRepository.Pinterest2List();
		model.addAttribute("pinterest2cd", pinterest2cd);
		
		List <Interest2CD> Dinterest2cd = interest2CDRepository.Dinterest2List();
		model.addAttribute("dinterest2cd", Dinterest2cd);
		
		List <Interest2CD> Winterest2cd = interest2CDRepository.Winterest2List();
		model.addAttribute("winterest2cd", Winterest2cd);
		
		List <Interest2CD> Ninterest2cd = interest2CDRepository.Ninterest2List();
		model.addAttribute("ninterest2cd", Ninterest2cd);
		
				
		return "tutor/tutor_signuptutor";
		
	}
	
	
	@RequestMapping("/tutor/insert")
	public String insertTutor(Model model,  @ModelAttribute Tutor tutor) {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String id = auth.getName();
				
		Member member = memberrepository.findById(id).get();
		List<MemberRoles> roles = memberrolesrepository.findByRole(id);
		MemberRoles memberroles = new MemberRoles();
		memberroles.setRoleName("W");
		roles.add(memberroles);
		member.setGradeCD(new GradeCD("W"));
		member.setRoles(roles);	
		memberrepository.save(member);
		tutorrepository.save(tutor);

		return "redirect:/";		
	}
	
	@RequestMapping("/tutor/inquery")
	public String inqueryTutor(Model model, @RequestParam String id) {
		
		Tutor tutor = tutorrepository.findByTutor(id);
		model.addAttribute("tutor", tutor); 
		
		return "tutor/tutor_signupinquery";		
	}	
}
