/**
 * 
 */
package org.mohajo.studyrepublic.studypage;

import java.util.Comparator;

import org.mohajo.studyrepublic.domain.LeveltestResponse;

/**
 * @author 신상용
 * @since 2019. 2. 25.
 * @version 0.0
 * - 기능설명
 */
public class LevelTestComparator implements Comparator<LeveltestResponse>{
	@Override
	public int compare(LeveltestResponse o1, LeveltestResponse o2) {
		int thisValue = o1.getLeveltest().getQuestionNumber();
		int inputValue = o2.getLeveltest().getQuestionNumber();
		if(thisValue < inputValue) {
			return -1;
		}else if(thisValue == inputValue) {
			return 0;
		}else {
			return 1;
		}
	}
}
