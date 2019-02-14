/**
 * 
 */
package org.mohajo.studyrepublic.admin;

import java.util.ArrayList;
import java.util.List;

import org.mohajo.studyrepublic.domain.FreeBoard;
import org.mohajo.studyrepublic.domain.InquireBoard;
import org.mohajo.studyrepublic.domain.RequestBoard;
import org.mohajo.studyrepublic.repository.FreeBoardRepository;
import org.mohajo.studyrepublic.repository.InquireBoardRepository;
import org.mohajo.studyrepublic.repository.RequestBoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.common.collect.Lists;

/**
 * @author 이요한
 * @since 2019. 2. 14.
 * @version 0.0
 * -기능 설명1
 */
@Controller
@RequestMapping("/adminPage/community")
public class AdminCommunityController {

	@Autowired
	FreeBoardRepository freeBoardRepository;
	@Autowired
	RequestBoardRepository requestBoardRepository;
	@Autowired
	InquireBoardRepository inquireBoardRepository;
	
	@RequestMapping("/list")
	public String list(Model model) {
		model.addAttribute("freeBoardList",freeBoardRepository.findAll());
		model.addAttribute("requestBoardList",requestBoardRepository.findAll());
		model.addAttribute("inquireBoardList",inquireBoardRepository.findAll());
		return "/adminPage/community/list";
	}	
	
	@RequestMapping("/searchCommunity")
	public String searchCommunity(Model model,String[] type,String searchKey, String searchValue) {
		if(type == null) {
			type = new String[3]; 
			type[0]="free";
			type[1]="request";
			type[2]="inquire";
		}
		for(String board:type) {
			switch (board) {
			case "free":
				Iterable<FreeBoard> searchFreeBoard = freeBoardRepository.findAll(AdminPredicate.searchFreeBoard(searchKey, searchValue));
				model.addAttribute("freeBoardList",Lists.newArrayList(searchFreeBoard));
				break;
			case "request":
				Iterable<RequestBoard> searchRequestBoard = requestBoardRepository.findAll(AdminPredicate.searchRequestBoard(searchKey, searchValue));
				model.addAttribute("requestBoardList",Lists.newArrayList(searchRequestBoard));
				break;
			case "inquire":
				Iterable<InquireBoard> searchInquireBoard = inquireBoardRepository.findAll(AdminPredicate.searchInquireBoard(searchKey, searchValue));
				model.addAttribute("inquireBoardList",Lists.newArrayList(searchInquireBoard));
				break;
					
			}
		}
		return "/adminPage/community/list";
	}
	
	@RequestMapping("/delete")
	public String delete(Model model,String[] selectedBoard,int[] selectedId) {
		
		List<FreeBoard> selectedFreeBoard = new ArrayList<>();
		List<RequestBoard> selectedRequestBoard = new ArrayList<>();
		List<InquireBoard> selectedInquireBoard = new ArrayList<>();
		
		for(int i=0; i<selectedId.length; i++) {
			switch (selectedBoard[i]) {
			case "자유게시판":
				selectedFreeBoard.add(freeBoardRepository.findById(selectedId[i]).get());
				break;
			case "요청게시판":
				selectedRequestBoard.add(requestBoardRepository.findById(selectedId[i]).get());
				break;
			case "문의게시판":
				selectedInquireBoard.add(inquireBoardRepository.findById(selectedId[i]).get());
				break;
			}
		}
		for(FreeBoard selected : selectedFreeBoard) {
			selected.setStatus(1);
			freeBoardRepository.save(selected);
		}
		for(RequestBoard selected : selectedRequestBoard) {
			selected.setStatus(1);
			requestBoardRepository.save(selected);
		}
		for(InquireBoard selected : selectedInquireBoard) {
			selected.setStatus(1);
			inquireBoardRepository.save(selected);
		}
		return "redirect:/adminPage/community/list";
	}	
}
