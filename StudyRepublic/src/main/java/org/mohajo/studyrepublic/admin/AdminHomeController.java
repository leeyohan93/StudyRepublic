package org.mohajo.studyrepublic.admin;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.mohajo.studyrepublic.domain.AnalyticsDTO;
import org.mohajo.studyrepublic.repository.MemberRepository;
import org.mohajo.studyrepublic.repository.StudyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author 이요한
 * @since 2019.01.22
 * @version
 * - AdminHomeCotnroller 추가
 * - index페이지 데모버전 완성
 */
@Controller
@RequestMapping("/adminPage")
public class AdminHomeController {
	
	@Autowired
	MemberRepository memberRepository;
	@Autowired
	StudyRepository studyRepository;
	
	@RequestMapping("/index")
	public String index(Model model) {
		
		List<Object[]> resultMembertList = memberRepository.getMemberCount();
		List<Object[]> resultTutorList = memberRepository.getTutorCount();
		List<Object[]> resultStudyList = studyRepository.getStudyCount();
		
		model.addAttribute("memberCount",conversion(resultMembertList));
		model.addAttribute("tutorCount",conversion(resultTutorList));
		model.addAttribute("studyCount",conversion(resultStudyList));
		model.addAttribute("totalMember",totalCount(conversion(resultMembertList)));
		model.addAttribute("totalTutor",totalCount(conversion(resultTutorList)));
		model.addAttribute("totalStudy",totalCount(conversion(resultStudyList)));
		
		return "/adminPage/index";
	}
	
	public List<AnalyticsDTO> conversion(List<Object[]> obj){
		
		return obj.stream().map(product -> new AnalyticsDTO(
                ((String)product[0]),
                ((BigInteger) product[1]).longValue())).collect(Collectors.toList());
	}
	
	public int totalCount(List<AnalyticsDTO> obj) {
		int sum = 0;
		for(AnalyticsDTO dto:obj) {
			sum+=dto.getCount();
		}
		return sum;
	}
	
	@ResponseBody
	@RequestMapping("/analyticsGraph")
	public Map<String,Map<String,Integer>> analyticsGraph(){
		List<AnalyticsDTO> memberCount = conversion(memberRepository.getMemberCountYear());
		List<AnalyticsDTO> tutorCount = conversion(memberRepository.getTutorCountYear());
		List<AnalyticsDTO> studyCount = conversion(studyRepository.getStudyCountYear());
		
		Map<String,Integer> member = new HashMap<>();
		Map<String,Integer> tutor = new HashMap<>();
		Map<String,Integer> study = new HashMap<>();
		for(AnalyticsDTO temp : memberCount) {
			member.put(temp.getPeriod(), (int) temp.getCount());
		}
		for(AnalyticsDTO temp : tutorCount) {
			tutor.put(temp.getPeriod(), (int) temp.getCount());
		}
		for(AnalyticsDTO temp : studyCount) {
			study.put(temp.getPeriod(), (int) temp.getCount());
		}
		
//		Map<String, List<AnalyticsDTO>> analytics = new HashMap();
//		analytics.put("member", memberCount);
//		analytics.put("tutor", tutorCount);
//		analytics.put("study", studyCount);
		Map<String,Map<String,Integer>> analytics = new HashMap();
		analytics.put("member",member);
		analytics.put("tutor",tutor);
		analytics.put("study",study);
		return analytics;
	}
}
	

