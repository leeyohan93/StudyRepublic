package org.mohajo.studyrepublic.board;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.mohajo.studyrepublic.security.UserDetatilsServiceImpl;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import lombok.extern.java.Log;


@Log
public class StudyEchoHandler extends TextWebSocketHandler{

	
	List<WebSocketSession> sessions = new ArrayList<>();
	Map<String, WebSocketSession> userSessions = new HashMap<>();
	
	
	
	//소켓이 연결됬을떄 발생
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception{
		log.info("afterConnectionEstablished:" + session);
		log.info(session.toString());
		sessions.add(session);
	    
		System.out.println("이것만 나오게하면 되는거죠?: " + UserDetatilsServiceImpl.socketId);
		
		
//		Authentication auth = SecurityContextHolder.getContext().getAuthentication();	
//		auth.getName();
//		String senderId =UserDetatilsServiceImpl.socketId;
		
//		String senderId = UserDetatilsServiceImpl.socketId;
		String senderId = "유노원식";
		System.out.println(senderId);

		log.info("senderId:"+senderId);
		userSessions.put(senderId, session);
		log.info("userSessions :"+userSessions.toString());
	}
	
	
	//메시지 전송
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		
		
		//protocol: cmd,댓글작성자,게시글작성자,bno  (ex: reply,댓글작성자,게시판작성자,게시판번호,로그인유저)
		String msg = message.getPayload();
		
		if (StringUtils.isNotEmpty(msg)) {
			String[] strs = msg.split(",");
			if (strs != null && strs.length == 5) {
				String cmd = strs[0];
				String userId = strs[1];
				String nickName = strs[2];
				String studyId = strs[3];
				
				
			
				WebSocketSession targetUserSession = userSessions.get(nickName);
				log.info("targetUserSession :" + targetUserSession);
				
				if ("study".equals(cmd) && targetUserSession != null) {
					TextMessage tmpMsg = new TextMessage(userId + "님이 "
							+ "<a href='/board/viewBoard?freeBoardId=" + studyId + "'>" + studyId + "</a>스터디에 가입신청을 했습니다!");
					log.info("=============================");
					targetUserSession.sendMessage(tmpMsg);
				}
			}
		}
	}

    
	//연결 종료후에 발생 
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		log.info("afterConnectionEstablished:" + session + ":" + status);
	}
}
