/**
 * 
 */
package org.mohajo.studyrepublic.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;


/**
 * @author 윤원식
 * @since 2019. 1. 23.
 * @version
 * -Board 기초클래스 추가
 */


@Data
@MappedSuperclass
public class Board {

   protected String id;
   protected String title;
   protected String content;
   
   @Temporal(TemporalType.TIMESTAMP)
   protected Date date = new Date();
   protected int notice;
   protected int status;
   protected int hit;
   @Column(name = "likecount")
   protected int likeCount;
   @Column(name = "replycount")
   protected int replyCount;
   
}
