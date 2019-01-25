
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

		this.currentPageNum = currentPage.getPageNumber() + 1;

		this.totalPageNum = list.getTotalPages();

		this.pageList = new ArrayList<>();

		calcPages();
	}

	private void calcPages() {

		int tempEndNum = (int)(Math.ceil(this.currentPageNum/10.0)*10);

		int startNum = tempEndNum -9;

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
