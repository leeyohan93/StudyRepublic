package org.mohajo.studyrepublic.message;
/**
 * @author 김준석
 * @since 2019.01.23
 * @version 0.0
 * -받은쪽지함 상속된 데이터가져오기 테스트 추가
 * -기능2 추가
 * -기능3 추가
 */

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.mohajo.studyrepublic.domain.Member;
import org.mohajo.studyrepublic.domain.PageDTO;
import org.mohajo.studyrepublic.domain.PageMaker;
import org.mohajo.studyrepublic.domain.ReceiveMessage;
import org.mohajo.studyrepublic.domain.Report;
import org.mohajo.studyrepublic.domain.ReportTypeCD;
import org.mohajo.studyrepublic.domain.ReportWhyCD;
import org.mohajo.studyrepublic.domain.SendMessage;
import org.mohajo.studyrepublic.repository.MemberRepository;
import org.mohajo.studyrepublic.repository.ReceiveMessageRepository;
import org.mohajo.studyrepublic.repository.ReportRepository;
import org.mohajo.studyrepublic.repository.SendMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.extern.java.Log;


@Controller
public class MessageController {
	
	@Autowired
	private MemberRepository mbr;
	@Autowired
	private SendMessageRepository sendmessagerepository;
	@Autowired
	private ReceiveMessageRepository receivemessagerepository;
	
	@Autowired
	MemberRepository memberRepository;
	@Autowired
	ReportRepository reportRepository;
	
	/*받은쪽지함
	@RequestMapping("/receiveMessage")
	public String receivemessagelist(Model model) {
		Authentication auth =SecurityContextHolder.getContext().getAuthentication();
		String id = auth.getName();
		
		List<ReceiveMessage> list = receivemessagerepository.findreceiveById(id);
		
		
		model.addAttribute("receivemessagerepository", list);
		

			
		
		return"MessageTest/receiveMessage";
	}	*/
	
	/*페이징가능한 받은쪽지함 */
	@RequestMapping("/receiveMessage")
	public String receivemessagelist(Model model, PageDTO pageDTO) {
		Authentication auth =SecurityContextHolder.getContext().getAuthentication();
		String id = auth.getName();
		
		Pageable page = pageDTO.makePageable(0, "message_date");
		
		Page<ReceiveMessage> list = receivemessagerepository.findreceiveById(id,page);
		
		
		model.addAttribute("receiveList",new PageMaker<>(list));
		

			
		
		return "MessageTest/receiveMessage";
	}	
	
	/*페이징가능한 보낸쪽지함 */
	@RequestMapping("/sendMessage")
	public String snedmessagelist(Model model, PageDTO pageDTO) {
		Authentication auth =SecurityContextHolder.getContext().getAuthentication();
		String id = auth.getName();
		
		Pageable page = pageDTO.makePageable(0, "message_date");
		
		Page<SendMessage> list = sendmessagerepository.findSendById(id,page);
		
		
		model.addAttribute("sendList",new PageMaker<>(list));
		

			
		
		return "MessageTest/sendMessage";
	}	
	
	@RequestMapping("/receiveMessageSelect")
	public String receiveMessageSelect( String listCount, PageDTO pageDTO, Model model) {
		Authentication auth =SecurityContextHolder.getContext().getAuthentication();
		String id = auth.getName();
		
		
		pageDTO.setSize(Integer.parseInt(listCount));
		
		Pageable page = pageDTO.makePageable(0, "message_date");
		
		Page<ReceiveMessage> list = receivemessagerepository.findreceiveById(id,page);
		
		
		model.addAttribute("receiveList",new PageMaker<>(list));
		

			
		
		return "MessageTest/receiveMessage";
		
	}
	
	@RequestMapping("/sendMessageSelect")
	public String sendMessageSelect( String listCount, PageDTO pageDTO, Model model) {
		Authentication auth =SecurityContextHolder.getContext().getAuthentication();
		String id = auth.getName();
		
		pageDTO.setSize(Integer.parseInt(listCount));
		
		Pageable page = pageDTO.makePageable(0, "message_date");
		
		Page<SendMessage> list = sendmessagerepository.findSendById(id,page);
		
		
		model.addAttribute("sendList",new PageMaker<>(list));
		

			
		
		return "MessageTest/sendMessage";
	}	
	
		
		
		
	
/*	보낸쪽지함
	@RequestMapping("/sendMessage")
	public String sendmessagelist(Model model) {
		Authentication auth =SecurityContextHolder.getContext().getAuthentication();
		String id = auth.getName();
	
		List<SendMessage> sendMessageList = sendmessagerepository.findSendById(id);
		model.addAttribute("sendmessagerepository",sendMessageList);
		
		System.out.println("sendmessagerepository");
		return "MessageTest/sendMessage";
	}*/
	
	@RequestMapping("/messageWrite")
	public String messagewrite(Model model) {
		Authentication auth =SecurityContextHolder.getContext().getAuthentication();
		String id = auth.getName();
		
		return "MessageTest/message_write";
	}
	@RequestMapping("/messageWriteResult")
	public String messageResult(Model model,@RequestParam("receiveId")String receiveId,@RequestParam("messageContent")String messageContent) {
		Authentication auth =SecurityContextHolder.getContext().getAuthentication();
		String id = auth.getName();
		
		SendMessage sendmessage = new SendMessage();
		sendmessage.setSendId(id);
		sendmessage.setReceiveId(receiveId);
		sendmessage.setMessageContent(messageContent);
		
		sendmessagerepository.save(sendmessage);
		
		return "redirect:/sendMessage";
	}
	
	@RequestMapping("/remessageWrite")
	public String remessagewrite(Model model, @RequestParam String sendId) {
		Authentication auth =SecurityContextHolder.getContext().getAuthentication();
		String id = auth.getName();
		
		//List<ReceiveMessage> remessageWrite = receivemessagerepository.findByMessageReceiveId(messageReceiveId,id);
		//model.addAttribute("remessageWrite", remessageWrite);
		model.addAttribute("sendId", sendId);
		return "MessageTest/re_message_write";
	}
	
	
/*	@RequestMapping("/remessageWriteResult")
	public String remessageResult(Model model,@RequestParam("receiveId")String receiveId,@RequestParam("messageContent")String messageContent) {
		Authentication auth =SecurityContextHolder.getContext().getAuthentication();
		String id = auth.getName();
		
		SendMessage sendmessage = new SendMessage();
		sendmessage.setSendId(id);
		sendmessage.setReceiveId(receiveId);
		sendmessage.setMessageContent(messageContent);
		
		sendmessagerepository.save(sendmessage);
		
		return "redirect:/sendMessage";
	}*/
	
	
	
	
	@RequestMapping("/viewSendmessage")
	public String sendMessageView(Model model, @RequestParam("messageSendId") int messageSendId) {
		Authentication auth =SecurityContextHolder.getContext().getAuthentication();
		String id = auth.getName();
		
		List<SendMessage> viewSendMessage = sendmessagerepository.findByMessageSendId(messageSendId,id);
		model.addAttribute("viewSendMessage", viewSendMessage);
		
		return "MessageTest/viewsendMessage";
		
		
	}
	
	@RequestMapping("/viewReceivemessage")
	public String receiveMessageView(Model model, @RequestParam("messageReceiveId") int messageReceiveId) {
		Authentication auth =SecurityContextHolder.getContext().getAuthentication();
		String id = auth.getName();
		Member member = memberRepository.findById(id).get();
	    model.addAttribute("member",member);


	    ReceiveMessage viewReceiveMessage = receivemessagerepository.findById(messageReceiveId).get();
	    System.out.println(viewReceiveMessage.toString());
		model.addAttribute("viewReceiveMessage", viewReceiveMessage);
		
//		List<ReceiveMessage> viewReceiveMessage = receivemessagerepository.findByMessageReceiveId(messageReceiveId,id);
//		model.addAttribute("viewReceiveMessage", viewReceiveMessage);
		
		return "MessageTest/viewreceiveMessage";
		
	}
	
	
	
	/*보낸 쪽지 삭제 상태값 변환*/
	@RequestMapping("/messageDelete")
	public String messagedelete(int messageSendId) {
		Authentication auth =SecurityContextHolder.getContext().getAuthentication();
		String id = auth.getName();
		
		SendMessage sendMessage = sendmessagerepository.findById(messageSendId).get();
		
		sendMessage.setMessageDelete("1");
	
		sendmessagerepository.save(sendMessage);
		

	
		return "redirect:receiveMessage";
		
	}
	/*받은 쪽지 삭제 상태값 변환*/
	@RequestMapping("/receivemessageDelete")
	public String receivemessagedelete(int messageReceiveId) {
		Authentication auth =SecurityContextHolder.getContext().getAuthentication();
		String id = auth.getName();
		
			
		ReceiveMessage receiveMessage = receivemessagerepository.findById(messageReceiveId).get();
		
		receiveMessage.setMessageDelete("1");
		receivemessagerepository.save(receiveMessage);
		
		
	    
		return "redirect:receiveMessage";
		
		
	}
	/*
	@RequestMapping("/receiveMessage")
	public String receivemessagelist(Model model,Pageable pageable) {
		Authentication auth =SecurityContextHolder.getContext().getAuthentication();
		String id = auth.getName();
		
		model.addAttribute("list", receivemessagerepository.findreceiveById(id,pageable));
		
		return"MessageTest/receiveMessage";
	}	
	*/
	
	@RequestMapping("/receiveCheckdelete")
	public String receiveAllDelete(@RequestParam int[] RowCheck) {
		System.out.println(RowCheck);
	
		if(RowCheck.length != 0) {
			for(int i=0; i<RowCheck.length; i++) {
				
				ReceiveMessage receiveMessage = receivemessagerepository.findById(RowCheck[i]).get();
				
				receiveMessage.setMessageDelete("1");
				receivemessagerepository.save(receiveMessage);
			}
		}
		
		return "redirect:receiveMessage";
	}
	
	@RequestMapping("/sendCheckdelete")
	public String sendAllDelete(@RequestParam int[] RowCheck) {
		System.out.println(RowCheck);
		
		if(RowCheck.length != 0) {
			for(int i=0; i<RowCheck.length; i++) {
				
				SendMessage sendMessage = sendmessagerepository.findById(RowCheck[i]).get();
				
				sendMessage.setMessageDelete("1");
				sendmessagerepository.save(sendMessage);
			}
		}
		
		
		return "redirect:sendMessage";
		
	}
	
	
		
		
	
	@RequestMapping("/modalmessageWrite")
	public String modalmessageWrite(Model model, @RequestParam String Id) {
		Authentication auth =SecurityContextHolder.getContext().getAuthentication();
		String id = auth.getName();
		
		//List<ReceiveMessage> remessageWrite = receivemessagerepository.findByMessageReceiveId(messageReceiveId,id);
		//model.addAttribute("remessageWrite", remessageWrite);
		model.addAttribute("sendId", Id);
		return "MessageTest/re_message_write";
	}
	
	@RequestMapping("/modalmessageWriteResult")
	public String modalmessageResult(HttpServletRequest request, Model model,@RequestParam("receiveId")String receiveId,@RequestParam("messageContent")String messageContent) {
		Authentication auth =SecurityContextHolder.getContext().getAuthentication();
		String id = auth.getName();
		
		SendMessage sendmessage = new SendMessage();
		sendmessage.setSendId(id);
		sendmessage.setReceiveId(receiveId);
		sendmessage.setMessageContent(messageContent);
		
		sendmessagerepository.save(sendmessage);
		
		   String referer = request.getHeader("Referer");
		    return "redirect:"+ referer;
	}

	//신고하기
		@PostMapping("/reportMessage")
		@ResponseBody
		public int boardReport(Model model, String id,String target,String reportTypeCD,int reportWhyCD,String content) {
	   
	  
	     
	     
	     Report report = new Report();
	     report.setId(id);
	     report.setTarget(target);

	     report.setReportTypeCD(new ReportTypeCD(reportTypeCD));
	     report.setReportWhyCD(new ReportWhyCD(reportWhyCD));
	     report.setContent(content);
	     
	     reportRepository.save(report);
	     
		      
			return 1;
	      
		}
		
		
}
		
		
		
		
		
	
	
	
	
	

	
	
