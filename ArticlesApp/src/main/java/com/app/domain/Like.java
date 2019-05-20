package com.app.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Like {

	@Id
	@GeneratedValue
	@Column(name="Like_ID")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="Comment_ID")
	private Comment Comment;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Comment getComment() {
		return Comment;
	}
	public void setComment(Comment Comment) {
		this.Comment = Comment;
	}
	
	@Override
	public String toString() {
		return getId() + ", " + getComment();
	}
	
}
