package org.mohajo.studyrepublic.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.maven.shared.invoker.SystemOutHandler;
import org.mohajo.studyrepublic.domain.Member;
import org.mohajo.studyrepublic.domain.MemberRoles;
import org.mohajo.studyrepublic.domain.SendMessage;
import org.mohajo.studyrepublic.domain.Tutor;
import org.mohajo.studyrepublic.repository.MemberRepository;
import org.mohajo.studyrepublic.repository.MemberRolesRepository;
import org.mohajo.studyrepublic.repository.SendMessageRepository;
import org.mohajo.studyrepublic.repository.TutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
	@Autowired
	SendMessageRepository sendMessageRepository;
	@Autowired
	MemberRepository memberRepository;
	@Autowired
	MemberRolesRepository memberRoleRepository;
	@Autowired
	TutorRepository tutorRepository;
	
	
	BCryptPasswordEncoder bcryptpasswordencoder = new BCryptPasswordEncoder();
	
	@RequestMapping("/list")
	public String list(Model model) {
		model.addAttribute("memberList", adminMemberService.getMemberList());
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    model.addAttribute("adminId",auth.getName());
		return "/adminPage/member/list";
	}
	
	@RequestMapping("/changePassword")
	public String changePassword(Model model,String memberId,String[] memberEmail,String memberPhonenumber,HttpServletRequest request) {
		String subject="Study Republic 비밀번호 초기화 확인 메일입니다";
		String text="비밀번호는 회원님의 휴대전화번호로 초기화 되었습니다";
		adminMemberService.sendEmailMessage(memberEmail,subject,text);

//		adminMemberService.changePassword(memberId,memberPhonenumber);
		adminMemberService.changePassword(memberId,bcryptpasswordencoder.encode(memberPhonenumber));
//		return "redirect:/adminPage/member/list";
		return "redirect:"+request.getHeader("Referer");

	}
	
	@RequestMapping(value = "/message")
	public String message(Model model,String[] selectedId,String sendId,String messageContent,HttpServletRequest request) {
		for(String receiveId : selectedId) {
			SendMessage sendmessage = new SendMessage();
			sendmessage.setSendId(sendId);
			sendmessage.setReceiveId(receiveId);
			sendmessage.setMessageContent(messageContent);
			sendMessageRepository.save(sendmessage);
		}
		return "redirect:"+request.getHeader("Referer");
	}
	
	@RequestMapping(value = "/unpause")
	public String unpause(Model model,String[] selectedId, String[] selectedEmail,HttpServletRequest request) {
		String subject="당신의 활동을 허하노라";
		String text="하위";
		adminMemberService.sendEmailMessage(selectedEmail,subject,text);
		List<Member> list = adminMemberService.unpauseMember(selectedId);
//		return "redirect:/adminPage/member/list";
		return "redirect:"+request.getHeader("Referer");
	}
	
	@RequestMapping("/changeGrade")
	public String changeGrade(Model model,String changeGrade, String[] selectedId,HttpServletRequest request) {
		 	
		for(String receiveId : selectedId) {
	 	
	// 권한테이블에 등급추가.	
		MemberRoles memberroles = new MemberRoles();
		memberroles.setRoleName(changeGrade);				
		List<MemberRoles> roles = memberRoleRepository.findByRole(receiveId);	
		roles.add(memberroles);	
		Member member = memberRepository.findById(receiveId).get();
		member.setRoles(roles);
		
		System.out.println("-- 등급 : " + member.getGradeCD().getGradeCode());
		
		System.out.println((member.getGradeCD().getGradeCode().equals("N")));
		
	// T나 W로 권한 변경 시 default 값으로 tutor 테이블에 데이터를 생성한다. 
		Tutor tutor= new Tutor();
		
		
	switch (changeGrade) {
	
	// T나 W로 권한 변경 시 default 값으로 tutor 테이블에 데이터를 생성한다. 	
	
	case "T":
		if(member.getGradeCD().getGradeCode().equals("W")) {
		// 권한테이블에 W권한 삭제.	
		memberRoleRepository.deleteTutorWait(receiveId);	
		}
		
		else if(member.getGradeCD().getGradeCode().equals("N")) {
		// 권한테이블에 N권한 삭제.	
		memberRoleRepository.deleteNormal(receiveId);
			
		// tutor 테이블에 tutor 등록.
	
		tutor.setMember(member);
		tutorRepository.save(tutor);
		}
		break;
	
	case "W":
		if(member.getGradeCD().getGradeCode().equals("T")) {	// 현재신분이 강사인 사람들은 강사을삭제한다. (등급하락)
		// 권한테이블에서 T권한 삭제.	
			memberRoleRepository.deleteTutor(receiveId);	
		}
		else if (member.getGradeCD().getGradeCode().equals("N")) {	// 현재신분이 일반인 사람들은 일반권한을 삭제하고 W권한을 부여한다.
		// 권한테이블에서 N권한 삭제.				
			memberRoleRepository.deleteNormal(receiveId);	
			tutor.setMember(member);
			tutorRepository.save(tutor);
		}
		break;
		
	
	case "N":
				
		Tutor DeletedTutor = tutorRepository.findByTutor(receiveId);
		tutorRepository.deleteById(DeletedTutor.getTutorNumber()); // 강사정보 삭제		
		
		if(member.getGradeCD().getGradeCode().equals("T")) {			
			memberRoleRepository.deleteTutor(receiveId);					
		} 
		else if(member.getGradeCD().getGradeCode().equals("W")) {
			memberRoleRepository.deleteTutorWait(receiveId);
		}
		break;
		
	default:
		
		break;
		}		
		memberRepository.save(member);	
		}
		
		 adminMemberService.changeGrade(selectedId,changeGrade);
		 	 
		 return "redirect:"+request.getHeader("Referer");
	}
	
	@RequestMapping("/pause")
	public String pauseMember(Model model,String[] selectedId,String[] selectedEmail,String subject,String text,HttpServletRequest request) {
		adminMemberService.sendEmailMessage(selectedEmail,subject,text);
		adminMemberService.pauseMember(selectedId);
//		return "redirect:/adminPage/member/list";
		return "redirect:"+request.getHeader("Referer");
	}
	
	@RequestMapping("/exit")
	public String exitMember(Model model,String[] selectedId,String[] selectedEmail,String subject,String text,HttpServletRequest request) {
		adminMemberService.sendEmailMessage(selectedEmail,subject,text);
		adminMemberService.exitMember(selectedId);
//		return "redirect:/adminPage/member/list";
		return "redirect:"+request.getHeader("Referer");
	}
	
	@RequestMapping("/search")
	public String search(Model model,String[] grade,String[] status,String searchKey,String searchValue){
		model.addAttribute("memberList",adminMemberService.getSearchMember(grade, status, searchKey, searchValue));
		return "/adminPage/member/list";
	}

	
	
	//ajax 사용 했으나 paging ajax와 맞물려서 다시 redirect로 수정 함...
	/*@ResponseBody
	@RequestMapping(value = "/unpause", method = RequestMethod.POST)
	public List<Member> unpause(Model model,String[] selectedId, String[] selectedEmail) {
		String subject="당신의 활동을 허하노라";
		String text="하위";
		adminMemberService.sendEmailMessage(selectedEmail,subject,text);
		List<Member> list = adminMemberService.unpauseMember(selectedId);
		return list;
	}
	
	@ResponseBody
	@RequestMapping("/changeGrade")
	public List<Member> changeGrade(Model model,String changeGrade, String[] selectedId) {
		return adminMemberService.changeGrade(selectedId,changeGrade);
	}
	
	@ResponseBody
	@RequestMapping("/pause")
	public List<Member> pauseMember(Model model,String[] selectedId,String[] selectedEmail,String subject,String text) {
		adminMemberService.sendEmailMessage(selectedEmail,subject,text);
		return adminMemberService.pauseMember(selectedId);
	}
	
	@ResponseBody
	@RequestMapping("/exit")
	public List<Member> exitMember(Model model,String[] selectedId,String[] selectedEmail,String subject,String text) {
		adminMemberService.sendEmailMessage(selectedEmail,subject,text);
		return adminMemberService.exiteMember(selectedId);
	}
*/}
