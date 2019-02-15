package org.mohajo.studyrepublic.message;
/**
 * @author 김준석
 * @since 2019.01.23
 * @version 0.0
 * -받은쪽지함 상속된 데이터가져오기 테스트 추가
 * -기능2 추가
 * -기능3 추가
 */

import java.io.Serializable;
import java.util.List;

import org.mohajo.studyrepublic.domain.Member;
import org.mohajo.studyrepublic.domain.ReceiveMessage;
import org.mohajo.studyrepublic.domain.SendMessage;
import org.mohajo.studyrepublic.repository.MemberRepository;
import org.mohajo.studyrepublic.repository.ReceiveMessageRepository;
import org.mohajo.studyrepublic.repository.SendMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MessageController {
	
	@Autowired
	private MemberRepository mbr;
	@Autowired
	private SendMessageRepository sendmessagerepository;
	@Autowired
	private ReceiveMessageRepository receivemessagerepository;
	
	/*받은쪽지함*/
	@RequestMapping("/receiveMessage")
	public String receivemessagelist(Model model) {
		Authentication auth =SecurityContextHolder.getContext().getAuthentication();
		String id = auth.getName();
		
		List<ReceiveMessage> receiveMessageList= receivemessagerepository.findreceiveById(id);
		model.addAttribute("receivemessagerepository", receiveMessageList);
		
		return"MessageTest/receiveMessage";
	}	
	
	/*보낸쪽지함*/
	@RequestMapping("/sendMessage")
	public String sendmessagelist(Model model) {
		Authentication auth =SecurityContextHolder.getContext().getAuthentication();
		String id = auth.getName();
	
		List<SendMessage> sendMessageList = sendmessagerepository.findSendById(id);
		model.addAttribute("sendmessagerepository",sendMessageList);
		
		System.out.println("sendmessagerepository");
		return "MessageTest/sendMessage";
	}
	
	@RequestMapping("/messageWrite")
	public String messagewrite(Model model) {
		Authentication auth =SecurityContextHolder.getContext().getAuthentication();
		String id = auth.getName();
		
		return "MessageTest/message_write";
	}
	
	@RequestMapping("/messageWriteResult")
	public String messageResult(Model model,@RequestParam("receiveId")String receiveId,@RequestParam("messageContent")String messageContent) {
		Authentication auth =SecurityContextHolder.getContext().getAuthentication();
		String id = auth.getName();
		
		SendMessage sendmessage = new SendMessage();
		sendmessage.setSendId(id);
		sendmessage.setReceiveId(receiveId);
		sendmessage.setMessageContent(messageContent);
		
		sendmessagerepository.save(sendmessage);
		
		return "redirect:/sendMessage";
	}
		
		
		
		
		
		
		
	
	
	
	
	

	
	
}
