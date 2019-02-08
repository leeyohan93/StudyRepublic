package org.mohajo.studyrepublic.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.java.Log;

/**
 * @author 이요한
 * @since 2019.01.22
 * @version
 * - AdminHomeCotnroller 추가
 * - index페이지 데모버전 완성
 */
@Log
@Controller
@RequestMapping("/adminPage")
public class AdminHomeController {
	
	@RequestMapping("/index")
	public String index() {
		return "/adminPage/index";
	}
}
	

