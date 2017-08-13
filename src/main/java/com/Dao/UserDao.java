package com.Dao;

import java.util.List;

import com.model.User;

public interface UserDao {

	public void userRegister(User user);
	
	List<User> getAllUser();
	
	public User login(User user);
	
	public User updateUser(User validUser);
	
}
