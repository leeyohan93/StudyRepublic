/**
 * 
 */
package org.mohajo.studyrepublic.domain;



import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;



/**
 * @author 윤원식
 * @since 2019. 1. 23.
 * @version
 * - Page 페이징에 필요한 도메인 추가
 * - 1.24 PageDTO로 이름수정
 */


public class PageDTO {
   
	public static final int DEFAULT_SIZE = 15;
	public static final int DEFAULT_MAX_SIZE = 50;
	
	
	private int page;
	private int size;
	private String keyword;
	private String searchType;
//	private String searchPeriod;

	
	public PageDTO( ) {
		this.page =1;
		this.size =DEFAULT_SIZE;
	}

	public Pageable makePageable(int direction, String... props) {
		
		Sort.Direction dir = direction == 0 ? Sort.Direction.DESC : Sort.Direction.ASC;
		
		return PageRequest.of(this.page - 1, this.size, dir, props);
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page < 0? 1 :page;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size < DEFAULT_SIZE || size > DEFAULT_MAX_SIZE ? DEFAULT_SIZE :size;
	}


	public String getKeyword() {
		return keyword;
	}


	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getSearchType() {
		return searchType;
	}
	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}



	
	
}
