package org.mohajo.studyrepublic.tutor;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.mohajo.studyrepublic.domain.CareerCD;
import org.mohajo.studyrepublic.domain.EducationCD;
import org.mohajo.studyrepublic.domain.GradeCD;
import org.mohajo.studyrepublic.domain.Interest1CD;
import org.mohajo.studyrepublic.domain.Interest2CD;
import org.mohajo.studyrepublic.domain.Member;
import org.mohajo.studyrepublic.domain.MemberRoles;
import org.mohajo.studyrepublic.domain.Tutor;
import org.mohajo.studyrepublic.domain.TutorUploadFile;
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
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
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
		
	      MyUploadForm myUploadForm = new MyUploadForm();
	      model.addAttribute("myUploadForm", myUploadForm);
		
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
	public String insertTutor(Model model,  @ModelAttribute Tutor tutor, MultipartHttpServletRequest request, @RequestParam MultipartFile file)  throws Exception {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String id = auth.getName();
				
		Member member = memberrepository.findById(id).get();
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
		doUpload(request, model, uploadFileList, tutor, member);
			
		return "redirect:/index";		
	}

	 
	   private void doUpload(HttpServletRequest request, Model model, //
			   List<MultipartFile> uploadFileList, Tutor tutor, Member member) {
		 
		   	  String fileOriginName = "";

		      // Root Directory.
		      String uploadRootPath = request.getServletContext().getRealPath("upload");
		   	/*String uploadRootPath = request.getServletPath();*/
		      System.out.println("uploadRootPath=" + uploadRootPath);
		 
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
				     String fileSaveName = RandomStringUtils.randomAlphanumeric(32)+"."+sourceFileNameExtension;

		         
		         System.out.println("Client File Name = " + name);
		 
		         if (fileSaveName != null && fileSaveName.length() > 0) {
		            try {
		               // Create the file at server
		               File serverFile = new File(uploadRootDir.getAbsolutePath()+ File.separator + fileSaveName);
		            //   File serverFile = new File(uploadRootDir.getCanonicalPath() + File.separator + fileSaveName);
		           //    File serverFile = new File(uploadRootDir.getPath() + File.separator + fileSaveName);
		 
		               BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
		               stream.write(fileData.getBytes());
		               stream.close();
		               //
		               uploadedFiles.add(serverFile);
		               System.out.println("Write file: " + serverFile);
		               
		 		      TutorUploadFile tutoruploadfile = new TutorUploadFile();
				      tutoruploadfile.setTutor(tutor);
				      tutoruploadfile.setTutorfileOriginname(fileOriginName);	      
				         
	      
				      tutoruploadfile.setTutorfileSavename(fileSaveName);      
/*				      String fullUrl = uploadRootPath+"\\"+fileSaveName;
				      tutoruploadfile.setTutorfileUrl(fullUrl);	 */
				      String partUrl = "\\upload\\"+fileSaveName;
				      tutoruploadfile.setTutorfileUrl(partUrl);	
				      tutoruploadfile.setMember(member);
				      tutoruploadfilerepository.save(tutoruploadfile);
		               		      
		               
		            } catch (Exception e) {
		               System.out.println("Error Write file: " + name);
		               failedFiles.add(name);
		            }
		         }
		      }
		      
		      /*upload/nPGZp1yi55UvwJHOFaza3aKfHE16oD3n.jpg*/
		      	 	   	     
		      model.addAttribute("uploadedFiles", uploadedFiles);
		      model.addAttribute("failedFiles", failedFiles);
	
	   }
	   
	  public void downloadFile(TutorUploadFile tutoruploadfile, HttpServletRequest request, HttpServletResponse response) throws Exception {
		  
		  File file = new File(tutoruploadfile.getTutorfileUrl(),tutoruploadfile.getTutorfileOriginname());
		  
		  BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));
		  
		  String header = request.getHeader("User-Agent");
		  String fileName;
	/*	  
		    if ((header.contains("MSIE")) || (header.contains("Trident")) || (header.contains("Edge"))) {
		        //인터넷 익스플로러 10이하 버전, 11버전, 엣지에서 인코딩 
		        fileName = URLEncoder.encode(boardfile.getFileOrgName(), "UTF-8");
		    } else {
		        //나머지 브라우저에서 인코딩
		        fileName = new String(boardfile.getFileOrgName().getBytes("UTF-8"), "iso-8859-1");
		    }
		    //형식을 모르는 파일첨부용 contentType
		    response.setContentType("application/octet-stream");
		    //다운로드와 다운로드될 파일이름
		    response.setHeader("Content-Disposition", "attachment; filename=\""+ fileName + "\"");
		    //파일복사
		    FileCopyUtils.copy(in, response.getOutputStream());
		    in.close();
		    response.getOutputStream().flush();
		    response.getOutputStream().close();*/
		  	  
	  }
	   
	
	@RequestMapping("/tutor/inquery")
	public String inqueryTutor(Model model) {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String id = auth.getName();
		
/*		Tutor tutor = tutorrepository.findByTutor(id);*/
/*		model.addAttribute("tutor", tutor); */
		
		List <TutorUploadFile> tutoruploadfile = tutoruploadfilerepository.findByTutorUploadFile(id);
		
/*		String tutorfileName = tutoruploadfile.
		String tutorfileUrl = tutoruploadfile.getTutorfileUrl();*/
		
		model.addAttribute("tutoruploadfile", tutoruploadfile);			
		
		return "tutor/tutor_signupinquery";		
	}	
	
	@RequestMapping("/tutor/delete")
	public String inqueryTutor(@RequestParam int id) {
		
		tutorrepository.deleteById(id);
		return "redirect:/";		
	}	
	
	
	public void previewFile(HttpServletRequest request, @RequestParam String tutorfileUrl, HttpServletResponse response) throws Exception {
		
		TutorUploadFile tutoruploadfile = tutoruploadfilerepository.findByTutorUploadPreviewFile(tutorfileUrl);
		
		File file = new File(tutorfileUrl, tutoruploadfile.getTutorfileSavename());
		
		 BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));
		 
		 String header = request.getHeader("User-Agent");
		 String fileName;
		 
		 if ((header.contains("MSIE")) || (header.contains("Trident")) || (header.contains("Edge"))) {
			 fileName = URLEncoder.encode(tutoruploadfile.getTutorfileOriginname(), "UTF-8");
			  } else {
				  fileName = new String(tutoruploadfile.getTutorfileOriginname().getBytes("UTF-8"), "iso-8859-1");	  
			  }
		 response.setContentType("application/octet-stream");
		 response.setHeader("Content-Disposition", "attachment; filename=\""+fileName+"\"");
		 FileCopyUtils.copy(in, response.getOutputStream());	
		 in.close();
		 response.getOutputStream().flush();
		 response.getOutputStream().close();
		 }	
	
	@GetMapping("/tutor/file")
	public String downloadFile(HttpServletRequest request, @RequestParam String tutorfileUrl, HttpServletResponse response) throws Exception {
		previewFile(request, tutorfileUrl, response);
		return "redirect:/tutor/inquery";
	}
	
}
