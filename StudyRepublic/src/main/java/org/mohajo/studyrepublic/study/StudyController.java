package org.mohajo.studyrepublic.study;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.mohajo.studyrepublic.domain.DayCD;
import org.mohajo.studyrepublic.domain.Interest1CD;
import org.mohajo.studyrepublic.domain.Interest2CD;
import org.mohajo.studyrepublic.domain.LeveltestList;
import org.mohajo.studyrepublic.domain.Member;
import org.mohajo.studyrepublic.domain.OnoffCD;
import org.mohajo.studyrepublic.domain.PageDTO;
import org.mohajo.studyrepublic.domain.PageMaker;
import org.mohajo.studyrepublic.domain.Study;
import org.mohajo.studyrepublic.domain.StudyFile;
import org.mohajo.studyrepublic.domain.StudyHelper;
import org.mohajo.studyrepublic.domain.StudyInterest;
import org.mohajo.studyrepublic.domain.StudyLocation;
import org.mohajo.studyrepublic.domain.StudyMember;
import org.mohajo.studyrepublic.domain.StudyMemberId;
import org.mohajo.studyrepublic.domain.StudyView;
import org.mohajo.studyrepublic.domain.Tutor;
import org.mohajo.studyrepublic.domain.TypeCD;
import org.mohajo.studyrepublic.repository.DayCDRepository;
import org.mohajo.studyrepublic.repository.Interest1CDRepository;
import org.mohajo.studyrepublic.repository.Interest2CDRepository;
import org.mohajo.studyrepublic.repository.LevelCDRepository;
import org.mohajo.studyrepublic.repository.LeveltestRepository;
import org.mohajo.studyrepublic.repository.LeveltestResponseRepository;
import org.mohajo.studyrepublic.repository.MemberRepository;
import org.mohajo.studyrepublic.repository.OnoffCDRepository;
import org.mohajo.studyrepublic.repository.PaymentRepository;
import org.mohajo.studyrepublic.repository.ReviewRepository;
import org.mohajo.studyrepublic.repository.StudyFileRepository;
import org.mohajo.studyrepublic.repository.StudyInterestRepository;
import org.mohajo.studyrepublic.repository.StudyLocationRepository;
import org.mohajo.studyrepublic.repository.StudyMemberRepository;
import org.mohajo.studyrepublic.repository.StudyNoticeboardRepository;
import org.mohajo.studyrepublic.repository.StudyRepository;
import org.mohajo.studyrepublic.repository.StudyViewRepository;
import org.mohajo.studyrepublic.repository.TutorRepository;
import org.mohajo.studyrepublic.repository.TypeCDRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

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
	
	@Autowired
	StudyViewRepository svr;
	
	@Autowired
	Interest1CDRepository i1cdr;
	
	@Autowired
	Interest2CDRepository i2cdr;
	
	@Autowired
	StudyFileRepository sfr;
	
	@Autowired
	StudyInterestRepository sir;
	
	@Autowired
	StudyLocationRepository slr;
	
	@Autowired
	TypeCD typeCd;
	
	
	/**
	 * @author	이미연
	 * @return	스터디 목록 페이지 (/study/list.html)
	 * 
	 * - 스터디 타입에 따른 조회 결과를 리스트 형태로 출력한다.
	 * - 단, 완료 또는 해체된 스터디는 결과에서 제외한다. 
	 */
	@RequestMapping("/list/{typeCode}")
	public String list(@PathVariable("typeCode") String typeCode, PageDTO pageDto, Model model) {
		// P/B --> p, b 로 변함. 왜지.

		// 날짜 동적 쿼리 쓸 때 참고:
		// new SimpleDateFormat("yyyy-MM-dd").parse("2018-01-01")
		
		log.info("list() called...");
		
		//스터디 리스트 조회 페이지로 이동
			//분야코드, 스터디 (진행상태, 요일)
			//완료, 해체 불포함
		
		//// Pageable paging = PageRequest.of(0, 2, Sort.Direction.DESC, "postDate");
		// Pageable paging = pageDto.makePageable(0, "postDate");	//(ERROR) java.sql.SQLSyntaxErrorException: Unknown column 'sv.postDate' in 'order clause'
		// pageDto.setSize(2);	//변화 없음
		
		Pageable page = pageDto.makePageable(0, "post_date");
		log.info(page.toString());
//		Pageable paging = PageRequest.of(0, 2, Sort.Direction.DESC, "post_date");
		// Page<Study> list = sr.findValidStudyByTypeCode(typeCd, paging);
		Page<StudyView> list = svr.selectValidStudyViewByTypeCode(typeCode, page);
		
		model.addAttribute("pagedList", new PageMaker<>(list));
		model.addAttribute("typeCode", typeCode);

		return "study/list";
	}
	
	/**
	 * @author	이미연
	 * @return	스터디 상세 페이지 (/study/detail.html), 또는 에러 페이지 (/study/404.html)
	 * 
	 * - 잘못된 studyId 값이 넘어오는 경우, 에러 페이지로 이동한다.
	 * - 출력할 데이터는 다음과 같다:
	 * 	- 공통:				스터디, 리더의 기본 회원정보 및 활동내역
	 * 	- 프리미엄 스터디:		튜터 정보 및 활동내역
	 * 	- 완료된 스터디:		리뷰 정보 (평균 별점, 총 리뷰 개수)
	 */
	@RequestMapping("/detail/{studyId}")
	public String detail(@PathVariable("studyId") String studyId, Model model) {
		
		log.info("detail() called...");
		
		Authentication auth =SecurityContextHolder.getContext().getAuthentication();
		String id = auth.getName();
		
		model.addAttribute("loggedInUserId", id);

		// StudyView 테이블 타입으로 쿼리 결과를 리턴 받는다. (평균 별점, 총 리뷰 개수를 출력하기 위함.)
		StudyView study = svr.selectStudyViewByStudyId(studyId);
		model.addAttribute("study", study);
		
		// 파라미터로 넘어온 studyId 로 검색되는 결과가 없으면 에러 페이지로 연결한다.
		if(study == null) {
			return "/study/404";
		}
		
		//		// 스터디 진행 시간 및 기간을 계산한다. (추후 자바스크립트로 대체할 수 있음.) --> 뷰 테이블를 통해 DB 단에서 처리.
		//		long dayDiffMillies = Math.abs(study.getStartDate().getTime() - study.getEndDate().getTime());
		//		long dayDiff = TimeUnit.DAYS.convert(dayDiffMillies, TimeUnit.MILLISECONDS);
		//		dayDiff = dayDiff/30;
		//		long timeDiffMillies = Math.abs(study.getStartTime().getTime() - study.getEndTime().getTime());
		//		long timeDiff = TimeUnit.HOURS.convert(timeDiffMillies, TimeUnit.MILLISECONDS);
		//		
		//		model.addAttribute("dayDiff", dayDiff);
		//		model.addAttribute("timeDiff", timeDiff);
		
		//		// 종료된 스터디에 한해 리뷰 정보를 조회한다. + 평균 평점을 조회한다.  --> 뷰 테이블를 통해 View 단에서 처리.
		//		if(study.getStudyStatusCode().getStudyStatusCode().equals("C")) {
		//			List<Review> review = rr.findByStudyId(studyId, "admin123");
		//			
		//			model.addAttribute("review", review);
		//		}
		
		// 리더 정보를 조회한다. (회원정보 및 스터디 활동내역)
		/*
		*	일반 스터디		--> Member
		*	프리미엄 스터디	--> Tutor (내부적으로 Member 와 조인)
		*/
		//typeCd == "P":
		//+ tutor, 스터디멤버 (where typeCd = "P") where leaderId = ? 
		//typeCd != "P" :
		//+ 스터디멤버 where studyStatusCode in ('LE', 'ME') where id = ?
		
		String studyTypeCode = studyId.substring(0,1);
		String leaderId = study.getMember().getId();
		Member leaderInfo = null;
		Tutor tutorInfo = null;
		List<StudyMember> studyActivity = null;
		
		switch(studyTypeCode) {
			case "B":
				leaderInfo = mr.findById(leaderId).get();
				studyActivity = smr.findStudyActivityById(leaderId);
				
				model.addAttribute("leaderInfo", leaderInfo);
			
			case "P":
				tutorInfo = tr.findByTutor(leaderId);
				studyActivity = smr.findTutorActivityById(leaderId);
				
				model.addAttribute("tutorInfo", tutorInfo);
		}
		
		model.addAttribute("studyActivity", studyActivity);

		return "/study/detail";
	}
	
	/**
	 * @author	이미연
	 * @return	ajax 응답
	 * - 로그인 유저의 아이디와 스터디 아이디로 StudyMember 테이블에서 결과를 조회한다.
	 * - 스터디 가입 여부 등을 판별하여 신청을 제한 하기 위함.
	 */
// (1)	JSON 으로 AJAX 응답 전송
/*	@RequestMapping("/prejoin")
	public void prejoin( @RequestParam("studyId") String studyId,  @RequestParam("userId") String userId, HttpServletResponse response) {

		log.info("prejoin() called...");

		log.info("studyId = " + studyId);
		log.info("userId = " + userId);
		
		StudyMemberId smi = new StudyMemberId();
		smi.setId(userId);
		smi.setStudyId(studyId);
		log.info("smi test = " + smi.getId());
		StudyMember history = smr.findByStudyIdAndId(studyId, userId);
//		StudyMember history = smr.findById(smi).get();
		log.info("find test = " + history);
		ObjectMapper objMapper = new ObjectMapper();
		
		try {
			response.getWriter().print(objMapper.writeValueAsString(history));
			
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}*/
// (2)	객체로 AJAX 응답 전송
	@RequestMapping("/prejoin")
	@ResponseBody
	public StudyMember prejoin( @RequestParam("studyId") String studyId,  @RequestParam("userId") String userId) {

		log.info("prejoin() called...");

		StudyMemberId smi = new StudyMemberId();
		smi.setId(userId);
		smi.setStudyId(studyId);
		StudyMember history = smr.findByStudyIdAndId(studyId, userId);
		
		return history;
	}
	
	/**
	 * @author	이미연
	 * @return	스터디 개설 (/study/open.html)
	 */
	@RequestMapping("/open")
	public String open(Model model) {

		log.info("open() called...");

		Authentication auth =SecurityContextHolder.getContext().getAuthentication();

		if(auth.getName() == "anonymousUser") {
			return "/member/login";
		}
		
		String id = auth.getName();
		Member member = mr.findById(id).get();
		
		log.info("logged in user: " + member);
		
		List<TypeCD> typeCode = tcr.findAll();
		List<OnoffCD> onoffCode = ocr.findAll();
		List<DayCD> dayCode = dcr.findAll();
		
		List<Interest1CD> interest1cd = i1cdr.findAll();
		List<Interest2CD> interest2cd = i2cdr.findAll();
		List<Interest2CD> Pinterest2cd = i2cdr.Pinterest2List();
		List<Interest2CD> Dinterest2cd = i2cdr.Dinterest2List();
		List<Interest2CD> Winterest2cd = i2cdr.Winterest2List();
		List<Interest2CD> Ninterest2cd = i2cdr.Ninterest2List();

		model.addAttribute("interest1cd", interest1cd);
		model.addAttribute("interest2cd", interest2cd);
		model.addAttribute("pinterest2cd", Pinterest2cd);
		model.addAttribute("dinterest2cd", Dinterest2cd);
		model.addAttribute("winterest2cd", Winterest2cd);
		model.addAttribute("ninterest2cd", Ninterest2cd);
		
		
		model.addAttribute("loggedInUser", member);
		model.addAttribute("typeCode", typeCode);
		model.addAttribute("onoffCode", onoffCode);
		model.addAttribute("dayCode", dayCode);
		
		return "/study/open";
	}
	
	
	/**
	 * @author	이미연
	 * @return	스터디 가입 페이지 (미결사항: join 페이지는 테스트용)
	 */
	@RequestMapping("/join/{studyId}")
	public String join(@PathVariable("studyId") String studyId, HttpServletRequest request) {
		
		log.info("join() called...");

		//hasLeveltest == 1 && referer != ...leveltest
			//return "레벨테스트 페이지";
		//else if, typeCd == "P"
			//return "결제 페이지";
		
		Study study = sr.findById(studyId).get();
		String referer = request.getHeader("referer");
		log.info(referer);
		
		if(study.getHasLeveltest() == 1) {
			return "/study/takeLeveltest";
		}
		
		if(study.getTypeCode().getTypeCode() == "P") {
			return "/study/pay";
		}
		
		return "/study/join";
	}
	

	@RequestMapping("/review")
	public String review() {

		log.info("review() called...");

		return "/study/review";
	}
	
	// 테스트 시작.
	@RequestMapping("/test")
	public String test(Model model) {
		
		log.info("test() called...");

/*
//		List<LeveltestResponse> leveltestResponse = lrr.selectByStudyId("BF00003");
		
		List<Study> study = sr.findAll();
		
		model.addAttribute("test", study);
		*/
		
		List<StudyMember> studyMember = smr.selectByStudyId("BF00002");
		model.addAttribute("studyMember", studyMember);
		
//		List<Leveltest> leveltest = lr.findAll();
//		List<LeveltestResponse> leveltestResponse = lrr.findAll();
//		model.addAttribute("leveltest", leveltest);
//		model.addAttribute("leveltestResponse", leveltestResponse);
	
		return "/study/test";
		
//		return "/study/paperBootTest";
		
	}
	// 테스트 끝.
	
	
	@RequestMapping("/register")
	public String leveltestSubmitTest(@ModelAttribute Study study, @ModelAttribute StudyHelper studyHelper, MultipartHttpServletRequest mhsRequest, @RequestParam MultipartFile file, @ModelAttribute LeveltestList leveltests, Model model) throws ParseException, IOException {
		
		log.info("register() called...");
		log.info(study.toString());
		
		// 참고:  https://stackoverflow.com/a/2009224
		// 설명:   "unparseable date" exception can here only be thrown by SimpleDateFormat#parse()
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		
		// 문제:  AM, PM 을 파싱하지 못하는 에러 - a 는 한국 기준 '오전/오후', 미국 기준 'AM/PM'
		// 해결:  Locale 을 지정
		// 참고:  https://stackoverflow.com/a/3618809, https://bvc12.tistory.com/168
	    SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a", Locale.US);
	    
		String startDateStr = studyHelper.getStartDateStr().trim();
		String endDateStr = studyHelper.getEndDateStr().trim();

		String startTimeStr = studyHelper.getStartTimeStr().trim();
		String endTimeStr = studyHelper.getEndTimeStr().trim();
		
		if(startTimeStr != "") {
			Date startDate = dateFormat.parse(startDateStr);
			Date endDate = dateFormat.parse(endDateStr);

			Date preStartTime = (Date)timeFormat.parse(startTimeStr);
			Date preEndTime = (Date)timeFormat.parse(endTimeStr);
			
		    java.sql.Time startTime = new java.sql.Time(preStartTime.getTime());
		    java.sql.Time endTime = new java.sql.Time(preEndTime.getTime());
		
			study.setStartDate(startDate);
			study.setEndDate(endDate);
			study.setStartTime(startTime);
			study.setEndTime(endTime);
		}

		String studyId = sr.getNewStudyId(study.getTypeCode().getTypeCode(), study.getOnoffCode().getOnoffCode());
		

		study.setStudyId(studyId);
		
//		List<StudyInterest> studyInterests = study.getStudyInterest();
//		
//		for(StudyInterest studyInterest : studyInterests) {
//			sir.save(studyInterest);
//		}
//		
//		List<StudyLocation> studyLoccations = study.getStudyLocation();
//		
//		for(StudyLocation studyLocation : studyLoccations) {
//			slr.save(studyLocation);
//		}
		
		log.info(study.toString());
		sr.save(study);

		log.info("file = " + file.toString());
		List<MultipartFile> files = mhsRequest.getFiles("file");
		doUpload(mhsRequest, model, files, studyId);
				
		model.addAttribute("study", study);
		
		
		return "/study/test";
	}

	private void doUpload(HttpServletRequest request, Model model, List<MultipartFile> files, String studyId) throws IOException {
		
		String fileOriginName = "";
		
		final DefaultResourceLoader defaultResourceLoader = new DefaultResourceLoader();
		
		Resource resource = defaultResourceLoader.getResource("file:src\\main\\resources\\static\\study\\" + studyId);
		String uploadRootPath = resource.getFile().getAbsolutePath();	//예외처리 필요
		
		File file = new File(uploadRootPath);	//폴더생성용
		
		if(!file.exists()) {
			file.mkdirs();
		}
		
		List<File> uploadedFiles = new ArrayList<File>();	//test 
		List<String> failedFiles = new ArrayList<String>();	//test
		
		for(MultipartFile fileData : files) {
			String name = fileData.getOriginalFilename();
			fileOriginName = name;
			
			String sourceFileNameExtension = FilenameUtils.getExtension(fileOriginName).toLowerCase();
			
			String fileSaveName = RandomStringUtils.randomAlphabetic(32) + "." + sourceFileNameExtension;
			
			if(fileSaveName != null && fileSaveName.length() > 0) {
				
				try {
					File serverFile = new File(uploadRootPath + File.separator + fileSaveName);	//파일생성용
					
					BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
					stream.write(fileData.getBytes());
					stream.close();
					
					uploadedFiles.add(serverFile);	//test
					
					StudyFile studyFile = new StudyFile();
					studyFile.setStudyId(studyId);
					studyFile.setStudyfileOriginname(fileOriginName);
					studyFile.setStudyfileSavename(fileSaveName);
					studyFile.setStudyfileParturl("\\study\\" + studyId + "\\" + fileSaveName);
					studyFile.setStudyfileFullurl(uploadRootPath + "\\" + fileSaveName);
					
					log.info(studyFile.toString());
					
					sfr.save(studyFile);
					
				} catch(Exception e) {
					System.out.println("Error Write file: " + name);
					failedFiles.add(name);
				}
			}
		}
		
		model.addAttribute("uploadedFiles", uploadedFiles);
		model.addAttribute("failedFiles", failedFiles);
		
		log.info(uploadedFiles.toString());
		log.info(failedFiles.toString());
	}
	
	
}
	