package org.mohajo.studyrepublic.main;

import java.util.List;

import org.mohajo.studyrepublic.domain.Interest1CD;
import org.mohajo.studyrepublic.domain.Interest2CD;
import org.mohajo.studyrepublic.domain.Member;
import org.mohajo.studyrepublic.domain.Study;
import org.mohajo.studyrepublic.domain.StudyInterest;
import org.mohajo.studyrepublic.repository.Interest1CDRepository;
import org.mohajo.studyrepublic.repository.Interest2CDRepository;
import org.mohajo.studyrepublic.repository.MemberRepository;
import org.mohajo.studyrepublic.repository.StudyInterestRepository;
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
	@Autowired
	Interest1CDRepository interest1CDRepository;
	@Autowired
	Interest2CDRepository interest2CDRepository;
		
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
		// 성호 로그인 정보 가져오는것 정보공유 후 진행 예정
	}
	
	@Override
	public List<Interest2CD> getPremiumPopularTag(){
		return interest2CDRepository.findPremiumPopularTag();
	}
	
	@Override
	public List<Interest2CD> getBasicPopularTag(){
		return interest2CDRepository.findBasicPopularTag();
	}
	
	@Override
	public List<Interest1CD> getInterest1Code(){
		return interest1CDRepository.findAll();
	}
	@Override
	public List<Interest2CD> getInterest2Code(){
		return interest2CDRepository.findAll();
	}
	@Override
	public List<Interest2CD> getPInterest2Code(){
		return interest2CDRepository.Pinterest2List();
	}
	@Override
	public List<Interest2CD> getDInterest2Code(){
		return interest2CDRepository.Dinterest2List();
	}
	@Override
	public List<Interest2CD> getWInterest2Code(){
		return interest2CDRepository.Winterest2List();
	}
	@Override
	public List<Interest2CD> getNInterest2Code(){
		return interest2CDRepository.Ninterest2List();
	}
}
