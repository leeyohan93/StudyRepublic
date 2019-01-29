package org.mohajo.studyrepublic.studypage;

import java.util.List;

import org.mohajo.studyrepublic.domain.StudyNoticeboard;
import org.mohajo.studyrepublic.repository.StudyFileshareboardFileRepository;
import org.mohajo.studyrepublic.repository.StudyFileshareboardReplyRepository;
import org.mohajo.studyrepublic.repository.StudyFileshareboardRepository;
import org.mohajo.studyrepublic.repository.StudyNoticeboardFileRepository;
import org.mohajo.studyrepublic.repository.StudyNoticeboardReplyRepository;
import org.mohajo.studyrepublic.repository.StudyNoticeboardRepository;
import org.mohajo.studyrepublic.repository.StudyQnaboardFileRepository;
import org.mohajo.studyrepublic.repository.StudyQnaboardReplyRepository;
import org.mohajo.studyrepublic.repository.StudyQnaboardRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.java.Log;

@Log
@Controller
public class StudyPageController {
	

	
	private static final Logger log = LoggerFactory.getLogger(StudyPageController.class);

	/**
	 * Noticeboard 관련 Repository
	 */
	@Autowired
	StudyNoticeboardRepository studyNoticeboardRepository;
	@Autowired
	StudyNoticeboardFileRepository studyNoticeboardFileRepository;
	@Autowired
	StudyNoticeboardReplyRepository studyNoticeboardReplyRepository;
	
	/**
	 * Fileshareboard 관련 Repository
	 */
	@Autowired
	StudyFileshareboardRepository studyFileshareboardRepository;
	@Autowired
	StudyFileshareboardFileRepository studyFileshareboardFileRepository;
	@Autowired
	StudyFileshareboardReplyRepository studyFileshareboardReplyRepository;
	
	/**
	 * Qnaboard 관련 Repository
	 */
	@Autowired
	StudyQnaboardRepository studyQnaboardRepository;
	@Autowired
	StudyQnaboardFileRepository studyQnaboardFileRepository;
	@Autowired
	StudyQnaboardReplyRepository studyQnaboardReplyRepository;
	
	@RequestMapping("/StudyNoticeboard")
	public String test3(Model model) {
		
		List <StudyNoticeboard> findAllStudyNoticeboard = studyNoticeboardRepository.findAll();
		model.addAttribute("findAllStudyNoticeboard", findAllStudyNoticeboard);

		return "studypage/StudyNoticeboard";
	}
	
	@RequestMapping("/studypagemain")
	public String studyPageMain(Model model/*, @RequestParam("studyId") String studyId*/) {
		//extra varialbe
		String studyId = "BB00001";
		
		/* 이 주석 코드는 로그인 기능이 활성화 된 후 체크할 예정임.
		 * 
		 * Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String userId = auth.getName();
		
		log.info(userId);
		if(userId.isEmpty()|| userId.equals("")|| userId.equals("anonymousUser")) {
			return "studypage/Error";
		}*/

		if(studyId.isEmpty()||studyId.equals("")) {
			log.info(studyId.isEmpty() + "/" + studyId);
			return "studypage/Error";
		}
		
		StudyPagePridicate pridicate = new StudyPagePridicate();
		//studyId를 이용해서 해당 스터디의 해당 스터디의 공지사항 게시판 내용을 3개 가지고 옴.
		List <StudyNoticeboard> studynoticeboard3result = pridicate.studyNotice3ResultPredicate(studyId, studyNoticeboardRepository);
		//studyId를 이용해서 해당 스터디의 해당 스터디의 Qna 게시판 내용을 3개 가지고 옴.
		//List <StudyQnaboard> studyqnaboard3result = pridicate.studyQna3ResultPredicate(studyId, studyQnaboardRepository);
		
		System.out.println(studynoticeboard3result.get(0));
		
		model.addAttribute("studynoticeboard3result", studynoticeboard3result);
		//model.addAttribute("studyqnaboard3result", studyqnaboard3result);
		
		return "studypage/StudyPageMain";
	}
	
	
}
