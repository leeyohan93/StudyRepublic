package org.mohajo.studyrepublic.studypage;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.tomcat.util.json.JSONParser;
import org.mohajo.studyrepublic.domain.InquireBoard;
import org.mohajo.studyrepublic.domain.StudyBoard;
import org.mohajo.studyrepublic.domain.StudyFileshareboard;
import org.mohajo.studyrepublic.domain.StudyMember;
import org.mohajo.studyrepublic.domain.StudyNoticeboard;
import org.mohajo.studyrepublic.domain.StudyNoticeboardReply;
import org.mohajo.studyrepublic.domain.StudyQnaboard;
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
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.data.repository.query.Param;
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
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.fasterxml.jackson.databind.util.JSONPObject;

import lombok.extern.java.Log;

@Log
@Controller
@RequestMapping(value="/StudyPage")
@SessionAttributes("studyId")	//studyId 라는 이름으로 저장되는 model.attribute는 세션에 저장됨.
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
	
	@RequestMapping("/Main")
	public String studyPageMain(Model model, @Param("studyId") String studyId) {
		
		//Id를 추출한다. 로그인 상태가 아닐 경우, anonymousUser를 넣는다.
		String userId = SecurityContextHolder.getContext().getAuthentication().getName();
		
		//userId = "aaa123";
		//String studyId = "123";
		
		//유저가 로그인 상태가 아니거나, studyId가 null일 경우
		if(userId.equals("anonymousUser")||studyId==null) {
			log.info("I'm not Login! OR studyId is null");
			return "studypage/error";
		}
		
		//생성되는 각 페이지 마다, 다른 결과를 보여줄려면 해당 페이지마다 studyId를 식별할 수 있는것이 있어야 함.
		//session으로 연결할 경우 해당 페이지마다 식별이 어려울 것이기 때문에, model을 통한 값 전달 및 링크 전송을 통한 값 전달을 실행함.
		//session으로 하는 방법도 있지만, 그렇게 할 경우 여러페이지의 페이지를 띄우더라도 결국은 session에 남은 하나의 페이지만 관리 될 수 있음.
		System.out.println(studyId);

		try {
			model.addAttribute("findbystudymember", predicate.findByThisStudyMemberPredicate(studyId, smr));
			model.addAttribute("studynoticeboard3result",
					predicate.studyNoticeResultPredicate(studyId, 3, studyNoticeboardRepository));
			model.addAttribute("studyqnaboard3result",
					predicate.studyQnaResultPredicate(studyId, 3, studyQnaboardRepository));
			model.addAttribute("studyId", studyId);
			model.addAttribute("studyMemberInfo", smr.findByStudyIdAndId(studyId, userId));
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
		String id = SecurityContextHolder.getContext().getAuthentication().getName();
		model.addAttribute("memberid", id);
		return "studypage/studypage_write";
	}
	
	@RequestMapping(value="/Register", method= RequestMethod.POST)
	public String writeRegister(Model model, @ModelAttribute StudyNoticeboard studyNoticeboard, @ModelAttribute StudyFileshareboard studyfileshare, @ModelAttribute StudyQnaboard studyQnaboard, @RequestParam(name="boardName") String boardName){
		
		log.info(boardName);
		
		String id = SecurityContextHolder.getContext().getAuthentication().getName();
		studyNoticeboard.setId(id);
		studyNoticeboard.setStudyId("BB00001");
		
		
		
		model.addAttribute("memberid", id);
		
		switch(boardName) {		
		
		case "Noticeboard":
			log.info(studyNoticeboard.getStudyId());
			log.info(studyNoticeboard.toString());
			studyNoticeboardRepository.save(studyNoticeboard);
			log.info("현 최종 목적지에 도달함2");
			return "redirect:/StudyPage/Noticeboard";
		case "Fileshareboard":
			log.info("현 최종 목적지에 도달함3");
			studyQnaboardRepository.save(studyQnaboard);
			return "redirect:/StudyPage/Fileshareboard";
		case "Qnaboard":
			studyFileshareboardRepository.save(studyfileshare);
			return "redirect:/StudyPage/Qnaboard";
		}
		
		return "studypage/error";
	}
	
	//미리보기
	@RequestMapping(value="/StudypagePreview")
	public String studypagePreview() {
		
		log.info("들어오긴 하냐?");
		System.out.println("더 울어라 젊은 인생아");
		return "studypage/studypage_preview";
	}
	
	@RequestMapping(value="/PhotoUpload")
	public String studypagePhotoupload() {
		return "/photoupload";
	}
}
