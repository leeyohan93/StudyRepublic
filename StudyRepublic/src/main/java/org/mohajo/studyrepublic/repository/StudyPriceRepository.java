package org.mohajo.studyrepublic.repository;

import org.mohajo.studyrepublic.domain.Study;
import org.mohajo.studyrepublic.domain.StudyPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

/**
 * @author	이미연
 * @since	2019. 2. 26.
 * @version	0.0
 * - 분명히 만들어서 사용했는데 갑자기 없어졌다... 무섭다... 두 번째 만드는 중
 */
public interface StudyPriceRepository extends JpaRepository<StudyPrice, String> {

}