package org.mohajo.studyrepublic.mypage;

import java.util.List;

import org.mohajo.studyrepublic.domain.Member;
import org.mohajo.studyrepublic.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author 김준석
 * @since 2019.01.23
 * @version 0.0
 * -
 * 
 * 
 */


@Controller
public class MypageController {
	
	@Autowired
	private MemberRepository mbr;
	
	@RequestMapping("/mypage")
	public String userinfo(Model model) {
		
		/*로그인 상태 유저 정보 가져오기 느낌만 나중에 메소드 추가할때 repository에 명명규칙에 맞게 작성*/
		Member user= mbr.findById("aaa123").get();
		model.addAttribute("mbr",user);
		
		
		
		
		System.out.println(user);
		return "mypage/mypage_main";
	}
}	 

