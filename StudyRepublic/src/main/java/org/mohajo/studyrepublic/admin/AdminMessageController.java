/**
 * 
 */
package org.mohajo.studyrepublic.admin;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.mohajo.studyrepublic.domain.FreeBoard;
import org.mohajo.studyrepublic.domain.InquireBoard;
import org.mohajo.studyrepublic.domain.RequestBoard;
import org.mohajo.studyrepublic.domain.SendMessage;
import org.mohajo.studyrepublic.domain.Study;
import org.mohajo.studyrepublic.domain.StudyStatusCD;
import org.mohajo.studyrepublic.repository.SendMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.common.collect.Lists;

/**
 * @author 이요한
 * @since 2019. 2. 18.
 * @version 0.0
 * -기능 설명1
 */
@Controller
@RequestMapping("/adminPage/message")
public class AdminMessageController {
	
	@Autowired
	SendMessageRepository sendMessageRepostiory;
	
	@RequestMapping("/list")
	public String list(Model model) {
		model.addAttribute("messageList",sendMessageRepostiory.findAll());
		return "adminPage/message/list";
	}
	
	@RequestMapping("/search")
	public String search(Model model,String searchKey, String searchValue) {
		Iterable<SendMessageRepository> searchMessage = sendMessageRepostiory.findAll(AdminPredicate.searchMessage(searchKey, searchValue));
		model.addAttribute("messageList",Lists.newArrayList(searchMessage));
		return "/adminPage/message/list";
	}
	
	@RequestMapping("/recovery")
	public String recovery(Model model,int[] selectedId,HttpServletRequest request) {
		List<SendMessage> selectedMessage = sendMessageRepostiory.getSelectedMessage(selectedId);
		
		for (int i = 0; i < selectedMessage.size(); i++) {
			selectedMessage.get(i).setMessageDelete("0");
			sendMessageRepostiory.save(selectedMessage.get(i));
		}
		return "redirect:"+request.getHeader("Referer");
	}	
	
	@RequestMapping("/delete")
	public String delete(Model model,int[] selectedId,HttpServletRequest request) {
		List<SendMessage> selectedMessage = sendMessageRepostiory.getSelectedMessage(selectedId);
		
		for (int i = 0; i < selectedMessage.size(); i++) {
			selectedMessage.get(i).setMessageDelete("1");
			sendMessageRepostiory.save(selectedMessage.get(i));
		}
		return "redirect:"+request.getHeader("Referer");
	}	
		
}
