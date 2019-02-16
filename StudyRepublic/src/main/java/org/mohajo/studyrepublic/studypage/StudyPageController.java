package org.mohajo.studyrepublic.studypage;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.apache.tomcat.util.json.JSONParser;
import org.mohajo.studyrepublic.domain.StudyMember;
import org.mohajo.studyrepublic.domain.StudyNoticeboard;
import org.mohajo.studyrepublic.domain.StudyNoticeboardReply;
import org.mohajo.studyrepublic.repository.MemberRepository;
import org.mohajo.studyrepublic.repository.StudyFileshareboardFileRepository;
import org.mohajo.studyrepublic.repository.StudyFileshareboardReplyRepository;
import org.mohajo.studyrepublic.repository.StudyFileshareboardRepository;
import org.mohajo.studyrepublic.repository.StudyMemberRepository;
import org.mohajo.studyrepublic.repository.StudyNoticeboardFileRepository;
import org.mohajo.studyrepublic.repository.StudyNoticeboardReplyRepository;
import org.mohajo.studyrepublic.repository.StudyNoticeboardRepository;
import org.mohajo.studyrepublic.repository.StudyQnaboardFileRepository;
import org.mohajo.studyrepublic.repository.StudyQnaboardReplyRepository;
import org.mohajo.studyrepublic.repository.StudyQnaboardRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.util.JSONPObject;

import lombok.extern.java.Log;

@Log
@Controller
@RequestMapping(value="/StudyPage")
public class StudyPageController {

	static private StudyPagePredicate predicate = new StudyPagePredicate();

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

	@Autowired
	StudyMemberRepository smr;
	@Autowired
	MemberRepository mr;

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

	@PostMapping("/Main")
	public String studyPageMain(Model model , @RequestParam("studyId") String studyId ) {
		// extra varialbe
		/*String studyId = "BB00001";*/
		String id = SecurityContextHolder.getContext().getAuthentication().getName();/*"aaa123";*/
		
		
		System.out.println(studyId);
		/*
		 * 이 주석 코드는 로그인 기능이 활성화 된 후 체크할 예정임.
		 * 
		 * Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		 * String userId = auth.getName();
		 * 
		 * log.info(userId); if(userId.isEmpty()|| userId.equals("")||
		 * userId.equals("anonymousUser")) { return "studypage/Error"; }
		 */

		if (studyId.isEmpty() || studyId.equals("")) {
			log.info(studyId.isEmpty() + "/" + studyId);
			return "studypage/Error";
		}

		// StudyPagePredicate pridicate = new StudyPagePredicate();

		try {
			model.addAttribute("findbystudymember", predicate.findByThisStudyMemberPredicate(studyId, smr));
			model.addAttribute("studynoticeboard3result",
					predicate.studyNoticeResultPredicate(studyId, 3, studyNoticeboardRepository));
			model.addAttribute("studyqnaboard3result",
					predicate.studyQnaResultPredicate(studyId, 3, studyQnaboardRepository));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return "studypage/studypage_main";
	}

	@RequestMapping("/Noticeboard")
	public String studyNoticeboard(Model model) {
		String studyId = "BB00001";
		String id = "aaa123";

		model.addAttribute("findAllStudyNoticeboard", studyNoticeboardRepository.findNoticeboardListByStudyId(studyId));
		return "studypage/studypage_notice";
	}

	@RequestMapping("/Qnaboard")
	public String studyQnaboard(Model model) {
		String studyId = "BB00001";
		String id = "aaa123";

		model.addAttribute("findQnaboardInfoByStudyId",
				predicate.studyQnaResultPredicate(studyId, 10, studyQnaboardRepository));
		//model.addAttribute("findQnaboardInfoByStudyId", studyQnaboardRepository.findQnaboardInfoByStudyId(studyId));
		return "studypage/studypage_qna";
	}

	@RequestMapping("/Fileshareboard")
	public String studyFileshareboard(Model model) {
		String studyId = "BB00001";
		String id = "aaa123";

		model.addAttribute("findAllStudyFileshareboard",
				predicate.studyFileshareResultPredicate(studyId, 10, studyFileshareboardRepository));
		return "studypage/studypage_fileshare";
	}
	
	@RequestMapping("/Management")
	public String studyManagement(Model model) {
		String studyId = "BB00001";
		String id = "aaa123";
		
		model.addAttribute("findStudyMemberLEMEbyStudyIdANDStudyStatusCode", smr.findStudyMemberLEMEbyStudyIdANDStudyStatusCode(studyId, "LE", "ME"));
		model.addAttribute("findStudyMemberWAbyStudyIdANDStudyStatusCode", smr.finStudyMemberWAbyStudyIdANDStudyStatusCode(studyId, "WA"));
		return "studypage/studypage_management";
	}
	
	@RequestMapping("/VideoCalling")
	public String studyVideoCalling(Model model) {
		String studyId = "BB00001";
		String id = "aaa123";

		return "video_calling/video_calling";
	}

	@ResponseBody
	@RequestMapping(value="/Noticeboard/show", method = RequestMethod.POST)
	public StudyNoticeboard showStudyNoticeboard(/*@RequestBody String studyId*/@RequestBody StudyNoticeboard readBoardContent){
		log.info("시행됨1");
		/*log.info(studyId);
		
		StudyNoticeboard readBoardContent = new StudyNoticeboard();*/
	/*	Optional<StudyNoticeboard> studyNoticeboardVO = predicate.findByNoticeboardNumberAndStudyId(readBoardContent, studyNoticeboardRepository);
		System.out.println(studyNoticeboardVO);
		return studyNoticeboardVO;*/
		
		StudyNoticeboard s = studyNoticeboardRepository.findNoticeboardByStudyIdANDNumber(readBoardContent.getStudyId(), readBoardContent.getNumber());
	
		log.info("내역 :" + s.toString());
		//log.info("내역2 :" + s.getStudyMember().toString());

		return s;
	}
	
	@ResponseBody
	@RequestMapping(value="/Noticeboard/replyshow", method = RequestMethod.POST)
	public List<StudyNoticeboardReply> showStudyNoticeboardReply(/*@RequestBody String studyId*/@RequestBody StudyNoticeboard readBoardContent){
		log.info("시행됨2");
		
		return studyNoticeboardReplyRepository.findNoticeboardReplyByStudyIdANDNumber(readBoardContent.getStudyId(), readBoardContent.getNumber());
	}

	@RequestMapping(value="/Write", method=RequestMethod.GET)
	public String showStudyBoardWrite(Model model, @RequestParam(name="board") String board){
		log.info("글쓰기 폼 진입");
		log.info(board);
		model.addAttribute("boardName", board);
		return "studypage/studypage_write";
	}
	
	@RequestMapping(value="/Register", method= RequestMethod.POST)
	public String writeRegister(Model model, @RequestParam(name="boardName") String boardName){
		log.info(boardName+"2");
		return "redirect:/StudyPage/Noticeboard";
	}
	
	//미리보기
	@RequestMapping(value="/StudypagePreview")
	public String studypage_preview() {
		
		log.info("들어오긴 하냐?");
		System.out.println("더 울어라 젊은 인생아");
		return "studypage/studypage_preview";
	}
}
