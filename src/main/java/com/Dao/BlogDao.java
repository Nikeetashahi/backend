package com.Dao;

import java.util.List;

import com.model.Blog;

public interface BlogDao {
	
	public boolean saveBlog(Blog blog);
	
	public List getAllBlogUser();
	
	public List getAllBlogAdmin();
	
	public boolean updateBlog(Blog blog);
	
	public boolean enableBlog(int id, char status);

}
