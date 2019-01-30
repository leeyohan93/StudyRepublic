/**
 * 
 */
package org.mohajo.studyrepublic.main;

import java.util.List;

import org.mohajo.studyrepublic.domain.Member;
import org.mohajo.studyrepublic.domain.Study;

/**
 * @author 이요한
 * @since 2019. 1. 25.
 * @version 0.0
 * -MainService 인터페이스 작성
 */
public interface MainService {
	public List<Study> getPopularPremiumStudy();
	public List<Member> getRecommendTutorMember();
	public List<Study> getPopularBasicStudy();
	public void getRecommendNomalMember();
	
	
}
