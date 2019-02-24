/**
 * 
 */
package org.mohajo.studyrepublic.security;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mohajo.studyrepublic.domain.Study;
import org.mohajo.studyrepublic.domain.StudyMember;
import org.mohajo.studyrepublic.repository.StudyMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.util.StringUtils;

/**
 * @author 윤성호
 * @since 2019.01.22
 * @version
 * -기능1 추가
 * -기능2 추가
 * -기능3 추가
 */

public class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	/*아래의 두개 변수 추가해줌.*/
	protected final Log logger = LogFactory.getLog(this.getClass());
	private RequestCache requestCache = new HttpSessionRequestCache();
	
	@Autowired
	StudyMemberRepository studymemberrepository;
	
	@Override
	protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response) {
		logger.info("동작 Test 1 logger");
		Object dest = request.getSession().getAttribute("dest");
		String nextURL = null;
		if(dest != null) {
			request.getSession().removeAttribute("dest");
			nextURL =(String) dest;
		} else {			
			System.out.println("동작 Test sysout");
			nextURL = super.determineTargetUrl(request, response);
		}
		return nextURL;
	}

	/**
	 * @author 신상용
	 * @since 2019.02.22
	 * @version 1.1.1
	 * 
	 * 로그인 성공시 session을 만들어주기 위한 커스텀...
	 * 이었지만, 해당 클래스 자체를 실행을 하지 않는 듯 하다. 현재로서는 효용성이 없음..
	 */
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request,
		HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		if(session.getAttribute("studyNameAndStudyIdMap")==null&&session.getAttribute("studyIdAndStatusKoreanMap")==null) {
			logger.info("동작 Test 2 logger :" + authentication.getName());
			List <StudyMember> studyMemberList = studymemberrepository.joinedstudymember(authentication.getName());
			HashMap <String, String> studyNameAndStudyIdMap = new HashMap<>();
			HashMap <String, String> studyIdAndStatusKoreanMap = new HashMap<>();
			
			for(StudyMember studyMember : studyMemberList) {
				Study studyDomain = studyMember.getStudy();
				studyNameAndStudyIdMap.put(studyDomain.getName(), studyDomain.getStudyId());
				studyIdAndStatusKoreanMap.put(studyDomain.getStudyId(), studyMember.getStudyMemberStatusCode().getCodeValueKorean());
			}
			
			System.out.println(studyIdAndStatusKoreanMap.get("BB00001"));
			
			//아래부분 사용하지 않는 걸로 사료되어 주석 처리함. 2019.02.20 - sangyong.shin
			//model.addAttribute("joiningStudy", studyNameAndStudyIdjoiningStudy);
			System.out.println("스터디 Map: "  + studyNameAndStudyIdMap.toString());
			System.out.println("스터디별 권한: " + studyIdAndStatusKoreanMap.toString());
			
			//membercontroller.getSession_Study(auth, hs, joiningStudy);
			for( String s : studyNameAndStudyIdMap.keySet()) {
				System.out.println(s);
			}
			session.setAttribute("studyNameAndStudyIdMap", studyNameAndStudyIdMap);
			session.setAttribute("studyIdAndStatusKoreanMap", studyIdAndStatusKoreanMap);
		}
		
		SavedRequest savedRequest = requestCache.getRequest(request, response);

		if (savedRequest == null) {
			super.onAuthenticationSuccess(request, response, authentication);

			return;
		}
		String targetUrlParameter = getTargetUrlParameter();
		if (isAlwaysUseDefaultTargetUrl()
				|| (targetUrlParameter != null && StringUtils.hasText(request
						.getParameter(targetUrlParameter)))) {
			requestCache.removeRequest(request, response);
			super.onAuthenticationSuccess(request, response, authentication);

			return;
		}

		clearAuthenticationAttributes(request);

		// Use the DefaultSavedRequest URL
		String targetUrl = savedRequest.getRedirectUrl();
		logger.debug("Redirecting to DefaultSavedRequest Url: " + targetUrl);
		getRedirectStrategy().sendRedirect(request, response, targetUrl);
	}
	
	public void setRequestCache(RequestCache requestCache) {
		this.requestCache = requestCache;
	}
}
