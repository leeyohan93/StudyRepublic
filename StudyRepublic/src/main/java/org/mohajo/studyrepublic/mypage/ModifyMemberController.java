package org.mohajo.studyrepublic.mypage;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpSession;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.mohajo.studyrepublic.domain.Member;
import org.mohajo.studyrepublic.member.MemberController;
import org.mohajo.studyrepublic.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @author 김준석
 * @since 2019.01.23
 * @version 0.0
 * -
 * 
 * 
 */
@Controller
public class ModifyMemberController {
   
   
   @Autowired
   private MemberRepository mbr;
   
   @Autowired
   private MemberController membercontroller;
   
   BCryptPasswordEncoder bcryptpasswordencoder = new BCryptPasswordEncoder();

@RequestMapping("/mypage/passwordmodi")
public String pwdmodi(Model model) {
   Authentication auth =SecurityContextHolder.getContext().getAuthentication();
   String id = auth.getName();
   
   
   return "mypage/password_modify";
   
}

/*@PostMapping("/completepwdcheck")
@ResponseBody
public Map <Object,Object> passwordcheck(@RequestBody String password){
   
   int pwdcount =0;
   Map<Object,Object> map =new HashMap<Object,Object>();
   
   pwdcount = mbr.pwdcheck(password);
   map.put("pwdcount", pwdcount);
   
   return map;
} */

/*@ResponseBody
@RequestMapping(value ="/emailcheck", method=RequestMethod.POST)
   public String checkemail(HttpServletRequest request,Model model) {
   System.out.println("123");
   String email = request.getParameter("email");
   int rowcount = mbr.checkemail(email);
   return String.valueOf(rowcount);
}*/
//완료버튼 누를때 진행중
/*@PostMapping("/passwordcheck")
@ResponseBody
   public Map<Object, Object> checkPwd(@RequestParam String password){
   
   Authentication auth =SecurityContextHolder.getContext().getAuthentication();
   String id = auth.getName();
   
   int count = 0;
   Map<Object, Object> map = new HashMap<Object, Object>();
   
   System.out.print("잘들어왔어요");

   count = mbr.checkPwd(password);
   map.put("count", count);

   return map;   
}
*/


@RequestMapping("/mypage/memberexit") /*탈퇴버튼 클릭시 회원상태변경*/
public String memberexit(@ModelAttribute Member member) {
   Authentication auth =SecurityContextHolder.getContext().getAuthentication();
   String id = auth.getName();
   member = mbr.findById(id).get();
   
   int exitresult = mbr.memberExit(member.getId());
   
   
   return "redirect:/logout";
}

/*@RequestMapping("/modimember2")
public String membermodiresult(Model model, HttpServletRequest request,@RequestParam("email") String email, @RequestParam("phonenumber") String phonenumber) {
   Authentication auth =SecurityContextHolder.getContext().getAuthentication();
   String id = auth.getName();
   Member member = mbr.findById(id).get();
   
   member.setEmail(email);
   member.setPhonenumber(phonenumber);
   
   
   int membermodeiResult = mbr.saveById(member.getEmail(),member.getPhonenumber(),member.getVisibility(),id);
   mbr.save(member);   
   System.out.println("모디멤버투");
   return "redirect:mypage/member_modify";
}*/

@RequestMapping("/mypage/passwordResult") /*비밀번호 변경하기*/
public String update_password(@RequestParam("password") String password) {
   Authentication auth =SecurityContextHolder.getContext().getAuthentication();
   String id = auth.getName();
   
   Member member =  mbr.findById(id).get();
   
   member.setPassword(password);
   member.setPassword(bcryptpasswordencoder.encode(member.getPassword()));
   mbr.save(member);

   
   return "redirect:/index";

}

@RequestMapping("/mypage/modimember2")//이메일, 폰번호, 공개여부 변경
public String update_email(@RequestParam("email")String email, @RequestParam("phonenumber")String phonenumber,@RequestParam("visibility")int visibility) {
   Authentication auth =SecurityContextHolder.getContext().getAuthentication();
   String id = auth.getName();
   Member member =  mbr.findById(id).get();
   
   member.setEmail(email);
   member.setPhonenumber(phonenumber);
   member.setVisibility(visibility);
   
   mbr.save(member);
  
   return "redirect:/mypage";
   
}

@RequestMapping("/mypage/emailAuth")
@ResponseBody 
public Map <String, Integer> chk_email(@RequestParam String email) {      
   
   int emailCount = 0;
   Map<String, Integer> map = new HashMap<String, Integer>();
   
   emailCount = mbr.checkemail(email);
   
   map.put("emailCount", emailCount);

   return map;   
}
//완료버튼 클릭시 비밀번호 체크하는건데 지금 우선 사용금지 
@RequestMapping(value="/mypage/passwordAuth", method=RequestMethod.POST)
@ResponseBody 
public Map <String, Integer> chk_password(@RequestParam String password) {      
  
   Authentication auth =(Authentication)SecurityContextHolder.getContext().getAuthentication();
   String id = auth.getName();
   Member memberpassword = mbr.findById(id).get();
   bcryptpasswordencoder.matches(password, memberpassword.getPassword()); //회원의 기존 비밀번호를 입력 
   int mypassword = 0;
   Map<String, Integer> map = new HashMap<String, Integer>();
   
   mypassword = mbr.checkpassword(id,password);
   
   map.put("passwordCheck", mypassword);

   return map;   
}



@RequestMapping(value="/mypage/attachments", method=RequestMethod.POST)
public ResponseEntity<?> uploadAttachment(MultipartHttpServletRequest request ,HttpSession session, @RequestPart MultipartFile sourceFile)throws IOException{
   Authentication auth =SecurityContextHolder.getContext().getAuthentication();
   String id = auth.getName();
   
   Member member = mbr.findById(id).get();

   
   
   
   String sourceFileName = sourceFile.getOriginalFilename();
   String sourceFileNameExtension = FilenameUtils.getExtension(sourceFileName).toLowerCase();
   
   String realPath =  request.getSession().getServletContext().getRealPath("member_image/");
   
   
   File saveFile;
   String saveFileName;
   /**
    * 
    * saveFileName -> 저장할 파일명
    * sourceFile.getOriginalFilename() -> 원래파일명
    * */
   System.out.println("realPath:"+realPath);
   do {
      saveFileName = RandomStringUtils.randomAlphanumeric(32)+"."+ sourceFileNameExtension;
      saveFile = new File(realPath + saveFileName);
      System.out.println("filename:"+saveFileName);
      
   }while(saveFile.exists());
   saveFile.getParentFile().mkdirs();
   sourceFile.transferTo(saveFile);
   
   UploadAttachementResponse response = new UploadAttachementResponse();
   response.setFileName(sourceFile.getOriginalFilename());
   response.setFileSize(sourceFile.getSize());
   response.setFileContentType(sourceFile.getContentType());
   response.setAttachmentUrl("http://localhost:8080/member_image/" + saveFileName);
   
   member.setProfileSaveName(saveFileName);
   mbr.save(member);
   
   int uploadResult = mbr.changeProfile(sourceFileName+sourceFileNameExtension, saveFileName, id);
   
   membercontroller.createSession(session, member);
   
   
   return new ResponseEntity<>(response,HttpStatus.OK);
}


	@NoArgsConstructor
	@Data
	private static class UploadAttachementResponse{
		private String fileName;
		private long fileSize;
		private String fileContentType;
		private String attachmentUrl;
	}
	
	@RequestMapping("/mypage/setdefault_img")
	public String defaultimg(Model model) {
		Authentication auth =SecurityContextHolder.getContext().getAuthentication();
		String id = auth.getName();
		
		int uploadResult = mbr.changeProfile("default_img.png", "default_img.png", id);
		
		
		return "redirect:/mypage/modimember";
	}
	
	
	/*회원정보수정페이지 폼만드는중*/
	@RequestMapping("/testmodi")
	public String membermodi(Model model) {
		Authentication auth =SecurityContextHolder.getContext().getAuthentication();
		String id = auth.getName();
		Member modifyuser = mbr.findById(id).get();
		
		model.addAttribute("mdu",modifyuser);
		
		
		return "mypage/testmodify";
		
	}



@RequestMapping(value="/attachments_tutor", method=RequestMethod.POST)
public ResponseEntity<?> uploadAttachment_tutor(MultipartHttpServletRequest request ,HttpSession session, @RequestPart MultipartFile sourceFile)throws IOException{
   Authentication auth =SecurityContextHolder.getContext().getAuthentication();
   String id = auth.getName();
   
   String sourceFileName = sourceFile.getOriginalFilename();
   String sourceFileNameExtension = FilenameUtils.getExtension(sourceFileName).toLowerCase();
   
   String realPath =  request.getSession().getServletContext().getRealPath("member_image/");
   
   
   File saveFile;
   String saveFileName;
   /**
    * 
    * saveFileName -> 저장할 파일명
    * sourceFile.getOriginalFilename() -> 원래파일명
    * */
   System.out.println("realPath:"+realPath);
   do {
      saveFileName = RandomStringUtils.randomAlphanumeric(32)+"."+ sourceFileNameExtension;
      saveFile = new File(realPath + saveFileName);
      System.out.println("filename:"+saveFileName);
      
   }while(saveFile.exists());
   saveFile.getParentFile().mkdirs();
   sourceFile.transferTo(saveFile);
   
   UploadAttachementResponse response = new UploadAttachementResponse();
   response.setFileName(sourceFile.getOriginalFilename());
   response.setFileSize(sourceFile.getSize());
   response.setFileContentType(sourceFile.getContentType());
   
   Member member = mbr.findById(id).get();
   member.setProfileOriginName(sourceFileName);
   member.setProfileSaveName(saveFileName);
   mbr.save(member);
   
   response.setAttachmentUrl("http://localhost:8080/member_image/" + member.getProfileSaveName());
   
   int uploadResult = mbr.changeProfile(sourceFileName+sourceFileNameExtension, saveFileName, id);
   System.out.println("윤성호");
   System.out.println("원본명: " + member.getProfileOriginName());
   System.out.println("파일저장명: " + member.getProfileSaveName());
   
   return new ResponseEntity<>(response,HttpStatus.OK);
}


 


}