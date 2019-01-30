package org.mohajo.studyrepublic.member;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.mohajo.studyrepublic.domain.Member;
import org.mohajo.studyrepublic.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MemberController {

	
	@Autowired
	private MemberRepository memberrepository;
		
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
	
	@RequestMapping("/member/insert")
	public String insertMember(Model model, HttpServletRequest request, @ModelAttribute Member member) {
		member.setPassword(bcryptpasswordencoder.encode(member.getPassword()));	
		memberrepository.save(member);	
		return "redirect:/member/";		
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
		return "index";
	}
	
	
	
	@RequestMapping("/member/delete")		// 회원 삭제.
	public String delete(@RequestParam("id") String id) {	
		memberrepository.deleteById(id);					// delete from member where id = id와 동일.
		System.out.println("-------------------- id: " + id + " 삭제 완료. ------------------");
		return "redirect:/member/";
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
	
	@RequestMapping ("/logout")
	public void logout() {
	}
	
	
	
	@RequestMapping("/accessDenied")
	public String accessDenied() {
		return "etc/accessDenied";
	}
	
	@RequestMapping("/")
	public String loggedIn() {
		return "index";
	}
	
	@RequestMapping("/testjido")
	public String jusoTest() {
		return "test";
	}
	
	
	

	
}
