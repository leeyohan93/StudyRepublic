/**
 * 
 */
package org.mohajo.studyrepublic.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.mohajo.studyrepublic.domain.Report;
import org.mohajo.studyrepublic.domain.SendMessage;
import org.mohajo.studyrepublic.domain.Study;
import org.mohajo.studyrepublic.domain.StudyStatusCD;
import org.mohajo.studyrepublic.repository.ReportRepository;
import org.mohajo.studyrepublic.repository.SendMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.common.collect.Lists;

/**
 * @author 이요한
 * @since 2019. 2. 17.
 * @version 0.0
 * -기능 설명1
 */
@Controller
@RequestMapping("/adminPage/report")
public class AdminReportController {

	@Autowired
	ReportRepository reportRepository;
	@Autowired
	SendMessageRepository sendMessageRepository;
	
	@RequestMapping("/list")
	public String list(Model model) {
		model.addAttribute("reportList",reportRepository.findAll());
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    model.addAttribute("adminId",auth.getName());
		return "/adminPage/report/list";
	}
	
	@RequestMapping("/search")
	public String search(Model model,String[] type,String searchKey, String searchValue) {
		Iterable<Report> searchReport = reportRepository.findAll(AdminPredicate.searchReport(type,searchKey,searchValue));
		model.addAttribute("reportList",Lists.newArrayList(searchReport));
		return "/adminPage/report/list";
	}
	
	@RequestMapping("/process")
	public String process(Model model,String[] selectedMemberId,int[] selectedId,String sendId,String messageContent,HttpServletRequest request){
		for(String receiveId : selectedMemberId) {
			SendMessage sendmessage = new SendMessage();
			sendmessage.setSendId(sendId);
			sendmessage.setReceiveId(receiveId);
			sendmessage.setMessageContent(messageContent);
			sendMessageRepository.save(sendmessage);
		}
		List<Report> selectedReport = reportRepository.getSelectedReport(selectedId);
		for(Report selected : selectedReport) {
			selected.setStatus(1);
			reportRepository.save(selected);
		}
		return "redirect:"+request.getHeader("Referer");
	
	}
}
