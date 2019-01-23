package org.mohajo.studyrepublic.message;

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
