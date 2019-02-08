package org.mohajo.studyrepublic.study;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.mohajo.studyrepublic.domain.DayCD;
import org.mohajo.studyrepublic.domain.LevelCD;
import org.mohajo.studyrepublic.domain.Member;
import org.mohajo.studyrepublic.domain.OnoffCD;
import org.mohajo.studyrepublic.domain.PageDTO;
import org.mohajo.studyrepublic.domain.PageMaker;
import org.mohajo.studyrepublic.domain.Review;
import org.mohajo.studyrepublic.domain.Study;
import org.mohajo.studyrepublic.domain.StudyMember;
import org.mohajo.studyrepublic.domain.Tutor;
import org.mohajo.studyrepublic.domain.TypeCD;
import org.mohajo.studyrepublic.repository.DayCDRepository;
import org.mohajo.studyrepublic.repository.LevelCDRepository;
import org.mohajo.studyrepublic.repository.LeveltestRepository;
import org.mohajo.studyrepublic.repository.LeveltestResponseRepository;
import org.mohajo.studyrepublic.repository.MemberRepository;
import org.mohajo.studyrepublic.repository.OnoffCDRepository;
import org.mohajo.studyrepublic.repository.PaymentRepository;
import org.mohajo.studyrepublic.repository.ReviewRepository;
import org.mohajo.studyrepublic.repository.StudyMemberRepository;
import org.mohajo.studyrepublic.repository.StudyNoticeboardRepository;
import org.mohajo.studyrepublic.repository.StudyRepository;
//import org.mohajo.studyrepublic.repository.StudyViewRepository;
import org.mohajo.studyrepublic.repository.TutorRepository;
import org.mohajo.studyrepublic.repository.TypeCDRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.java.Log;

/**
 * @author	이미연
 * @since	2019. 1. 22.
 * @version	
 * - 기능 설명 1
 */
@Controller
@RequestMapping("/study")
@Log
public class StudyController {

	@Autowired
	StudyRepository sr;
	
	@Autowired
	StudyMemberRepository smr;
	
	@Autowired
	LeveltestRepository lr;
	
	@Autowired
	LeveltestResponseRepository lrr;
	
	@Autowired
	ReviewRepository rr;
	
	@Autowired
	PaymentRepository pr;
	
	@Autowired
	StudyNoticeboardRepository snbr;

	@Autowired
	MemberRepository mr;
	
	@Autowired
	TutorRepository tr;
	
	@Autowired
	LevelCDRepository lcr;
	
	@Autowired
	TypeCDRepository tcr;
	
	@Autowired
	DayCDRepository dcr;
	
	@Autowired
	OnoffCDRepository ocr;
	
//	@Autowired
//	StudyViewRepository svr;
	
	
	@Autowired
	TypeCD typeCd;
	
	
	
	@RequestMapping("/list/{typeCode}")
	public String list(@PathVariable("typeCode") String typeCode, PageDTO pageDto, Model model) {

//		날짜 동적 쿼리 쓸 때 참고:
//		new SimpleDateFormat("yyyy-MM-dd").parse("2018-01-01")
		
		log.info("list() called...");
		
		//P/B --> p, b 로 변함. 왜지.
		//스터디 리스트 조회 페이지로 이동
			//분야코드, 스터디 (진행상태, 요일)
			//완료, 해체 불포함
		
//		Pageable paging = PageRequest.of(0, 2, Sort.Direction.DESC, "postDate");
		Pageable paging = pageDto.makePageable(0, "postDate");
		typeCd.setTypeCode(typeCode);
//		List<Study> list = sr.findValidStudyByTypeCode(typeCd, paging);
		Page<Study> list = sr.findValidStudyByTypeCode(typeCd, paging);
		
//		model.addAttribute("list", list);
		model.addAttribute("list", new PageMaker(list));
		model.addAttribute("typeCode", typeCode);

		return "study/list";
	}
	
	// 리팩토링 시 아래를 뷰 테이블로 대체해보기
	/**
	 * @author	이미연
	 * @return	스터디 상세 페이지 (/study/detail.html), 또는 에러 페이지 (/study/404.html)
	 * - 잘못된 studyId 값이 넘어오는 경우, 에러 페이지로 이동
	 * - 출력할 데이터는 다음과 같다:
	 * 	- 공통:			스터디, 리더의 회원 정보 및 활동내역
	 * 	- 프리미엄 스터디:	튜터 정보 및 활동내역
	 * 	- 완료된 스터디:		리뷰 정보 (평균별점)
	 */
	@RequestMapping("/detail/{studyId}")
	public String detail(@PathVariable("studyId") String studyId, Model model) {
		
		log.info("detail() called...");
				
		//스터디 상세 조회 페이지로 이동
			//공통:
				//스터디 (분야, 지역, 가격, 유형, 진행방식), 리더 
			//studyStatusCode == "C":
				//+ 리뷰 정보
			//typeCd == "P":
				//+ tutor, 스터디멤버 (where typeCd = "P") where leaderId = ? 
			//typeCd != "P" :
				//+ 스터디멤버 where studyStatusCode in ('LE', 'ME') where id = ?

		Study study = sr.findById(studyId).get();		
		log.info(study.toString());
		
		if(study == null) {
			return "/study/404";
		}
		
		// 스터디 진행 시간 및 기간을 계산한다. (추후 자바스크립트로 대체할 수 있음.)
		long dayDiffMillies = Math.abs(study.getStartDate().getTime() - study.getEndDate().getTime());
		long dayDiff = TimeUnit.DAYS.convert(dayDiffMillies, TimeUnit.MILLISECONDS);
		dayDiff = dayDiff/30;
		long timeDiffMillies = Math.abs(study.getStartTime().getTime() - study.getEndTime().getTime());
		long timeDiff = TimeUnit.HOURS.convert(timeDiffMillies, TimeUnit.MILLISECONDS);
		
		model.addAttribute("dayDiff", dayDiff);
		model.addAttribute("timeDiff", timeDiff);
		
		// 종료된 스터디에 한해 리뷰 정보를 조회한다. + 평균 평점을 조회한다.
		if(study.getStudyStatusCode().getStudyStatusCode().equals("C")) {
			List<Review> review = rr.findByStudyId(studyId, "admin123");
			
			model.addAttribute("review", review);
		}
		
		// 리더 정보를 조회한다. (회원정보 및 스터디 활동내역)
		String leaderId = study.getMember().getId();
		Member leaderInfo = null;
		Tutor tutorInfo = null;
		List<StudyMember> studyActivity = null;
		
		//리더정보, 참여스터디
		leaderInfo = mr.findById(leaderId).get();
		studyActivity = smr.findStudyActivityById(leaderId);
		
		model.addAttribute("leaderInfo", leaderInfo);
		
		//강사정보, 개설스터디
		if(studyId.substring(0, 1) == "P") {
			tutorInfo = tr.findByTutor(leaderId);
			studyActivity = smr.findTutorActivityById(leaderId);
			
			model.addAttribute("tutorInfo", tutorInfo);

		}
		
		model.addAttribute("study", study);
		model.addAttribute("studyActivity", studyActivity);

		return "/study/detail";
	}
	
	/**
	 * @author	이미연
	 * @return	스터디 개설 1단계 (openBasic.html)
	 */
	@RequestMapping("/open/basic")
	public String open(Model model) {

		log.info("open() called...");

//		Authentication auth =SecurityContextHolder.getContext().getAuthentication();
//		String id = auth.getName();
//		Member user = mr.findById(id).get();
		
		//테스트용
		Member user = mr.findById("aaa123").get();
		
		log.info("user is ... " + user);
		
		
		List<TypeCD> typeCode = tcr.findAll();
		List<OnoffCD> onoffCode = ocr.findAll();
		List<LevelCD> levelCode = lcr.findAll();
		List<DayCD> dayCode = dcr.findAll();
		
		model.addAttribute("typeCode", typeCode);
		model.addAttribute("onoffCode", onoffCode);
		model.addAttribute("levelCode", levelCode);
		model.addAttribute("dayCode", dayCode);
		
		return "/study/openBasic";
	}
	
	
	
	/**
	 * @author	이미연
	 * @return	스터디 가입 페이지
	 */
	@RequestMapping("/join/{studyId}")
	public String join() {
		
		//hasLeveltest == 1 && referer != ...leveltest
			//return "레벨테스트 페이지";
		//else if, typeCd == "P"
			//return "결제 페이지";
		
		return "";
	}
	
	@RequestMapping("")
	public String pay() {
		
		//결제 api 테스트 후 추가 --> 포인트 결제 or 포인트 충전 창으로 연결
		
		return "";
	}
	
////	StudyNoticeboard 테스트용
//	@RequestMapping("/test/{noticeId}")
//	public String test(@PathVariable("noticeId") int noticeId, Model model) {
//		
////		StudyNoticeboard notice = snbr.findById(noticeId).get();
//		List<StudyNoticeboard> notice = snbr.findAll();
//		log.info(notice.toString());
//		model.addAttribute(notice);
//		
//		return "/study/test";
//	}
	
	//StudyMember 테스트용
	@RequestMapping("/test")
	public String test(Model model) {
		
		List<StudyMember> sm = smr.findAll();
		model.addAttribute("studyMember", sm);
		
		return "/study/studyMemberList";
	}
	
	@RequestMapping("/test2")
	public String test2(Model model) {
		
//		StudyView studyView = svr.findById("BB00001").get();
//		model.addAttribute("studyView", studyView);
		
		return "/study/studyViewTest";
	}

}
	