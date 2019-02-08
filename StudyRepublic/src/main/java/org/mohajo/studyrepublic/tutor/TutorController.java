package org.mohajo.studyrepublic.tutor;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.mohajo.studyrepublic.domain.CareerCD;
import org.mohajo.studyrepublic.domain.EducationCD;
import org.mohajo.studyrepublic.domain.GradeCD;
import org.mohajo.studyrepublic.domain.Interest1CD;
import org.mohajo.studyrepublic.domain.Interest2CD;
import org.mohajo.studyrepublic.domain.Member;
import org.mohajo.studyrepublic.domain.MemberRoles;
import org.mohajo.studyrepublic.domain.Tutor;
import org.mohajo.studyrepublic.fileupload.MyUploadForm;
import org.mohajo.studyrepublic.repository.CareerCDRepository;
import org.mohajo.studyrepublic.repository.EducationCDRepository;
import org.mohajo.studyrepublic.repository.Interest1CDRepository;
import org.mohajo.studyrepublic.repository.Interest2CDRepository;
import org.mohajo.studyrepublic.repository.MemberRepository;
import org.mohajo.studyrepublic.repository.MemberRolesRepository;
import org.mohajo.studyrepublic.repository.TutorRepository;
import org.mohajo.studyrepublic.repository.TutorUploadFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;


@Controller
public class TutorController {
	
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
	
	
	@RequestMapping("/tutor")
	public String tutorInfo(Model model) {
		
		List <Tutor> tutor = tutorrepository.findAll();
		model.addAttribute("tutor", tutor);
		
		return "tutor/tutor_info";
		
	}
	
	@RequestMapping("/tutor/signup")
	public String signupTutor(Model model) {
	
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String id = auth.getName();
		model.addAttribute("id", id);
		
		List <CareerCD> careercd = careerCDRepository.findAll();
		model.addAttribute("careercd", careercd);
		
		List <EducationCD> educationcd = educationCDRepository.findAll();
		model.addAttribute("educationcd", educationcd);
		
		List <Interest1CD> interest1cd = interest1CDRepository.findAll();
		model.addAttribute("interest1cd", interest1cd);
		
		List <Interest2CD> interest2cd = interest2CDRepository.findAll();
		model.addAttribute("interest2cd", interest2cd);
		
		List <Interest2CD> Pinterest2cd = interest2CDRepository.Pinterest2List();
		model.addAttribute("pinterest2cd", Pinterest2cd);
		
		List <Interest2CD> Dinterest2cd = interest2CDRepository.Dinterest2List();
		model.addAttribute("dinterest2cd", Dinterest2cd);
		
		List <Interest2CD> Winterest2cd = interest2CDRepository.Winterest2List();
		model.addAttribute("winterest2cd", Winterest2cd);
		
		List <Interest2CD> Ninterest2cd = interest2CDRepository.Ninterest2List();
		model.addAttribute("ninterest2cd", Ninterest2cd);
								
		return "tutor/tutor_signuptutor";
		
	}
	
	
	@RequestMapping("/tutor/insert")
	public String insertTutor(Model model,  @ModelAttribute Tutor tutor, HttpServletRequest request, @RequestPart MultipartFile files)  throws Exception {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String id = auth.getName();
				
		Member member = memberrepository.findById(id).get();
		List<MemberRoles> roles = memberrolesrepository.findByRole(id);
		MemberRoles memberroles = new MemberRoles();
		memberroles.setRoleName("W");
		roles.add(memberroles);
		member.setGradeCD(new GradeCD("W"));
		member.setRoles(roles);	
			
		/* 파일업로드 관련 */
/*		String sourceFileName = files.getOriginalFilename();
		String sourceFileNameExtension = FilenameUtils.getExtension(sourceFileName).toLowerCase();
		File destinationFile;
		String destinationFileName;
		String fileUrl = request.getSession().getServletContext().getRealPath("images");*/	
/*		do {
			destinationFileName = RandomStringUtils.randomAlphanumeric(32)+"."+sourceFileNameExtension;
			destinationFile = new File(fileUrl + destinationFileName);
		} while (destinationFile.exists());
		
		destinationFile.getParentFile().mkdirs();
		files.transferTo(destinationFile);	*/	
		/* 파일업로드 관련 */
		
/*		TutorUploadFile tutoruploadfile = new TutorUploadFile();
		tutoruploadfile.setFileName(sourceFileName);
		tutoruploadfile.setFileOriName(sourceFileNameExtension);
		tutoruploadfile.setFileUrl(fileUrl);
		tutoruploadfile.setTutor_number(tutor.getTutorNumber());
		tutoruploadfilerepository.save(tutoruploadfile);*/
		
		
		

		memberrepository.save(member);
		tutorrepository.save(tutor);
		return "redirect:/";		
	}
	
	
	
/*	@RequestMapping(value = "/uploadForm")
	public void uploadForm() {

	}*/
	
	   // GET: Show upload form page.
	   @RequestMapping(value = "/uploadOneFile", method = RequestMethod.GET)
	   public String uploadOneFileHandler(Model model) {
	 
	      MyUploadForm myUploadForm = new MyUploadForm();
	      model.addAttribute("myUploadForm", myUploadForm);
	 
	      return "uploadtest/uploadOneFile";
	   }
	 
	   // POST: Do Upload
	   @RequestMapping(value = "/uploadOneFile", method = RequestMethod.POST)
	   public String uploadOneFileHandlerPOST(HttpServletRequest request, //
	         Model model, //
	         @ModelAttribute("myUploadForm") MyUploadForm myUploadForm) {
	 
	      return this.doUpload(request, model, myUploadForm);
	 
	   }
	 
	   // GET: Show upload form page.
	   @RequestMapping(value = "/uploadMultiFile", method = RequestMethod.GET)
	   public String uploadMultiFileHandler(Model model) {
	 
	      MyUploadForm myUploadForm = new MyUploadForm();
	      model.addAttribute("myUploadForm", myUploadForm);
	 
	      return "uploadtest/uploadMultiFile";
	   }
	   
	   private String doUpload(HttpServletRequest request, Model model, //
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
		      return "uploadtest/uploadResult";
		   }
		 
		
	
	
/*	@RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
	public String uploadFile(MultipartFile file, Model model) {
		
		System.out.println("originName : " + file.getOriginalFilename());
		System.out.println("size : " + file.getSize());
		System.out.println("contentType:" + file.getContentType());
		
		String savedName = uploadFile(file.getOriginalFilename(), file.getBytes());
		String savedName = 
		model.addAttribute("saveName", savedName);
		
		return "redirect:/index";
	}
	
	private String uploadFile(String originalName, byte[] fileData) throws Exception {
		UUID uid = UUID.randomUUID();
		String savedName = uid.toString() + "_" + originalName;
		File target = new File(uploadPath, savedName);
		FileCopyUtils.copy(fileData,target);
		return savedName;
	}*/
	
	
	@RequestMapping("/tutor/inquery")
	public String inqueryTutor(Model model, @RequestParam String id) {
		
		Tutor tutor = tutorrepository.findByTutor(id);
		model.addAttribute("tutor", tutor); 
		
		return "tutor/tutor_signupinquery";		
	}	
	
	@RequestMapping("/tutor/delete")
	public String inqueryTutor(@RequestParam int id) {
		tutorrepository.deleteById(id);
		return "redirect:/";		
	}	
}
