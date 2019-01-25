package org.mohajo.studyrepublic.member;

import java.util.List;

import org.mohajo.studyrepublic.domain.Member;
import org.mohajo.studyrepublic.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MemberController {

	
	@Autowired
	private MemberRepository memberrepository;
	
	@RequestMapping("/member")
	public String view (Model model) {
		List<Member> member = memberrepository.findAll();	// findAll()은 전체 테이블 조회함을 의미.
		model.addAttribute("member", member);
		System.out.println(member);
		return "member/member_info";	
	}
}
