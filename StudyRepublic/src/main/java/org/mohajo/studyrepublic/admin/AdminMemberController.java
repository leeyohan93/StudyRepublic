package org.mohajo.studyrepublic.admin;

import java.util.List;

import org.mohajo.studyrepublic.domain.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author 이요한
 * @since 2019.01.22
 * @version
 * -기능1 추가
 * -기능2 추가
 * -기능3 추가
 * 
 */
@Controller
@RequestMapping("/adminPage/member")
public class AdminMemberController {
	
	@Autowired
	AdminMemberService adminMemberService;
	
	
	
	@RequestMapping("/list")
	public String list(Model model) {
		
		model.addAttribute("memberList", adminMemberService.getMemberList());

		return "/adminPage/member/list";
	}

}
