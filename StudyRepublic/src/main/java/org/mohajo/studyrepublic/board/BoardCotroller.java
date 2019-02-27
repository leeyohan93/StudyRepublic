/**
 * 
 */
package org.mohajo.studyrepublic.board;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.mohajo.studyrepublic.domain.BoardLike;
import org.mohajo.studyrepublic.domain.FreeBoard;
import org.mohajo.studyrepublic.domain.FreeBoardFile;
import org.mohajo.studyrepublic.domain.InquireBoard;
import org.mohajo.studyrepublic.domain.Member;
import org.mohajo.studyrepublic.domain.PageDTO;
import org.mohajo.studyrepublic.domain.PageMaker;
import org.mohajo.studyrepublic.domain.Report;
import org.mohajo.studyrepublic.domain.ReportTypeCD;
import org.mohajo.studyrepublic.domain.ReportWhyCD;
import org.mohajo.studyrepublic.domain.RequestBoard;
import org.mohajo.studyrepublic.domain.RequestBoardFile;
import org.mohajo.studyrepublic.repository.BoardLikeRepository;
import org.mohajo.studyrepublic.repository.FreeBoardFileRepository;
import org.mohajo.studyrepublic.repository.FreeBoardRepository;
import org.mohajo.studyrepublic.repository.InquireBoardRepository;
import org.mohajo.studyrepublic.repository.MemberRepository;
import org.mohajo.studyrepublic.repository.ReportRepository;
import org.mohajo.studyrepublic.repository.RequestBoardFileRepository;
import org.mohajo.studyrepublic.repository.RequestBoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

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
	MemberRepository memberRepository;
	
	@Autowired
	FreeBoardFileRepository freeBoardFileRepository;
	
	@Autowired
	RequestBoardFileRepository requestBoardFileRepository;
	
	@Autowired
	ReportRepository ReportRepository;
	
	@Autowired
	BoardLikeRepository boardLikeRepository;


	//자유게시판 글목록 페이징
	@GetMapping("/listFreeBoard")
	public void listFreeBoard(@ModelAttribute("pageDTO") PageDTO pageDTO, Model model) {
       	       
		
		log.info(pageDTO.toString());
		Pageable page = pageDTO.makePageable(0, "freeBoardId");
		Pageable noticePage = pageDTO.noticeMakePageable("notice", "freeBoardId");
		
		
		Page<FreeBoard> list = freeBoardRepository.findAll(freeBoardRepository.makePredicate(pageDTO.getSearchType(), pageDTO.getKeyword(), pageDTO.getSearchPeriod()),noticePage); 
		model.addAttribute("list", new PageMaker<>(list));
        model.addAttribute("boardName", "자유게시판");

	}
	
	//스터디 요청게시판 글목록 페이징
	@GetMapping("/listRequestBoard")
	public void listRequestBoard(@ModelAttribute("pageDTO") PageDTO pageDTO, Model model) {

		Pageable page = pageDTO.makePageable(0, "requestBoardId");
		Pageable noticePage = pageDTO.noticeMakePageable("notice", "requestBoardId");
		Page<RequestBoard> list = requestBoardRepository.findAll(requestBoardRepository.makePredicate(pageDTO.getSearchType(), pageDTO.getKeyword(), pageDTO.getSearchPeriod()),noticePage);

		model.addAttribute("list", new PageMaker<>(list));
		model.addAttribute("boardName", "스터디 요청게시판");

	}

	//뮨의게시판 글목록 페이징
	@GetMapping("/listInquireBoard")
	public void listInquireBoard(@ModelAttribute("pageDTO") PageDTO pageDTO, Model model) {

		Pageable page = pageDTO.makePageable(0, "commentGroup");
		Page<InquireBoard> list = inquireBoardRepository.findAll(inquireBoardRepository.makePredicate(pageDTO.getSearchType(), pageDTO.getKeyword(), pageDTO.getSearchPeriod()),page);

		model.addAttribute("list", new PageMaker<>(list));
		model.addAttribute("boardName", "문의게시판");

	}

	//글쓰기 폼으로 이동
	@GetMapping("/writeBoard")
	public String writeBoard(Model model, String boardType) {
        
		log.info(boardType);
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String id = auth.getName();
		Member member = memberRepository.findById(id).get();
	    model.addAttribute("member",member);
	    model.addAttribute("boardType",boardType);
	      
		return "board/write";

	}

	
	//게시판 글등록 파일업로드
	@PostMapping("/registerBoard")
	public String registerFreeBoard(@ModelAttribute FreeBoard freeBoard,@ModelAttribute RequestBoard requestBoard,@ModelAttribute InquireBoard inquireBoard,String boardType, MultipartHttpServletRequest request,MultipartFile file) throws Exception {
		 
		

	      
		log.info(boardType);
		
		switch(boardType) {		
		
		case "freeBoard":
			log.info("freeBoard 파일업로드 시작 : " + freeBoard.toString());
			freeBoardRepository.save(freeBoard);
			List<MultipartFile> uploadFileList = request.getFiles("file");
			log.info("uploadFileList : " + uploadFileList.toString());
			log.info("freeBoard 저장하고 값 : " + freeBoard.toString());
			doUpload(request,uploadFileList,freeBoard,requestBoard, inquireBoard,boardType);
			return "redirect:/board/listFreeBoard";
		
		case "requestBoard":
			requestBoardRepository.save(requestBoard);
			List<MultipartFile> uploadFileList2 = request.getFiles("file");
			log.info("uploadFileList : " + uploadFileList2.toString());
			doUpload(request,uploadFileList2,freeBoard,requestBoard, inquireBoard,boardType);
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
	//파일업로드 상세
	private void doUpload(HttpServletRequest request, //
			List<MultipartFile> uploadFileList,FreeBoard freeBoard,RequestBoard requestBoard,InquireBoard inquireBoard,String boardType) throws IOException {
		
		String fileOriginName = "";
		
		final DefaultResourceLoader defaultresourceloader = new DefaultResourceLoader();
		
		//사용자에 맞게 절대경로로 변경
		Resource resource = defaultresourceloader
				.getResource("file:src\\main\\resources\\static\\boardFileUpload");
		log.info("resource : " + resource);
		
		String uploadRootPath = resource.getFile().getAbsolutePath();
		log.info("resource 경로: " + uploadRootPath);
		
		File file = new File(resource.getFile().getAbsolutePath());
		
		//파일이 존재하지않으면 만들어준다.
		if (!file.exists()) {
		         file.mkdirs();
		      }
		
		log.info("file: " + file);
		
		File uploadRootDir = new File(uploadRootPath);
		
		if (!uploadRootDir.exists()) {
			uploadRootDir.mkdirs();
		}
		
		List<File> uploadedFiles = new ArrayList<File>();
		List<String> failedFiles = new ArrayList<String>();
		
		
		for (MultipartFile fileData : uploadFileList) {
			
			//원래 파일 이름
			String name = fileData.getOriginalFilename();
			fileOriginName = name;
			String sourceFileNameExtension = FilenameUtils.getExtension(fileOriginName).toLowerCase();
			String fileSaveName = RandomStringUtils.randomAlphanumeric(20) + "." + sourceFileNameExtension;
			
			log.info("fileOriginName :" + fileOriginName);
			
			if (fileSaveName != null && fileSaveName.length() > 0) {
				try {
				
					File serverFile = new File(uploadRootPath + File.separator + fileSaveName);
					BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
					stream.write(fileData.getBytes());
					stream.close();
					
					uploadedFiles.add(serverFile);
					log.info("serverFile :" + serverFile);
					log.info("boardType:"+boardType);
					switch(boardType) {
					
					case "freeBoard":
					
					FreeBoardFile freeBoardFile = new FreeBoardFile();
					freeBoardFile.setFreeBoardId(freeBoard.getFreeBoardId());
					freeBoardFile.setOriginName(fileOriginName);
					freeBoardFile.setUploadPath(uploadRootPath);
					freeBoardFile.setSaveName(fileSaveName);
					
					String fullUrl = uploadRootPath + "\\" + fileSaveName;
					log.info("fullUrl : " + fullUrl);
					freeBoardFile.setFullUrl(fullUrl);
					
					String partUrl = "\\boardFileUpload\\" + fileSaveName;
					
					freeBoardFile.setPartUrl(partUrl);
					log.info("partUrl : " + partUrl);
					freeBoardFileRepository.save(freeBoardFile);
					break;
					
					case "requestBoard":
					RequestBoardFile requestBoardFile = new RequestBoardFile();
					requestBoardFile.setRequestBoardId(requestBoard.getRequestBoardId());
					requestBoardFile.setOriginName(fileOriginName);
					requestBoardFile.setUploadPath(uploadRootPath);
					requestBoardFile.setSaveName(fileSaveName);
						
					String fullUrl2 = uploadRootPath + "\\" + fileSaveName;
					log.info("fullUrl2 : " + fullUrl2);
					requestBoardFile.setFullUrl(fullUrl2);
						
					String partUrl2 = "\\boardFileUpload\\" + fileSaveName;
						
					requestBoardFile.setPartUrl(partUrl2);
					log.info("partUrl2 : " + partUrl2);
					log.info(requestBoardFile.toString());
					requestBoardFileRepository.save(requestBoardFile);	
					break;
				    }
					
				}catch (Exception e) {
					log.info("Error file :" + name );
					failedFiles.add(name);
				}
				
			}
		}
	}
	
	
	//자유게시판 파일다운로드
	@GetMapping("/freeBoardFile")
	public String freeBoardFile(HttpServletRequest request,String fullUrl, HttpServletResponse response) throws Exception {
		
		log.info("freeBoardId : " + fullUrl);
		FreeBoardFile freeBoardFile = freeBoardFileRepository.findByfullUrl(fullUrl);
		log.info( "boardFileFullUrl :" + freeBoardFile);
		String uploadRootPath = request.getServletContext().getRealPath("/");
		log.info("uploadRootPath :" + uploadRootPath);
		
		final DefaultResourceLoader defaultresourceloader = new DefaultResourceLoader();
		
		Resource resource = defaultresourceloader.getResource("file:src\\main\\resources\\static" + freeBoardFile.getPartUrl());
		
		log.info("resource :" + resource);
		log.info("resource path :" + resource.getFile().getAbsolutePath());
		
		File file = new File(resource.getFile().getAbsolutePath());
		log.info("file :" + file);
		BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));
		
		String header =  request.getHeader("User-Agent");
		String fileName;
		
		if((header.contains("MSIE")) || (header.contains("Trident")) || (header.contains("Edge"))) {
			fileName = URLEncoder.encode(freeBoardFile.getOriginName(), "UTF-8");
		}else {
			fileName = new String(freeBoardFile.getOriginName().getBytes("UTF-8"),"iso-8859-1" );
		}
		
		response.setContentType("applicaiton/octet-stream");
		response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
		OutputStream outputStream = response.getOutputStream();
		FileCopyUtils.copy(in, outputStream);
		in.close();
		outputStream.flush();
		outputStream.close();
		return "board/viewBoard";
	}
	
	//스터디요청게시판 파일다운로드
	@GetMapping("/requestBoardFile")
	public String requestBoardFile(HttpServletRequest request,String fullUrl, HttpServletResponse response) throws Exception {
		
		log.info("requestBoardId : " + fullUrl);
		RequestBoardFile reqeustBoardFile = requestBoardFileRepository.findByfullUrl(fullUrl);
		log.info( "boardFileFullUrl :" + reqeustBoardFile);
		String uploadRootPath = request.getServletContext().getRealPath("/");
		log.info("uploadRootPath :" + uploadRootPath);
		
		final DefaultResourceLoader defaultresourceloader = new DefaultResourceLoader();
		
		Resource resource = defaultresourceloader.getResource("file:src\\main\\resources\\static" + reqeustBoardFile.getPartUrl());
		
		log.info("resource :" + resource);
		log.info("resource path :" + resource.getFile().getAbsolutePath());
		
		File file = new File(resource.getFile().getAbsolutePath());
		log.info("file :" + file);
		BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));
		
		String header =  request.getHeader("User-Agent");
		String fileName;
		
		if((header.contains("MSIE")) || (header.contains("Trident")) || (header.contains("Edge"))) {
			fileName = URLEncoder.encode(reqeustBoardFile.getOriginName(), "UTF-8");
		}else {
			fileName = new String(reqeustBoardFile.getOriginName().getBytes("UTF-8"),"iso-8859-1" );
		}
		
		response.setContentType("applicaiton/octet-stream");
		response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
		OutputStream outputStream = response.getOutputStream();
		FileCopyUtils.copy(in, outputStream);
		in.close();
		outputStream.flush();
		outputStream.close();
		return "board/viewBoard";
	}
	
	//파일업로드 삭제
	@GetMapping("/boardFileDelete")
	@ResponseBody
	public List<FreeBoardFile> boardFileDelete(int freeBoardFileId,int freeBoardId) throws Exception {
		
		log.info("freeBoardFileId:" +freeBoardFileId);
		log.info("freeBoardId:" +freeBoardId);
		log.info("파일삭제 시작");
		FreeBoardFile freeBoardFile = freeBoardFileRepository.findById(freeBoardFileId).get();
		freeBoardFileRepository.deleteById(freeBoardFileId);
		final DefaultResourceLoader defaultresourceloader = new DefaultResourceLoader();
		
		Resource resource = defaultresourceloader.getResource("file:src\\main\\resources\\static" + freeBoardFile.getPartUrl());
		
		//파일 저장 위치가 사람마다 다르기 때문에 get resource를 받아와 이용자에 맞는 절대경로로 반환해준다.
		log.info("resource :" + resource); 
		
		log.info("resource 경로 :" + resource.getFile().getAbsolutePath());
		
		File file = new File(resource.getFile().getAbsolutePath());
		
		log.info("file :" + file);
		
		if(file.exists()) {
			if(file.isDirectory()) {
				File[] files = file.listFiles();
				for (int i=0; i<file.length(); i++) {
					if(files[i].delete()) {
						log.info(files[i].getName() + "삭제성공");
					}else {
						log.info(files[i].getName() + "삭제실패");
					}
				}
			}
			if(file.delete()) {
				log.info("파일삭제 성공");
			}else {
				log.info("파일삭제 실패");
			}
		}else {
			log.info("파일이 존재하지 않습니다");
		}
		
		FreeBoard freeBoard = freeBoardRepository.findById(freeBoardId).get();
		List<FreeBoardFile> freeBoardFileInfo = freeBoard.getFreeBoardFile();
		return freeBoardFileInfo;

	}
	
	//요청게시판 파일업로드 삭제
		@GetMapping("/requestBoardFileDelete")
		@ResponseBody
		public List<RequestBoardFile> requestBoardFileDelete(int requestBoardFileId,int requestBoardId) throws Exception {
			
			log.info("requestBoardFileId:" +requestBoardFileId);
			log.info("requestBoardId:" +requestBoardId);
			log.info("파일삭제 시작");
			RequestBoardFile requestBoardFile = requestBoardFileRepository.findById(requestBoardFileId).get();
			requestBoardFileRepository.deleteById(requestBoardFileId);
			final DefaultResourceLoader defaultresourceloader = new DefaultResourceLoader();
			
			Resource resource = defaultresourceloader.getResource("file:src\\main\\resources\\static" + requestBoardFile.getPartUrl());
			
			//파일 저장 위치가 사람마다 다르기 때문에 get resource를 받아와 이용자에 맞는 절대경로로 반환해준다.
			log.info("resource :" + resource); 
			
			log.info("resource 경로 :" + resource.getFile().getAbsolutePath());
			
			File file = new File(resource.getFile().getAbsolutePath());
			
			log.info("file :" + file);
			
			if(file.exists()) {
				if(file.isDirectory()) {
					File[] files = file.listFiles();
					for (int i=0; i<file.length(); i++) {
						if(files[i].delete()) {
							log.info(files[i].getName() + "삭제성공");
						}else {
							log.info(files[i].getName() + "삭제실패");
						}
					}
				}
				if(file.delete()) {
					log.info("파일삭제 성공");
				}else {
					log.info("파일삭제 실패");
				}
			}else {
				log.info("파일이 존재하지 않습니다");
			}
			
			RequestBoard requestBoard = requestBoardRepository.findById(requestBoardId).get();
			List<RequestBoardFile> requestBoardFileInfo = requestBoard.getRequestBoardFile();
			return requestBoardFileInfo;

		}

		//글확인 및 조회수
		@GetMapping("/viewBoard")
	    public String viewFreeBoard(String boardType,FreeBoard freeBoard,RequestBoard requestBoard,InquireBoard inquireBoard, Model model) {
			 
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		    String id = auth.getName();
		    
			    
		    model.addAttribute("memberId",id);
		    
		   try {
			
		
			switch(boardType) {
			case "freeBoard" : 
			
				FreeBoard freeBoardHit = freeBoardRepository.findById(freeBoard.getFreeBoardId()).get();
				freeBoardHit.setHit(freeBoardHit.getHit() + 1);
				freeBoardRepository.save(freeBoardHit);
				freeBoardRepository.findById(freeBoard.getFreeBoardId()).ifPresent(board-> model.addAttribute("freeBoard", board));
				int fileCount = freeBoardRepository.fileCount(freeBoard.getFreeBoardId());
				model.addAttribute("fileExist", fileCount);
				
				return "board/viewFreeBoard";
			
			case "requestBoard":
				RequestBoard requestBoardHit = requestBoardRepository.findById(requestBoard.getRequestBoardId()).get();
				requestBoardHit.setHit(requestBoardHit.getHit() + 1);
				requestBoardRepository.save(requestBoardHit);
				requestBoardRepository.findById(requestBoard.getRequestBoardId()).ifPresent(board-> model.addAttribute("requestBoard", board));
				int fileCount2 = requestBoardRepository.fileCount(requestBoard.getRequestBoardId());
				model.addAttribute("fileExist", fileCount2);
				return "board/viewRequestBoard";
				
			case "inquireBoard":
				InquireBoard inquireBoardHit = inquireBoardRepository.findById(inquireBoard.getInquireBoardId()).get();
				inquireBoardHit.setHit(inquireBoardHit.getHit() + 1);
				inquireBoardRepository.save(inquireBoardHit);
				inquireBoardRepository.findById(inquireBoard.getInquireBoardId()).ifPresent(board-> model.addAttribute("inquireBoard", board));
				return "board/viewInquireBoard";
			}
				
		   } catch (Exception e) {
			   FreeBoard freeBoardHit = freeBoardRepository.findById(freeBoard.getFreeBoardId()).get();
				freeBoardHit.setHit(freeBoardHit.getHit() + 1);
				freeBoardRepository.save(freeBoardHit);
				freeBoardRepository.findById(freeBoard.getFreeBoardId()).ifPresent(board-> model.addAttribute("freeBoard", board));
				int fileCount = freeBoardRepository.fileCount(freeBoard.getFreeBoardId());
				model.addAttribute("fileExist", fileCount);
				
				return "board/viewFreeBoard";
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
	public String modifyRegister(String boardType,RequestBoard requestBoard,InquireBoard inquireBoard,FreeBoard freeBoard, Model model,MultipartHttpServletRequest request,MultipartFile file) throws Exception {
		
		
		log.info(inquireBoard.toString());
		switch(boardType) {
		
		case "freeBoard":
			FreeBoard freeBoardModify = freeBoardRepository.findById(freeBoard.getFreeBoardId()).get();
			freeBoardModify.setTitle(freeBoard.getTitle());
			freeBoardModify.setContent(freeBoard.getContent());
			freeBoardRepository.save(freeBoardModify);	
			List<MultipartFile> uploadFileList = request.getFiles("file");
			doUpload(request,uploadFileList,freeBoard,requestBoard, inquireBoard,boardType);
			return "redirect:/board/listFreeBoard";
		case "requestBoard":
			RequestBoard requestBoardModify = requestBoardRepository.findById(requestBoard.getRequestBoardId()).get();
			requestBoardModify.setTitle(requestBoard.getTitle());
			requestBoardModify.setContent(requestBoard.getContent());
			requestBoardRepository.save(requestBoardModify);
			List<MultipartFile> uploadFileList2 = request.getFiles("file");
			doUpload(request,uploadFileList2,freeBoard,requestBoard, inquireBoard,boardType);
			return "redirect:/board/listRequestBoard";

		}
		
		return "redirect:/board/listFreeBoard";
	}
	
	
	//문의게시판 수정
	@PostMapping("/modifyInquireRegister")
	public String modifyInquireRegister(InquireBoard inquireBoard) {
		
		InquireBoard inquireBoardModify = inquireBoardRepository.findById(inquireBoard.getInquireBoardId()).get();
		inquireBoardModify.setTitle(inquireBoard.getTitle());
		inquireBoardModify.setContent(inquireBoard.getContent());
		log.info(inquireBoardModify.toString());
		inquireBoardRepository.save(inquireBoardModify);	
		
		return "redirect:/board/listInquireBoard";
		
	}
	
    //삭제
	@GetMapping("/deleteBoard")
	public String deleteFreeBoard(String boardType,FreeBoard freeBoard,RequestBoard requestBoard,InquireBoard inquireBoard) {
		

		switch(boardType) {
		case "freeBoard" :
			FreeBoard freeBoardDelete = freeBoardRepository.findById(freeBoard.getFreeBoardId()).get();
			freeBoardDelete.setStatus(1);
			freeBoardRepository.save(freeBoardDelete);
			return "redirect:/board/listFreeBoard";
		case "requestBoard" :
			RequestBoard requestBoardDelete = requestBoardRepository.findById(requestBoard.getRequestBoardId()).get();
			requestBoardDelete.setStatus(1);
			requestBoardRepository.save(requestBoardDelete);
			return "redirect:/board/listRequestBoard";
			
		case "inquireBoard" :
			InquireBoard inquireBoardDelete = inquireBoardRepository.findById(inquireBoard.getInquireBoardId()).get();
			inquireBoardDelete.setStatus(1);
			inquireBoardRepository.save(inquireBoardDelete);
			return "redirect:/board/listInquireBoard";
		}
			
		return "redirect:/board/listFreeBoard";
	}
	
	//자유게시판 이전글로 이동
	@GetMapping("/goBeforeFreePage")
	public String goBeforeFreePage(int freeBoardId, Model model) {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    String id = auth.getName();
	    model.addAttribute("memberId",id);
	    
		try {
			log.info(freeBoardId+"");
			FreeBoard beforeBoard = freeBoardRepository.beForeBoard(freeBoardId);
			log.info(beforeBoard.toString());
			model.addAttribute("freeBoard",beforeBoard);
			
			int fileCount = freeBoardRepository.fileCount(beforeBoard.getFreeBoardId());
			model.addAttribute("fileExist", fileCount);
			
		} catch (Exception e) {
			FreeBoard freeBoard = freeBoardRepository.findById(freeBoardId).get();
			model.addAttribute("freeBoard",freeBoard);
			
			int fileCount = freeBoardRepository.fileCount(freeBoard.getFreeBoardId());
			model.addAttribute("fileExist", fileCount);
			return "board/viewFreeBoard";
		}
		
		
		return "board/viewFreeBoard";
		
	}
	
	//요청게시판 이전글로 이동
		@GetMapping("/goBeforeRequestPage")
		public String goBeforeRequestPage(int requestBoardId, Model model) {
			
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		    String id = auth.getName();
		    model.addAttribute("memberId",id);
		    
			try {
				log.info(requestBoardId+"");
				RequestBoard beforeBoard = requestBoardRepository.beForeBoard(requestBoardId);
				log.info(beforeBoard.toString());
				model.addAttribute("requestBoard",beforeBoard);
				
				int fileCount = requestBoardRepository.fileCount(beforeBoard.getRequestBoardId());
				model.addAttribute("fileExist", fileCount);
				
			} catch (Exception e) {
				RequestBoard requestBoard = requestBoardRepository.findById(requestBoardId).get();
				model.addAttribute("requestBoard",requestBoard);
				
				int fileCount = requestBoardRepository.fileCount(requestBoard.getRequestBoardId());
				model.addAttribute("fileExist", fileCount);
				return "board/viewRequestBoard";
			}
			
			
			return "board/viewRequestBoard";
			
		}
	
	//자유게시판 다음글로 이동
		@GetMapping("/goAfterFreePage")
		public String goAfterFreePage(int freeBoardId, Model model) {
			
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		    String id = auth.getName();
		    model.addAttribute("memberId",id);
		    
			try {
				log.info(freeBoardId+"");
				FreeBoard beAfterBoard = freeBoardRepository.beAfterBoard(freeBoardId);
				log.info(beAfterBoard.toString());
				model.addAttribute("freeBoard",beAfterBoard);
				
				int fileCount = freeBoardRepository.fileCount(beAfterBoard.getFreeBoardId());
				model.addAttribute("fileExist", fileCount);
				
			} catch (Exception e) {
				FreeBoard freeBoard = freeBoardRepository.findById(freeBoardId).get();
				model.addAttribute("freeBoard",freeBoard);
				
				int fileCount = freeBoardRepository.fileCount(freeBoard.getFreeBoardId());
				model.addAttribute("fileExist", fileCount);
				return "board/viewFreeBoard";
			}
			
			return "board/viewFreeBoard";
			
		}
		
		//요청게시판 다음글로 이동
		@GetMapping("/goAfterRequestePage")
		public String goAfterRequestePage(int requestBoardId, Model model) {
			
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		    String id = auth.getName();
		    model.addAttribute("memberId",id);
		    
			try {
				log.info(requestBoardId+"requestBoardId");
				RequestBoard beAfterBoard = requestBoardRepository.beAfterBoard(requestBoardId);
				log.info(beAfterBoard.toString());
				model.addAttribute("requestBoard",beAfterBoard);
				
				int fileCount = requestBoardRepository.fileCount(beAfterBoard.getRequestBoardId());
				model.addAttribute("fileExist", fileCount);
				
			} catch (Exception e) {
				RequestBoard requestBoard = requestBoardRepository.findById(requestBoardId).get();
				model.addAttribute("requestBoard",requestBoard);
				
				int fileCount = requestBoardRepository.fileCount(requestBoard.getRequestBoardId());
				model.addAttribute("fileExist", fileCount);
				return "board/viewRequestBoard";
			}
			
			return "board/viewRequestBoard";
			
		}
	
	
	
	//글목록 갯수
	@GetMapping("/listWriteOption")
	public String listWriteOption(String boardType, String listCount, PageDTO pageDTO, Model model) {
		
				
		pageDTO.setSize(Integer.parseInt(listCount));
		
		log.info(pageDTO.toString());
		
		switch(boardType) {
		case "freeBoard":
			Pageable page = pageDTO.makePageable(0, "freeBoardId");
			Pageable noticePage = pageDTO.noticeMakePageable("notice", "freeBoardId");
			Page<FreeBoard> list = freeBoardRepository.findAll(freeBoardRepository.makePredicate(pageDTO.getSearchType(), pageDTO.getKeyword(), pageDTO.getSearchPeriod()),noticePage);
	       
			model.addAttribute("list", new PageMaker<>(list));
	        model.addAttribute("boardName", "자유게시판");
			return "board/listFreeBoard";
		case "requestBoard":			
			Pageable page1 = pageDTO.makePageable(0, "requestBoardId");
			Pageable noticePage2 = pageDTO.noticeMakePageable("notice", "requestBoardId");
			Page<RequestBoard> list1 = requestBoardRepository.findAll(requestBoardRepository.makePredicate(pageDTO.getSearchType(), pageDTO.getKeyword(), pageDTO.getSearchPeriod()),noticePage2);

			model.addAttribute("list", new PageMaker<>(list1));
			model.addAttribute("boardName", "스터디 요청게시판");
			return "board/listRequestBoard";
		case "inquireBoard":
			Pageable page2 = pageDTO.makePageable(0, "commentGroup");
			Page<InquireBoard> list2 = inquireBoardRepository.findAll(inquireBoardRepository.makePredicate(pageDTO.getSearchType(), pageDTO.getKeyword(), pageDTO.getSearchPeriod()),page2);

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
	
	//자유게시판 추천수
	@PostMapping("/likeCountFreeBoard")
	@ResponseBody
	public int likeCountFreeBoard(int freeBoardId, 	String memberId) {
		
		FreeBoard freeBoardLikeCount = freeBoardRepository.findById(freeBoardId).get();
		
		//먼저 게시판에 추천여부 확인 처음이면 객체생성
		try {
			log.info(freeBoardId+"");
			log.info(memberId);
			BoardLike boardlikeList = boardLikeRepository.findBoardLike(freeBoardId, memberId);
	       
			log.info(boardlikeList.getLikeCount()+"");
			//추천여부확인
			if(boardlikeList.getLikeCount()==1) {
			
			
			freeBoardLikeCount.setLikeCount(freeBoardLikeCount.getLikeCount()-1);
			freeBoardRepository.save(freeBoardLikeCount);	
			
			boardlikeList.setLikeCount(0);
			boardLikeRepository.save(boardlikeList);
			
			log.info(freeBoardLikeCount.getLikeCount()+"");
			return freeBoardLikeCount.getLikeCount();
			}
			else {
				
			freeBoardLikeCount.setLikeCount(freeBoardLikeCount.getLikeCount()+1);
			freeBoardRepository.save(freeBoardLikeCount);
			
			boardlikeList.setLikeCount(1);
			boardLikeRepository.save(boardlikeList);
			log.info(freeBoardLikeCount.getLikeCount()+"");
			return freeBoardLikeCount.getLikeCount();
				
			}

		//처음이면 추천객체 생성
		} catch (Exception e) {
			
			BoardLike boardLike = new BoardLike();
			boardLike.setBoardId(freeBoardId);
			boardLike.setBoardWriter(memberId);
			boardLike.setLikeCount(1);
			boardLikeRepository.save(boardLike);
			
		
			freeBoardLikeCount.setLikeCount(freeBoardLikeCount.getLikeCount()+1);
			freeBoardRepository.save(freeBoardLikeCount);	
			log.info(freeBoardLikeCount.getLikeCount()+"");
			return freeBoardLikeCount.getLikeCount();
		}

	}
	
	//요청게시판 추천수
		@PostMapping("/likeCountRequestBoard")
		@ResponseBody
		public int likeCountRequestBoard(int requestBoardId, String memberId) {
			
			RequestBoard requestBoardLikeCount = requestBoardRepository.findById(requestBoardId).get();
			
			//먼저 게시판에 추천여부 확인 처음이면 객체생성
			try {
				log.info(requestBoardId+"requestBoardId");
				log.info("memberId :"+ memberId);
				BoardLike boardlikeList = boardLikeRepository.findBoardLike(requestBoardId, memberId);
		       
				log.info(boardlikeList.getLikeCount()+"");
				//추천여부확인
				if(boardlikeList.getLikeCount()==1) {
				
				
				requestBoardLikeCount.setLikeCount(requestBoardLikeCount.getLikeCount()-1);
				requestBoardRepository.save(requestBoardLikeCount);	
				
				boardlikeList.setLikeCount(0);
				boardLikeRepository.save(boardlikeList);
				
				log.info(requestBoardLikeCount.getLikeCount()+"");
				return requestBoardLikeCount.getLikeCount();
				}
				else {
					
				requestBoardLikeCount.setLikeCount(requestBoardLikeCount.getLikeCount()+1);
				requestBoardRepository.save(requestBoardLikeCount);
				
				boardlikeList.setLikeCount(1);
				boardLikeRepository.save(boardlikeList);
				log.info(requestBoardLikeCount.getLikeCount()+"");
				return requestBoardLikeCount.getLikeCount();
					
				}

			//처음이면 추천객체 생성
			} catch (Exception e) {
				
				BoardLike boardLike = new BoardLike();
				boardLike.setBoardId(requestBoardId);
				boardLike.setBoardWriter(memberId);
				boardLike.setLikeCount(1);
				boardLikeRepository.save(boardLike);
				
			
				requestBoardLikeCount.setLikeCount(requestBoardLikeCount.getLikeCount()+1);
				requestBoardRepository.save(requestBoardLikeCount);	
				log.info(requestBoardLikeCount.getLikeCount()+"");
				return requestBoardLikeCount.getLikeCount();
			}

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
	
	
	//신고하기
	@PostMapping("/report")
	@ResponseBody
	public int boardReport(Model model, String id,String target,String reportTypeCD,int reportWhyCD,String content) {
   
  
     
     
     Report report = new Report();
     report.setId(id);
     report.setTarget(target);

     report.setReportTypeCD(new ReportTypeCD(reportTypeCD));
     report.setReportWhyCD(new ReportWhyCD(reportWhyCD));
     report.setContent(content);
     log.info(report.toString());
     ReportRepository.save(report);
     
	      
		return 1;
      
	}
	
	//공지숨기기
/*	@GetMapping("/listNoticeOption")
	public String listNoticeOption(String boardType,PageDTO pageDTO, Model model) {
		
		switch(boardType) {
		case "freeBoard":
			Pageable page = pageDTO.makePageable(0, "freeBoardId");
			Page<FreeBoard> list = freeBoardRepository.findAll(freeBoardRepository.noticePredicate(pageDTO.getSearchType(), pageDTO.getKeyword(), pageDTO.getSearchPeriod()),page);
	       
			model.addAttribute("list", new PageMaker<>(list));
	        model.addAttribute("boardName", "자유게시판");
			return "board/listFreeBoard";
		case "requestBoard":			
			Pageable page1 = pageDTO.makePageable(0, "requestBoardId");
			Page<RequestBoard> list1 = requestBoardRepository.findAll(requestBoardRepository.makePredicate(pageDTO.getSearchType(), pageDTO.getKeyword()),page1);

			model.addAttribute("list", new PageMaker<>(list1));
			model.addAttribute("boardName", "스터디 요청게시판");
			return "board/listRequestBoard";
		}
		
		return "board/listFreeBoard";

}*/
}
	
	

