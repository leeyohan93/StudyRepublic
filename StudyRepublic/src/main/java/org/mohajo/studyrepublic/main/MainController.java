/**
 * 
 */
package org.mohajo.studyrepublic.main;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.mohajo.studyrepublic.domain.Interest2CD;
import org.mohajo.studyrepublic.domain.Member;
import org.mohajo.studyrepublic.domain.PageDTO;
import org.mohajo.studyrepublic.domain.PageMaker;
import org.mohajo.studyrepublic.domain.Study;
import org.mohajo.studyrepublic.domain.StudyMember;
import org.mohajo.studyrepublic.member.MemberController;
import org.mohajo.studyrepublic.repository.StudyMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author 이요한
 * @since 2019. 1. 23.
 * @version 0.0
 * -MainController 생성
 * -index 메인페이지 추가
 * -saerch 검색기능 추가
 */
@Controller 
public class MainController {
	
	@Autowired
	MainService mainService;
	@Autowired
	MemberController membercontroller;
	@Autowired
	StudyMemberRepository studymemberrepository;
	
	@RequestMapping("/index")
	public void index(Model model, Member member, Authentication authentication, HttpSession hs) {
		List<Interest2CD> basicPopularTagList = mainService.getBasicPopularTag();
		String[] basicPopularTag = new String[basicPopularTagList.size()];
		for(int i=0; i<basicPopularTagList.size(); i++) {
			basicPopularTag[i]=basicPopularTagList.get(i).getInterest2Code();
		}
		
//		List<Study> premiumStudy = mainService.getPopularPremiumStudy();
//		List<PopularStudy> premiumStudy = mainService.getPopularPremiumStudy();
//		List<Study> basicStudy = mainService.getPopularBasicStudy();
//		List<PopularStudy> basicStudy = mainService.getPopularBasicStudy(basicPopularTag);
//		
//		for(int i=0; i<premiumStudy.size(); i++) {
//			model.addAttribute("popularPremiumStudy"+i,premiumStudy.get(i));
//			System.out.println(premiumStudy.get(i).toString());
//		}
//		
//		for(int i=0; i<basicStudy.size(); i++) {
//			model.addAttribute("popularBasicStudy"+i,basicStudy.get(i));
//		}
		model.addAttribute("popularPremiumStudy", mainService.getPopularPremiumStudy());
		model.addAttribute("popularBasicStudy", mainService.getPopularBasicStudy(basicPopularTag));
		
		model.addAttribute("recommendTutorMember", mainService.getRecommendTutorMember());
		model.addAttribute("premiumPopularTag", mainService.getPremiumPopularTag());
		model.addAttribute("basicPopularTag", basicPopularTagList);
		
//		model.addAttribute("interest1cd", mainService.getInterest1Code());
//		model.addAttribute("interest2cd", mainService.getInterest2Code());
//		model.addAttribute("pinterest2cd", mainService.getPInterest2Code());
//		model.addAttribute("dinterest2cd", mainService.getDInterest2Code());
//		model.addAttribute("winterest2cd", mainService.getWInterest2Code());
//		model.addAttribute("ninterest2cd", mainService.getNInterest2Code());
		
		membercontroller.getSession(authentication,hs,member);
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String id = auth.getName();
		
		List <StudyMember> joiningStudy = studymemberrepository.joinedstudymember(id);
		HashMap <String, String> studyNameAndStudyId = new HashMap<>();
		
		for(StudyMember joiningStudyObject : joiningStudy) {
			Study extraValue = joiningStudyObject.getStudy();
			studyNameAndStudyId.put(extraValue.getName(), extraValue.getStudyId());
		}
		
		//아래부분 사용하지 않는 걸로 사료되어 주석 처리함. 2019.02.20 - sangyong.shin
		//model.addAttribute("joiningStudy", studyNameAndStudyIdjoiningStudy);
		System.out.println("조이닝스터디: "  + studyNameAndStudyId/*joiningStudy*/.toString());
		
		//membercontroller.getSession_Study(auth, hs, joiningStudy);
		for( String s : studyNameAndStudyId.keySet()) {
			System.out.println(s);
		}

		if(auth!=null) {
			 hs.setAttribute("studyNameAndStudyId", studyNameAndStudyId);
		}
	}
	
	@RequestMapping("/search")
//	public String search(Study studyInfo,String searchDate,String[] location, String[] interest, Model model){
	public String search(Study studyInfo, String searchDate,String[] location, String[] interest, PageDTO pageDto, Model model){
		
		Pageable page = pageDto.studyMakePageable(0, 12, "postDate");
		Page<Study> searchList = mainService.search(studyInfo, searchDate, location, interest, page);
//		System.out.println("typecode는 "+studyInfo.getTypeCode().getTypeCode());
//		model.addAttribute("list", searchList);
		model.addAttribute("pagedList", new PageMaker<>(searchList));
		model.addAttribute("typeCode", studyInfo.getTypeCode().getTypeCode());
		
		model.addAttribute("search", 1);
		model.addAttribute("name", studyInfo.getName());
		model.addAttribute("onoffCode",studyInfo.getOnoffCode().getOnoffCode());
		model.addAttribute("searchDate",searchDate);
		model.addAttribute("levelCode",studyInfo.getLevelCode().getLevelCode());
		if(location!=null)
			model.addAttribute("location", location);
		if(interest!=null)
			model.addAttribute("interest", interest);
		
		return "study/list";
	}
	

	

}
