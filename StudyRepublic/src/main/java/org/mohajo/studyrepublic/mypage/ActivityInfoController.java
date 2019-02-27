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
import org.mohajo.studyrepublic.domain.Review;
import org.mohajo.studyrepublic.domain.StudyMember;
import org.mohajo.studyrepublic.repository.FreeBoardRepository;
import org.mohajo.studyrepublic.repository.InquireBoardRepository;
import org.mohajo.studyrepublic.repository.MemberRepository;
import org.mohajo.studyrepublic.repository.RequestBoardRepository;
import org.mohajo.studyrepublic.repository.ReviewRepository;
import org.mohajo.studyrepublic.repository.StudyMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
	@Autowired
	private ReviewRepository reviewRepository;
	
	
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
		String isReview = "";
		
		List<StudyMember> mybasicStudy= smr.findbasicStudylist(id);
		
		model.addAttribute("smr",mybasicStudy);
		
		String[] studyList = new String[mybasicStudy.size()];
		for(int i=0; i<mybasicStudy.size(); i++) {
			studyList[i]=mybasicStudy.get(i).getStudyId();
		}
		
		List<Review> reviewList = reviewRepository.findStudyReview(studyList, id);
		if(reviewList!=null) {
			for(int i=0; i<reviewList.size(); i++) {
				isReview+=reviewList.get(i).getStudyId();
			}
		}
		model.addAttribute("isReview", isReview);
		
		return "mypage/basicStudy_list";
	}
	
	@RequestMapping("/premiumstudylist")
	public String premiumStudyList(Model model) {
		Authentication auth =SecurityContextHolder.getContext().getAuthentication();
		String id = auth.getName();
		String isReview = "";
		
		List<StudyMember> mypremiumStudy= smr.findPremiumStudylist(id);
		model.addAttribute("myp",mypremiumStudy);
		
		String[] studyList = new String[mypremiumStudy.size()];
		for(int i=0; i<mypremiumStudy.size(); i++) {
			studyList[i]=mypremiumStudy.get(i).getStudyId();
		}
		
		List<Review> reviewList = reviewRepository.findStudyReview(studyList, id);
		if(reviewList!=null) {
			for(int i=0; i<reviewList.size(); i++) {
				isReview+=reviewList.get(i).getStudyId();
			}
		}
		model.addAttribute("isReview", isReview);
		
		return "mypage/premiumStudy_list";
	}
	
	@ResponseBody
	@RequestMapping("/writeReview")
	public void writeReview(String memberId, String studyId, String reviewContent,float score) {
		Review review = new Review();
		review.setId(memberId);
		review.setStudyId(studyId);
		review.setContent(reviewContent);
		review.setScore(score);
		System.out.println(review.toString());
		reviewRepository.save(review);
		
		
	}
}
