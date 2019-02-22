/**
 * 
 */
package org.mohajo.studyrepublic.domain;


import java.math.BigInteger;

import lombok.Data;

/**
 * @author 이요한
 * @since 2019. 2. 18.
 * @version 0.0
 * -기능 설명1
 */
@Data
public class AnalyticsDTO {
	/**
	 * @param string
	 * @param integer
	 */
	public AnalyticsDTO(String period, long count) {
		this.period=period;
		this.count=count;
	}
	private String period;
	private long count;
}
