/**
 * 
 */
package org.mohajo.studyrepublic.domain;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author 윤원식
 * @since 2019. 1. 23.
 * @version
 * -Board 기초클래스 추가
 */


@Getter
@Setter
@ToString
@MappedSuperclass
public class Board {

   protected String id;
   protected String title;
   protected String content;
   @CreationTimestamp
   protected Timestamp date;
   protected int notice;
   protected int status;
   protected int hit;
   @Column(name = "likecount")
   protected int likeCount;
   @Column(name = "replycount")
   protected int replyCount;
   
}
