package com.model;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name="blogcomment")
public class Comment {
	@Id
	@GeneratedValue
	private int comment_id;
	
	private int blog_id;
	
	private String comment_by;
	@Lob
    private String comments;
	
	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	private String comment_on;	
	
	private String comment_byperson;

	public int getComment_id() {
		return comment_id;
	}

	public void setComment_id(int comment_id) {
		this.comment_id = comment_id;
	}

	public int getBlog_id() {
		return blog_id;
	}

	public void setBlog_id(int blog_id) {
		this.blog_id = blog_id;
	}

	public String getComment_by() {
		return comment_by;
	}

	public void setComment_by(String comment_by) {
		this.comment_by = comment_by;
	}

	
	public String getComment_on() {
		return comment_on;
	}

	public void setComment_on(String comment_on) {
		this.comment_on = comment_on;
	}

	public String getComment_byperson() {
		return comment_byperson;
	}

	public void setComment_byperson(String comment_byperson) {
		this.comment_byperson = comment_byperson;
	}
	
	
	

}
