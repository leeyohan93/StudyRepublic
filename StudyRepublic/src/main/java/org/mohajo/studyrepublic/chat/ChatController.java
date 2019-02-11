package org.mohajo.studyrepublic.chat;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
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

	@RequestMapping("/chat")
	public void chat() {

	}

	@RequestMapping("chatTest")
	public void chatTest() {

	}
    //맵핑받아서 @sendto로 메세지를 보냄
	@MessageMapping("/chat.sendMessage")
	@SendTo("/topic/public")
	public ChatMessage sendMessage(@Payload ChatMessage chatMessage) {
		log.info(chatMessage.toString());
		return chatMessage;
	}
    //세션으로 유저네임을 가져와서 @sendto로 보냄
	@MessageMapping("/chat.addUser")
	@SendTo("/topic/public")
	public ChatMessage addUser(@Payload ChatMessage chatMessage,
			SimpMessageHeaderAccessor headerAccessor) {
		headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
		return chatMessage;
	}
}
