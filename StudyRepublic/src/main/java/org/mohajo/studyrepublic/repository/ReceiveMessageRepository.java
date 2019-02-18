package org.mohajo.studyrepublic.repository;
import java.util.List;

/**
 * @author 김준석
 * @since 2019.01.23
 * @version 0.0
 * -
 * 
 * 
 */
import org.mohajo.studyrepublic.domain.ReceiveMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ReceiveMessageRepository extends JpaRepository<ReceiveMessage, Integer> {
	
	/*받은쪽지함*/
	@Query(value="select * from message_receive where receive_id = :id ORDER BY message_date DESC" ,nativeQuery=true)
	List<ReceiveMessage> findreceiveById(String id);
	

}
