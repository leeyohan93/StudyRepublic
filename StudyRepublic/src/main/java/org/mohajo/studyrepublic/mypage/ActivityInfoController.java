package org.mohajo.studyrepublic.mypage;
/**
 * @author 김준석
 * @since 2019.01.23
 * @version 0.0
 * -
 * 
 * 
 */

import java.util.List;

import org.mohajo.studyrepublic.domain.Board;
import org.mohajo.studyrepublic.domain.Member;
import org.mohajo.studyrepublic.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ActivityInfoController {
	
/*	@Autowired
	private MemberRepository mbr;
	
	@RequestMapping("/free")
	public String allboard(Model model) {
		Authentication auth =SecurityContextHolder.getContext().getAuthentication();
		String id = auth.getName();
		
		List<Board> allboard = mbr.findAllBoardById(id);
		model.addAttribute("mbr", allboard);
		
		System.out.println(allboard);
		return "mypage/activity_info";
	
	}
*/
}
