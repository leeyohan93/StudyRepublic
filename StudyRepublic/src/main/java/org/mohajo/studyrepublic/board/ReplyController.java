/**
 * 
 */
package org.mohajo.studyrepublic.board;

import java.util.List;

import javax.transaction.Transactional;

import org.mohajo.studyrepublic.domain.FreeBoard;
import org.mohajo.studyrepublic.domain.FreeBoardReply;
import org.mohajo.studyrepublic.repository.FreeBoardReplyRepository;
import org.mohajo.studyrepublic.repository.FreeBoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.extern.java.Log;

/**
 * @author 윤원식
 * @since 2019. 1. 30.
 * @version
 * -ReplyController 댓글 컨트롤러 생성
 */

@Log
@Controller
@RequestMapping("/comment")
public class ReplyController {

	
	@Autowired
	FreeBoardReplyRepository freeBoardReplyRepository;
	
	@Autowired
	FreeBoardRepository freeBoardRepository;
	
	//댓글 리스트 출력
	@GetMapping("/listFreeReply")
	@ResponseBody
	public List<FreeBoardReply> listFreeReply(Model model, int freeBoardId){
	
	    FreeBoard freeBoard = freeBoardRepository.findById(freeBoardId).get();
		return freeBoard.getFreeBoardReply(); 
	}
	
	
	//댓글 등록
	@PostMapping("/registerFreeReply")
	@ResponseBody
	public FreeBoardReply registerFreeReply(int freeBoardId, String content, String id) {
		
		FreeBoardReply freeBoardReply = new FreeBoardReply();
		freeBoardReply.setFreeBoardId(freeBoardId);
		freeBoardReply.setContent(content);
		freeBoardReply.setId(id);
		
		log.info(freeBoardReplyRepository.save(freeBoardReply)+"1");
		   
		return freeBoardReplyRepository.save(freeBoardReply);
	}
	
	//댓글수정
	@PostMapping("/updateFreeReply")
	@ResponseBody
	public FreeBoardReply updateFreeReply(int freeBoardReplyId, String content) {

		log.info(freeBoardReplyId+"");

		FreeBoardReply freeBoardReply = freeBoardReplyRepository.findById(freeBoardReplyId).get();
		freeBoardReply.setContent(content);

		return freeBoardReplyRepository.save(freeBoardReply);
	}

	//댓글삭제
	@PostMapping("/deleteFreeReply/{freeBoardReplyId}")
	@ResponseBody
	public int deleteFreeReply(@PathVariable int freeBoardReplyId) {

		log.info(freeBoardReplyId+"");
		freeBoardReplyRepository.deleteById(freeBoardReplyId);
		return freeBoardReplyId;
	}
	
	//댓글 카운트
	@GetMapping("/commentCount")
	@ResponseBody
	public int commentCount(int freeBoardId) {
		
		log.info(freeBoardId+"");
		int freeCount =freeBoardReplyRepository.replyCount(freeBoardId);
		log.info(freeCount+"");
		return freeCount;
	}

}