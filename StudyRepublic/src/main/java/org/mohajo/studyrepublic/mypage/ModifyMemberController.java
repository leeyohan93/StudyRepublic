package org.mohajo.studyrepublic.mypage;
import java.util.HashMap;
import java.util.Map;

import org.mohajo.studyrepublic.domain.Member;
import org.mohajo.studyrepublic.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ModifyMemberController {
	
	
	@Autowired
	private MemberRepository mbr;
	
	BCryptPasswordEncoder bcryptpasswordencoder = new BCryptPasswordEncoder();

@RequestMapping("/passwordmodi")
public String pwdmodi(Model model) {
	Authentication auth =SecurityContextHolder.getContext().getAuthentication();
	String id = auth.getName();
	
	
	return "mypage/password_modify";
	
}

/*@PostMapping("/complatepwdcheck")
@ResponseBody
public Map <Object,Object> passwordcheck(@RequestBody String password){
	
	int pwdcount =0;
	Map<Object,Object> map =new HashMap<Object,Object>();
	
	pwdcount = mbr.pwdcheck(password);
	map.put("pwdcount", pwdcount);
	
	return map;
} */

/*@ResponseBody
@RequestMapping(value ="/emailcheck", method=RequestMethod.POST)
	public String checkemail(HttpServletRequest request,Model model) {
	System.out.println("123");
	String email = request.getParameter("email");
	int rowcount = mbr.checkemail(email);
	return String.valueOf(rowcount);
}*/
//완료버튼 누를때 진행중
/*@PostMapping("/passwordcheck")
@ResponseBody
	public Map<Object, Object> checkPwd(@RequestParam String password){
	
	Authentication auth =SecurityContextHolder.getContext().getAuthentication();
	String id = auth.getName();
	
	int count = 0;
	Map<Object, Object> map = new HashMap<Object, Object>();
	
	System.out.print("잘들어왔어요");

	count = mbr.checkPwd(password);
	map.put("count", count);

	return map;	
}
*/


@RequestMapping("/memberexit") /*탈퇴버튼 클릭시 회원상태변경*/
public String memberexit(@ModelAttribute Member member) {
	Authentication auth =SecurityContextHolder.getContext().getAuthentication();
	String id = auth.getName();
	member = mbr.findById(id).get();
	
	int exitresult = mbr.memberExit(member.getId());
	
	
	System.out.println(123);
	
	return "redirect:index";
}

/*@RequestMapping("/modimember2")
public String membermodiresult(Model model, HttpServletRequest request,@RequestParam("email") String email, @RequestParam("phonenumber") String phonenumber) {
	Authentication auth =SecurityContextHolder.getContext().getAuthentication();
	String id = auth.getName();
	Member member = mbr.findById(id).get();
	
	member.setEmail(email);
	member.setPhonenumber(phonenumber);
	
	
	int membermodeiResult = mbr.saveById(member.getEmail(),member.getPhonenumber(),member.getVisibility(),id);
	mbr.save(member);	
	System.out.println("모디멤버투");
	return "redirect:mypage/member_modify";
}*/

@RequestMapping("/passwordResult") /*비밀번호 변경하기*/
public String update_password(@RequestParam("password") String password) {
	Authentication auth =SecurityContextHolder.getContext().getAuthentication();
	String id = auth.getName();
	Member member =  mbr.findById(id).get();
	
	
	member.setPassword(password);
	member.setPassword(bcryptpasswordencoder.encode(member.getPassword()));
	mbr.save(member);

	
	return "redirect:index";

}

@RequestMapping("/modimember2")//이메일, 폰번호, 공개여부 변경
public String update_email(@RequestParam("email")String email, @RequestParam("phonenumber")String phonenumber,@RequestParam("visibility")int visibility) {
	Authentication auth =SecurityContextHolder.getContext().getAuthentication();
	String id = auth.getName();
	Member member =  mbr.findById(id).get();
	
	member.setEmail(email);
	member.setPhonenumber(phonenumber);
	member.setVisibility(visibility);
	
	mbr.save(member);
	System.out.println(777);
	return "redirect:mypage";
	
}

@RequestMapping("/emailAuth")
@ResponseBody 
public Map <String, Integer> chk_email(@RequestParam String email) {		
	
	int emailCount = 0;
	Map<String, Integer> map = new HashMap<String, Integer>();
	
	emailCount = mbr.checkemail(email);
	
	map.put("emailCount", emailCount);

	return map;	
}
//완료버튼 클릭시 비밀번호 체크하는건데 지금 우선 사용금지 
@RequestMapping(value="/passwordAuth", method=RequestMethod.POST)
@ResponseBody 
public Map <String, Integer> chk_password(@RequestParam String password) {		
	System.out.println(password);
	Authentication auth =SecurityContextHolder.getContext().getAuthentication();
	String id = auth.getName();
	
	int mypassword = 0;
	Map<String, Integer> map = new HashMap<String, Integer>();
	
	mypassword = mbr.checkpassword(id,password);
	
	map.put("passwordCheck", mypassword);

	return map;	
}


	
	





}
