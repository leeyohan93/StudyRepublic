/**
 * 
 */
package org.mohajo.studyrepublic.board;

import java.util.List;

import javax.transaction.Transactional;

import org.mohajo.studyrepublic.domain.FreeBoard;
import org.mohajo.studyrepublic.domain.FreeBoardReply;
import org.mohajo.studyrepublic.domain.InquireBoard;
import org.mohajo.studyrepublic.domain.InquireBoardReply;
import org.mohajo.studyrepublic.domain.RequestBoard;
import org.mohajo.studyrepublic.domain.RequestBoardReply;
import org.mohajo.studyrepublic.repository.FreeBoardReplyRepository;
import org.mohajo.studyrepublic.repository.FreeBoardRepository;
import org.mohajo.studyrepublic.repository.InquireBoardReplyRepository;
import org.mohajo.studyrepublic.repository.InquireBoardRepository;
import org.mohajo.studyrepublic.repository.RequestBoardReplyRepository;
import org.mohajo.studyrepublic.repository.RequestBoardRepository;
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
	FreeBoardRepository freeBoardRepository;
	@Autowired
	RequestBoardRepository requestBoardRepository;
    @Autowired
    InquireBoardRepository inquireBoardRepository;
	
	@Autowired
	FreeBoardReplyRepository freeBoardReplyRepository;
	@Autowired
	RequestBoardReplyRepository requestBoardReplyRepository;
	@Autowired
	InquireBoardReplyRepository inquireBoardReplyRepository;
	
	
	//자유게시판 댓글 리스트 출력
	@GetMapping("/listFreeReply")
	@ResponseBody
	public List<FreeBoardReply> listFreeReply(Model model, int freeBoardId){
	
	    FreeBoard freeBoard = freeBoardRepository.findById(freeBoardId).get();
		return freeBoard.getFreeBoardReply(); 
	}
	
	//스터디 요청게시판 댓글 리스트 출력
	@GetMapping("/listRequestReply")
	@ResponseBody
	public List<RequestBoardReply> listRequestReply(Model model, int requestBoardId){
	
	    RequestBoard requestBoard = requestBoardRepository.findById(requestBoardId).get();
		return requestBoard.getRequestBoardReply(); 
	}
	
	//문의게시판 댓글 리스트 출력
	@GetMapping("/listInquireReply")
	@ResponseBody
	public List<InquireBoardReply> listInquireReply(Model model, int inquireBoardId){
	
		InquireBoard inquireBoard = inquireBoardRepository.findById(inquireBoardId).get();
		return inquireBoard.getInquireBoardReply(); 
	}
	
	
	//자유게시판 댓글 등록
	@PostMapping("/registerFreeReply")
	@ResponseBody
	public FreeBoardReply registerFreeReply(int freeBoardId, String content, String id) {
		
		FreeBoardReply freeBoardReply = new FreeBoardReply();
		freeBoardReply.setFreeBoardId(freeBoardId);
		freeBoardReply.setContent(content);
		freeBoardReply.setId(id);	   
		return freeBoardReplyRepository.save(freeBoardReply);
	}
	
	//스터디 요청게시판 댓글 등록
	@PostMapping("/registerRequestReply")
	@ResponseBody
	public RequestBoardReply registerRequestReply(int requestBoardId, String content, String id) {
		
		RequestBoardReply requestBoardReply = new RequestBoardReply();
		requestBoardReply.setRequestBoardId(requestBoardId);
		requestBoardReply.setContent(content);
		requestBoardReply.setId(id);	   
		return requestBoardReplyRepository.save(requestBoardReply);
	}
	
	//문의게시판 댓글 등록
	@PostMapping("/registerInquireReply")
	@ResponseBody
	public InquireBoardReply registerInquireReply(int inquireBoardId, String content, String id) {

		InquireBoardReply inquireBoardReply = new InquireBoardReply();
		inquireBoardReply.setInquireBoardId(inquireBoardId);
		inquireBoardReply.setContent(content);
		inquireBoardReply.setId(id);	   
		return inquireBoardReplyRepository.save(inquireBoardReply);
	}
	
	
	//자유게시판 댓글수정
	@PostMapping("/updateFreeReply")
	@ResponseBody
	public FreeBoardReply updateFreeReply(int freeBoardReplyId, String content) {

		FreeBoardReply freeBoardReply = freeBoardReplyRepository.findById(freeBoardReplyId).get();
		freeBoardReply.setContent(content);
		return freeBoardReplyRepository.save(freeBoardReply);
	}
	
	//스터디 요청게시판 댓글수정
	@PostMapping("/updateRequestReply")
	@ResponseBody
	public RequestBoardReply updateRequestReply(int requestBoardReplyId, String content) {

		RequestBoardReply requestBoardReply = requestBoardReplyRepository.findById(requestBoardReplyId).get();
		requestBoardReply.setContent(content);
		return requestBoardReplyRepository.save(requestBoardReply);
	}
	
	//문의게시판 댓글수정
	@PostMapping("/updateInquireReply")
	@ResponseBody
	public InquireBoardReply updateInquireReply(int inquireBoardReplyId, String content) {

		InquireBoardReply inquireBoardReply = inquireBoardReplyRepository.findById(inquireBoardReplyId).get();
		inquireBoardReply.setContent(content);
		return inquireBoardReplyRepository.save(inquireBoardReply);
	}
	

	//자유게시판 댓글삭제
	@PostMapping("/deleteFreeReply/{freeBoardReplyId}")
	@ResponseBody
	public int deleteFreeReply(@PathVariable int freeBoardReplyId) {

		freeBoardReplyRepository.deleteById(freeBoardReplyId);
		return freeBoardReplyId;
	}
	
	//스터디 요청게시판 댓글삭제
	@PostMapping("/deleteRequestReply/{requestBoardReplyId}")
	@ResponseBody
	public int deleteRequestReply(@PathVariable int requestBoardReplyId) {

		requestBoardReplyRepository.deleteById(requestBoardReplyId);
		return requestBoardReplyId;
	}
	
	//문의게시판 댓글삭제
	@PostMapping("/deleteInquireReply/{inquireBoardReplyId}")
	@ResponseBody
	public int deleteInquireReply(@PathVariable int inquireBoardReplyId) {

		inquireBoardReplyRepository.deleteById(inquireBoardReplyId);
		return inquireBoardReplyId;
	}
	
	
	//자유게시판 댓글 카운트
	@GetMapping("/commentFreeCount")
	@ResponseBody
	public int commentFreeCount(int freeBoardId) {
		
		int freeCount =freeBoardReplyRepository.replyCount(freeBoardId);
		return freeCount;
	}
	
	//스터디 요청게시판 댓글 카운트
	@GetMapping("/commentRequestCount")
	@ResponseBody
	public int commentRequestCount(int requestBoardId) {

		int requestCount =requestBoardReplyRepository.replyCount(requestBoardId);
		return requestCount;
	}
	
	//문의게시판 댓글 카운트
	@GetMapping("/commentInquireCount")
	@ResponseBody
	public int commentInquireCount(int inquireBoardId) {
		
		int inquireCount =inquireBoardReplyRepository.replyCount(inquireBoardId);
		return inquireCount;
	}

}