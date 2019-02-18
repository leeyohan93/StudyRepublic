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
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface SendMessageRepository extends JpaRepository<SendMessage, Integer> {
	
	
	/*보낸쪽지함*/
	@Query(value="select * from message_send where send_id = :id ORDER BY message_date DESC" ,nativeQuery=true)
	List<SendMessage> findSendById(String id);
	
	/*쪽지삭제 미완성*/
	@Modifying
	@Query(value="update message_send set message_delete ='1' where message_send_id = :message_send_id", nativeQuery = true)
	int sendmessagedelete(int message_send_id);

	
}
	
	
	


