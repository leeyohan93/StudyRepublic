/**
 * 
 */
package org.mohajo.studyrepublic.board;

import org.mohajo.studyrepublic.domain.FreeBoard;
import org.mohajo.studyrepublic.domain.PageDTO;
import org.mohajo.studyrepublic.domain.PageMaker;
import org.mohajo.studyrepublic.repository.FreeBoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

	//자유게시판 글목록 받아오는 메소드
	@RequestMapping("/listFreeBoard")
	public String listFreeBoard(@ModelAttribute("pageDTO") PageDTO pageDTO, Model model) {

		Pageable page = pageDTO.makePageable(0, "freeBoardId");
		Page<FreeBoard> list = freeBoardRepository.findAll(freeBoardRepository.makePredicate(pageDTO.getSearchType(), pageDTO.getKeyword()),page);

		model.addAttribute("list", new PageMaker<>(list));
        model.addAttribute("boardName", "자유게시판");
		return "board/list";

	}

	//글쓰기 폼으로 이동 메소드
	@GetMapping("/writeBoard")
	public String writeBoard() {
		
		return "board/write";
	
	}
	
	
	//자유게시판 글등록 메소드
	@PostMapping("/registerFreeBoard")
	public String registerFreeBoard(@ModelAttribute("freeBoard")FreeBoard freeBoard,String boardType) {
		
		log.info(freeBoard.toString());
		
		freeBoardRepository.save(freeBoard);
		
		return "redirect:/board/listFreeBoard";
		
	}

	//글확인 조회수 메소드 
	@GetMapping("/viewFreeBoard")
    public String viewFreeBoard(int freeBoardId, @ModelAttribute("freeBoard")FreeBoard freeBoard, Model model) {
		
		
		FreeBoard freeBoardHit = freeBoardRepository.findById(freeBoardId).get();
		freeBoardHit.setHit(freeBoardHit.getHit() + 1);
		freeBoardRepository.save(freeBoardHit);
		
		freeBoardRepository.findById(freeBoardId).ifPresent(board-> model.addAttribute("freeBoard", board));
		
		return "board/view";
	}
	
	
	//수정 메소드
	@GetMapping("/modifyBoard")
    public String modifyBoard(int freeBoardId, @ModelAttribute("freeBoard")FreeBoard freeBoard, Model model) {
    	
    	
    	freeBoardRepository.findById(freeBoardId).ifPresent(board-> model.addAttribute("freeBoard", board));
    	
    	return "board/modify";
    }
	
    //삭제 메소드
	@GetMapping("/deleteFreeBoard")
	public String deleteFreeBoard(int freeBoardId) {
		
		
		freeBoardRepository.deleteById(freeBoardId);
		
		return "redirect:/board/listFreeBoard";
	}
	
	
}
	

