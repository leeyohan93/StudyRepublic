package org.mohajo.studyrepublic.member;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.mohajo.studyrepublic.domain.Member;
import org.mohajo.studyrepublic.domain.MemberPoint;
import org.mohajo.studyrepublic.repository.MemberPointRepository;
import org.mohajo.studyrepublic.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

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
	public String login() {
		return "member/login";
	}
	
	@RequestMapping("/login2")
	public String login2() {
		return "member/login2";
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
	
	

	
}
