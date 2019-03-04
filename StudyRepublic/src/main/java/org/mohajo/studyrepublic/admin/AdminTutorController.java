package org.mohajo.studyrepublic.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.mohajo.studyrepublic.domain.Member;
import org.mohajo.studyrepublic.domain.MemberRoles;
import org.mohajo.studyrepublic.domain.SendMessage;
import org.mohajo.studyrepublic.domain.Tutor;
import org.mohajo.studyrepublic.domain.TutorCareer;
import org.mohajo.studyrepublic.domain.TutorInterest;
import org.mohajo.studyrepublic.domain.TutorUploadFile;
import org.mohajo.studyrepublic.repository.MemberRepository;
import org.mohajo.studyrepublic.repository.MemberRolesRepository;
import org.mohajo.studyrepublic.repository.SendMessageRepository;
import org.mohajo.studyrepublic.repository.TutorCareerRepository;
import org.mohajo.studyrepublic.repository.TutorInterestRepository;
import org.mohajo.studyrepublic.repository.TutorRepository;
import org.mohajo.studyrepublic.repository.TutorUploadFileRepository;
import org.mohajo.studyrepublic.tutor.TutorController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.common.collect.Lists;

/**
 * @author 이요한
 * @since 2019. 2. 12.
 * @version 0.0
 * -기능 설명1
 */
@Controller
@RequestMapping("/adminPage/tutor")
public class AdminTutorController {
	
	@Autowired
	MemberRepository memberRepository;
	@Autowired
	AdminMemberService adminMemberService;
	@Autowired
	SendMessageRepository sendMessageRepository;
	@Autowired
	TutorUploadFileRepository tutoruploadfilerepository;
	@Autowired
	TutorCareerRepository tutorcareerrepository;
	@Autowired
	TutorInterestRepository tutorinterestrepository;
	@Autowired
	TutorRepository tutorRepository;
	@Autowired
	MemberRolesRepository memberrolerepository;
	@Autowired
	TutorController tutorcontroller;
	
	
	
	
	
	@RequestMapping("/list")
	public String list(Model model) {
		model.addAttribute("memberList",memberRepository.getTutorMember("W"));
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    model.addAttribute("adminId",auth.getName());
		return "/adminPage/tutor/list";
	}
	
	@RequestMapping("/search")
	public String search(Model model,String searchKey,String searchValue){
		Iterable<Member> searchTutor = memberRepository.findAll(AdminPredicate.searchTutor(searchKey, searchValue));
		model.addAttribute("memberList",Lists.newArrayList(searchTutor));
		return "/adminPage/tutor/list";
	}
	
	@RequestMapping(value= {"/permission","/{signup}/permission"})
	public String permission(Model model,String[] selectedId,String sendId,String messageContent,HttpServletRequest request){
		for(String receiveId : selectedId) {
			SendMessage sendmessage = new SendMessage();
			sendmessage.setSendId(sendId);
			sendmessage.setReceiveId(receiveId);
			sendmessage.setMessageContent(messageContent);
			sendMessageRepository.save(sendmessage);
				
			
		// 권한테이블에 T권한 등록.
			MemberRoles memberroles = new MemberRoles();
			memberroles.setRoleName("T");				
			Member member = memberRepository.findById(receiveId).get();
			List<MemberRoles> roles = memberrolerepository.findByRole(receiveId);	
			roles.add(memberroles);								
			member.setRoles(roles);
			
		// 권한테이블에 W권한 삭제.	
			memberrolerepository.deleteTutorWait(receiveId);

			
			memberRepository.save(member);
											
		}
		
		
			adminMemberService.changeGrade(selectedId,"T");
			
		return "redirect:/adminPage/tutor/list";
//		return "redirect:"+request.getHeader("Referer");
	}

	@RequestMapping(value= {"/prohibition","/signup/prohibition"})
	public String prohibition(Model model,String[] selectedId,String sendId,String messageContent,HttpServletRequest request){
		for(String receiveId : selectedId) {
			SendMessage sendmessage = new SendMessage();
			sendmessage.setSendId(sendId);
			sendmessage.setReceiveId(receiveId);
			sendmessage.setMessageContent(messageContent);
			sendMessageRepository.save(sendmessage);
			
			//권한테이블에 N권한 등록.
			MemberRoles memberroles = new MemberRoles();
			memberroles.setRoleName("N");				
			Member member = memberRepository.findById(receiveId).get();
			List<MemberRoles> roles = memberrolerepository.findByRole(receiveId);	
			roles.add(memberroles);								
			member.setRoles(roles);
			
			// 권한테이블에 W권한 삭제.	
			memberrolerepository.deleteTutorWait(receiveId);
			
			memberRepository.save(member);
			
			// Tutor 테이블에서 거절당한 사람의 데이터 삭제
			Tutor tutor = tutorRepository.findByTutor(receiveId);
			tutorRepository.deleteById(tutor.getTutorNumber());
			
//			tutorcontroller.changeGrade("N",receiveId);
			
			System.out.println("튜터: " + tutor);
			System.out.println("튜터넘버: " + tutor.getTutorNumber());
		}
		adminMemberService.changeGrade(selectedId,"N");
		return "redirect:/adminPage/tutor/list";
	}
	
	@RequestMapping("/signup")
	public String signup(Model model,String memberId) {

		String id = memberId;
		
		Tutor tutor = tutorRepository.findByTutor(id);
		List<TutorUploadFile> tutoruploadfile = tutoruploadfilerepository.findByTutorUploadFile(id);
				
		model.addAttribute("tutor", tutor);
		model.addAttribute("tutoruploadfile", tutoruploadfile);
		
		int tutor_number = tutor.getTutorNumber();
		
		List<TutorCareer> selectedtutorcareer = tutorcareerrepository.selectedtutorcareer(tutor_number);
		model.addAttribute("selectedtutorcareer", selectedtutorcareer);
		
		List<TutorInterest> selectedtutorinterest = tutorinterestrepository.selectedtutorinterest(tutor_number);
		model.addAttribute("selectedtutorinterest", selectedtutorinterest);
		
		Member modifyuser = memberRepository.findById(id).get();
		model.addAttribute("mdu",modifyuser);
		
		String tutorEducationCD = tutor.getEducationCD().getCodeValueKorean();
		model.addAttribute("tutorEducationCD", tutorEducationCD);
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    model.addAttribute("adminId",auth.getName());

		return "/adminPage/tutor/signup";
	}
	
	//ajax 사용 했으나 paging ajax와 맞물려서 다시 redirect로 수정 함...
	/*@ResponseBody
	@RequestMapping("/permission")
	public List<Member> permission(Model model,String[] selectedId,String sendId,String messageContent){
		for(String receiveId : selectedId) {
			SendMessage sendmessage = new SendMessage();
			sendmessage.setSendId(sendId);
			sendmessage.setReceiveId(receiveId);
			sendmessage.setMessageContent(messageContent);
			sendMessageRepository.save(sendmessage);
		}
		
		return adminMemberService.changeGrade(selectedId,"T");
	}

	@ResponseBody
	@RequestMapping("/prohibition")
	public List<Member> prohibition(Model model,String[] selectedId,String sendId,String messageContent){
		for(String receiveId : selectedId) {
			SendMessage sendmessage = new SendMessage();
			sendmessage.setSendId(sendId);
			sendmessage.setReceiveId(receiveId);
			sendmessage.setMessageContent(messageContent);
			sendMessageRepository.save(sendmessage);
		}
		
		return adminMemberService.changeGrade(selectedId,"N");
	}*/
}
