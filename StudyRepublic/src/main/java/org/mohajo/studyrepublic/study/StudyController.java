package org.mohajo.studyrepublic.study;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.mohajo.studyrepublic.domain.DayCD;
import org.mohajo.studyrepublic.domain.Interest1CD;
import org.mohajo.studyrepublic.domain.Interest2CD;
import org.mohajo.studyrepublic.domain.Leveltest;
import org.mohajo.studyrepublic.domain.LeveltestList;
import org.mohajo.studyrepublic.domain.LeveltestResponseList;
import org.mohajo.studyrepublic.domain.Member;
import org.mohajo.studyrepublic.domain.MemberPoint;
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
import org.mohajo.studyrepublic.domain.StudyMemberStatusCD;
import org.mohajo.studyrepublic.domain.StudyPrice;
import org.mohajo.studyrepublic.domain.StudyView;
import org.mohajo.studyrepublic.domain.Tutor;
import org.mohajo.studyrepublic.domain.TypeCD;
import org.mohajo.studyrepublic.repository.DayCDRepository;
import org.mohajo.studyrepublic.repository.Interest1CDRepository;
import org.mohajo.studyrepublic.repository.Interest2CDRepository;
import org.mohajo.studyrepublic.repository.LevelCDRepository;
import org.mohajo.studyrepublic.repository.LeveltestRepository;
import org.mohajo.studyrepublic.repository.LeveltestResponseRepository;
import org.mohajo.studyrepublic.repository.MemberPointRepository;
import org.mohajo.studyrepublic.repository.MemberRepository;
import org.mohajo.studyrepublic.repository.OnoffCDRepository;
import org.mohajo.studyrepublic.repository.PaymentRepository;
import org.mohajo.studyrepublic.repository.ReviewRepository;
import org.mohajo.studyrepublic.repository.StudyFileRepository;
import org.mohajo.studyrepublic.repository.StudyInterestRepository;
import org.mohajo.studyrepublic.repository.StudyLocationRepository;
import org.mohajo.studyrepublic.repository.StudyMemberRepository;
import org.mohajo.studyrepublic.repository.StudyMemberStatusCDRepository;
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
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
	StudyMemberStatusCDRepository smscr;
	
	@Autowired
	MemberPointRepository mpr;
	
	@Autowired
	TypeCD typeCd;
	
	String origin = "http://localhost:8080";
	String pathname = "";
	
	
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
		
		Pageable page = pageDto.studyMakePageable(0, 2, "post_date");
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
	 * @return	스터디 개설 (/study/open.html)
	 */
	@RequestMapping("/open")
	public String open(HttpServletRequest request, Model model) {

		log.info("open() called...");

		String referer = request.getHeader("referer");
		log.info("referer = " + referer);
		
		if(referer == null) {
			pathname = "/index";
			
		} else {
			pathname = referer.substring(referer.indexOf(origin) + origin.length());
			
		}
		log.info("pathname = " + pathname);
		
		
		Authentication auth =SecurityContextHolder.getContext().getAuthentication();

		if(auth.getName() == "anonymousUser") {
			return "redirect:/study/pleaseLogin/?pathname=" + pathname;
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
	
	@RequestMapping("/register")
	public String register(@ModelAttribute Study study, @ModelAttribute StudyPrice studyPrice, @ModelAttribute StudyHelper studyHelper, MultipartHttpServletRequest mhsRequest, @RequestParam MultipartFile file, @ModelAttribute LeveltestList leveltests, Model model) throws ParseException, IOException {
//	public String register(@ModelAttribute StudyView study, @ModelAttribute StudyHelper studyHelper, MultipartHttpServletRequest mhsRequest, @RequestParam MultipartFile file, @ModelAttribute LeveltestList leveltests, Model model) throws ParseException, IOException {
		
		log.info("register() called...");
		log.info("원본 = " + study.toString());
		
		if(!study.getMember().getGradeCD().getGradeCode().equals("T") && study.getTypeCode().getTypeCode().equals("P")) {
			log.info("Invalid member tried to open premium study");
			// 작성중인 내용 세션에 저장하도록 추가할 것
			model.addAttribute("errorMsg", "Your grade is not 'tutor'");
			return "/study/error";
		}
		
		
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
		log.info("----------------[ date & time processed ]----------------");
		
		List<StudyLocation> studyLocations = study.getStudyLocation();
		List<StudyInterest> studyInterests = study.getStudyInterest();
		
		if(studyLocations != null) {
			for(int i=studyLocations.size()-1; i>=0; i--) {
				StudyLocation studyLocation = studyLocations.get(i);
				if(studyLocation.getInterestLocation() == null) {
					studyLocations.remove(i);
				}
			}
		}

		if(studyInterests != null) {
			for(int i=studyInterests.size()-1; i>=0; i--) {
				StudyInterest studyInterest = studyInterests.get(i);
				if(studyInterest.getInterest2code() == null) {
					studyInterests.remove(i);
				}
			}
		}
		log.info("----------------[ location & interest processed ]----------------");

		String studyId = sr.getNewStudyId(study.getTypeCode().getTypeCode(), study.getOnoffCode().getOnoffCode());
		
		if(studyId == null) {
			log.info("studyId is null");
			// 작성중인 내용 세션에 저장하도록 추가할 것
			model.addAttribute("errorMsg", "Could not generate study_id");
			return "/study/error";
		}
		log.info("new studyId = " + studyId);
		study.setStudyId(studyId);
		log.info("----------------[ studyId generated ]----------------");

		log.info(studyPrice.toString());	
		studyPrice.setStudyId(studyId);
		study.setPrice(studyPrice);
		log.info(studyPrice.toString());
		log.info("----------------[ price processed ]----------------");

		log.info("수정 = " + study.toString());
		try {
			sr.save(study);
	//		svr.save(study);	//java.sql.SQLException: The target table study_view of the INSERT is not insertable-into
			log.info("----------------[ study save completed ]----------------");
			
			StudyMember studyMember = new StudyMember();
			studyMember.setStudy(study);
			studyMember.setId(study.getMember().getId());
			StudyMemberStatusCD studyMemberStatusCode = new StudyMemberStatusCD();
			studyMemberStatusCode.setStudyMemberStatusCode("LE");
			studyMember.setStudyMemberStatusCode(studyMemberStatusCode);
			
			smr.save(studyMember);
			log.info("----------------[ studyMember save completed ]----------------");
			
		} catch(Exception e) {
			e.printStackTrace();
			model.addAttribute("errorMsg", "Could not save study");
			return "/study/error";
		}

		List<Leveltest> leveltestList = leveltests.getLeveltests();

		if(leveltestList != null) {
			log.info(leveltestList.toString());
	
			for(int i=0; i<leveltestList.size(); i++) {
				leveltestList.get(i).setStudyId(studyId);
			}
			log.info(leveltestList.toString());
			lr.saveAll(leveltestList);
			log.info("----------------[ leveltest save completed ]----------------");
		}

		if(file != null) {
			log.info("file = " + file.toString());
			List<MultipartFile> files = mhsRequest.getFiles("file");
			doUpload(mhsRequest, model, files, studyId);
			log.info("----------------[ file save completed ]----------------");
		}

		model.addAttribute("test", study);
		
		
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
	

	/**
	 * @author	이미연
	 * @return	스터디 가입 페이지 (미결사항: join 페이지는 테스트용)
	 * @throws IOException 
	 */
	@RequestMapping("/join/{studyId}")
	public String join(@PathVariable("studyId") String studyId, HttpServletResponse response, RedirectAttributes redirectAttr, Model model) throws IOException {
		
		
		log.info("join() called...");

		Authentication auth =SecurityContextHolder.getContext().getAuthentication();
		String id = auth.getName();
		log.info("Logged in user = " + id);

		
		/************************* StudyMember 로 사용자 재검증 ******************************/

		// 참고:	https://epthffh.tistory.com/entry/JAVA단에서-alert창-띄우기, https://nahosung.tistory.com/74
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		
		// 1) 비로그인 유저의 경우 --> 로그인 페이지
		if (id.equals("anonymousUser")) {
			
			log.info("----------------[ Anonymous user ]----------------");
			
			out.println("<script>alert('로그인 후 신청할 수 있어요!'); window.location.href='/study/pleaseLogin/?pathname='/study/join" + studyId + "';</script>");
			out.flush();
			
			return null;
			
			
		} else {
			
			StudyMember history = prejoin(studyId, id);
			
			if(history != null) {
				// 2) 이미 가입된 스터디, 거절/탈퇴한 스터디, 가입 승인 대기중인 스터디인 경우
				
				String studyMemberStatusCode = history.getStudyMemberStatusCode().getStudyMemberStatusCode();
				String alert = "";
				
				switch (studyMemberStatusCode) {
				case "WA":
					alert = "승인 대기중인 스터디입니다.";
					break;
				case "ME":
				case "LE":
					alert = "이미 가입된 스터디입니다.";
					break;
				case "EX":
				case "DE":
					alert = "가입이 거절되거나 탈퇴한 스터디는 다시 신청할 수 없습니다.";
					break;
				}
				log.info("----------------[ User is rejected ]----------------");
				
				out.println("<script>alert('" + alert + "'); window.location.href='/index'</script>");
				out.flush();
				
				return "redirect:" + pathname;
				
			}		
		}
		
		log.info("----------------[ User is valid ]----------------");

		/************************* study 가입 절차 확인 ******************************/

		Study study;
		
		try {
			study = sr.findById(studyId).get();
		
		} catch(Exception e) {
			e.printStackTrace();
			
			return "/study/404";
			
		}
		
		int checkStudy = 0;
		
		if(study.getHasLeveltest() == 1) {
			List<Leveltest> leveltestList = lr.findByStudyId(studyId);
			model.addAttribute("leveltestList", leveltestList);
			checkStudy += 1;
			log.info("----------------[ Has leveltest ]----------------");
			
		}
		
		if(study.getTypeCode().getTypeCode() == "P") {
			// memberpointrepository 에서 사용자의 포인트 정보 조회 --> model 에 add
			MemberPoint memberPoint = mpr.findById(id).get();
			
			model.addAttribute("study", study);
			model.addAttribute("memberPoint", memberPoint);
			
			checkStudy += 1;
			log.info("----------------[ Is premium ]----------------");

		}
		
		if(checkStudy == 0) {
			
			// 참고:	https://sendthesignal.tistory.com/
			redirectAttr.addAttribute("id", id);
			redirectAttr.addAttribute("studyId", studyId);
			log.info("----------------[ Fast-forward join ]----------------");

			return "redirect:/study/join/confirm";
		}
			
		return "/study/join/process";
		
	}
	
	@RequestMapping("/join/confirm")
	public String joinConfirm(@RequestParam("id") String id, @RequestParam("studyId") String studyId, @ModelAttribute LeveltestResponseList leveltestResponseList, Model model) {
		
		if(id == null || studyId == null) {
			model.addAttribute("errorMsg", "스터디 가입 실패! 예기치 못한 문제가 발생하였습니다. 다시 시도해주세요.");
			
			return "/study/error";
			
		}
		
		try {
			saveStudyMember(id, studyId);
			
		} catch(Exception e) {
			e.printStackTrace();
			model.addAttribute("errorMsg", "가입 신청 처리중 문제가 발생했습니다. 다시 시도해주세요.");
			
			return "/study/error";
		}
		
		model.addAttribute("studyId", studyId);
		
		return "/study/join/confirm";
		
	}
	
//	@RequestMapping("/join/{studyId}")
//	public String join(@PathVariable("studyId") String studyId, /*@ModelAttribute StudyMember studyMember, */@ModelAttribute LeveltestResponseList leveltestResponseList, HttpSession session, HttpServletRequest request, HttpServletResponse response, Model model) throws IOException {
//		
//		log.info("join() called...");
//
//		// index 거치지 않고도 id 를 세션에 저장할 수 있다면, 세션에서 값을 읽어오도록 변경할 것.
//		Authentication auth =SecurityContextHolder.getContext().getAuthentication();
//		String id = auth.getName();
//		log.info("id = " + id);
//		
//		
//		/* 유입 경로 확인 */
//		String referer = request.getHeader("referer");
//		log.info("referer = " + referer);
//		
//		if(referer == null) {
//			pathname = "/index";
//			
//		} else {
//			pathname = referer.substring(referer.indexOf(origin) + origin.length());
//			
//		}
//		log.info("pathname = " + pathname);
//		
//		
//		/* 사용자, 스터디 식별을 위해 model 에 추가 */
//		model.addAttribute("id", id);
//		model.addAttribute("studyId", studyId);
//		
//		Study study;
//		
//		try {
//			study = sr.findById(studyId).get();
//		
//		} catch(Exception e) {
//			e.printStackTrace();
//			return "/study/404";
//			
//		}
//		
//		// 참고:	https://epthffh.tistory.com/entry/JAVA단에서-alert창-띄우기, https://nahosung.tistory.com/74
//		response.setContentType("text/html; charset=UTF-8");
//		PrintWriter out = response.getWriter();
//		
//		
//		/************************* StudyMember 로 사용자 재검증 ******************************/
//
//		// 1) 비로그인 유저의 경우 --> 로그인 페이지
//		if (id.equals("anonymousUser")) {
//			
//			log.info("----------------[ Anonymous user ]----------------");
//			
//			out.println("<script>alert('로그인 후 신청할 수 있어요!'); window.location.href='/study/pleaseLogin/?pathname=" + pathname + "';</script>");
////			out.println("<script>alert('로그인 후 신청할 수 있어요!');</script>");
//			out.flush();
//			
////			 return "redirect:/study/pleaseLogin/?pathname=" + pathname;
//			return "redirect:" + pathname;
//			
//		} else {
//			
//			StudyMember history = prejoin(studyId, id);
//			
//			if(history != null) {
//				// 2) 이미 가입된 스터디, 거절/탈퇴한 스터디, 가입 승인 대기중인 스터디인 경우
//				
//				String studyMemberStatusCode = history.getStudyMemberStatusCode().getStudyMemberStatusCode();
//				String alert = "";
//				
//				switch (studyMemberStatusCode) {
//				case "WA":
//					alert = "승인 대기중인 스터디입니다.";
//					break;
//				case "ME":
//				case "LE":
//					alert = "이미 가입된 스터디입니다.";
//					break;
//				case "EX":
//				case "DE":
//					alert = "가입이 거절되거나 탈퇴한 스터디는 다시 신청할 수 없습니다.";
//					break;
//				}
//				log.info("----------------[ User is rejected ]----------------");
//				
//				out.println("<script>alert('" + alert + "'); window.location.href='/index'</script>");
//				out.flush();
//				
////				 return "redirect:/index";	//java.lang.IllegalStateException: Cannot call sendRedirect() after the response has been committed
//				return "redirect:" + pathname;
//				
////			} else {
////				// 3) 가입 이력이 없는 경우 --> 가입 페이지
////
////				log.info("----------------[ User is a new studyMember ]----------------");
////				
////				out.println("<script>var answer = confirm('이 스터디에 가입하시겠어요?'); "
////						+ "if(answer) { window.location.href = " + str + "/study/join/" + studyId + "?id=" + id + "; "
////						+ "} else { return; }</script>");
////				out.flush();
//////				return new ModelAndView("redirect:/helpInfo/update");
//				
//			}		
//		}
//		
//		log.info("화ㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏ인");
//		
//		/*************************  *************************/
//		
//		/* 1. 결제 후 매핑되는 경우 */
//		if(pathname.indexOf("/pay") != -1) {
//			
//			log.info("-------------------- case 1 --------------------");
//			saveStudyMember(id, studyId);
//			//save payment
//			
//			if(study.getHasLeveltest() == 1) {
//				List<LeveltestResponse> leveltestResponses = (List<LeveltestResponse>) session.getAttribute("leveltestResponses");
//				
//				if(leveltestResponses.get(0).getStudyId().equals(studyId)) {
//					lrr.saveAll(leveltestResponses);
//					session.removeAttribute("leveltestResponses");
//				}
//			}
//			
//			return "/study/join/confirm";
//			
//		}
//		/* 2. 레벨테스트 응시 후 매핑되는 경우 */
//		else if(pathname.indexOf("/leveltest") != -1) {
//
//			List<LeveltestResponse> leveltestResponses = leveltestResponseList.getLeveltesResponses();
//
//			
//			/* 2-1. 프리미엄 스터디 --> 결제 페이지 */
//			if(study.getTypeCode().getTypeCode() == "P") {
//				
//				log.info("-------------------- case 2-1 --------------------");
//				
//				session.setAttribute("leveltestResponses", leveltestResponses);
//				// session.timeout 확인 --> 문제 발생 시 스크립트단에서 처리할 것
//				// html 에 새로 입력 버튼 추가
//				
//				// memberpointrepository 에서 사용자의 포인트 정보 조회 --> model 에 add
//				model.addAttribute("study", study);	//출력을 위해 추가
//				
//				return "/study/join/pay";
//				
//			} 
//			/* 2-2. 일반 스터디 --> 가입 완료 */
//			else {
//				
//				log.info("-------------------- case 2-2 --------------------");
//
//				saveStudyMember(id, studyId);
//				lrr.saveAll(leveltestResponses);
//				session.removeAttribute("leveltestResponses");
//				
//				return "/study/join/confirm";
//				
//			}
//			
//		}
//		/* 3. 최초 매핑되는 경우 */
//		// 이 경우가 많다면, 최상위로 위치를 이동해야 한다.
//		else {
//			
//			
//			/* 3-1. 레벨테스트 보유 --> 레벨테스트 페이지 */
//			if(study.getHasLeveltest() == 1) {
//				
//				log.info("-------------------- case 3-1 --------------------");
//
//				List<Leveltest> leveltestList = lr.findByStudyId(studyId);
//				model.addAttribute("leveltestList", leveltestList);
//				
//				return "/study/join/leveltest";
//				
//			} 
//			/* 3-2. 프리미엄 스터디 --> 결제 페이지 */
//			else if(study.getTypeCode().getTypeCode() == "P") {
//
//				log.info("-------------------- case 3-2 --------------------");
//
//				// memberpointrepository 에서 사용자의 포인트 정보 조회 --> model 에 add
//				model.addAttribute("study", study);
//				
//				return "/study/join/pay";
//				
//			} 
//			/* 레벨테스트가 미등록된 일반 스터디 */
//			else {
//				
//				log.info("-------------------- case 3-3 --------------------");
//				
//				saveStudyMember(id, studyId);
//				
//				return "/study/join/confirm";
//				
//			}
//		}
//	}
	
	
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	private void saveStudyMember(String id, String studyId) {
//	private void saveStudyMember(StudyMember studyMember, String studyId) {

		StudyMember studyMember = new StudyMember();
		
		//org.springframework.orm.jpa.JpaSystemException: Could not set field value [aaa123] value by reflection : [class org.mohajo.studyrepublic.domain.StudyMemberId.id] setter of org.mohajo.studyrepublic.domain.StudyMemberId.id; nested exception is org.hibernate.PropertyAccessException: Could not set field value [aaa123] value by reflection : [class org.mohajo.studyrepublic.domain.StudyMemberId.id] setter of org.mohajo.studyrepublic.domain.StudyMemberId.id] with root cause
//		Member member = new Member();
//		member.setId(id);
//		studyMember.setMember(member);
		
		//org.springframework.orm.jpa.JpaSystemException: attempted to assign id from null one-to-one property [org.mohajo.studyrepublic.domain.StudyMember.member]; nested exception is org.hibernate.id.IdentifierGenerationException: attempted to assign id from null one-to-one property [org.mohajo.studyrepublic.domain.StudyMember.member]
//		StudyMemberId studyMemberId = new StudyMemberId();
//		studyMemberId.setId(id);
//		studyMemberId.setStudyId(studyId);
//		studyMember.setStudyMemberId(studyMemberId);
		
		Member member = mr.findById(id).get();
		Study study = sr.findById(studyId).get();
		// 참고:  https://stackoverflow.com/a/27467933/11111203
		// 답변자님 절 받으세요
		// @EmbeddedId ContractServiceLocationPK id = new ContractServiceLocationPK(); 와 같은 식으로 도메인에서 복합키를 초기화하라는 답변. --> save 시 직접 지정하는 것으로 변경
		StudyMemberStatusCD studyMemberStatusCode = smscr.findById("WA").get();
		
		studyMember.setMember(member);
		studyMember.setStudy(study);
		studyMember.setStudyMemberStatusCode(studyMemberStatusCode);
		
		smr.save(studyMember);
		// studyMemberStatusCD 확인하기
		log.info("-------------------- studyMember saved --------------------");

		// [Request processing failed; nested exception is org.springframework.dao.InvalidDataAccessApiUsageException: Executing an update/delete query; nested exception is javax.persistence.TransactionRequiredException: Executing an update/delete query] with root cause
		sr.plusEnrollActual(studyId);
		log.info("-------------------- study enrollActual updated --------------------");
	}
	
	
	@RequestMapping("/pleaseLogin")
	public String pleaseLogin(@RequestParam String pathname) {
		
		log.info("pathname = " + pathname);
		
		// 참고:  https://best421.tistory.com/53
		// 오류:  org.thymeleaf.exceptions.TemplateInputException: Error resolving template [/study/detail/BO00002], template might not exist or might not be accessible by any of the configured Template Resolvers
		// 원본:  return pathname;
		return "redirect:" + pathname;
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
	
	
	@RequestMapping("/insertTest")
	public String insertTest(Model model) {
		
		Study study = new Study();
		String studyId = "ST00001";
		study.setStudyId(studyId);
		study.setName("테스트");
		
		Member member = new Member();
		member.setId("bbb123");
		study.setMember(member);
		
//		List<StudyInterest> studyInterestList = new ArrayList<StudyInterest>();
//		StudyInterest studyInterest = new StudyInterest();
//		Interest2CD interest2code = new Interest2CD();
//		interest2code.setInterest2Code("P02");
//		studyInterest.setInterest2code(interest2code);
//		studyInterestList.add(studyInterest);
//		study.setStudyInterest(studyInterestList);

//		List<StudyLocation> studyLocationList = new ArrayList<StudyLocation>();
//		StudyLocation studyLocation = new StudyLocation();
//		studyLocation.setInterestLocation("태양계 지구");
//		studyLocationList.add(studyLocation);
//		study.setStudyLocation(studyLocationList);
		
		StudyPrice studyPrice = new StudyPrice();
		studyPrice.setPrice(30000);
		studyPrice.setStudyId(studyId);
		study.setPrice(studyPrice);
		
		log.info(study.toString());
		sr.save(study);
		model.addAttribute("test", study);
		
		return "/study/test";
	}
}
	