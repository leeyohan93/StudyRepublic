/**
 * 
 */
package org.mohajo.studyrepublic.admin;

import java.util.List;

import org.mohajo.studyrepublic.domain.Member;
import org.mohajo.studyrepublic.domain.SendMessage;
import org.mohajo.studyrepublic.domain.Study;
import org.mohajo.studyrepublic.domain.StudyStatusCD;
import org.mohajo.studyrepublic.repository.SendMessageRepository;
import org.mohajo.studyrepublic.repository.StudyRepository;
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
 * @since 2019. 2. 13.
 * @version 0.0
 * -기능 설명1
 */
@Controller
@RequestMapping("/adminPage/study")
public class AdminStudyController {

	@Autowired
	StudyRepository studyRepository;
	@Autowired
	SendMessageRepository sendMessageRepository;

	
	@RequestMapping("/list")
	public String list(Model model) {
		model.addAttribute("studyList",studyRepository.findAll());
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    model.addAttribute("adminId",auth.getName());
		return "/adminPage/study/list";
	}
	
	@RequestMapping("/searchStudy")
	public String searchStudy(Model model,String[] type, String[] status,String[] onoff,String searchKey, String searchValue) {
		Iterable<Study> searchStudy = studyRepository.findAll(AdminPredicate.searchStudy(type, status, onoff, searchKey, searchValue));
		model.addAttribute("studyList",Lists.newArrayList(searchStudy));
		return "/adminPage/study/list";
	}
	
	@ResponseBody
	@RequestMapping("/disband")
	public List<Study> disband(Model model,String[] selectedMemberId,String[] selectedId,String sendId,String messageContent){
		for(String receiveId : selectedMemberId) {
			SendMessage sendmessage = new SendMessage();
			sendmessage.setSendId(sendId);
			sendmessage.setReceiveId(receiveId);
			sendmessage.setMessageContent(messageContent);
			sendMessageRepository.save(sendmessage);
		}
		List<Study> selectedStudy = studyRepository.getSelectedStudy(selectedId);
		for(Study selected : selectedStudy) {
			selected.setStudyStatusCode(new StudyStatusCD("D","해체"));
//			selected.setStudyStatusCode(new StudyStatusCD("O","모집중"));
			studyRepository.save(selected);
		}
		return studyRepository.getSelectedStudy(selectedId);
	}
}
