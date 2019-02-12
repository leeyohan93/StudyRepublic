package org.mohajo.studyrepublic.mypage;
/**
 * @author 김준석
 * @since 2019.01.23
 * @version 0.0
 * -
 * 
 * 
 */

import java.util.List;

import org.mohajo.studyrepublic.domain.Board;
import org.mohajo.studyrepublic.domain.FreeBoard;
import org.mohajo.studyrepublic.domain.InquireBoard;
import org.mohajo.studyrepublic.domain.Member;
import org.mohajo.studyrepublic.domain.RequestBoard;
import org.mohajo.studyrepublic.domain.StudyMember;
import org.mohajo.studyrepublic.repository.FreeBoardRepository;
import org.mohajo.studyrepublic.repository.InquireBoardRepository;
import org.mohajo.studyrepublic.repository.MemberRepository;
import org.mohajo.studyrepublic.repository.RequestBoardRepository;
import org.mohajo.studyrepublic.repository.StudyMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ActivityInfoController {
	
	@Autowired
	private StudyMemberRepository smr;
	@Autowired
	private FreeBoardRepository fbr;
	@Autowired
	private RequestBoardRepository rbr;
	@Autowired
	private InquireBoardRepository ibr;
	
	
	@RequestMapping("/allboard")
	public String freeboard(Model model) {
		Authentication auth =SecurityContextHolder.getContext().getAuthentication();
		String id = auth.getName();
		
		List<FreeBoard> myfreeboard = fbr.findFreeBoardById(id);
		model.addAttribute("fbr", myfreeboard);
		
		List<RequestBoard> myrequestboard = rbr.findRequestBoardById(id);
		model.addAttribute("rbr", myrequestboard);
		
		List<InquireBoard> myinquireboard = ibr.findInquireBoardById(id);
		model.addAttribute("ibr", myinquireboard);
		
		return "mypage/allboard";
	}
	
	
	@RequestMapping("/basicstudylist")
	public String basicStudyList(Model model) {
		Authentication auth =SecurityContextHolder.getContext().getAuthentication();
		String id = auth.getName();
		
		List<StudyMember> mybasicStudy= smr.findbasicStudylist(id);
		
		model.addAttribute("smr",mybasicStudy);
		
		System.out.println(mybasicStudy);
		
		return "mypage/basicStudy_list";
	}
	
	@RequestMapping("/premiumstudylist")
	public String premiumStudyList(Model model) {
		Authentication auth =SecurityContextHolder.getContext().getAuthentication();
		String id = auth.getName();
		
		List<StudyMember> mypremiumStudy= smr.findPremiumStudylist(id);
		
		model.addAttribute("myp",mypremiumStudy);
		
		return "mypage/premiumStudy_list";
	}
}
