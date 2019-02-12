package org.mohajo.studyrepublic.repository;
import java.util.List;

import org.mohajo.studyrepublic.domain.ReceiveMessage;
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
import org.springframework.data.jpa.repository.Query;

public interface SendMessageRepository extends JpaRepository<SendMessage, Integer> {
	
	
	/*보낸쪽지함*/
	@Query(value="select * from message_send where send_id = :id" ,nativeQuery=true)
	List<SendMessage> findSendById(String id);
	
}
	
	
	


