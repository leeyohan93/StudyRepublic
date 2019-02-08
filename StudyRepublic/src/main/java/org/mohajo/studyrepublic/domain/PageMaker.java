
package org.mohajo.studyrepublic.domain;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import lombok.Getter;
import lombok.ToString;
import lombok.extern.java.Log;

/**
 * @author 윤원식
 * @since 2019. 1. 24.
 * @version
 * -페이징 번호 출력을 위한 PageMaker클래스 추가
 * @param <T>
 */

@Getter
@ToString(exclude ="pageList")
@Log
public class PageMaker<T> {

	private Page<T> list;

	private Pageable prevPage;
	private Pageable nextPage;

	private int currentPageNum;
	private int totalPageNum;

	private Pageable currentPage;

	private List<Pageable> pageList;

	public PageMaker(Page<T> list) {

		this.list = list;

		this.currentPage = list.getPageable();

		this.currentPageNum = currentPage.getPageNumber() + 1;	//Pageable 객체는 페이지 카운트를 0부터 시작. 뷰에서 제대로 된 값을 출력하기 위해 +1 처리.

		this.totalPageNum = list.getTotalPages();

		this.pageList = new ArrayList<>();

		calcPages();
	}

	private void calcPages() {

		int tempEndNum = (int)(Math.ceil(this.currentPageNum/10.0)*10);	//현재 페이지가 18 일 때, tempEndNum 은 20

		int startNum = tempEndNum -9;	//tempEndNum 이 20 일 때, startNum 은 11. 즉 ~Num 식 변수는 현재 페이지에서 11~20 까지의 페이지 번호를 출력하기 위한 요소임. 

		Pageable startPage = this.currentPage;


		for(int i = startNum; i< this.currentPageNum; i++) {
			startPage = startPage.previousOrFirst();
		}

		this.prevPage = startPage.getPageNumber() <= 0 ? null :startPage.previousOrFirst();


		if(this.totalPageNum < tempEndNum) {
			tempEndNum = this.totalPageNum;
			this.nextPage = null;
		}

		for(int i =startNum ; i <=tempEndNum; i++) {
			pageList.add(startPage);
			startPage = startPage.next();
		}

		this.nextPage = startPage.getPageNumber() + 1 < totalPageNum ? startPage : null;
	}


}
