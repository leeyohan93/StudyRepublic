package org.mohajo.studyrepublic.mypage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.mohajo.studyrepublic.domain.Interest1CD;
import org.mohajo.studyrepublic.domain.Interest2CD;
import org.mohajo.studyrepublic.domain.InterestLocation;
import org.mohajo.studyrepublic.domain.Member;
import org.mohajo.studyrepublic.domain.MemberInterest;
import org.mohajo.studyrepublic.domain.MemberPoint;
import org.mohajo.studyrepublic.domain.MemberRoles;
import org.mohajo.studyrepublic.domain.StudyMember;
import org.mohajo.studyrepublic.repository.Interest1CDRepository;
import org.mohajo.studyrepublic.repository.Interest2CDRepository;
import org.mohajo.studyrepublic.repository.InterestLocationRepository;
import org.mohajo.studyrepublic.repository.MemberInterestRepository;
import org.mohajo.studyrepublic.repository.MemberPointRepository;
import org.mohajo.studyrepublic.repository.MemberRepository;
import org.mohajo.studyrepublic.repository.StudyMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
	private MemberInterestRepository mir;
	@Autowired
	private StudyMemberRepository smr;
	@Autowired
	private InterestLocationRepository iir;
	@Autowired
	private Interest1CDRepository i1cdr;
	@Autowired
	private Interest2CDRepository i2cdr;
	@Autowired
	MemberPointRepository  memberpointrepository;
	
	private Member member;
	

	@RequestMapping("/mypage")
	public String userinfo(Model model, HttpSession session ) {
		Authentication auth =SecurityContextHolder.getContext().getAuthentication();
		String id = auth.getName();
		
		Member user = mbr.findById(id).get();
		model.addAttribute("mbr",user);
		model.addAttribute("userId", user.getId());
		
		List<StudyMember> studymemberbasic = smr.findstudyall(id); 
		List<StudyMember> studymemberpremium = smr.findstudyall2(id); 
		int studymemberCount = smr.studycount(id);
		//[StudyMember(studyMemberId=StudyMemberId(studyId=BF00002, id=bbb123), id=bbb123, studyId=BF00002, studyMemberStatusCode=StudyMemberStatusCD(studyMemberStatusCode=ME, codeValueEnglish=MEMBER, codeValueKorean=승인), enrollDate=2018-11-01, exitDate=null, member=Member(id=bbb123, password=$2a$10$qY/H2aAzE7bBGoaZ8sp/0ehMrl7yw.8.G/pZqWpkKSyyHAG2/KBoq, name=이요한, gender=여, birth=1993-05-15, nickname=요하네스, email=good_1411@naver.com, phonenumber=01098765432, visibility=0, profileOriginName=iu_profile.jpgjpg, profileSaveName=ordiV08D6iiVsXZizCByixFOJydFpAGp.jpg, registrationDate=2018-03-03, memberStatusCD=MemberStatusCD(memberStatusCode=N, codeValueEnglish=NORMAL, codeValueKorean=일반), gradeCD=GradeCD(gradeCode=T, codeValueEnglish=TUTOR, codeValueKorean=강사), roles=[MemberRoles(fno=13, roleName=T), MemberRoles(fno=45, roleName=N)], memberInterest=[], interestlocation=[]), study=Study(studyId=BF00002, name=소개합니다 믿습니까 갓성호, member=Member(id=eee123, password=$2a$10$X/tz1ZOqYJ5CHOqxSNEiKu5iEJACUH51KBTwWuI6w2Y/lj/Fdq6Iu, name=윤성호, gender=여, birth=1993-07-31, nickname=해군, email=dune93@naver.com, phonenumber=01033333333, visibility=0, profileOriginName=null, profileSaveName=d, registrationDate=2018-03-05, memberStatusCD=MemberStatusCD(memberStatusCode=N, codeValueEnglish=NORMAL, codeValueKorean=일반), gradeCD=GradeCD(gradeCode=T, codeValueEnglish=TUTOR, codeValueKorean=강사), roles=[MemberRoles(fno=16, roleName=T), MemberRoles(fno=48, roleName=N)], memberInterest=[], interestlocation=[]), typeCode=TypeCD(typeCode=B, codeValueEnglish=BASIC_STUDY, codeValueKorean=일반 스터디), onoffCode=OnoffCD(onoffCode=F, codeValueEnglish=OFF, codeValueKorean=오프라인), studyStatusCode=StudyStatusCD(studyStatusCode=G, codeValueEnglish=ONGOING, codeValueKorean=진행중), levelCode=LevelCD(levelCode=H, codeValueEnglish=HIGH, codeValueKorean=상), startDate=2018-12-01, endDate=2018-12-29, dayCode=DayCD(dayCode=6, codeValueEnglish=SATURDAY, codeValueKorean=토), studyCount=4, startTime=11:00:00, endTime=13:00:00, enrollCapacity=8, enrollActual=5, introduction=소개합니다 소개합니다 믿습니까 갓성호, hasLeveltest=1, disbandDate=null, postDate=2018-11-01 00:00:00.0, studyInterest=[StudyInterest(StudyInterestId=25, interest2code=Interest2CD(interest2Code=D01, codeValueEnglish=Database, codeValueKorean=전체, interest1cd=Interest1CD(interest1Code=D, codeValueEnglish=DATABASE, codeValueKorean=데이터베이스))), StudyInterest(StudyInterestId=26, interest2code=Interest2CD(interest2Code=D02, codeValueEnglish=Oracle, codeValueKorean=오라클, interest1cd=Interest1CD(interest1Code=D, codeValueEnglish=DATABASE, codeValueKorean=데이터베이스)))], price=null, studyLocation=[StudyLocation(interestLocationId=6, interestLocation=서울특별시 강남구 역삼5동)]), studyNoticeboard=[], studyFileshareboard=[], studyQnaboard=[])]
		
		model.addAttribute("smr",studymemberbasic);
		model.addAttribute("smr2",studymemberpremium);
		model.addAttribute("smr3",studymemberCount);
		
		List<InterestLocation> interestlocation = iir.findInterestById(id) ; 	
		model.addAttribute("iir",interestlocation);
		
	      List<Interest1CD> interest1cd = i1cdr.findAll();
	      List<Interest2CD> interest2cd = i2cdr.findAll();
	      List<Interest2CD> Pinterest2cd = i2cdr.Pinterest2List();
	      List<Interest2CD> Dinterest2cd = i2cdr.Dinterest2List();
	      List<Interest2CD> Winterest2cd = i2cdr.Winterest2List();
	      List<Interest2CD> Ninterest2cd = i2cdr.Ninterest2List();
	      List<MemberInterest> mymemberinterest = mir.findMemberInterest(id);
	      
	      System.out.println("나의 관심분야: " + mymemberinterest);
	      

	      model.addAttribute("interest1cd", interest1cd);
	      model.addAttribute("interest2cd", interest2cd);
	      model.addAttribute("pinterest2cd", Pinterest2cd);
	      model.addAttribute("dinterest2cd", Dinterest2cd);
	      model.addAttribute("winterest2cd", Winterest2cd);
	      model.addAttribute("ninterest2cd", Ninterest2cd);
	      model.addAttribute("mymemberinterest", mymemberinterest);
		
		
		System.out.println(studymemberpremium); /*유저정보 데이터*/
		
		
		
		
		MemberPoint memberpoint = memberpointrepository.inqueryPoint(id);
		model.addAttribute("memberpoint", memberpoint);
		model.addAttribute("memberpoint_point", memberpoint.getPoint());
		
    	         
        session.setAttribute("memberpoint", memberpoint.getPoint());
        System.out.println("보유포인트갱신: " + memberpoint.getPoint());
		
		return "mypage/mypage_main";
		/*회원 스터디정보 가져오기*/
		
		
		
	}
	

	
	@RequestMapping("/mypage/modimember")
	public String modifyMember(Model model) {
		Authentication auth =SecurityContextHolder.getContext().getAuthentication();
		String id = auth.getName();
		Member modifyuser = mbr.findById(id).get();
		model.addAttribute("mdu",modifyuser);
		
		System.out.println(modifyuser);
		return "mypage/member_modify";
		
		
	}
		

	@RequestMapping("/activityinfo")
	public String allboard(Model model) {
		Authentication auth =SecurityContextHolder.getContext().getAuthentication();
		String id = auth.getName();
		
		
		
	return "mypage/activity_info";
		
	}
	

	
	  @RequestMapping(value="/scheduler")
	   public String scheduler(Model model) {
	      Authentication auth =SecurityContextHolder.getContext().getAuthentication();
	      String id = auth.getName();
	      
	      
	      return "mypage/myscheduler";
	   }
	   
	   @RequestMapping("/schedulerApi")
	   @ResponseBody 
	   public Map <Object, Object> myscheduler(Model model) {      
	      Authentication auth =SecurityContextHolder.getContext().getAuthentication();
	      String id = auth.getName();
	      List biginfo;
	      Map<Object, Object> map = new HashMap<Object, Object>();
	      
	      biginfo = smr.findActivityById(id);
	      
	      map.put("biginfo", biginfo);
	      
	      return map;   
	   }
	
		
	@RequestMapping("/modalmember")
	@ResponseBody
	public Map<String, Object> modalmeber(Model model, @RequestParam String id){
		List<Member> member = mbr.findMemberbyId(id);
		
		System.out.println(member.get(0));
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("memberInfo", member);
		

		return map;
	}

	@RequestMapping("/member/modify/interest") 
	public String modifyInterest(Model model, @RequestParam String[] minterest) {
		
		Authentication auth =SecurityContextHolder.getContext().getAuthentication();
		String id = auth.getName();
		
		System.out.println("-----관심분야시작------");
		System.out.println("관심분야 갯수: " + minterest.length);
		
		mir.deleteInterest(id);
			
//		List<MemberInterest> memberInterestList = new ArrayList<>();

		
		
		for (int i = 0; i < minterest.length; i++) {
		System.out.println("interest" + i + " : " + minterest[i]);
		Interest2CD minterest2cd = i2cdr.findById(minterest[i]).get();
		MemberInterest mmemberinterest = new MemberInterest();
		mmemberinterest.setId(id);
		mmemberinterest.setInterest2cd(minterest2cd);
		mir.save(mmemberinterest);

/*		memberInterestList.add(mmemberinterest);
		System.out.println("멤버 인터레스트 사이즈: " + memberInterestList.size());
		System.out.println("멤버 인터레스트 리스트 " + i + " : " +  memberInterestList.get(i));*/
		}

		
/*		member.setMemberInterest(memberInterestList);
		mbr.save(member);*/
		
		return "redirect:/mypage";
		
	}
	
	
/*	@RequestMapping("/member/modify/location") 
	public String modifyLocation(Model model, @RequestParam String[] mlocation) {
		
		Authentication auth =SecurityContextHolder.getContext().getAuthentication();
		String id = auth.getName();
		Member member = mbr.findById(id).get();
		System.out.println("-----관심지역시작------");
		System.out.println("관심지역 갯수: " + mlocation.length);
		
		iir.deleteLocation(id);
		
		for (int i = 0; i < mlocation.length; i++) {
		System.out.println("interest" + i + " : " + mlocation[i]);
		
		InterestLocation interestlocation = new InterestLocation();
		interestlocation.setId(member.getId());
		interestlocation.setInterestLocation(mlocation[i]);
		iir.save(interestlocation);

		}
		
		return "redirect:/mypage";
	}*/

	
		
		
	
}	 


