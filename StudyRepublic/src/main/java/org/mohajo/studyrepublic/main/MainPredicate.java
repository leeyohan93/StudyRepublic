/**
 * 
 */
package org.mohajo.studyrepublic.main;



import org.mohajo.studyrepublic.domain.QReview;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;

/**
 * @author 이요한
 * @since 2019. 1. 27.
 * @version 0.0
 * -기능 설명1
 */
public class MainPredicate {

	
	public static Predicate searchPopularPremiumStudy() {
		
		BooleanBuilder builder = new BooleanBuilder();
		
		QReview review = QReview.review;
		
		
		return null;
	}
}
