package org.mohajo.studyrepublic.mypage;
import java.util.List;

import org.mohajo.studyrepublic.domain.Member;
import org.mohajo.studyrepublic.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
/**
 * @author 김준석
 * @since 2019.01.23
 * @version 0.0
 * -
 * 
 * 
 */
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ModifyMemberController {

@RequestMapping("/passwordmodi")
public String pwdmodi(Model model) {
	Authentication auth =SecurityContextHolder.getContext().getAuthentication();
	
	
	return "mypage/password_modify";
	
}



}
