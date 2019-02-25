package org.mohajo.studyrepublic.board;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
//		log.info("afterConnectionEstablished:" + session);
//		log.info(session.toString());
//		sessions.add(session);
//		Authentication auth = SecurityContextHolder.getContext().getAuthentication();	
//		auth.getName();
//		String senderId = "ccc123";
//		log.info(senderId);
//		userSessions.put(senderId, session);
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
				String replyWriter = strs[1];
				String boardWriter = strs[2];
				String bno = strs[3];
				String loginUser =strs[4];
				
				log.info(session.toString());
				sessions.add(session);
				
				String senderId = loginUser;
				log.info(senderId);
				userSessions.put(senderId, session);
				
				userSessions.put(senderId, session);
				
				WebSocketSession boardWriterSession = userSessions.get(boardWriter);
				if ("reply".equals(cmd) && boardWriterSession != null) {
					TextMessage tmpMsg = new TextMessage(replyWriter + "님이 "
							+ "<a href='/board/viewBoard?freeBoardId=" + bno + "'>" + bno + "</a>번 게시글에 댓글을 달았습니다!");
					boardWriterSession.sendMessage(tmpMsg);
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
