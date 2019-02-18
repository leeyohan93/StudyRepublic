/**
 * 
 */
package org.mohajo.studyrepublic.board;

import org.mohajo.studyrepublic.domain.FreeBoard;
import org.mohajo.studyrepublic.domain.InquireBoard;
import org.mohajo.studyrepublic.domain.Member;
import org.mohajo.studyrepublic.domain.PageDTO;
import org.mohajo.studyrepublic.domain.PageMaker;
import org.mohajo.studyrepublic.domain.RequestBoard;
import org.mohajo.studyrepublic.repository.FreeBoardRepository;
import org.mohajo.studyrepublic.repository.InquireBoardRepository;
import org.mohajo.studyrepublic.repository.MemberRepository;
import org.mohajo.studyrepublic.repository.RequestBoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.extern.java.Log;

/**
 * @author 윤원식
 * @since 2019. 1. 23.
 * @version
 * -BoardController 클래스 추가
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
	InquireBoardRepository inquireBoardRepository;
	
	@Autowired
	MemberRepository memberrepository;


	//자유게시판 글목록 페이징
	@RequestMapping("/listFreeBoard")
	public void listFreeBoard(@ModelAttribute("pageDTO") PageDTO pageDTO, Model model) {
       	       
		log.info(pageDTO.toString());
		Pageable page = pageDTO.makePageable(0, "freeBoardId");
		Pageable page1 = pageDTO.noticeMakePageable("notice", "freeBoardId");
		
		Page<FreeBoard> list = freeBoardRepository.findAll(freeBoardRepository.makePredicate(pageDTO.getSearchType(), pageDTO.getKeyword(), pageDTO.getSearchPeriod()),page1);     
		model.addAttribute("list", new PageMaker<>(list));
        model.addAttribute("boardName", "자유게시판");

	}
	
	//스터디 요청게시판 글목록 페이징
	@RequestMapping("/listRequestBoard")
	public void listRequestBoard(@ModelAttribute("pageDTO") PageDTO pageDTO, Model model) {

		Pageable page = pageDTO.makePageable(0, "requestBoardId");
		Page<RequestBoard> list = requestBoardRepository.findAll(requestBoardRepository.makePredicate(pageDTO.getSearchType(), pageDTO.getKeyword()),page);

		model.addAttribute("list", new PageMaker<>(list));
		model.addAttribute("boardName", "스터디 요청게시판");

	}

	//뮨의게시판 글목록 페이징
	@RequestMapping("/listInquireBoard")
	public void listInquireBoard(@ModelAttribute("pageDTO") PageDTO pageDTO, Model model) {

		Pageable page = pageDTO.makePageable(0, "commentGroup");
		Page<InquireBoard> list = inquireBoardRepository.findAll(inquireBoardRepository.makePredicate(pageDTO.getSearchType(), pageDTO.getKeyword()),page);

		model.addAttribute("list", new PageMaker<>(list));
		model.addAttribute("boardName", "문의게시판");

	}

	//글쓰기 폼으로 이동
	@GetMapping("/writeBoard")
	public String writeBoard(Model model) {
        
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		 
	    String id = auth.getName();
	    log.info(id);
	    model.addAttribute("memberId",id);
	      
		return "board/write";

	}


	//게시판 글등록
	@PostMapping("/registerBoard")
	public String registerFreeBoard(FreeBoard freeBoard,RequestBoard requestBoard,InquireBoard inquireBoard,String boardType) {
		 

	      
		log.info(boardType);
		
		switch(boardType) {		
		
		case "freeBoard":
			freeBoardRepository.save(freeBoard);	
			return "redirect:/board/listFreeBoard";
		
		case "requestBoard":
			requestBoardRepository.save(requestBoard);
			return "redirect:/board/listRequestBoard";
			
		case "inquireBoard":
			inquireBoardRepository.save(inquireBoard);
			InquireBoard inquireBoardGroup= inquireBoardRepository.findById(inquireBoard.getInquireBoardId()).get();
			inquireBoardGroup.setCommentGroup(inquireBoardGroup.getInquireBoardId());
			inquireBoardRepository.save(inquireBoardGroup);
			
			return "redirect:/board/listInquireBoard";
		}
			
		return "redirect:/board/listFreeBoard";
		
	}

	//글확인 및 조회수
	@GetMapping("/viewBoard")
    public String viewFreeBoard(String boardType,FreeBoard freeBoard,RequestBoard requestBoard,InquireBoard inquireBoard, Model model) {
		 
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    String id = auth.getName();
		    
	    model.addAttribute("memberId",id);
	    

		switch(boardType) {
		case "freeBoard" : 
		
			FreeBoard freeBoardHit = freeBoardRepository.findById(freeBoard.getFreeBoardId()).get();
			freeBoardHit.setHit(freeBoardHit.getHit() + 1);
			freeBoardRepository.save(freeBoardHit);
			freeBoardRepository.findById(freeBoard.getFreeBoardId()).ifPresent(board-> model.addAttribute("freeBoard", board));
			return "board/viewFreeBoard";
		
		case "requestBoard":
			RequestBoard requestBoardHit = requestBoardRepository.findById(requestBoard.getRequestBoardId()).get();
			requestBoardHit.setHit(requestBoardHit.getHit() + 1);
			requestBoardRepository.save(requestBoardHit);
			requestBoardRepository.findById(requestBoard.getRequestBoardId()).ifPresent(board-> model.addAttribute("requestBoard", board));		
			return "board/viewRequestBoard";
			
		case "inquireBoard":
			InquireBoard inquireBoardHit = inquireBoardRepository.findById(inquireBoard.getInquireBoardId()).get();
			inquireBoardHit.setHit(inquireBoardHit.getHit() + 1);
			inquireBoardRepository.save(inquireBoardHit);
			inquireBoardRepository.findById(inquireBoard.getInquireBoardId()).ifPresent(board-> model.addAttribute("inquireBoard", board));		
			return "board/viewInquireBoard";
		}
			
		return "board/viewFreeBoard";
	}
	
	
	//수정폼
	@GetMapping("/modifyBoard")
    public String modifyBoard(String boardType,FreeBoard freeBoard,RequestBoard requestBoard,InquireBoard inquireBoard,Model model) {
    	

        switch(boardType) {
        case "freeBoard":
        	freeBoardRepository.findById(freeBoard.getFreeBoardId()).ifPresent(board-> model.addAttribute("board", board)); 
        	return "board/modifyFreeBoard";
        case "requestBoard":
        	requestBoardRepository.findById(requestBoard.getRequestBoardId()).ifPresent(board-> model.addAttribute("board", board));       	
        	return "board/modifyRequestBoard";

        case "inquireBoard":
        	inquireBoardRepository.findById(inquireBoard.getInquireBoardId()).ifPresent(board-> model.addAttribute("board", board));       	
        	return "board/modifyInquireBoard";
    }
        
   	
    	return "board/modify";
    } 
	
	//수정 등록 
	@PostMapping("/modifyRegister")
	public String modifyRegister(String boardType,RequestBoard requestBoard,InquireBoard inquireBoard,FreeBoard freeBoard, Model model) {
		
		switch(boardType) {
		case "freeBoard":
			FreeBoard freeBoardModify = freeBoardRepository.findById(freeBoard.getFreeBoardId()).get();
			freeBoardModify.setTitle(freeBoard.getTitle());
			freeBoardModify.setContent(freeBoard.getContent());
			freeBoardRepository.save(freeBoardModify);		
			return "redirect:/board/listFreeBoard";
		case "requestBoard":
			RequestBoard requestBoardModify = requestBoardRepository.findById(requestBoard.getRequestBoardId()).get();
			requestBoardModify.setTitle(requestBoard.getTitle());
			requestBoardModify.setContent(requestBoard.getContent());
			requestBoardRepository.save(requestBoardModify);		
			return "redirect:/board/listRequestBoard";
		case "inquireBoard":
			InquireBoard inquireBoardModify = inquireBoardRepository.findById(inquireBoard.getInquireBoardId()).get();
			inquireBoardModify.setTitle(inquireBoard.getTitle());
			inquireBoardModify.setContent(inquireBoard.getContent());
			inquireBoardRepository.save(inquireBoardModify);		
			return "redirect:/board/listInquireBoard";
		}
		
		return "redirect:/board/listFreeBoard";
	}
    //삭제
	@GetMapping("/deleteBoard")
	public String deleteFreeBoard(String boardType,FreeBoard freeBoard,RequestBoard requestBoard,InquireBoard inquireBoard) {
		
		log.info(boardType);
		log.info(freeBoard.toString());
		log.info(requestBoard.toString());
		log.info(inquireBoard.toString());
		switch(boardType) {
		case "freeBoard" :
			freeBoardRepository.deleteById(freeBoard.getFreeBoardId());		
			return "redirect:/board/listFreeBoard";
		case "requestBoard" :
			requestBoardRepository.deleteById(requestBoard.getRequestBoardId());		
			return "redirect:/board/listRequestBoard";
		case "inquireBoard" :
			inquireBoardRepository.deleteById(inquireBoard.getInquireBoardId());		
			return "redirect:/board/listInquireBoard";
		}
			
		return "redirect:/board/listFreeBoard";
	}
	
	//이전글로 이동
	@GetMapping("/goBeforeFreePage")
	public String goBeforeFreePage(String boardType, int freeBoardId, Model model) {
		
		log.info(freeBoardId+"");
		FreeBoard beforeBoard = freeBoardRepository.beForeBoard(freeBoardId);
		log.info(beforeBoard.toString());
        model.addAttribute("freeBoard",beforeBoard);
		
		return "board/viewFreeBoard";
		
	}
	
	//다음글로 이동
		@GetMapping("/goAfterFreePage")
		public String goAfterFreePage(String boardType, int freeBoardId, Model model) {
			
			log.info(freeBoardId+"");
			FreeBoard beforeBoard = freeBoardRepository.beAfterBoard(freeBoardId);
			log.info(beforeBoard.toString());
	        model.addAttribute("freeBoard",beforeBoard);
			
			return "board/viewFreeBoard";
			
		}
	
	
	
	//글목록 갯수
	@GetMapping("/listWriteOption")
	public String listWriteOption(String boardType, String listCount, PageDTO pageDTO, Model model) {
		
				
		pageDTO.setSize(Integer.parseInt(listCount));
		
		log.info(pageDTO.toString());
		
		switch(boardType) {
		case "freeBoard":
			Pageable page = pageDTO.makePageable(0, "freeBoardId");
			Page<FreeBoard> list = freeBoardRepository.findAll(freeBoardRepository.makePredicate(pageDTO.getSearchType(), pageDTO.getKeyword(), pageDTO.getSearchPeriod()),page);
	       
			model.addAttribute("list", new PageMaker<>(list));
	        model.addAttribute("boardName", "자유게시판");
			return "board/listFreeBoard";
		case "requestBoard":			
			Pageable page1 = pageDTO.makePageable(0, "requestBoardId");
			Page<RequestBoard> list1 = requestBoardRepository.findAll(requestBoardRepository.makePredicate(pageDTO.getSearchType(), pageDTO.getKeyword()),page1);

			model.addAttribute("list", new PageMaker<>(list1));
			model.addAttribute("boardName", "스터디 요청게시판");
			return "board/listRequestBoard";
		case "inquireBoard":
			Pageable page2 = pageDTO.makePageable(0, "commentGroup");
			Page<InquireBoard> list2 = inquireBoardRepository.findAll(inquireBoardRepository.makePredicate(pageDTO.getSearchType(), pageDTO.getKeyword()),page2);

			model.addAttribute("list", new PageMaker<>(list2));
			model.addAttribute("boardName", "문의게시판");
			return "board/listInquireBoard";
	
		}
		
		return "board/listFreeBoard";
				
	}
	
	//미리보기
	@GetMapping("/preview")
	public void preview() {
		
	}
	
	//추천수
	@PostMapping("/likeCountFreeBoard")
	@ResponseBody
	public int likeCountFreeBoard(int freeBoardId) {
		
		log.info(freeBoardId+"");
		
		FreeBoard freeBoardLikeCount = freeBoardRepository.findById(freeBoardId).get();
		freeBoardLikeCount.setLikeCount(freeBoardLikeCount.getLikeCount()+1);
		freeBoardRepository.save(freeBoardLikeCount);	
		log.info(freeBoardLikeCount.getLikeCount()+"");
		return freeBoardLikeCount.getLikeCount();
	}
	
	//문의게시판 답글폼
	@GetMapping("/commentBoard")
	public void commentBoard(int inquireBoardId, Model model) {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		 
	    String id = auth.getName();
	    model.addAttribute("memberId",id);
		
		inquireBoardRepository.findById(inquireBoardId).ifPresent(board->{
		model.addAttribute("board", board);
		});
				
	}
	
	//답글등록
	@PostMapping("/registerInquireComment")
	public String registerInquireComment(InquireBoard inquireBoard) {
		
		
		log.info(inquireBoard.toString());
		inquireBoardRepository.save(inquireBoard);

		InquireBoard inquireBoardComment = inquireBoardRepository.findById(inquireBoard.getInquireBoardId()).get();
		log.info(inquireBoardComment.toString());
		inquireBoardComment.setCommentStep(inquireBoardComment.getCommentStep()+1);
		inquireBoardRepository.save(inquireBoardComment);
		log.info(inquireBoardComment.toString());
		
		return "redirect:/board/listInquireBoard";
	}
	
	
}
	

