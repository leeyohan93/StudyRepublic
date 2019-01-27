package org.mohajo.studyrepublic.member;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.mohajo.studyrepublic.domain.InterestLocation;
import org.mohajo.studyrepublic.domain.Member;
import org.mohajo.studyrepublic.domain.MemberRoles;
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
	
	@RequestMapping("/memberAll")
	public String memberInfo (Model model) {
		List<Member> member = memberrepository.findAll();	// findAll()은 전체 테이블 조회함을 의미.
		model.addAttribute("member", member);
	//	System.out.println(member);
		return "/member/member_info";	
	}
	
	@RequestMapping("/signup")
	public String signup (Model model) {
		return "signup/signup_signup";	
	}
	
	@RequestMapping("/insertMember")
	public String insertMember(Model model, HttpServletRequest request, @ModelAttribute Member member) {
		member.setPassword(bcryptpasswordencoder.encode(member.getPassword()));
/*		String interestlocation1 = request.getParameter("interestlocation1");*/
/*		member.setInterestlocation(interestlocation1); */
/*		member.setInterestlocation(member.getInterestlocation());*/
		
/*		public void setIntereslocation(String location) {		// set메서드 재정의함.N으로 초기값 주기 위함.	// 등급이 업데이트 되면 어떻게될까.. 등급을 업데이트 하는 것인가 같은 아이디에 등급을 하나 더 추가하는 것인가.. securityConfig에서 hasRole을 두 개 다 가질 수 있을 것인가.... 
			InterestLocation il = new InterestLocation();
			il.setInterestLocation(location);
			this.interestlocation.add(il);
		}*/
		
		memberrepository.save(member);	
		return "redirect:/memberAll/";		
	}
	
	@RequestMapping("/memberInquery")					// 회원 한명 한명 조회.
	public String inquery(Model model, @RequestParam("id") String id) {
			Member member = memberrepository.findById(id).get();		// findById(id).get() = select * from member where id = id와 동일.	
			model.addAttribute("member", member);
			System.out.println(member);		
			return "member/member_inquery";
	}
	
	@RequestMapping("/memberUpdate")				// 수정할 정보를 뿌려줌. // 현재는 비밀번호만 수정 가능하게 해놓음 ㅎ.
	public String update(Model model, @RequestParam("id") String id) {		
		Member member = memberrepository.findById(id).get();		//findById(id).get() = select * from member where id = id와 동일.	
		model.addAttribute("member", member);	
		return "member/member_update";				// 입력한 값을 저장.

	}
	
	@RequestMapping("/memberUpdateAction")		// 수정정보를 업데이트함.
	public String updateAction(@RequestParam("id") String id, @RequestParam("password") String password) {
		Member member =  memberrepository.findById(id).get(); 	
	
		member.setPassword(bcryptpasswordencoder.encode(password));	
		memberrepository.save(member);
		return "redirect:/memberAll/";

	}
	
	@RequestMapping("/memberDelete")		// 회원 삭제.
	public String delete(@RequestParam("id") String id) {	
		memberrepository.deleteById(id);					// delete from member where id = id와 동일.
		System.out.println("-------------------- id: " + id + " 삭제 완료. ------------------");
		return "redirect:/memberAll/";
	}
	
	@RequestMapping("/admin") 
	public String forAdmin(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String id = auth.getName();
		model.addAttribute("id", id);
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
