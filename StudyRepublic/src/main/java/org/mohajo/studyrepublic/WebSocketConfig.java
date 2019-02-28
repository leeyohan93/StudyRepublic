package org.mohajo.studyrepublic;

import org.mohajo.studyrepublic.board.StudyEchoHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer{

	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		// TODO Auto-generated method stub
		
		registry.addHandler(new StudyEchoHandler(), "/StudyEcho").setAllowedOrigins("*").withSockJS(); 
	}
	
	

}
