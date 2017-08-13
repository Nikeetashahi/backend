package com.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.model.Error;

import com.Dao.FriendDao;
import com.model.Friend;
import com.model.User;

@RestController
public class FriendController {
	
	@Autowired
	FriendDao frienddao;
	
	
	@RequestMapping(value="/addfriend/{emailid}/", method=RequestMethod.GET) // send friend request
	public ResponseEntity<?> addFriend(@PathVariable("emailid")String emailid, HttpSession session){
		User logeduser = (User) session.getAttribute("user");

		if(logeduser==null){
			Error error=new Error(3,"you are not loged in");
			return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);
			
		}
		else{
			Friend frnd=new Friend();
			frnd.setFrom_id(logeduser.getEmail_id());
			frnd.setTo_id(emailid);
			frnd.setStatus('P');
			boolean issaved=frienddao.sendFriendRequest(frnd);
			
			
		if(issaved==true){
			return new ResponseEntity<Boolean>(true,HttpStatus.OK);
		}
		else
		{
			Error error=new Error(4, "Something went wrong");
			return new ResponseEntity<Error>(error,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		}
		
	}
	
	@RequestMapping(value="/pendingrequest", method=RequestMethod.GET)
	public ResponseEntity<?> listoffriendrequest(HttpSession session){
		
		User user = (User) session.getAttribute("user");
		
		if(user==null){
			Error error = new Error(5,"At First u login your A/c to views friend");
			return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);
		}
		
		List pendingrequest= frienddao.listFriendRequest(user.getEmail_id());
		 
		return new ResponseEntity<List>(pendingrequest, HttpStatus.OK);
		
	}
	
	@RequestMapping(value="/listoffriend", method=RequestMethod.GET)
	public ResponseEntity<?> listOfFriend(HttpSession session){
		
		User user=(User) session.getAttribute("user");
		
		if(user==null){
			
			Error error = new Error(5,"At First u login your A/c to see Your Friend");
			return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);
		}
		
		List listoffriend = frienddao.listOfFriend(user.getEmail_id());
		return new ResponseEntity<List>(listoffriend , HttpStatus.OK);
		
		
	}
	
	
	
	@RequestMapping(value="/listofsuggestedfriend", method=RequestMethod.GET)
	public ResponseEntity<?> listOfsuggestFriend(HttpSession session){
		
		User user=(User) session.getAttribute("user");
		
		if(user==null){
			
			Error error = new Error(5,"At First u login your A/c to see Your suggested Friend");
			return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);
		}
		
		List<User> listoffriend = frienddao.listOfSuggestedUsers(user.getEmail_id());
		return new ResponseEntity<List<User>>(listoffriend, HttpStatus.OK);
		
		
	}
	
	
	@RequestMapping(value="/updaterequest/{fromid}/{status}", method=RequestMethod.GET)
	public ResponseEntity<?> updatePandingRequest(@PathVariable("fromid")String fromid, @PathVariable("status")char status, HttpSession session){
		
		User user= (User) session.getAttribute("user");
		
		if(user==null){
			Error error = new Error(5,"At First u login your A/c to update your Friend request");
			return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);
		}
		
		frienddao.updateFriendRequest(fromid, user.getEmail_id(), status);
		return new ResponseEntity<Boolean>(true,HttpStatus.OK);
		
	}
	
	
		
		


}