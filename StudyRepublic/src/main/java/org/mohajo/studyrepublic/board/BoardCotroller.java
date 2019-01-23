/**
 * 
 */
package org.mohajo.studyrepublic.board;

import java.util.List;

import org.mohajo.studyrepublic.domain.FreeBoard;
import org.mohajo.studyrepublic.domain.InquireBoard;
import org.mohajo.studyrepublic.domain.RequestBoard;
import org.mohajo.studyrepublic.repository.FreeBoardRepository;
import org.mohajo.studyrepublic.repository.InquireBoardRepository;
import org.mohajo.studyrepublic.repository.RequestBoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.java.Log;

/**
 * @author 윤원식
 * @since 2019. 1. 23.
 * @version
 * -BoardController 추가
 */
@Log
@Controller
@RequestMapping("/board")
public class BoardCotroller {
	
	
	@Autowired
	FreeBoardRepository freeBoardRepository;
	@Autowired
	RequestBoardRepository requestBoardRepository;
	@Autowired
	InquireBoardRepository InquireBoardRepository;
	
	@RequestMapping("/list")
	public void test() {
		
		List<FreeBoard> result = freeBoardRepository.findAll();
		List<RequestBoard> result2 = requestBoardRepository.findAll();
		List<InquireBoard> result3 = InquireBoardRepository.findAll();
		
		log.info(result.toString());
		log.info(result2.toString());
		log.info(result3.toString());
		
		
	}

}
