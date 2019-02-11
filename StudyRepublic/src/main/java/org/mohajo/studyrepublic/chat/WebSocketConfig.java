package org.mohajo.studyrepublic.chat;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

/**
 * @author 윤원식
 * @since 2019. 2. 5.
 * @version
 * -WebSocketConfig 클래스 추가
 */

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

	//handshake와 통신을 담당할 endpoint를 지정
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
       //서버연결
    	registry.addEndpoint("/ws").withSockJS();
    }
    //Application내부에서 사용할 path를 지정
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        //client에서 send요청을 처리
    	registry.setApplicationDestinationPrefixes("/app");
    	//해당경로로 simplebroker를 등록한다. simplebroker는 해당하는 경로를  subscribe하는 client에게 메시지를 전당하는 간단한 작업을  수행
        registry.enableSimpleBroker("/topic");  

    }
}
