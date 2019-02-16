/**
 * 
 */
package org.mohajo.studyrepublic.domain;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author 윤원식
 * @since 2019. 1. 23.
 * @version
 * -InquireBoard 도메인 추가
 */


@Getter
@Setter
@ToString
@Entity
@Table(name = "inquireboard")
public class InquireBoard{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "inquireboard_id")
	private int inquireBoardId;
	private int hide;
	@Column(name = "commentgroup")
	private int commentGroup;
	@Column(name = "commentstep")
	private int commentStep;
	@Column(name = "commentindent")
	private int commentIndent;

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

	@OneToMany(cascade=CascadeType.ALL)
	@JoinColumn(name="inquireboard_id")
	private List<InquireBoardReply> inquireBoardReply;

	@OneToMany
	@JoinColumn(name="inquireboard_id")
	private List<InquireBoardFile> inquireBoardFile;
	
	@ManyToOne
    @JoinColumn(name="id",insertable=false, updatable=false)
	protected Member member;




}
