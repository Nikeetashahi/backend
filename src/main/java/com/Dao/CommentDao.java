package com.Dao;

import java.util.List;

import com.model.Comment;

public interface CommentDao {
	
	public boolean addComment(Comment comment);
	
	public List<Comment> getComments(int id);

}
