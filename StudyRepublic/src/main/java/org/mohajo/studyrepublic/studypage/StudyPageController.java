package org.mohajo.studyrepublic.studypage;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;

import org.mohajo.studyrepublic.domain.QStudyNoticeboard;
import org.mohajo.studyrepublic.domain.StudyNoticeboard;
import org.mohajo.studyrepublic.domain.StudyNoticeboardReply;
import org.mohajo.studyrepublic.repository.StudyFileshareboardReplyRepository;
import org.mohajo.studyrepublic.repository.StudyNoticeboardFileRepository;
import org.mohajo.studyrepublic.repository.StudyNoticeboardReplyRepository;
import org.mohajo.studyrepublic.repository.StudyNoticeboardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;

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
	
	@Autowired
	StudyNoticeboardRepository snb;
	
	
	@RequestMapping("/StudyNoticeboardTest")
	public String test3(Model model) {
		
		List <StudyNoticeboard> tmp = snb.findAll();
		
		for (StudyNoticeboard studyNoticeboard : tmp) {
			for (StudyNoticeboardReply studyNoticeboard2 : studyNoticeboard.getStudyNoticeboardReply()) {
				System.out.println(studyNoticeboard2.getReplyStep() + "/" + studyNoticeboard2.getReplyGroup());
			}
		}
		
		model.addAttribute("tmp", tmp);

		return "studypage/StudyNoticeboard";
	}
	
	@RequestMapping("/studypagemain")
	public String studyPageMain(Model model, @RequestParam("studyId") String studyId) {
		
		List <StudyNoticeboard> tmp = studyIdPredicate(studyId);
		model.addAttribute("tmp", tmp);
		return "studypage/StudyPageMain";
	}
	
	
	/**
	 * 동적 쿼리 메서드
	 * 
	 * 수정 시 사용방법 : StudyNoticeboard을 자신이 찾고자하는 table의 domain으로 설정한다.
	 * snb를 autowired 된 자신이 사용하고자하는 repository 변수명으로 변경한다.
	 * 그리고, builder.and(domain, 칼럼(변수), 조건) 안에 있는 조건을 자신이 원하는 조건으로 설명한다.
	 * 만약 동적으로 사용하는 파라미터를 바꿔야 한다면, studyId를 자신이 원하는 양식에 맞게 바꾸면 된다.
	 * 
	 * @param String studyId
	 * @return List<T> findAll(Predicate 조건을 만족한 것);
	 * @author shin.sangyong
	 * @since 2019.01.25 16:52
	 * @version 1.0	lastEdit 2019.01.25 16:52
	 *
	 */
	public List<StudyNoticeboard> studyIdPredicate(String studyId) {
		
		BooleanBuilder builder = new BooleanBuilder();
		QStudyNoticeboard studynoticeboard = QStudyNoticeboard.studyNoticeboard;
		
		builder.and(studynoticeboard.studyId.like(studyId));
		
		Iterable<StudyNoticeboard> result = snb.findAll(builder);
		Iterator<StudyNoticeboard> iterator = result.iterator();
		List<StudyNoticeboard> list = new ArrayList<StudyNoticeboard>();
		
		while(iterator.hasNext()) {
			list.add(iterator.next());
		}
		
		return list;
	}
	
//	@RequestMapping("/test")
//	public String test(Model model) {
//		
//		
//		List<StudyNoticeboard> studyNoticeboard = studyNoticeboardRepository.findAll();
//		model.addAttribute("studyNoticeboard", studyNoticeboard);
//		return "test";
//	}
//	
//	@RequestMapping("/test2")
//	public String test2(Model model) {
//		
//		 StudyFileshareboardReply studyfileshareboardreply = new StudyFileshareboardReply();
//	
//		List  <StudyFileshareboardReply> tmp = sffr.findAll();
//		
//		
//		model.addAttribute("tmp", tmp);
//		
//
//		return "test2";
//	}
}
