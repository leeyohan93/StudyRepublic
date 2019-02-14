/**
 * 
 */
package org.mohajo.studyrepublic.admin;

import java.util.List;

import org.mohajo.studyrepublic.domain.Member;
import org.mohajo.studyrepublic.domain.SendMessage;
import org.mohajo.studyrepublic.repository.MemberRepository;
import org.mohajo.studyrepublic.repository.SendMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
	
	@RequestMapping("/list")
	public String list(Model model) {
		model.addAttribute("memberList",memberRepository.getTutorMember("W"));
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    model.addAttribute("adminId",auth.getName());
		return "/adminPage/tutor/list";
	}
	
	@RequestMapping("/searchTutor")
	public String searchTutor(Model model,String searchKey,String searchValue){
		Iterable<Member> searchTutor = memberRepository.findAll(AdminPredicate.searchTutor(searchKey, searchValue));
		model.addAttribute("memberList",Lists.newArrayList(searchTutor));
		return "/adminPage/tutor/list";
	}
	
	@ResponseBody
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
	}
}
