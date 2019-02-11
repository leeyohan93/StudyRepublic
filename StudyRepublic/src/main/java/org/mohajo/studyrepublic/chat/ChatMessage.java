package org.mohajo.studyrepublic.chat;

import lombok.Data;

/**
 * @author 윤원식
 * @since 2019. 2. 5.
 * @version
 * -ChatMessage 클래스 추가
 */

@Data
public class ChatMessage {
    private MessageType type;
    private String content;
    private String sender;

    public enum MessageType {
        CHAT,
        JOIN,
        LEAVE
    }


    
    
}
