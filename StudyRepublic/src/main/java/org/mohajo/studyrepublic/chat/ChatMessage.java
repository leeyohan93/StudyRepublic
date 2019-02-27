package org.mohajo.studyrepublic.chat;

import java.util.Date;
import java.util.List;

import lombok.Data;

/**
 * @author 윤원식
 * @since 2019. 2. 5.
 * @version
 * -ChatMessage 클래스 추가
 */

@Data
public class ChatMessage {

    private String content;
    private String sender;
    private String profile;
    private int userCount;
    private Date date;
    private List<String> userList;
    private List<String> userProfile;



    
    
}
