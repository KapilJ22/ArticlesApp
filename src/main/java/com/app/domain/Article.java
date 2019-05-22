package com.app.domain;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
public class Article {

	@Id
	@GeneratedValue
	@Column(name="Article_ID")
	private Long id;
	
	@Column(name="ArticleText")
	@NotEmpty
	private String ArticleText;
	
	@OneToMany(cascade=CascadeType.ALL)
	@JoinColumn(name="Article_ID")
	@OrderBy
	@Size(min=2, max = 6)
	private Set<Comment> Comments;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getArticleText() {
		return ArticleText;
	}
	public void setArticleText(String ArticleText) {
		this.ArticleText = ArticleText;
	}
	public Set<Comment> getComments() {
		return Comments;
	}
	public void setComments(Set<Comment> Comments) {
		this.Comments = Comments;
	}
	
	@Override
	public String toString() {
		return getId() + ", " + getArticleText() + ", " + getComments();
	}
	
}
