package org.mohajo.studyrepublic.studypage;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.mohajo.studyrepublic.domain.StudyFileshareboard;
import org.mohajo.studyrepublic.domain.StudyMember;
import org.mohajo.studyrepublic.domain.StudyNoticeboard;
import org.mohajo.studyrepublic.domain.StudyNoticeboardReply;
import org.mohajo.studyrepublic.domain.StudyQnaboard;
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
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

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
			sendString[0] = "#";
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
	public String studyNoticeboard(Model model, @Param(value="studyId") String studyId) {
		log.info(studyId);
		
		studyId = "BB00001";
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
	public String writeRegister(Model model, @ModelAttribute StudyNoticeboard studyNoticeboard,
			@ModelAttribute StudyFileshareboard studyfileshare, @ModelAttribute StudyQnaboard studyQnaboard,
			@RequestParam(name="boardName") String boardName,@RequestParam MultipartFile file, MultipartHttpServletRequest mpsr){
		
		
		/*List<MultipartFile> role = find한 결과를 가온다.;*/
		
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
}
