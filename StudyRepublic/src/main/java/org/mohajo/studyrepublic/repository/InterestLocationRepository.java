/**
 * 
 */
package org.mohajo.studyrepublic.repository;

import java.util.List;

import org.mohajo.studyrepublic.domain.InterestLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @author	김준석
 * @since	2019. 2. 14.
 * @version	0.0
 * -기능1 추가
 * -기능2 추가
 * -기능3 추가
 */
public interface InterestLocationRepository extends JpaRepository<InterestLocation, Integer> {
	
	@Query(value ="select * from interest_location where id = :id",nativeQuery=true)
	List<InterestLocation> findInterestById(String id) ; 
	
	
	

}
