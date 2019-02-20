/**
 * 
 */
package org.mohajo.studyrepublic.member;

import java.util.HashMap;
import java.util.Map;

import org.mohajo.studyrepublic.domain.Member;
import org.mohajo.studyrepublic.domain.MemberPoint;
import org.mohajo.studyrepublic.repository.MemberPointRepository;
import org.mohajo.studyrepublic.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;



/**
 * @author 윤성호
 * @since 2019.01.22
 * @version
 * -기능1 추가
 * -기능2 추가
 * -기능3 추가
 */

@Controller
public class payController {

	@Autowired
	MemberRepository memberrepository;
	
	@Autowired
	MemberPointRepository  memberpointrepository;
	
	@RequestMapping("/member/point/charge")
	public String chargePoint(Model model) {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String id = auth.getName();
		
		MemberPoint memberpoint = memberpointrepository.inqueryPoint(id);
		model.addAttribute("memberpoint", memberpoint);
		
		return "member/pointCharge";
		
	}
	
/*	@PostMapping("/member/kakaopay")
	@ResponseBody
	public Map <Object, Object> KakaoPay (@RequestParam String plusPoint, @RequestParam String id, Model model) {
		
		System.out.println("충전 아이디: " + id);
		
		int plus_point = Integer.parseInt(plusPoint);
		
		Map<Object, Object> map = new HashMap <Object, Object> ();
		
		MemberPoint memberpoint = memberpointrepository.inqueryPoint(id);
		
		int afterPoint = memberpoint.getPoint() + plus_point;
		memberpoint.setPoint(afterPoint);
		System.out.println("보유 포인트 : " + memberpoint.getPoint());
		System.out.println("충전 포인트: " + plus_point);
		System.out.println("충전 후 포인트 : " + afterPoint);
		map.put("chargingPoint",afterPoint);
		
		memberpointrepository.save(memberpoint);
		model.addAttribute("afterPoint", memberpoint);
		
		
		return map;
	}*/
	
	@PostMapping("/member/kakaopay")
	@ResponseBody
	public Map <Object, Object> KakaoPay (@RequestParam int plusPoint, @RequestParam String id, Model model) {
		
		System.out.println("충전 아이디: " + id);
		
		/*int plus_point = Integer.parseInt(plusPoint);*/
		
		Map<Object, Object> map = new HashMap <Object, Object> ();
		
		MemberPoint memberpoint = memberpointrepository.inqueryPoint(id);
		
		int afterPoint = memberpoint.getPoint() + plusPoint;
		memberpoint.setPoint(afterPoint);
		System.out.println("보유 포인트 : " + memberpoint.getPoint());
		System.out.println("충전 포인트: " + plusPoint);
		System.out.println("충전 후 포인트 : " + afterPoint);
		map.put("chargingPoint",afterPoint);
		
		memberpointrepository.save(memberpoint);
		model.addAttribute("afterPoint", memberpoint);
		
		
		return map;
	}
	
}
