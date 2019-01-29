package org.mohajo.studyrepublic.study;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.mohajo.studyrepublic.domain.Review;
import org.mohajo.studyrepublic.domain.Study;
import org.mohajo.studyrepublic.domain.StudyMemberId;
import org.mohajo.studyrepublic.domain.TypeCD;
import org.mohajo.studyrepublic.repository.LeveltestRepository;
import org.mohajo.studyrepublic.repository.LeveltestResponseRepository;
import org.mohajo.studyrepublic.repository.PaymentRepository;
import org.mohajo.studyrepublic.repository.ReviewRepository;
import org.mohajo.studyrepublic.repository.StudyMemberRepository;
import org.mohajo.studyrepublic.repository.StudyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
	TypeCD typeCd;
	
	@Autowired
	StudyMemberId studyMemberId;
	
	
	@RequestMapping("/list/{typeCode}")
	public String list(@PathVariable("typeCode") String typeCode, Model model) {
		
		log.info("list() called...");
		log.info("typeCode: "+typeCode);
		
		//P/B --> p, b 로 변함. 왜지.
		//스터디 리스트 조회 페이지로 이동
			//분야코드, 스터디 (진행상태, 요일)
			//완료, 해체 불포함
		
		Pageable paging = PageRequest.of(0, 2, Sort.Direction.DESC, "postDate");
//		TypeCD typeCd = new TypeCD();	//Component - Autowired 로 분리할 것 --> 분리
		typeCd.setTypeCode(typeCode);
		List<Study> list = sr.findValidStudyByTypeCode(typeCd, paging);
		
		model.addAttribute("list", list);
		model.addAttribute("typeCode", typeCode);

		return "study/list";
	}
	
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
		studyMemberId.setStudyId(studyId);
		List<Review> review = rr.findByStudyId(studyMemberId);
		
		long dayDiffMillies = Math.abs(study.getStartDate().getTime() - study.getEndDate().getTime());
		long dayDiff = TimeUnit.DAYS.convert(dayDiffMillies, TimeUnit.MILLISECONDS);
		
		long timeDiffMillies = Math.abs(study.getStartTime().getTime() - study.getEndTime().getTime());
		long timeDiff = TimeUnit.HOURS.convert(timeDiffMillies, TimeUnit.MILLISECONDS);
				

		model.addAttribute("study", study);
		model.addAttribute("review", review);
		model.addAttribute(dayDiff);
		model.addAttribute(timeDiff);

		return "/study/detail";
	}
	
	@RequestMapping("/join/{studyId}")
	public String studyJoin() {
		
		//hasLeveltest == 1 && referer != ...leveltest
			//return "레벨테스트 페이지";
		//else if, typeCd == "P"
			//return "결제 페이지";
		
		return "";
	}
	
	@RequestMapping("")
	public String pay() {
		
		//결제 api 테스트 후 추가
		
		return "";
	}
	
}
	