package org.mohajo.studyrepublic.chat;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.java.Log;

/**
 * @author 윤원식
 * @since 2019. 2. 5.
 * @version
 * -ChatController 클래스 추가
 */
@Log
@Controller
@RequestMapping("/chat")
public class ChatController {

	@RequestMapping("/chatTest")
	public void chatTest(Model model) {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();		 
	    String id = auth.getName();
	    model.addAttribute("id",id);

	}
    //요청 토픽이 sendMessage일경우 수행
	@MessageMapping("/sendMessage")
	@SendTo("/topic/public") //mapping되어있는 api를 구독하고 있는 클라이언트들에게 브로드캐스팅된다.
	public ChatMessage sendMessage(@Payload ChatMessage chatMessage) {
		log.info("sendMessage : " + chatMessage.toString());
		return chatMessage;
	}
    //유저접속 
	@MessageMapping("/userJoin")
	@SendTo("/topic/userStatus")
	public String userJoin(@Payload String Message) {
		return Message;
	}
	
    //접속종료
	@MessageMapping("/leave")
	@SendTo("/topic/userStatus")
	public String userLeave(@Payload String Message) {
		return Message;
	}
	
    //참여자리스트
	@MessageMapping("/userList")
	@SendTo("/topic/userList")
	public String userStatus(@Payload String sessionId) {
		return sessionId;	
	}
}
