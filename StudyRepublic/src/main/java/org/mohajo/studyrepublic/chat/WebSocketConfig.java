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

//websocket, stomp관련 설정 ,서버 소켓 토픽 prefix,클라이언트 소켓 이름
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

	//handshake와 통신을 담당할 endpoint를 지정, 클라이언트에서 서버로 접근할 andpoint를 지정한다.
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
       //서버연결 여러가지 endpoint사용가능
    	registry.addEndpoint("/ws").withSockJS();
    }
    //Application내부에서 사용할 path를 지정
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        
    	//client에서 send요청을 처리,Server의 controller에서 사용할 토픽의 prefix, 서버에서 클라이언트의 메세지를 받을api의 prefix설정
    	registry.setApplicationDestinationPrefixes("/app");
    	
    	//해당경로로 simplebroker를 등록한다. simplebroker는 해당하는 경로를  subscribe하는 client에게 메시지를 전달하는 간단한 작업을  수행
    	//server-side에서 client-side로 전송할 때 사용하는 경로의 prefix 설정을 말함, 해당 api를 구독하고있는 클라이언트들에게 메시지를 전송
        registry.enableSimpleBroker("/topic");  

    }
}
