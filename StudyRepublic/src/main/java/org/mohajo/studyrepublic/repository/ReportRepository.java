package org.mohajo.studyrepublic.repository;

import org.mohajo.studyrepublic.domain.Report;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author 이요한
 * @since 2019.01.22
 * @version
 * - ReportRepository추가
 * 
 */
public interface ReportRepository extends JpaRepository<Report, Integer>{

}
