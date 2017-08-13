package com.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;

@Entity
public class Blog {
	@Id
	@GeneratedValue
	private int id;
	
	private String blog_title;
	@Lob
	private String content;
	
	private String posted_by;
	
	private String posted_on;
	
	private  char approved; //approved by admin 'Y' & 'N'

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBlog_title() {
		return blog_title;
	}

	public void setBlog_title(String blog_title) {
		this.blog_title = blog_title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getPosted_by() {
		return posted_by;
	}

	public void setPosted_by(String posted_by) {
		this.posted_by = posted_by;
	}

	public String getPosted_on() {
		return posted_on;
	}

	public void setPosted_on(String posted_on) {
		this.posted_on = posted_on;
	}

	public char getApproved() {
		return approved;
	}

	public void setApproved(char approved) {
		this.approved = approved;
	}
	
	
	

}
