package org.mohajo.studyrepublic.studypage;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.mohajo.studyrepublic.domain.PageDTO;
import org.mohajo.studyrepublic.domain.PageMaker;
import org.mohajo.studyrepublic.domain.RequestBoardReply;
import org.apache.http.auth.AUTH;
import org.mohajo.studyrepublic.domain.Leveltest;
import org.mohajo.studyrepublic.domain.LeveltestResponse;
import org.mohajo.studyrepublic.domain.StudyFileshareboard;
import org.mohajo.studyrepublic.domain.StudyMember;
import org.mohajo.studyrepublic.domain.StudyMemberStatusCD;
import org.mohajo.studyrepublic.domain.StudyNoticeboard;
import org.mohajo.studyrepublic.domain.StudyNoticeboardReply;
import org.mohajo.studyrepublic.domain.StudyQnaboard;
import org.mohajo.studyrepublic.domain.StudyQnaboardReply;
import org.mohajo.studyrepublic.domain.StudyStatusCD;
import org.mohajo.studyrepublic.domain.TutorUploadFile;
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
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import edu.emory.mathcs.backport.java.util.Arrays;
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
	
	@RequestMapping("/PowerCheck")
	public @ResponseBody String[] powerCheck(@RequestBody String studyId) {
		String id = SecurityContextHolder.getContext().getAuthentication().getName();
		log.info(id + "/" + studyId);		
		StudyMember findStudyMemberLEMEbyStudyIdAndId = smr.findStudyMemberLEMEbyStudyIdAndId(studyId, id, "LE", "ME");
		String[] sendString = new String[2];
		log.info(findStudyMemberLEMEbyStudyIdAndId.getStudyMemberStatusCode().toString());
		if(findStudyMemberLEMEbyStudyIdAndId.getStudyMemberStatusCode().getStudyMemberStatusCode().equals("LE")) {
			sendString[0] = "/StudyPage/Management";
			sendString[1] = "관리";
		}else if(findStudyMemberLEMEbyStudyIdAndId.getStudyMemberStatusCode().getStudyMemberStatusCode().equals("ME")) {
			sendString[0] = "#personalExit";
			sendString[1] = "탈퇴";
		}
		log.info(sendString[0], sendString[1]);
		return sendString;
	}
	
	@RequestMapping("/Main")
	public String studyPageMain(Model model, @Param("studyId") String studyId) {
		
		//왜인지 모르겠지만 Main으로 오는 메서드가 2번 실행 된다.
		
		//Id를 추출한다. 로그인 상태가 아닐 경우, anonymousUser를 넣는다.
		String userId = SecurityContextHolder.getContext().getAuthentication().getName();
		
		//userId = "aaa123";
		//String studyId = "123";
		
		//유저가 로그인 상태가 아니거나, studyId가 null일 경우
		if(userId.equals("anonymousUser")||studyId==null) {
			
			log.info("userId" + userId);
			log.info("studyId" + studyId);
			log.info("I'm not Login! OR studyId is null");
			log.info("여기 메인이야 이쉨아!!!");
			System.out.println("니가 여길 왜 들어오냐???");
			return "studypage/error";
		}
		
		//생성되는 각 페이지 마다, 다른 결과를 보여줄려면 해당 페이지마다 studyId를 식별할 수 있는것이 있어야 함.
		//session으로 연결할 경우 해당 페이지마다 식별이 어려울 것이기 때문에, model을 통한 값 전달 및 링크 전송을 통한 값 전달을 실행함.
		//session으로 하는 방법도 있지만, 그렇게 할 경우 여러페이지의 페이지를 띄우더라도 결국은 session에 남은 하나의 페이지만 관리 될 수 있음.
		log.info("스터디 이름" + studyId + "/" + "유저 이름" + userId);
		try {
			StudyMember studymember = smr.findStudyMemberLEMEbyStudyIdAndId(studyId, userId, "LE", "ME");
		}catch(Exception e){
			log.info("걸렸는데 error가 리턴이 안됨...");
			return "studypage/error";
		}
		log.info("여기로 왜 오냐????");

		try {
			log.info("findbystudymember");
			model.addAttribute("findbystudymember", predicate.findByThisStudyMemberPredicate(studyId, smr));
			log.info("studynoticeboard3result");
			model.addAttribute("studynoticeboard3result",
					predicate.studyNoticeResultPredicate(studyId, 3, studyNoticeboardRepository));
			log.info("studyqnaboard3result");
			model.addAttribute("studyqnaboard3result",
					predicate.studyQnaResultPredicate(studyId, 3, studyQnaboardRepository));
			log.info("studyId");
			model.addAttribute("studyId", studyId);
			log.info("studyMemberInfo");
			model.addAttribute("studyMemberInfo", smr.findByStudyIdAndId(studyId, userId));
			System.out.println("예외가 아니면 여기로");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			log.info("로그 체킹");
		}

		return "studypage/studypage_main";
	}

	@RequestMapping("/Noticeboard")
	public String studyNoticeboard(Model model, @Param(value="studyId") String studyId,PageDTO pageDTO) {
		
	/*	studyId = "BB00001";
		String id = "aaa123";*/
		String id = SecurityContextHolder.getContext().getAuthentication().getName();
		log.info("id : " + id + " / " + "studyId : " + studyId);
		///페이징 갯수
		pageDTO.setSize(1);
		log.info("====");
		
		Pageable page = pageDTO.makePageable(0, "studyId");
		Page<StudyNoticeboard> list = studyNoticeboardRepository.findAll(studyNoticeboardRepository.makePredicate(studyId), page);
		model.addAttribute("findAllStudyNoticeboard",new PageMaker<>(list));
//		model.addAttribute("findAllStudyNoticeboard", studyNoticeboardRepository.findNoticeboardListByStudyId(studyId));
		return "studypage/studypage_notice";
	}


	@RequestMapping("/Qnaboard")
	public String studyQnaboard(Model model,PageDTO pageDTO,String studyId) {
		/*String studyId = "BB00001";
		String id = "aaa123";*/
		String id = SecurityContextHolder.getContext().getAuthentication().getName();
		log.info("id : " + id + " / " + "studyId : " + studyId);
		//페이징 갯수
		pageDTO.setSize(10);
		
		Pageable page = pageDTO.makePageable(0, "studyId");
		Page<StudyQnaboard> list = studyQnaboardRepository.findAll(studyQnaboardRepository.makePredicate(studyId), page);
		
		model.addAttribute("findQnaboardInfoByStudyId",new PageMaker<>(list));
//		model.addAttribute("findQnaboardInfoByStudyId",
//				predicate.studyQnaResultPredicate(studyId, 10, studyQnaboardRepository));
		//model.addAttribute("findQnaboardInfoByStudyId", studyQnaboardRepository.findQnaboardInfoByStudyId(studyId));
		return "studypage/studypage_qna";
	}

	@RequestMapping("/Fileshareboard")
	public String studyFileshareboard(Model model,PageDTO pageDTO,String studyId) {
		/*String studyId = "BB00001";
		String id = "aaa123";*/
	   String id = SecurityContextHolder.getContext().getAuthentication().getName();
	   log.info("id : " + id + " / " + "studyId : " + studyId);

		pageDTO.setSize(10);
		
		Pageable page = pageDTO.makePageable(0, "studyId");
		Page<StudyFileshareboard> list = studyFileshareboardRepository.findAll(studyFileshareboardRepository.makePredicate(studyId), page);
		
		model.addAttribute("findAllStudyFileshareboard",new PageMaker<>(list));
//		model.addAttribute("findAllStudyFileshareboard",
//				predicate.studyFileshareResultPredicate(studyId, 10, studyFileshareboardRepository));
		return "studypage/studypage_fileshare";
	}
	
	@RequestMapping("/Management")
	public String studyManagement(Model model, String studyId) {
		/*String studyId = "BB00001";
		String id = "aaa123";*/
		String id = SecurityContextHolder.getContext().getAuthentication().getName();
		log.info("id : " + id + " / " + "studyId : " + studyId);
		model.addAttribute("findStudyMemberLEMEbyStudyIdANDStudyStatusCode", smr.findStudyMemberbyStudyIdANDStudyStatusCode(studyId,"ME"));
		model.addAttribute("findStudyMemberWAbyStudyIdANDStudyStatusCode", smr.findStudyMemberWAbyStudyIdANDStudyStatusCode(studyId, "WA"));

		return "studypage/studypage_management";
	}
	
	@RequestMapping("/VideoCalling")
	public String studyVideoCalling(Model model, String studyId) {
		/*String studyId = "BB00001";
		String id = "aaa123";*/
		
		String id = SecurityContextHolder.getContext().getAuthentication().getName();
		log.info("id : " + id + " / " + "studyId : " + studyId);

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
		if(board=="Noticeboard") {
			return "studypage/studypage_write";
		}else {
			//해당 부분 필히 수정해야 함. 
			return "studypage/studypage_write";
		}
	}
	
	@RequestMapping(value="/Register", method= RequestMethod.POST)
	public String writeRegister(Model model, @ModelAttribute StudyNoticeboard studyNoticeboard,
			@ModelAttribute StudyFileshareboard studyfileshare, @ModelAttribute StudyQnaboard studyQnaboard,
			@RequestParam(name="boardName") String boardName, @RequestParam(name="studyNameId") String studyNameId,
			@RequestParam MultipartFile file, MultipartHttpServletRequest mpsr){

		String id = SecurityContextHolder.getContext().getAuthentication().getName();
		StudyMember studyMember;
		
		//값이 조회되는지 확인한다.
		try{
			studyMember = smr.findByStudyIdAndId(studyNameId, id);
		}catch(Exception e) {
			log.info("ID 및 studyId로 정보를 조회할 수 없습니다.");
			return null;
		}
		
		switch(boardName) {		
		
		case "Noticeboard":
			studyNoticeboard.setId(id);
			studyNoticeboard.setStudyId(studyNameId);
			
			List<StudyNoticeboard> studyNoticeboardList = studyNoticeboardRepository.findNoticeboardListByStudyId(studyNameId);
			if(studyMember==null) {
				log.info("값은 조회됐지만, null임");
				return null;
			}else {
				//조회되는 리스트의 갯수를 확인하고 해당 리스트 + 1 해서 number를 설정해준다.
				int number = studyNoticeboardList.size();
				log.info("제대로 된 값이 들어옴");
				studyNoticeboard.setNumber(number+1);
			}
			
			model.addAttribute("memberid", id);
			
			log.info(studyNoticeboard.getStudyId());
			log.info(studyNoticeboard.toString());
			studyNoticeboardRepository.save(studyNoticeboard);
			log.info("현 최종 목적지에 도달함2");

			//파일 첨부 관련
			/*Member member = memberrepository.findById(id).get();
			List<MemberRoles> roles = memberrolesrepository.findByRole(id);
			MemberRoles memberroles = new MemberRoles();
			memberroles.setRoleName("W");
			roles.add(memberroles);
			member.setGradeCD(new GradeCD("W"));
			member.setRoles(roles);
			
			memberrepository.save(member);
			tutorrepository.save(tutor);

			System.out.println("파일전송완료: " + file);
			List<MultipartFile> uploadFileList = request.getFiles("file");
			doUpload(request, model, uploadFileList, tutor, member);*/
			
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
	
	@RequestMapping(value="/Exit")
	@ResponseBody
	public boolean exitUser(@RequestBody String studyId) {
		String id = SecurityContextHolder.getContext().getAuthentication().getName();
		log.info(studyId + "/" + id);
	
		StudyMember studyMember = smr.findStudyMemberLEMEbyStudyIdAndId(studyId, id, "ME", "ME");
		
		if(studyMember != null) {
			log.info("조회에 성공함");
			log.info("아이디 : " + studyMember.getId()
					+ "/스터디명  : " + studyMember.getStudyId() + "/권한 : " + studyMember.getStudyMemberStatusCode());
			try {
				log.info("try 진입");
				studyMember.setStudyMemberStatusCode(new StudyMemberStatusCD("EX"));
				smr.save(studyMember);
				log.info("update까지 성공함.");
				return true;
			}catch(Exception e) {
				log.info("실패 : update 하는 과정중에 데이터가 변경됐을 가능성이 있음.");
				e.printStackTrace();
				return false;
			}
		}else {
			log.info("조회에 실패함");
			return false;
		}
	}
	
	@RequestMapping(value="/GetoutUser")
	@ResponseBody
	public boolean getoutUser(@RequestBody HashMap getoutUserInfo) {
		String studyId = (String)getoutUserInfo.get("studyId");
		String nickName = (String)getoutUserInfo.get("nickName");
		log.info(studyId + "/" + nickName);
		StudyMember studyMember = smr.findStudyMemberbyStudyIdAndNickNameAndStudyMemberStatusCode(studyId, nickName, "ME");
		if(studyMember != null) {
			log.info("조회에 성공함");
			log.info("닉네임 : " + studyMember.getMember().getNickname()
					+ "/스터디명  : " + studyMember.getStudyId() + "/권한 : " + studyMember.getStudyMemberStatusCode());
			String id = studyMember.getId();
			log.info("아이디 : " + id);
			try {
				log.info("try 진입");
				studyMember.setStudyMemberStatusCode(new StudyMemberStatusCD("EX"));
				smr.save(studyMember);
				log.info("update까지 성공함.");
				return true;
			}catch(Exception e) {
				log.info("실패 : update 하는 과정중에 데이터가 변경됐을 가능성이 있음.");
				e.printStackTrace();
				return false;
			}
		}else {
			log.info("조회에 실패함");
			return false;
		}
	}
	
	@RequestMapping(value="/SelectedAllGetout")
	@ResponseBody
	public List selectedAllGetout(@RequestBody List<HashMap> checkedList) {
		
		List sqlResultList = new ArrayList();
		String studyId;
		String nickName;
		for (HashMap hashMap : checkedList) {
			studyId = (String)hashMap.get("studyId");
			nickName = (String)hashMap.get("nickName");
			try {
				log.info("첫번째 try 진입");
				StudyMember studyMember = smr.findStudyMemberbyStudyIdAndNickNameAndStudyMemberStatusCode(studyId, nickName, "ME");
				if(studyMember != null) {
					log.info("조회에 성공함");
					log.info("닉네임 : " + studyMember.getMember().getNickname()
							+ "/스터디명  : " + studyMember.getStudyId() + "/권한 : " + studyMember.getStudyMemberStatusCode());
					String id = studyMember.getId();
					log.info("아이디 : " + id);
					try {
						log.info("두번째 try 진입");
						studyMember.setStudyMemberStatusCode(new StudyMemberStatusCD("EX"));
						smr.save(studyMember);
						log.info("update까지 성공함.");
						sqlResultList.add(true);
					}catch(Exception e) {
						log.info("두번째 try 실패 : update 하는 과정중에 데이터가 변경됐을 가능성이 있음.");
						e.printStackTrace();
						sqlResultList.add(false);
					}
				}else {
					sqlResultList.add(false);
				}
			}
			catch(Exception e){
				log.info("첫번째 try 실패");
				e.printStackTrace();
				sqlResultList.add(false);
			}
			
			//이하 테스트 코드
			/*log.info(studyId + "/" + nickName);
			sqlResultList.add(true);
			log.info("리스트에 값이 추가 됨.");*/
		}
		
		return sqlResultList;
	}
	
	@RequestMapping(value="/DenyUser")
	@ResponseBody
	public boolean denyUser(@RequestBody HashMap getoutUserInfo) {
		String studyId = (String)getoutUserInfo.get("studyId");
		String nickName = (String)getoutUserInfo.get("nickName");
		log.info(studyId + "/" + nickName);
		StudyMember studyMember = smr.findStudyMemberbyStudyIdAndNickNameAndStudyMemberStatusCode(studyId, nickName, "WA");
		if(studyMember != null) {
			log.info("조회에 성공함");
			log.info("닉네임 : " + studyMember.getMember().getNickname()
					+ "/스터디명  : " + studyMember.getStudyId() + "/권한 : " + studyMember.getStudyMemberStatusCode());
			String id = studyMember.getId();
			log.info("아이디 : " + id);
			try {
				log.info("try 진입");
				studyMember.setStudyMemberStatusCode(new StudyMemberStatusCD("DE"));
				smr.save(studyMember);
				log.info("update까지 성공함.");
				return true;
			}catch(Exception e) {
				log.info("실패 : update 하는 과정중에 데이터가 변경됐을 가능성이 있음.");
				e.printStackTrace();
				return false;
			}
		}else {
			log.info("조회에 실패함");
			return false;
		}
	}
	
	@RequestMapping(value="/OKUser")
	@ResponseBody
	public boolean okUser(@RequestBody HashMap getoutUserInfo) {
		String studyId = (String)getoutUserInfo.get("studyId");
		String nickName = (String)getoutUserInfo.get("nickName");
		log.info(studyId + "/" + nickName);
		StudyMember studyMember = smr.findStudyMemberbyStudyIdAndNickNameAndStudyMemberStatusCode(studyId, nickName, "WA");
		int capacity = studyMember.getStudy().getEnrollCapacity();
		int actual = studyMember.getStudy().getEnrollActual();
		if(studyMember != null) {
			log.info("조회에 성공함");
			log.info("닉네임 : " + studyMember.getMember().getNickname()
					+ "/스터디명  : " + studyMember.getStudyId() + "/권한 : " + studyMember.getStudyMemberStatusCode());
			String id = studyMember.getId();
			log.info("아이디 : " + id);
			if(capacity > actual) {
				try {
					log.info("try 진입");
					studyMember.setStudyMemberStatusCode(new StudyMemberStatusCD("ME"));
					studyMember.getStudy().setEnrollActual(actual+1);
					log.info("스터디원 추가가 완료 됨");
					smr.save(studyMember);
					log.info("update까지 성공함.");
					return true;
				}catch(Exception e) {
					log.info("실패 : update 하는 과정중에 데이터가 변경됐을 가능성이 있음.");
					e.printStackTrace();
					return false;
				}
			}else {
				return false;
			}
		}else {
			log.info("조회에 실패함");
			return false;
		}
	}
	@RequestMapping(value="/SelectedAllOKDenyButton")
	@ResponseBody
	public List selectedAllOKDenyButton(@RequestBody List<HashMap> checkedList) {
		
		List sqlResultList = new ArrayList();
		String studyId;
		String nickName;
		String selectOption;
		String userStatus;
		for (HashMap hashMap : checkedList) {
			studyId = (String)hashMap.get("studyId");
			nickName = (String)hashMap.get("nickName");
			selectOption = (String)hashMap.get("selectOption");
			if(selectOption.equals("OK")) {
				log.info("touch : " + selectOption);
				userStatus = "ME";
			}else if(selectOption.equals("Deny")) {
				log.info("touch : " + selectOption);
				userStatus = "DE";
			}else {
				log.info("변조 된 값 수령" + selectOption);
				return null;
			}
			try {
				log.info("첫번째 try 진입");
				StudyMember studyMember = smr.findStudyMemberbyStudyIdAndNickNameAndStudyMemberStatusCode(studyId, nickName, "WA");
				int capacity = studyMember.getStudy().getEnrollCapacity();
				int actual = studyMember.getStudy().getEnrollActual();
				if(studyMember != null) {
					log.info("조회에 성공함");
					log.info("닉네임 : " + studyMember.getMember().getNickname()
							+ "/스터디명  : " + studyMember.getStudyId() + "/권한 : " + studyMember.getStudyMemberStatusCode());
					String id = studyMember.getId();
					log.info("아이디 : " + id);
					if(userStatus=="ME"&& capacity > actual) {
						try {
							log.info("두번째 try 진입");
							studyMember.setStudyMemberStatusCode(new StudyMemberStatusCD(userStatus));
							//원래라면 이하 같은 과정에는 Transaction을 적용해줘야 하지만, 현재는 시간의 여유가 없으므로 그냥 넘어간다...
							if(userStatus=="ME") {
								studyMember.getStudy().setEnrollActual(actual+1);
								log.info("스터디원 추가가 완료 됨");
							}
							smr.save(studyMember);
							log.info("update까지 성공함.");
							sqlResultList.add(true);
						}catch(Exception e) {
							log.info("두번째 try 실패 : update 하는 과정중에 데이터가 변경됐을 가능성이 있음.");
							e.printStackTrace();
							sqlResultList.add(false);
						}
						
					}
				}else {
					sqlResultList.add(false);
				}
			}
			catch(Exception e){
				log.info("첫번째 try 실패");
				e.printStackTrace();
				sqlResultList.add(false);
			}
			
			//이하 테스트 코드
			/*log.info(studyId + "/" + nickName + "/" + selectOption);
			sqlResultList.add(true);
			log.info("리스트에 값이 추가 됨.");*/
		}
		return sqlResultList;
	}
	
	@RequestMapping(value="/CloseStudy")
	@ResponseBody
	public boolean studypageCloseStudy(@RequestBody String studyId) {
		
		String id = SecurityContextHolder.getContext().getAuthentication().getName();
		if(id==null||studyId==null) {
			log.info("id : " + id + " / " + "studyId : " + studyId);
			log.info("아이디 혹은 studyId를 찾을 수 없음");
			return false;
		}
		try{
			log.info("폐쇄 시작");
			StudyMember studyMember = smr.findStudyMemberLEMEbyStudyIdAndId(studyId, id, "LE", "LE");
			studyMember.getStudy().setStudyStatusCode(new StudyStatusCD("D", "해체"));
			smr.save(studyMember);
			log.info("폐쇄 완료");
			return true;
		}
		catch(Exception e){
			log.info("폐쇄 실패");
			return false;
		}
	}
	
	@RequestMapping(value="/TestView")
	@ResponseBody
	public List<HashMap> studypageTestView(Model model, @RequestBody HashMap userId){
		
		String studyId = (String)userId.get("studyId");
		String nickName = (String)userId.get("nickName");
		
		List testView = new ArrayList<HashMap>();
		List<LeveltestResponse> testResultList = smr.findStudyMemberbyStudyIdAndNickNameAndStudyMemberStatusCode(studyId, nickName, "WA").getLeveltestResponse();
		if(testResultList.isEmpty()==false) {
			Collections.sort(testResultList, new LevelTestComparator());
			
			for(LeveltestResponse levelTestResponse : testResultList){
				Leveltest levelTest = levelTestResponse.getLeveltest();
				
				HashMap map = new HashMap();
				map.put("questionNumber", levelTest.getQuestionNumber());
				map.put("levelTestTypeCode", levelTest.getLeveltestTypeCode().getCodeValueKorean());
				map.put("content", levelTest.getContent());
				map.put("correctAnswer", levelTest.getCorrectAnswer());
				map.put("submitAnswer", levelTestResponse.getSubmitAnswer());
				
				if(levelTestResponse.getIsCorrect()==1) {
					map.put("isCorrect", "O");
				}else {
					map.put("isCorrect", "X");
				}
				
				
				testView.add(map);
			}
			
			return testView;
			//levelTest
			/*for(LeveltestResponse leveltestResponse : testResultList) {
				System.out.println(leveltestResponse);
			}
			return null;*/
		}else {
			return null;
		}
	}
	
	@RequestMapping(value="/TestModify")
	@ResponseBody
	public List studypageTestModify(@RequestBody List<HashMap> checkedList) {
		
		List sqlResultList = new ArrayList();
		String studyId;
		String nickName;
		String selectOption;
		int selectNumber;
		int correct;
		for (HashMap hashMap : checkedList) {
			studyId = (String)hashMap.get("studyId");
			nickName = (String)hashMap.get("nickName");
			selectOption = (String)hashMap.get("selectOption");
			selectNumber = Integer.parseInt((String)hashMap.get("selectNumber"));
			
			if(selectOption.equals("O")) {
				log.info("touch : " + selectOption);
				correct = 1;
			}else if(selectOption.equals("X")) {
				log.info("touch : " + selectOption);
				correct = 0;
			}else {
				log.info("변조 된 값 수령" + selectOption);
				return null;
			}
			try {
				log.info("첫번째 try 진입");
				StudyMember studyMember = smr.findStudyMemberbyStudyIdAndNickNameAndStudyMemberStatusCode(studyId, nickName, "WA");
				if(studyMember != null) {
					//List 형태의 정보를 가지고 온다.
					if(selectNumber >= 0 && selectNumber <= studyMember.getLeveltestResponse().size()) {
						studyMember.getLeveltestResponse().get(selectNumber-1).setIsCorrect(correct);
						smr.save(studyMember);
						log.info(studyMember.getLeveltestResponse().get(selectNumber-1).toString());
					}
					/*log.info("조회에 성공함");
					log.info("닉네임 : " + studyMember.getMember().getNickname()
							+ "/스터디명  : " + studyMember.getStudyId() + "/권한 : " + studyMember.getStudyMemberStatusCode());
					String id = studyMember.getId();
					log.info("아이디 : " + id);
					try {
						log.info("두번째 try 진입");
						studyMember.setStudyMemberStatusCode(new StudyMemberStatusCD(userStatus));
						smr.save(studyMember);
						log.info("update까지 성공함.");
						sqlResultList.add(true);
					}catch(Exception e) {
						log.info("두번째 try 실패 : update 하는 과정중에 데이터가 변경됐을 가능성이 있음.");
						e.printStackTrace();
						sqlResultList.add(false);
					}*/
				}else {
					sqlResultList.add(false);
				}
			}
			catch(Exception e){
				log.info("첫번째 try 실패");
				e.printStackTrace();
				sqlResultList.add(false);
			}
			
			//이하 테스트 코드
			log.info(studyId + " / " + nickName + " / " + selectOption + " / " + selectNumber);
			sqlResultList.add(true);
			log.info("리스트에 값이 추가 됨.");
		}
		return sqlResultList;
	}
	
	@RequestMapping(value="/PhotoUpload")
	public String studypagePhotoupload() {
		return "/photoupload";
	}
	
	/*@PostMapping(value="/Management/Getout")
	public String studypageManagementGetout(@Param(value="studyId") String studyId) {
		
		return "redirect:/StudyPage/Management";
	}*/
	
	private void doUpload(HttpServletRequest request, Model model, List<MultipartFile> uploadFileList /*dddd*/, String studyId) throws IOException {
		String fileOriginName = "";
		
		final DefaultResourceLoader defaultResourceLoader = new DefaultResourceLoader();
		Resource resource = defaultResourceLoader.getResource("file:src\\main\\resource\\static\\studupage\\" + studyId);
		String uploadRootPath = resource.getFile().getAbsolutePath();
		
		File file = new File(uploadRootPath);
		if(!file.exists()) {
			file.mkdir();
		}
		
		List<File> uploadedFiles = new ArrayList<File>();
		
		for (MultipartFile fileData : uploadFileList) {
			String name = fileData.getOriginalFilename();
			fileOriginName = name;
			// Client File Name
			String sourceFileNameExtension = FilenameUtils.getExtension(fileOriginName).toLowerCase();
				
			String fileSaveName = RandomStringUtils.randomAlphanumeric(32) + "." + sourceFileNameExtension;

			System.out.println("Client File Name = " + name);
			System.out.println("fileSaveName: " + fileSaveName );
			
			/*if (fileSaveName != null && fileSaveName.length() > 0) {
				try {
					// Create the file at server
					File serverFile = new File(uploadRootPath + File.separator + fileSaveName);
					
					  File serverFile = new File(uploadRootDir.getCanonicalPath() + File.separator
					  + fileSaveName); File serverFile = new File(uploadRootDir.getPath() +
					  File.separator + fileSaveName);
					 

					BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
					stream.write(fileData.getBytes());
					stream.close();
					
					uploadedFiles.add(serverFile);
					System.out.println("Write file: " + serverFile);
					//여기까지만 해도 업로드는 문제 없이 된다.
					
					//자기 형식에 맞게 
					TutorUploadFile tutoruploadfile = new TutorUploadFile();
					tutoruploadfile.setTutor(tutor);
					tutoruploadfile.setTutorfileOriginname(fileOriginName);

					tutoruploadfile.setTutorfileUploadPath(uploadRootPath);

					tutoruploadfile.setTutorfileSavename(fileSaveName);
					String fullUrl = uploadRootPath + "\\" + fileSaveName;
					tutoruploadfile.setTutorFileFullUrl(fullUrl);
					String partUrl = "\\tutorFileUpload\\" + member.getId() + "\\" + fileSaveName;

					tutoruploadfile.setTutorfilePartUrl(partUrl);
					tutoruploadfile.setMember(member);
					tutoruploadfilerepository.save(tutoruploadfile);

				} catch (Exception e) {
					System.out.println("Error Write file: " + name);
					failedFiles.add(name);
				}
			}*/
		}
		model.addAttribute("uploadedFiles", uploadedFiles);
	}
	
	/*@GetMapping("/tutor/file")
	public String downloadFile(HttpServletRequest request, @RequestParam String tutorFileFullUrl,
			HttpServletResponse response) throws Exception {

		TutorUploadFile tutoruploadfile = tutoruploadfilerepository.findByTutorUploadPreviewFile(tutorFileFullUrl);

		System.out.println(tutorFileFullUrl);
		String uploadRootPath = request.getServletContext().getRealPath("/");
		System.out.println("업로드 루트 패쓰" + uploadRootPath);

		final DefaultResourceLoader defaultresourceloader = new DefaultResourceLoader();
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String id = auth.getName();
		
		Resource resource = defaultresourceloader
				.getResource("file:src\\main\\resources\\static" +tutoruploadfile.getTutorfilePartUrl());
				
		System.out.println("resource: " + resource); // 파일 저장 위치가 사람마다 다르기 때문에 get resource를 받아와 이용자에 맞는 절대경로로 반환해준다.
		System.out.println("resource 경로: " + resource.getFile().getAbsolutePath());

		File file = new File(resource.getFile().getAbsolutePath());
		System.out.println("file: " + file);
		BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));

		String header = request.getHeader("User-Agent");
		String fileName;

		if ((header.contains("MSIE")) || (header.contains("Trident")) || (header.contains("Edge"))) {
			fileName = URLEncoder.encode(tutoruploadfile.getTutorfileOriginname(), "UTF-8");
		} else {
			fileName = new String(tutoruploadfile.getTutorfileOriginname().getBytes("UTF-8"), "iso-8859-1");
		}
		response.setContentType("application/octet-stream");
		response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
		OutputStream outputStream=  response.getOutputStream();
		FileCopyUtils.copy(in, outputStream);
		in.close();
		outputStream.flush();
		outputStream.close();
		return "tutor/tutor_signupinquery";
	}*/
	
		//질문게시판 댓글
		@PostMapping("/qnaBoardReplyRegister")
		@ResponseBody
		public StudyQnaboardReply qnaBoardReplyRegister(int studyQnaBoardId, String studyId, String content, int replyGroup, int replystep) {
			
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			String id = auth.getName();
		 
		 StudyQnaboardReply studyQnaboardReply = new StudyQnaboardReply();
		
		 studyQnaboardReply.setStudyqnaboardId(studyQnaBoardId);
		 studyQnaboardReply.setStudyId(studyId);
		 studyQnaboardReply.setContent(content);
		 studyQnaboardReply.setReplyGroup(replyGroup);
		 studyQnaboardReply.setReplyStep(replystep);
		 studyQnaboardReply.setId(id);
		
			return studyQnaboardReplyRepository.save(studyQnaboardReply);

		}
		//댓글리스트
		@GetMapping("/qnaBoardReplyList")
		@ResponseBody
		public List<StudyQnaboardReply> qnaBoardReplyList(int studyQnaBoardId) {
			
			log.info("studyQnaBoardId:" + studyQnaBoardId);
		
			log.info("=================");
			 StudyQnaboard studyQnaboard = studyQnaboardRepository.findById(studyQnaBoardId).get();
			 log.info("왜안되냐고!!!:"+studyQnaboard.toString());
			 log.info(studyQnaboard.getStudyQnaboardReply().toString());
			
			return studyQnaboard.getStudyQnaboardReply();
			
			
		}
		//댓글삭제
		@PostMapping("/qnaBoardReplyDelete/{studyQnaboardReplyId}")
		@ResponseBody
		public int qnaBoardReplyDelete(@PathVariable int studyQnaboardReplyId) {

			log.info("=======================");
			log.info("studyQnaboardReplyId:"+studyQnaboardReplyId);
			log.info("=======================");
			studyQnaboardReplyRepository.deleteById(studyQnaboardReplyId);
			return studyQnaboardReplyId;
		}
		
		
}
