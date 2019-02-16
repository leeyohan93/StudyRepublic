package org.mohajo.studyrepublic.chat;

import java.util.ArrayList;

import org.mohajo.studyrepublic.domain.Member;
import org.mohajo.studyrepublic.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

	ArrayList<String> userList = new ArrayList<>();
	ArrayList<String> userProfile = new ArrayList<>();

	int  userCount;
	

	@Autowired
	MemberRepository memberRepository;


	@RequestMapping("/studyChat")
	public void chatTest(Model model) throws Exception {
          
    	  Authentication auth = SecurityContextHolder.getContext().getAuthentication();		 
    	  String id = auth.getName();
    	  Member member = memberRepository.findById(id).get();
    	  model.addAttribute("id",id);
    	  model.addAttribute("member", member);
		
	

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
	@SendTo("/topic/userJoin")
	public ChatMessage userJoin(@Payload ChatMessage chatMessage) {
		log.info(chatMessage.getSender());
		log.info(userList.toString());
		
		
		for(int  i=0; i<userList.size();i++){
			if(chatMessage.getSender() == userList.get(i).toString()) {
				log.info("이럼에러야~");
				
			}
		}
		userList.add(chatMessage.getSender());
		chatMessage.setUserList(userList);
		userProfile.add(chatMessage.getProfile());
		chatMessage.setUserProfile(userProfile);
		chatMessage.setUserCount(userList.size());
		log.info("chatMessage :" +chatMessage.toString());
		log.info("userList :" + userList.toString());
		log.info("userProfile :" + userProfile.toString());	
		log.info("userCount :" +userList.size()+"");
	
		return chatMessage;
	}

	//접속종료
	@MessageMapping("/userLeave")
	@SendTo("/topic/userLeave")
	public ChatMessage userLeave(@Payload ChatMessage chatMessage) {
		userList.remove(chatMessage.getSender());
		chatMessage.setUserList(userList);
		userProfile.remove(chatMessage.getProfile());
		chatMessage.setUserProfile(userProfile);
		chatMessage.setUserCount(userList.size());
		log.info(userList.toString());
//		log.info(userCount+"");
		return chatMessage;
	}

}
