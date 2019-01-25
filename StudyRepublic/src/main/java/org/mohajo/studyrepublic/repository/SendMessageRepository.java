package org.mohajo.studyrepublic.repository;
/**
 * @author 김준석
 * @since 2019.01.23
 * @version 0.0
 * -
 * 
 * 
 */
import org.mohajo.studyrepublic.domain.SendMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SendMessageRepository extends JpaRepository<SendMessage, Integer> {

}
