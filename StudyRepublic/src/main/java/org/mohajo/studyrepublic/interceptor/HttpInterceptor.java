/**
 * 
 */
package org.mohajo.studyrepublic.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mohajo.studyrepublic.main.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * @author 이요한
 * @since 2019. 2. 20.
 * @version 0.0
 * -기능 설명1
 */
@Component
public class HttpInterceptor extends HandlerInterceptorAdapter {
	
	@Autowired
	MainService mainService;
	
	@Override
    public void postHandle(

            HttpServletRequest request,
            HttpServletResponse response,
            Object handler,
            ModelAndView modelAndView

    ) throws Exception {
		if (modelAndView != null) {
            modelAndView.getModelMap().addAttribute("interest1cd",mainService.getInterest1Code());
            modelAndView.getModelMap().addAttribute("interest2cd", mainService.getInterest2Code());
            modelAndView.getModelMap().addAttribute("pinterest2cd", mainService.getPInterest2Code());
            modelAndView.getModelMap().addAttribute("dinterest2cd", mainService.getDInterest2Code());
            modelAndView.getModelMap().addAttribute("winterest2cd", mainService.getWInterest2Code());
            modelAndView.getModelMap().addAttribute("ninterest2cd", mainService.getNInterest2Code());
        }

        // HTTP 요청 처리 후 수행할 로직 작성
    }
}
