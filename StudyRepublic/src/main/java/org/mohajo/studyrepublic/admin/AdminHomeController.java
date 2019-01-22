package org.mohajo.studyrepublic.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.java.Log;

/**
 * @author 이요한
 * @since 2019.01.22
 * @version
 * - AdminHomeCotnroller 추가
 */
@Log
@Controller
@RequestMapping("/index")
public class AdminHomeController {
	
	@RequestMapping("/index")
	public void admin_index() {
		
	}

}
