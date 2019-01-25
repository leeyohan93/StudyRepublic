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

import org.mohajo.studyrepublic.domain.SendMessage;
import org.mohajo.studyrepublic.repository.SendMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MessageController {

	@Autowired
	private SendMessageRepository smr;
	
	@RequestMapping("/Message")
	public String sendmessagelist(Model model) {
		List<SendMessage> sendlist = smr.findAll();
		model.addAttribute("smr",sendlist);
	
		System.out.println("smr");
		return "sendMessage";
	}
	
	
	

	
	
}
