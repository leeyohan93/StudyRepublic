package org.mohajo.studyrepublic.repository;

import org.mohajo.studyrepublic.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author	이미연
 * @since	2019. 1. 24.
 * @version	0.0
 * - 기능 설명 1
 */
public interface MemberRepository extends JpaRepository<Member, String> {

}
