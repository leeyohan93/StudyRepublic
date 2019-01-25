/**
 * 
 */
package org.mohajo.studyrepublic.main;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author 이요한
 * @since 2019. 1. 23.
 * @version 0.0
 * -MainController 생성
 */

@Controller 
public class MainController {
	
	@RequestMapping("/index")
	public void index(Model model) {
		
		model.addAttribute("greeting","안녕하세요");
	}
}
