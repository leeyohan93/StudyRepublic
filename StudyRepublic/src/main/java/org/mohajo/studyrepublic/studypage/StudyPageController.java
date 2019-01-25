package org.mohajo.studyrepublic.studypage;

import java.util.List;

import org.mohajo.studyrepublic.domain.StudyFileshareboardFile;
import org.mohajo.studyrepublic.domain.StudyFileshareboardReply;
import org.mohajo.studyrepublic.domain.StudyNoticeboard;
import org.mohajo.studyrepublic.repository.StudyFileshareboardFileRepository;
import org.mohajo.studyrepublic.repository.StudyFileshareboardReplyRepository;
import org.mohajo.studyrepublic.repository.StudyNoticeboardFileRepository;
import org.mohajo.studyrepublic.repository.StudyNoticeboardReplyRepository;
import org.mohajo.studyrepublic.repository.StudyNoticeboardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class StudyPageController {

	@Autowired
	StudyNoticeboardRepository studyNoticeboardRepository;
	@Autowired
	StudyNoticeboardFileRepository studyNoticeboardFileRepository;
	@Autowired
	StudyNoticeboardReplyRepository studyNoticeboardReplyRepository;
	@Autowired
	StudyFileshareboardReplyRepository sffr;
	
	
	@RequestMapping("/test")
	public String test(Model model) {
		
		
		List<StudyNoticeboard> studyNoticeboard = studyNoticeboardRepository.findAll();
		model.addAttribute("studyNoticeboard", studyNoticeboard);
		return "test";
	}
	
	@RequestMapping("/test2")
	public String test2(Model model) {
		
		 StudyFileshareboardReply studyfileshareboardreply = new StudyFileshareboardReply();
	
		List  <StudyFileshareboardReply> tmp = sffr.findAll();
		
		
		model.addAttribute("tmp", tmp);
		

		return "test2";
	}
	
}
