package org.mohajo.studyrepublic.study;

import java.util.List;

import org.mohajo.studyrepublic.domain.Leveltest;
import org.mohajo.studyrepublic.domain.LeveltestResponse;
import org.mohajo.studyrepublic.domain.Payment;
import org.mohajo.studyrepublic.domain.Review;
import org.mohajo.studyrepublic.domain.Study;
import org.mohajo.studyrepublic.domain.StudyMember;
import org.mohajo.studyrepublic.repository.LeveltestRepository;
import org.mohajo.studyrepublic.repository.LeveltestResponseRepository;
import org.mohajo.studyrepublic.repository.PaymentRepository;
import org.mohajo.studyrepublic.repository.ReviewRepository;
import org.mohajo.studyrepublic.repository.StudyMemberRepository;
import org.mohajo.studyrepublic.repository.StudyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author	이미연
 * @since	2019. 1. 22.
 * @version	
 * - 기능 설명 1
 */
@Controller
public class StudyController {

	@Autowired
	StudyRepository sr;
	
	@Autowired
	StudyMemberRepository smr;
	
	@Autowired
	LeveltestRepository lr;
	
	@Autowired
	LeveltestResponseRepository lrr;
	
	@Autowired
	ReviewRepository rr;
	
	@Autowired
	PaymentRepository pr;
	
}
	