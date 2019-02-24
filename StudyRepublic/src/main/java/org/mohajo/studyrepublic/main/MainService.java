package org.mohajo.studyrepublic.main;
import java.util.List;

import org.mohajo.studyrepublic.domain.Interest1CD;
import org.mohajo.studyrepublic.domain.Interest2CD;
import org.mohajo.studyrepublic.domain.Member;
import org.mohajo.studyrepublic.domain.PopularStudy;
import org.mohajo.studyrepublic.domain.Study;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author 이요한
 * @since 2019. 1. 25.
 * @version 0.0
 * -MainService 인터페이스 작성
 */
public interface MainService {
//	public List<Study> getPopularPremiumStudy();
	public List<PopularStudy> getPopularPremiumStudy();
	public List<Member> getRecommendTutorMember();
//	public List<Study> getPopularBasicStudy();
	public List<PopularStudy> getPopularBasicStudy(String[] popularTag);
	public void getRecommendNomalMember();
	public List<Interest2CD> getPremiumPopularTag();
	public List<Interest2CD> getBasicPopularTag();
	
	public List<Interest1CD> getInterest1Code();
	public List<Interest2CD> getInterest2Code();
	public List<Interest2CD> getPInterest2Code();
	public List<Interest2CD> getDInterest2Code();
	public List<Interest2CD> getWInterest2Code();
	public List<Interest2CD> getNInterest2Code();
	/**
	 * @param studyInfo
	 * @param searchDate
	 * @param location
	 * @param interest
	 * @return
	 */
	public Page<Study> search(Study studyInfo, String searchDate, String[] location, String[] interest, Pageable pageable);

}



