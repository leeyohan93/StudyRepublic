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
import org.mohajo.studyrepublic.domain.Study;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.transaction.annotation.Transactional;

public interface SendMessageRepository extends JpaRepository<SendMessage, Integer>, QuerydslPredicateExecutor<SendMessageRepository> {
	
	/*보낸쪽지함*/
	@Query(value="select * from message_send where send_id = :id and message_delete= '0' ORDER BY message_date DESC" ,nativeQuery=true)
	public Page<SendMessage> findSendById(String id,Pageable paging);
	
	/*쪽지삭제 미완성*/
	

/*	@Transactional
	@Modifying
	@Query(value="update message_send set message_delete ='1' where message_send_id = :message_send_id", nativeQuery = true)
	int sendmessagedelete(int message_send_id);
	

	@Query(value="update message_send set message_delete ='1' where message_send_id = :messageSendId and send_id=:id", nativeQuery = true)
	void sendmessagedelete(int messageSendId,String id);*/
	
	/*보낸메시지함 해당 내용 상세보기// 키값은 message_send_id*/
	@Query(value="select * from message_send where message_send_id=:messageSendId and send_id=:id", nativeQuery=true)
	List<SendMessage> findByMessageSendId(int messageSendId,String id);

	@Query(value = "select s from SendMessage s where s.messageSendId in ?1")
	List<SendMessage> getSelectedMessage(int[] selectedId);

	
}
	
	
	


