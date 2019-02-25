package org.mohajo.studyrepublic.repository;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.mohajo.studyrepublic.domain.FreeBoard;
import org.mohajo.studyrepublic.domain.QFreeBoard;
import org.mohajo.studyrepublic.domain.QReceiveMessage;
/**
 * @author 김준석
 * @since 2019.01.23
 * @version 0.0
 * -
 * 
 * 
 */
import org.mohajo.studyrepublic.domain.ReceiveMessage;
import org.mohajo.studyrepublic.domain.SendMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;

public interface ReceiveMessageRepository extends JpaRepository<ReceiveMessage, Integer>, QuerydslPredicateExecutor<ReceiveMessage> {
	
/*	public default Predicate makePredicate(String id) {

		BooleanBuilder builder = new BooleanBuilder();

		QReceiveMessage receiveMessage= QReceiveMessage.receiveMessage;

		 builder.and(receiveMessage.messageDelete.eq("0"));
		
		return builder;
	}
*/
	
	/*받은쪽지함*/
/*@Query(value="select *,(select nickname from member where id = send_id ) as send_nickname,"
		+ "(select nickname from member where id = receive_id) as receive_nickname "
		+ "from message_receive where receive_id= :id and message_receive_id= : messageReceiveId",nativeQuery=true);*/
@Query(value="select * from message_receive where receive_id= :id and message_delete ='0' ORDER BY message_date DESC",nativeQuery=true)
	public Page<ReceiveMessage> findreceiveById(String id, Pageable paging);
	
	/*받은메시지함 해당 내용 상세보기// 키값은 message_receive_id*/
	@Query(value="select * from message_receive where message_receive_id=:messageReceiveId and receive_id=:id", nativeQuery=true)
	List<ReceiveMessage> findByMessageReceiveId(int messageReceiveId,String id);



	
	
	
	

}
