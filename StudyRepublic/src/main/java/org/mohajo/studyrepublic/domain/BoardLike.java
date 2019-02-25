/**
 * 
 */
package org.mohajo.studyrepublic.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * @author 윤원식
 * @since 2019. 2. 23.
 * @version
 * -기능설명1
 */
@Data
@Entity
@Table(name="boardlike")
public class BoardLike {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name ="boardlike_id")
	private int boardLikeId;
	
	@Column(name="board_writer")
	private String boardWriter;
	
	@Column(name="board_id")
	private int boardId;

    @Column(name="like_count")
    private int likeCount;
	
}
