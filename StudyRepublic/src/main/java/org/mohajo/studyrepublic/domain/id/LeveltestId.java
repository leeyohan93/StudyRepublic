package org.mohajo.studyrepublic.domain.id;

import java.io.Serializable;

import javax.persistence.Embeddable;

/**
 * @author	이미연
 * @since	2019. 1. 22.
 * @version	
 * - 기능 설명 1
 */
@Embeddable
public class LeveltestId implements Serializable {

	private int questionNumber;
	private String studyId;
}
