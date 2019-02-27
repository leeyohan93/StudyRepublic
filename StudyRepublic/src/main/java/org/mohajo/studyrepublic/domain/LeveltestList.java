package org.mohajo.studyrepublic.domain;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * @author	이미연
 * @since	2019. 1. 22.
 * @version	0.0
 * - 레벨테스트 DTO
 */
@Data
public class LeveltestList implements Serializable {

	private List<Leveltest> leveltests;
	
}
