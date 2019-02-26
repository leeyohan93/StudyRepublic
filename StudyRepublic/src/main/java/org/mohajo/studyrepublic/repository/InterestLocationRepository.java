/**
 * 
 */
package org.mohajo.studyrepublic.repository;

import java.util.List;

import org.mohajo.studyrepublic.domain.InterestLocation;
import org.mohajo.studyrepublic.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

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
	
/*	@Transactional
	@Modifying
	@Query(value = "delete from interest_location where id = :id", nativeQuery=true)
	int deleteLocationById(String id);

	*//**
	 * @param memberlocation
	 * @param id
	 * @return
	 *//*
	@Query(value ="Insert into interest_location values(:interestLocation, :id)",nativeQuery=true)
	List<InterestLocation> save(String interestLocation, String id);*/
	
	@Modifying
	@Transactional
	@Query(value = "delete from interest_location where id = :id", nativeQuery = true)
	void deleteLocation(String id);

	/**
	 * @param interestLocation
	 */
	

	/**
	 * @param insertLocation
	 * @param member
	 * @return
	 */
/*	@Transactional
	@Modifying
	@Query(value = "insert into interest_location (interest_location, id) values(:interestLocation, :id)", nativeQuery=true)
	int insertLocation(String interestLocation, String id);*/
	
	
	
	

}
