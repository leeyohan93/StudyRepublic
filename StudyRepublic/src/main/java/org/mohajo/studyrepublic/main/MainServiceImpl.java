/**
 * 
 */
package org.mohajo.studyrepublic.main;

import java.util.List;

import org.mohajo.studyrepublic.domain.Member;
import org.mohajo.studyrepublic.domain.Study;
import org.mohajo.studyrepublic.repository.MemberRepository;
import org.mohajo.studyrepublic.repository.StudyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 이요한
 * @since 2019. 1. 25.
 * @version 0.0
 * - MainServiceImpl 작성
 */

@Service("MainService")
public class MainServiceImpl implements MainService{

	@Autowired
	StudyRepository studyRepository;
	@Autowired
	MemberRepository memberRepository;
	
	@Override
	public List<Study> getPopularPremiumStudy() {
		return studyRepository.findPrStudyBytypeCode();
		
	}

	@Override
	public List<Member> getRecommendTutorMember() {
		return memberRepository.getRecommendTutorMember();
		
	}
	@Override
	public List<Study> getPopularBasicStudy() {
		return studyRepository.findBsStudyBytypeCode();
		
	}

	@Override
	public void getRecommendNomalMember() {
		// TODO Auto-generated method stub
		
	}
	

}
