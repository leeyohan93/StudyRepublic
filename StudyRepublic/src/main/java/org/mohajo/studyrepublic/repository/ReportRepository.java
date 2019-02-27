package org.mohajo.studyrepublic.repository;

import java.util.List;

import org.mohajo.studyrepublic.domain.Report;
import org.mohajo.studyrepublic.domain.Study;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

/**
 * @author 이요한
 * @since 2019.01.22
 * @version
 * - ReportRepository추가
 * 
 */
public interface ReportRepository extends JpaRepository<Report, Integer>, QuerydslPredicateExecutor<Report>{

	@Query(value = "select s from Report s where s.reportId in ?1")
	List<Report> getSelectedReport(int[] selectedId);
}
