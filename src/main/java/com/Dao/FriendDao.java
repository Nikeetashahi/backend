package com.Dao;

import java.util.List;

import com.model.Friend;
import com.model.User;

public interface FriendDao {
	
	
	public boolean sendFriendRequest(Friend friend);
	
	public List listFriendRequest(String email);
	
	public List listOfFriend(String email);
		
	List<User> listOfSuggestedUsers(String email);
	
	public boolean updateFriendRequest(String fromid, String toid, char status);

}
