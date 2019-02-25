package org.mohajo.studyrepublic.chat;

import java.awt.List;
import java.security.Principal;
import java.util.ArrayList;

import org.mohajo.studyrepublic.domain.Member;
import org.mohajo.studyrepublic.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import lombok.extern.java.Log;

/**
 * @author 윤원식
 * @since 2019. 2. 5.
 * @version
 * -WebSocketEventListsner 클래스 추가
 */

@Log
@Component
public class WebSocketEventListener {


	//메시지 처리기 클래스
	@Autowired
	private SimpMessageSendingOperations simpMessageSendingOperations;
    
	WebSocketSession session;

	//연결 했을때 발생
	@EventListener
	public void handleWebSocketConnectListener(SessionConnectedEvent event) {

		


//		log.info("소켓연결됬을때~");
//		StompHeaderAccessor stompHeaderAccessor = StompHeaderAccessor.wrap(event.getMessage());
//		String username = stompHeaderAccessor.getUser().getName();
		//      String username = (String) stompHeaderAccessor.getSessionAttributes().get("username");
		//      log.info(username);
		//      if(username != null) {
		//      log.info("User Name : " + username);        
		//          ChatMessage chatMessage = new ChatMessage();
		//          chatMessage.setSender(username);

//		simpMessageSendingOperations.convertAndSend("/topic/userList", userList);
//		log.info("보내지냐~~");
	}
	//    }


	//연결 해제했을때 발생
	@EventListener
	public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {


		StompHeaderAccessor stompHeaderAccessor = StompHeaderAccessor.wrap(event.getMessage());
		String username = stompHeaderAccessor.getUser().getName();

		//        String username = (String) stompHeaderAccessor.getSessionAttributes().get("username");
		//        String message = username + "님이 떠나셨습니다.";
		//        log.info(username);
		//        if(username != null) {
		//            log.info("User Disconnected : " + username);
		//
		//            ChatMessage chatMessage = new ChatMessage();
		//            chatMessage.setType(ChatMessage.MessageType.LEAVE);
		//            chatMessage.setSender(username);
		//
		//            simpMessageSendingOperations.convertAndSend("/topic/userStatus", message);
	}
}

