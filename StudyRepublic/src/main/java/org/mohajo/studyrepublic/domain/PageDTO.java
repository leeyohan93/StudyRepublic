/**
 * 
 */
package org.mohajo.studyrepublic.domain;



import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import lombok.Data;



/**
 * @author 윤원식
 * @since 2019. 1. 23.
 * @version
 * - Page 페이징에 필요한 도메인 추가
 * - 1.24 PageDTO로 이름수정
 */

@Data
public class PageDTO {


   /* (non-Javadoc)
    * @see java.lang.Object#toString()
    */
   public static final int DEFAULT_MAX_SIZE = 50;

   private int page;
//   private int DEFAULT_SIZE = 15;
//   private int size = DEFAULT_SIZE;
   private int size = 15;
   private String keyword;
   private String searchType;
   private String searchPeriod;


   public PageDTO( ) {
      this.page =1;
   }

   public Pageable makePageable(int direction, String... props) {

      Sort.Direction dir = direction == 0 ? Sort.Direction.DESC : Sort.Direction.ASC;

      return PageRequest.of(this.page - 1, this.size, dir, props);
   }

   public Pageable noticeMakePageable(String notice, String freeBoardId) {


      return PageRequest.of(this.page - 1, this.size, Sort.by(
            Sort.Order.desc(notice),
            Sort.Order.desc(freeBoardId)));
   }
   
   public Pageable studyMakePageable(int direction, int size, String... props) {

      Sort.Direction dir = direction == 0 ? Sort.Direction.DESC : Sort.Direction.ASC;

      return PageRequest.of(this.page - 1, size, dir, props);
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

		this.size = size > DEFAULT_MAX_SIZE ? 15 :size;

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


   public String getSearchPeriod() {
      return searchPeriod;
   }


   public void setSearchPeriod(String searchPeriod) {
      this.searchPeriod = searchPeriod;
   }


   @Override
   public String toString() {
      return "PageDTO [page=" + page + ", size=" + size + ", keyword=" + keyword + ", searchType=" + searchType
            + ", searchPeriod=" + searchPeriod + "]";
   }





}