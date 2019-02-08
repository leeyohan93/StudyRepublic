package org.mohajo.studyrepublic.mypage;

import java.util.List;

import org.mohajo.studyrepublic.domain.Board;
import org.mohajo.studyrepublic.domain.FreeBoard;
import org.mohajo.studyrepublic.domain.InquireBoard;
import org.mohajo.studyrepublic.domain.Member;
import org.mohajo.studyrepublic.domain.RequestBoard;
import org.mohajo.studyrepublic.domain.Study;
import org.mohajo.studyrepublic.domain.StudyMember;
import org.mohajo.studyrepublic.repository.FreeBoardRepository;
import org.mohajo.studyrepublic.repository.InquireBoardRepository;
import org.mohajo.studyrepublic.repository.MemberRepository;
import org.mohajo.studyrepublic.repository.RequestBoardRepository;
import org.mohajo.studyrepublic.repository.StudyMemberRepository;
import org.mohajo.studyrepublic.repository.StudyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author 김준석
 * @since 2019.01.23
 * @version 0.0
 * -
 * 
 * 
 */


@Controller
public class MypageController {
	
	@Autowired
	private MemberRepository mbr;
	@Autowired
	private StudyRepository sr;
	@Autowired
	private FreeBoardRepository fbr;
	@Autowired
	private RequestBoardRepository rbr;
	@Autowired
	private InquireBoardRepository ibr;
	/*@RequestMapping("/mypage")
	public String userinfo(Model model) {
		
		로그인 상태 유저 정보 가져오기 느낌만 나중에 메소드 추가할때 repository에 명명규칙에 맞게 작성
		Member user= mbr.findById("aaa123").get();
		model.addAttribute("mbr",user);
		System.out.println(user);
		return "mypage/mypage_main";
	}*/
	


	@RequestMapping("/mypage")
	public String userinfo(Model model ) {
		Authentication auth =SecurityContextHolder.getContext().getAuthentication();
		String id = auth.getName();
		Member user = mbr.findById(id).get();
		model.addAttribute("mbr",user);
		
		
		List<Study> studymember = sr.findByMemberId(user);
		model.addAttribute("sr",studymember);
		
		System.out.println(studymember);
		
		System.out.println(user); /*유저정보 데이터*/
		return "mypage/mypage_main";
		/*회원 스터디정보 가져오기*/
		
		
		
	}
	

	
	@RequestMapping("/modimember")
	public String modifyMember(Model model) {
		Authentication auth =SecurityContextHolder.getContext().getAuthentication();
		String id = auth.getName();
		Member modifyuser = mbr.findById(id).get();
		
		model.addAttribute("mdu",modifyuser);
		
		System.out.println(modifyuser);
		return "mypage/member_modify";
		
	}
		
/*	@RequestMapping("/")
	public String activityinfo(Model model) {
		Authentication auth =SecurityContextHolder.getContext().getAuthentication();
		String id = auth.getName();
		
		
		return "";
	}*/
	
	@RequestMapping("/activityinfo")
	public String allboard(Model model) {
		Authentication auth =SecurityContextHolder.getContext().getAuthentication();
		String id = auth.getName();
		
	return "mypage/activity_info";
		
	}
	
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
		
		
	
	
	
}	 

