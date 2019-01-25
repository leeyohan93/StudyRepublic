package org.mohajo.studyrepublic.study;

import java.util.List;

import org.mohajo.studyrepublic.domain.Leveltest;
import org.mohajo.studyrepublic.domain.LeveltestResponse;
import org.mohajo.studyrepublic.domain.Payment;
import org.mohajo.studyrepublic.domain.Review;
import org.mohajo.studyrepublic.domain.Study;
import org.mohajo.studyrepublic.domain.StudyMember;
import org.mohajo.studyrepublic.repository.LeveltestRepository;
import org.mohajo.studyrepublic.repository.LeveltestResponseRepository;
import org.mohajo.studyrepublic.repository.PaymentRepository;
import org.mohajo.studyrepublic.repository.ReviewRepository;
import org.mohajo.studyrepublic.repository.StudyMemberRepository;
import org.mohajo.studyrepublic.repository.StudyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author	이미연
 * @since	2019. 1. 22.
 * @version	
 * - 기능 설명 1
 */
@Controller
public class StudyController {

	@Autowired
	StudyRepository sr;
	
	@Autowired
	StudyMemberRepository smr;
	
	@Autowired
	LeveltestRepository lr;
	
	@Autowired
	ReviewRepository rr;
	
	@Autowired
	LeveltestResponseRepository lrr;
	
	@Autowired
	PaymentRepository pr;
	
	
	@RequestMapping("/studyTest")
	public String studyTest(Model model) {
		
		List<Study> studyList = sr.findAll();
		model.addAttribute("studyList", studyList);
		
		return "study/AllStudyList";
	}
	
	@RequestMapping("/studyTest2")
	public String studyTest2(Model model) {
		
		List<StudyMember> studyMemberList = smr.findAll();
		model.addAttribute("studyMemberList", studyMemberList);
		
		return "study/AllStudyList2";
	}
	
	@RequestMapping("/studyTest3")
	public String studyTest3(Model model) {
		
		List<Leveltest> leveltestList = lr.findAll();
		model.addAttribute("leveltestList", leveltestList);
		
		return "study/AllStudyList3";
	}

	@RequestMapping("/studyTest4")
	public String studyTest4(Model model) {
		
		List<Review> reviewList = rr.findAll();
		model.addAttribute("reviewList", reviewList);
		
		return "study/AllStudyList4";
	}
	
	@RequestMapping("/studyTest5")
	public String studyTest5(Model model) {
		
		List<LeveltestResponse> leveltestResponseList = lrr.findAll();
		model.addAttribute("leveltestResponseList", leveltestResponseList);
		
		return "study/AllStudyList5";
	}
	
	@RequestMapping("/studyTest6")
	public String studyTest6(Model model) {
		
		List<Payment> paymentList = pr.findAll();
		model.addAttribute("paymentList", paymentList);
		
		return "study/AllStudyList6";
	}
}
