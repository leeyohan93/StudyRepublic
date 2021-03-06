package org.mohajo.studyrepublic.tutor;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.mohajo.studyrepublic.domain.CareerCD;
import org.mohajo.studyrepublic.domain.EducationCD;
import org.mohajo.studyrepublic.domain.GradeCD;
import org.mohajo.studyrepublic.domain.Interest1CD;
import org.mohajo.studyrepublic.domain.Interest2CD;
import org.mohajo.studyrepublic.domain.Member;
import org.mohajo.studyrepublic.domain.MemberRoles;
import org.mohajo.studyrepublic.domain.PageDTO;
import org.mohajo.studyrepublic.domain.PageMaker;
import org.mohajo.studyrepublic.domain.Study;
import org.mohajo.studyrepublic.domain.StudyMember;
import org.mohajo.studyrepublic.domain.StudyView;
import org.mohajo.studyrepublic.domain.Tutor;
import org.mohajo.studyrepublic.domain.TutorCareer;
import org.mohajo.studyrepublic.domain.TutorInterest;
import org.mohajo.studyrepublic.domain.TutorUploadFile;
import org.mohajo.studyrepublic.domain.TypeCD;
import org.mohajo.studyrepublic.fileupload.MyUploadForm;
import org.mohajo.studyrepublic.repository.CareerCDRepository;
import org.mohajo.studyrepublic.repository.EducationCDRepository;
import org.mohajo.studyrepublic.repository.Interest1CDRepository;
import org.mohajo.studyrepublic.repository.Interest2CDRepository;
import org.mohajo.studyrepublic.repository.MemberRepository;
import org.mohajo.studyrepublic.repository.MemberRolesRepository;
import org.mohajo.studyrepublic.repository.StudyMemberRepository;
import org.mohajo.studyrepublic.repository.StudyRepository;
import org.mohajo.studyrepublic.repository.StudyViewRepository;
import org.mohajo.studyrepublic.repository.TutorCareerRepository;
import org.mohajo.studyrepublic.repository.TutorInterestRepository;
import org.mohajo.studyrepublic.repository.TutorRepository;
import org.mohajo.studyrepublic.repository.TutorUploadFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@Controller
public class TutorController implements Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	MemberRepository memberrepository;
	@Autowired
	MemberRolesRepository memberrolesrepository;
	@Autowired
	TutorRepository tutorrepository;
	@Autowired
	CareerCDRepository careerCDRepository;
	@Autowired
	EducationCDRepository educationCDRepository;
	@Autowired
	Interest1CDRepository interest1CDRepository;
	@Autowired
	Interest2CDRepository interest2CDRepository;
	@Autowired
	TutorUploadFileRepository tutoruploadfilerepository;
	@Autowired
	TutorCareerRepository tutorcareerrepository;
	@Autowired
	TutorInterestRepository tutorinterestrepository;
	@Autowired
	StudyRepository sr;
	
	@Autowired
	TypeCD typeCd;
	
	@Autowired
	StudyViewRepository svr;
	
	@Autowired
	StudyMemberRepository smr;

	private static final String ROLE_PREFIX = "ROLE_";

	@RequestMapping("/tutor")
	public String tutorInfo(Model model) {

		List<Tutor> tutor = tutorrepository.findAll();
		model.addAttribute("tutor", tutor);

		return "tutor/tutor_info";

	}

	@RequestMapping("/tutor/signup")
	public String signupTutor(Model model) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String id = auth.getName();
		model.addAttribute("id", id);

		MyUploadForm myUploadForm = new MyUploadForm();
		model.addAttribute("myUploadForm", myUploadForm);

		List<CareerCD> careercd = careerCDRepository.findAll();
		model.addAttribute("careercd", careercd);

		List<EducationCD> educationcd = educationCDRepository.findAll();
		model.addAttribute("educationcd", educationcd);

		List<Interest1CD> interest1cd = interest1CDRepository.findAll();
		model.addAttribute("interest1cd", interest1cd);

		List<Interest2CD> interest2cd = interest2CDRepository.findAll();
		model.addAttribute("interest2cd", interest2cd);

		List<Interest2CD> Pinterest2cd = interest2CDRepository.Pinterest2List();
		model.addAttribute("pinterest2cd", Pinterest2cd);

		List<Interest2CD> Dinterest2cd = interest2CDRepository.Dinterest2List();
		model.addAttribute("dinterest2cd", Dinterest2cd);

		List<Interest2CD> Winterest2cd = interest2CDRepository.Winterest2List();
		model.addAttribute("winterest2cd", Winterest2cd);

		List<Interest2CD> Ninterest2cd = interest2CDRepository.Ninterest2List();
		model.addAttribute("ninterest2cd", Ninterest2cd);

		Member modifyuser = memberrepository.findById(id).get();
		model.addAttribute("mdu",modifyuser);
		return "tutor/tutor_signuptutor";

	}

	@RequestMapping("/tutor/insert")
	public String insertTutor(Model model, @ModelAttribute Tutor tutor, MultipartHttpServletRequest request,
		@RequestParam MultipartFile file, HttpSession session) throws Exception {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String id = auth.getName();

		Member member = memberrepository.findById(id).get();
		List<MemberRoles> roles = memberrolesrepository.findByRole(id);
		MemberRoles memberroles = new MemberRoles();
		memberroles.setRoleName("W");
		roles.add(memberroles);
		member.setGradeCD(new GradeCD("W"));
		member.setRoles(roles);
		
		memberrolesrepository.deleteNormal(member.getId());
				
		Set<GrantedAuthority> authoritySet = new HashSet<GrantedAuthority>(); 
		
		
		authoritySet.add(new SimpleGrantedAuthority(ROLE_PREFIX + "W"));
		
		
		Authentication newAuth = new UsernamePasswordAuthenticationToken(SecurityContextHolder.getContext().getAuthentication().getPrincipal(), "", authoritySet);
	
		System.out.println("roles" + roles);
//		auth = SecurityContextHolder.getContext().getAuthentication();
		System.out.println("권한체크: " + newAuth.getAuthorities());
		SecurityContext securityContext = SecurityContextHolder.getContext(); 
		securityContext.setAuthentication(newAuth);
/*		auth.setAuthenticated(true);*/
		session = request.getSession(); 
		session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);
		
		
		memberrolesrepository.deleteNormal(id);

		memberrepository.save(member);
//		memberrolesrepository.save(memberroles);
		tutorrepository.save(tutor);

		System.out.println("파일전송완료: " + file);
		List<MultipartFile> uploadFileList = request.getFiles("file");
		doUpload(request, model, uploadFileList, tutor, member);

		return "redirect:/index";
	}

	private void doUpload(HttpServletRequest request, Model model, //
			List<MultipartFile> uploadFileList, Tutor tutor, Member member) throws IOException {

		String fileOriginName = "";

		
		//
	
// 221줄 ~ 226줄은 localhost용 파일업로드 방법.		
/*		final DefaultResourceLoader defaultresourceloader = new DefaultResourceLoader();	
		Resource resource = defaultresourceloader
				.getResource("file:src\\main\\resources\\static\\tutorFileUpload\\" + member.getId());

		System.out.println("resource: " + resource); // 파일 저장 위치가 사람마다 다르기 때문에 get resource를 받아와 이용자에 맞는 절대경로로 반환해준다.
		System.out.println("resource 경로: " + resource.getFile().getAbsolutePath());  
		String uploadRootPath = resource.getFile().getAbsolutePath();*/

		
//231번줄은 서버용 파일업로드 방법.
		 String uploadRootPath =  request.getSession().getServletContext().getRealPath("tutorFileUpload/" + member.getId());

		System.out.println(uploadRootPath);		 
		 File file = new File(uploadRootPath);

		
	      if (!file.exists()) {
		         file.mkdirs();
		      }
	      
		System.out.println("file: " + file);
		
//

		File uploadRootDir = new File(uploadRootPath);
		// Create directory if it not exists.
		
		if (!uploadRootDir.exists()) {
			uploadRootDir.mkdirs();
		}

		//
		List<File> uploadedFiles = new ArrayList<File>();
		List<String> failedFiles = new ArrayList<String>();

		for (MultipartFile fileData : uploadFileList) {
			String name = fileData.getOriginalFilename();
			fileOriginName = name;
			// Client File Name
			String sourceFileNameExtension = FilenameUtils.getExtension(fileOriginName).toLowerCase();
				
			String fileSaveName = RandomStringUtils.randomAlphanumeric(32) + "." + sourceFileNameExtension;

			System.out.println("Client File Name = " + name);
			System.out.println("fileSaveName: " + fileSaveName );

			if (fileSaveName != null && fileSaveName.length() > 0) {
				try {
					// Create the file at server
					File serverFile = new File(uploadRootPath + File.separator + fileSaveName);
					/*
					 * File serverFile = new File(uploadRootDir.getCanonicalPath() + File.separator
					 * + fileSaveName); File serverFile = new File(uploadRootDir.getPath() +
					 * File.separator + fileSaveName);
					 */

					BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
					stream.write(fileData.getBytes());
					stream.close();
					//
					uploadedFiles.add(serverFile);
					System.out.println("Write file: " + serverFile);

					TutorUploadFile tutoruploadfile = new TutorUploadFile();
					tutoruploadfile.setTutor(tutor);
					tutoruploadfile.setTutorfileOriginname(fileOriginName);

					tutoruploadfile.setTutorfileUploadPath(uploadRootPath);

					tutoruploadfile.setTutorfileSavename(fileSaveName);
					String fullUrl = uploadRootPath + "/" + fileSaveName;
					tutoruploadfile.setTutorFileFullUrl(fullUrl);
					String partUrl = "/tutorFileUpload/" + member.getId() + "/" + fileSaveName;

					tutoruploadfile.setTutorfilePartUrl(partUrl);
					tutoruploadfile.setMember(member);
					tutoruploadfilerepository.save(tutoruploadfile);

				} catch (Exception e) {
					System.out.println("Error Write file: " + name);
					failedFiles.add(name);
				}
			}
		}

		/* upload/nPGZp1yi55UvwJHOFaza3aKfHE16oD3n.jpg */

		model.addAttribute("uploadedFiles", uploadedFiles);
		model.addAttribute("failedFiles", failedFiles);

	}

	@RequestMapping("/tutor/inquery")
	public String inqueryTutor(Model model) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String id = auth.getName();
		
		Tutor tutor = tutorrepository.findByTutor(id);
		List<TutorUploadFile> tutoruploadfile = tutoruploadfilerepository.findByTutorUploadFile(id);
				
		model.addAttribute("tutor", tutor);
		model.addAttribute("tutoruploadfile", tutoruploadfile);
		
		int tutor_number = tutor.getTutorNumber();
		System.out.println(tutor_number);
		
		List<TutorCareer> selectedtutorcareer = tutorcareerrepository.selectedtutorcareer(tutor_number);
		model.addAttribute("selectedtutorcareer", selectedtutorcareer);
		
		List<TutorInterest> selectedtutorinterest = tutorinterestrepository.selectedtutorinterest(tutor_number);
		model.addAttribute("selectedtutorinterest", selectedtutorinterest);
		
		Member modifyuser = memberrepository.findById(id).get();
		model.addAttribute("mdu",modifyuser);
		
		String tutorEducationCD = tutor.getEducationCD().getCodeValueKorean();
		model.addAttribute("tutorEducationCD", tutorEducationCD);
		
		

		return "tutor/tutor_signupinquery";
	}

	@RequestMapping("/tutor/delete")
	public String inqueryTutor(@RequestParam int id) {

		tutorrepository.deleteById(id);
		return "redirect:/";
	}

	@GetMapping("/tutor/file")
	public String downloadFile(HttpServletRequest request, @RequestParam String tutorFileFullUrl,
			HttpServletResponse response) throws Exception {

		TutorUploadFile tutoruploadfile = tutoruploadfilerepository.findByTutorUploadPreviewFile(tutorFileFullUrl);

		System.out.println(tutorFileFullUrl);
/*		String uploadRootPath = request.getServletContext().getRealPath("/");
		System.out.println("업로드 루트 패쓰" + uploadRootPath);*/
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String id = auth.getName();

// 368번째줄 ~ 370번째줄은 서버용 파일다운로드 방법... 안먹힘...
/*		 String uploadRootPath =  request.getSession().getServletContext().getRealPath("tutorFileUpload/" + id);
		 System.out.println(uploadRootPath);		 
		 File file = new File(uploadRootPath);*/
	
// 372번째줄 ~ 377번째줄은 localhost용 파일다운로드 방법
		final DefaultResourceLoader defaultresourceloader = new DefaultResourceLoader();
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
		//response.
//		previewFile(request, tutorFileFullUrl, response);
		return "tutor/tutor_signupinquery";
	}

	@GetMapping("/tutor/file/delete")
	public String deleteFile(@RequestParam String tutorFileFullUrl, HttpServletRequest request) throws IOException {

		TutorUploadFile tutoruploadfile = tutoruploadfilerepository.findByTutorUploadPreviewFile(tutorFileFullUrl);
		tutoruploadfilerepository.deleteById(tutoruploadfile.getTutorFileId());
		
// 413번째줄 424번째줄은 localhost용 삭제방법.
/*		final DefaultResourceLoader defaultresourceloader = new DefaultResourceLoader();
		Resource resource = defaultresourceloader
				.getResource("file:src\\main\\resources\\static" + tutoruploadfile.getTutorfilePartUrl());
		
		System.out.println("resource: " + resource); // 파일 저장 위치가 사람마다 다르기 때문에 get resource를 받아와 이용자에 맞는 절대경로로 반환해준다.
		System.out.println("resource 경로: " + resource.getFile().getAbsolutePath());
		File file = new File(resource.getFile().getAbsolutePath());*/
		
// 421번째줄 425번째줄은 서버용 파일삭제방법	
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String id = auth.getName();
		String uploadRootPath =  request.getSession().getServletContext().getRealPath("tutorFileUpload/" + id);
		System.out.println(uploadRootPath);		 
		File file = new File(uploadRootPath);


		System.out.println("file: " + file);

		if (file.exists()) {
			if (file.isDirectory()) {
				File[] files = file.listFiles();
				for (int i = 0; i < files.length; i++) {
					if (files[i].delete()) {
						System.out.println(files[i].getName() + "삭제성공");
					} else {
						System.out.println(files[i].getName() + "삭제실패");
					}
				}
			}
			if (file.delete()) {
				System.out.println("파일삭제 성공");
			} else {
				System.out.println("파일삭제 실패");
			}
		} else {
			System.out.println("파일이 존재하지 않습니다.");
		}

		return "redirect:/tutor/inquery";
	}

	@PostMapping("/tutor/delete/inquery")
	public String deleteTutor(@ModelAttribute Tutor tutor) throws IOException {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String id = auth.getName();	
		
		memberrolesrepository.deleteTutorWait(id);	
		tutorrepository.deleteById(tutor.getTutorNumber());
		
		MemberRoles memberroles = new MemberRoles();
		memberroles.setRoleName("N");				
		Member member = memberrepository.findById(id).get();
		List<MemberRoles> roles = memberrolesrepository.findByRole(id);	
		roles.add(memberroles);								
		member.setRoles(roles);
		member.setGradeCD(new GradeCD("N"));
		
		memberrolesrepository.deleteTutorWait(id);
		
		memberrepository.save(member);
	
		System.out.println("강사신청삭제완료!");
		return "redirect:/index";
	}
	
	@GetMapping("/tutor/profile/{typeCode}")
	public String goTutorProfile(@RequestParam String id, @PathVariable("typeCode") String typeCode, PageDTO pageDto, Model model) {
	/*	 */
		/*typeCode = "p";*/
		
		Tutor tutor = tutorrepository.findByTutor(id);
			
		model.addAttribute("tutor", tutor);
		model.addAttribute("tutorEducationCD", tutor.getEducationCD().getCodeValueKorean());

		List<StudyMember> studyActivity = smr.findTutorActivityById(id);
		model.addAttribute("studyActivity", studyActivity);
		
		Member modifyuser = memberrepository.findById(id).get();
		model.addAttribute("mdu",modifyuser);
		
		int tutor_number = tutor.getTutorNumber();
		System.out.println(tutor_number);
		
		List<TutorCareer> selectedtutorcareer = tutorcareerrepository.selectedtutorcareer(tutor_number);
		model.addAttribute("selectedtutorcareer", selectedtutorcareer);
		
		return "tutor/tutor_profile";
		
	}
	

	
	
	   @RequestMapping(value = "/uploadOneFile", method = RequestMethod.GET)
	   public String uploadOneFileHandler(Model model) {
	 
	      MyUploadForm myUploadForm = new MyUploadForm();
	      model.addAttribute("myUploadForm", myUploadForm);
	 
	      return "uploadOneFile";
	   }
	 
	   // POST: Do Upload
	   @RequestMapping(value = "/uploadOneFile", method = RequestMethod.POST)
	   public String uploadOneFileHandlerPOST(HttpServletRequest request, //
	         Model model, //
	         @ModelAttribute("myUploadForm") MyUploadForm myUploadForm) {
	 
	      return this.doUpload2(request, model, myUploadForm);
	 
	   }
	 
	   // GET: Show upload form page.
	   @RequestMapping(value = "/uploadMultiFile", method = RequestMethod.GET)
	   public String uploadMultiFileHandler(Model model) {
	 
	      MyUploadForm myUploadForm = new MyUploadForm();
	      model.addAttribute("myUploadForm", myUploadForm);
	 
	      return "uploadMultiFile";
	   }
	
	
	   @RequestMapping(value = "/uploadMultiFile", method = RequestMethod.POST)
	   public String uploadMultiFileHandlerPOST(HttpServletRequest request, //
	         Model model, //
	         @ModelAttribute("myUploadForm") MyUploadForm myUploadForm) {
	 
	      return this.doUpload2(request, model, myUploadForm);
	 
	   }
	   
	   
	   private String doUpload2(HttpServletRequest request, Model model, //
		         MyUploadForm myUploadForm) {
		 
		      String description = myUploadForm.getDescription();
		      System.out.println("Description: " + description);
		 
		      // Root Directory.
		      String uploadRootPath = request.getServletContext().getRealPath("upload");
		      System.out.println("uploadRootPath=" + uploadRootPath);
		 
		      File uploadRootDir = new File(uploadRootPath);
		      // Create directory if it not exists.
		      if (!uploadRootDir.exists()) {
		         uploadRootDir.mkdirs();
		      }
		      MultipartFile[] fileDatas = myUploadForm.getFileDatas();
		      //
		      List<File> uploadedFiles = new ArrayList<File>();
		      List<String> failedFiles = new ArrayList<String>();
		 
		      for (MultipartFile fileData : fileDatas) {
		 
		         // Client File Name
		         String name = fileData.getOriginalFilename();
		         System.out.println("Client File Name = " + name);
		 
		         if (name != null && name.length() > 0) {
		            try {
		               // Create the file at server
		               File serverFile = new File(uploadRootDir.getAbsolutePath() + File.separator + name);
		 
		               BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
		               stream.write(fileData.getBytes());
		               stream.close();
		               //
		               uploadedFiles.add(serverFile);
		               System.out.println("Write file: " + serverFile);
		            } catch (Exception e) {
		               System.out.println("Error Write file: " + name);
		               failedFiles.add(name);
		            }
		         }
		      }
		      model.addAttribute("description", description);
		      model.addAttribute("uploadedFiles", uploadedFiles);
		      model.addAttribute("failedFiles", failedFiles);
		      return "uploadResult";
		   }
	   
	      public void changeGrade(String authRole ,String id) {

	          Member member = memberrepository.findById(id).get();
	          List<MemberRoles> roles = memberrolesrepository.findByRole(id);
	          MemberRoles memberroles = new MemberRoles();
	          memberroles.setRoleName(authRole);
	          roles.add(memberroles);
	          member.setGradeCD(new GradeCD(authRole));
	          member.setRoles(roles);
	          
	          
/*	          Set<GrantedAuthority> authoritySet = new HashSet<GrantedAuthority>();    
	          authoritySet.add(new SimpleGrantedAuthority(ROLE_PREFIX + authRole ));      
	          Authentication newAuth = new UsernamePasswordAuthenticationToken(SecurityContextHolder.getContext().getAuthentication().getPrincipal(), "", authoritySet);
	       
	          System.out.println("roles" + roles);
//	          auth = SecurityContextHolder.getContext().getAuthentication();
	          System.out.println("권한체크: " + newAuth.getAuthorities());
	          SecurityContext securityContext = SecurityContextHolder.getContext(); 
	          securityContext.setAuthentication(newAuth);*/
	    /*      auth.setAuthenticated(true);*/

	       }
	

}

	



