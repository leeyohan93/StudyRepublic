package org.mohajo.studyrepublic.member;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.mohajo.studyrepublic.domain.Member;
import org.mohajo.studyrepublic.domain.MemberPoint;
import org.mohajo.studyrepublic.domain.StudyMember;
import org.mohajo.studyrepublic.repository.MemberPointRepository;
import org.mohajo.studyrepublic.repository.MemberRepository;
import org.mohajo.studyrepublic.security.MemberSecurity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import net.nurigo.java_sdk.api.Message;
import net.nurigo.java_sdk.exceptions.CoolsmsException;

@Controller

public class MemberController {

	
	@Autowired
	private MemberRepository memberrepository;
	
	@Autowired
	private MemberPointRepository memberpointrepository;
		
	BCryptPasswordEncoder bcryptpasswordencoder = new BCryptPasswordEncoder();
	
	@RequestMapping("/member")
	public String memberInfo (Model model) {
		List<Member> member = memberrepository.findAll();	// findAll()은 전체 테이블 조회함을 의미.
		model.addAttribute("member", member);
	//	System.out.println(member);
		return "/member/member_info";	
	}
	
	@RequestMapping("/member/signup")
	public String signup (Model model) {
		return "signup/signup_signup";	
	}
	
/*	@ResponseBody
	@PostMapping(value = "/member/checkid", produces="text/plain;charset=UTF-8")
	public String checkId(@RequestParam String id) {		
		int checkResult = memberrepository.checkId(id);
		System.out.println(id);
		return String.valueOf(checkResult);	
	}*/
	
	
/*	@PostMapping(value = "/member/checkid")
	@ResponseBody
	public int checkId(@RequestParam String id) {		
		int checkResult = memberrepository.checkId(id);
		System.out.println(id);
		return checkResult;	
	}*/
	
	@PostMapping("/member/checkid")
	@ResponseBody
	public Map <Object, Object> checkId(@RequestParam String id) {		
		
		int count = 0;
		Map<Object, Object> map = new HashMap<Object, Object>();
		
		count = memberrepository.checkId(id);
		map.put("count", count);

		return map;	
	}
	
	@PostMapping("/member/checknick")
	@ResponseBody
	public Map <Object, Object> checkNick(@RequestParam String nickname) {		
		
		int nickCount = 0;
		Map<Object, Object> map = new HashMap<Object, Object>();
		
		nickCount = memberrepository.checkNick(nickname);
		map.put("nickCount", nickCount);

		return map;	
	}
	
	
	
	@RequestMapping("/member/insert")
	public String insertMember(Model model, HttpServletRequest request, @ModelAttribute Member member, @RequestParam String id) {
		
		member.setPassword(bcryptpasswordencoder.encode(member.getPassword()));
				
		memberrepository.save(member);
		
		MemberPoint memberpoint = new MemberPoint();
		memberpoint.setMember(member);
		memberpoint.setPoint(0);
		
		memberpointrepository.save(memberpoint);
		
		return "redirect:/index";		
	}
	
	@RequestMapping("/member/inquery")					// 회원 한명 한명 조회.
	public String inquery(Model model, @RequestParam("id") String id) {
			Member member = memberrepository.findById(id).get();		// findById(id).get() = select * from member where id = id와 동일.	
			model.addAttribute("member", member);
			System.out.println(member);		
			return "member/member_inquery";
	}
	
	@RequestMapping("/member/update")				// 수정할 정보를 뿌려줌. // 현재는 비밀번호만 수정 가능하게 해놓음 ㅎ.
	public String update(Model model, @RequestParam("id") String id) {				
		Member member = memberrepository.findById(id).get();		//findById(id).get() = select * from member where id = id와 동일.	
		model.addAttribute("member", member);	
		return "member/member_update";				// 입력한 값을 저장.

	}
	
	
	@RequestMapping("/member/updateAction")		// 수정정보를 업데이트함.
	public String update_action(@RequestParam("id") String id, @RequestParam("password") String password, @ModelAttribute Member member) {
		member =  memberrepository.findById(id).get(); 	
		member.setPassword(bcryptpasswordencoder.encode(password));	
		memberrepository.save(member);
		return "redirect:/index";
	}
	
	
	
	@RequestMapping("/member/delete")		// 회원 삭제.
	public String delete(@RequestParam("id") String id) {	
		memberrepository.deleteById(id);					// delete from member where id = id와 동일.
		System.out.println("-------------------- id: " + id + " 삭제 완료. ------------------");
		return "redirect:/index";
	}
	
	@Transactional(readOnly = true)
	@RequestMapping("/admin") 
	public String forAdmin(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String id = auth.getName();
		model.addAttribute("id", id);
		System.out.println(auth);
		System.out.println(id);
		return "etc/admin";
	}
	
	@RequestMapping("/login")
	public String login(HttpServletRequest request) {
		return "member/login";
	}
		
	@RequestMapping ("/logout")
	public void logout() {
	}
	
	
	
	@RequestMapping("/accessDenied")
	public String accessDenied() {
		return "etc/accessDenied";
	}
	
/*	@RequestMapping("/")
	public String loggedIn() {
		return "index";
	}*/
	
	@RequestMapping("/testjido")
	public String jusoTest() {
		return "test";
	}

	@RequestMapping("/signup")
	public String signupTest() {
		return "sign_up";
	}
	
/*	public Member member (String id) {
		
		Member member = memberrepository.findById(id).get();
		System.out.println("멤버레퍼짓토리" + memberrepository);
		
		return member;
	}
	*/
	
	@PostMapping("/member/findPassword")
	@ResponseBody
	public Map <Object, Object> findPassword(@RequestParam String messageNumber, @RequestParam String phonenumber, Model model) {		
		
		int randomKey = (int)((Math.random()*900000))+100000;	// 6자리 난수 생성
		messageNumber = Integer.toString(randomKey);
		
	    String api_key = "NCSYDZXO7QQ6LVKH";
	    String api_secret = "FPBMC3GJHNDBUTOXR27TA9A4PR1CJH3D";
	    
	    Message coolsms = new Message(api_key, api_secret);
	    
	    System.out.println("PhoneNumber: " + phonenumber);

	    HashMap<String, String> params = new HashMap<String, String>();
	    params.put("to", phonenumber);
	    params.put("from", "01025791441");
	    params.put("type", "SMS");
	    params.put("text", randomKey + " 를 입력해주세요.");
	    params.put("text", "Study Republic 본인인증: 인증란에 인증번호 [" + randomKey + "] 를 입력해주세요.");
	    params.put("app_version", "JAVA SDK v1.2"); // application name and version
	    

	    try {
	        JSONObject obj = (JSONObject) coolsms.send(params);
	        System.out.println(obj.toString());
	      } catch (CoolsmsException e) {
	        System.out.println(e.getMessage());
	        System.out.println(e.getCode());
	      }

		model.addAttribute("messageNumber", messageNumber);
		System.out.println("messageNumber: " + messageNumber);
		
		
		Map<Object, Object> map = new HashMap<Object, Object>();
		

		map.put("messageNumber", messageNumber);

		return map;	
	}
	
	@RequestMapping(value="/member/successAuth")
	public String moveModifyPassword(@RequestParam String id ,Model model) {
		
		model.addAttribute("id", id);
		
		return "member/member_modifypassword";
	}
	
	@PostMapping(value = "/member/modifyPassword")
	public String afterAuth_ModifyPassword(@RequestParam String id,@RequestParam String password) {
		
		
		Member member = memberrepository.findById(id).get();
		member.setPassword(bcryptpasswordencoder.encode(password));	
		memberrepository.save(member);
		
		return "redirect:/index";
	}
	
	@RequestMapping(value = "/socialLogin")
	String socialLogin() {
		return "sign_up";
	}
	

	
	

	   
	   public void getSession(Authentication auth, HttpSession session, Member member) {
		      if(auth!=null && session.getAttribute("userId")==null) {
		         
		    	 MemberSecurity sc = (MemberSecurity)auth.getPrincipal();
		         
		         String id =sc.getUsername();
		         
		        member = memberrepository.findById(id).get();
		         
		         session.setAttribute("nickname", member.getNickname());
		         session.setAttribute("memberimg", member.getProfileSaveName());
		      
		      }
		   }
	   
	   public void getSession_Study(Authentication auth, HttpSession session, List<StudyMember> studymember) {
		      if(auth!=null && session.getAttribute("userId")==null) {
		         
		    	
		  		int listSize = studymember.size();
				
				for(int i = 0; i < listSize; i++) {
					System.out.println("스터디 제목: " + studymember.get(i).getStudy().getName());
				}
				
				for(int i = 0; i < listSize; i++) {
					System.out.println("스터디 아이디: " + studymember.get(i).getStudy().getStudyId());
				}
				
				
				List <String> studyNameList = new ArrayList<>(listSize+1);
				
				studyNameList.add("참여중인 스터디");
				
				for(int i = 1; i < listSize + 1; i++) {
					studyNameList.add(i, studymember.get(i-1).getStudy().getName());
				}
				
				
				
				
				List <String> studyIdList = new ArrayList<>(); 
				
				studyIdList.add("#");
				
				for(int i = 1; i < listSize+1; i++) {
					studyIdList.add(i, studymember.get(i-1).getStudy().getStudyId());
				}
		    	  
		         session.setAttribute("studyNameList", studyNameList);
		         session.setAttribute("studyIdList",studyIdList);
		         
		         
		      
		      }
		   }
	   
	   public void memberp() {
		   System.out.println("되라 - " + memberrepository);
	   }
	   
	   

	
	
}
