/**
 * 
 */
package org.mohajo.studyrepublic.domain;

import lombok.Getter;
import lombok.Setter;

/**
 * @author 윤원식
 * @since 2019. 1. 23.
 * @version
 * - Page 페이징에 필요한 도메인 추가
 */

@Getter
@Setter
public class Page {
   
	
	private int page;
	private int size;
	private String keyword;
	private String searchType;
	private String searchPeriod;
}
