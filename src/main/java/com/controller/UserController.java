package com.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.Dao.UserDao;
import com.model.User;
import com.model.Error;
import javax.servlet.http.*;

@RestController
public class UserController {
	
	@Autowired
	UserDao userdao;
	
	/*
	 * ResponseEntity<T> --> T is a type of data
	 * ResponseEntity<Error> --> it will add error type data in is a type of data
	 * responseEntity<Void> --> Void data in Http response
	 * ResponceEntity<?> --> any type of data
	 */
	
	@RequestMapping(value="/registration", method=RequestMethod.POST)
	public ResponseEntity<?> addUser( @RequestBody User user)
	{
		try{
			user.setEnabled(true);
			user.setOnline(false);
			List<User> user1=userdao.getAllUser();
			if(user1 != null)
			{	
				for(User u:user1){
			
					if(u.getEmail_id().equals(user.getEmail_id())){
						Error error =new Error(2,"Email-id already exists");
						return new  ResponseEntity<Error>(error,HttpStatus.INTERNAL_SERVER_ERROR); 
					}
					
				}	
			}
			userdao.userRegister(user);
			return new ResponseEntity<Void>(HttpStatus.CREATED);
			
		}
		catch(Exception e){
			Error error = new Error(1,"Cannot register user details");
			return new ResponseEntity<Error>(error,HttpStatus.INTERNAL_SERVER_ERROR);
			
		}
	}
		
		@RequestMapping(value="/login",method=RequestMethod.POST)
		public ResponseEntity<?> login(@RequestBody User user1, HttpSession session){
			System.out.println("Is Session new for user "+user1.getEmail_id()+""+session.isNew());
			User validUser=userdao.login(user1);
			if(validUser==null){
				Error error=new Error(3,"Invalid email-id and password.. please enter valid credentials");
				return new ResponseEntity<Error>(error,HttpStatus.NO_CONTENT);
				
			}else{
				
					validUser.setOnline(true);
					validUser=userdao.updateUser(validUser);
					session.setAttribute("user", validUser);
					return new ResponseEntity<User>(validUser, HttpStatus.OK);
				}
		}
		@RequestMapping(value="/logout",method=RequestMethod.GET)
		public ResponseEntity<?> logout(HttpSession session){
			User user = (User)session.getAttribute("user");
			if(user == null){
				Error error =new Error(4,"Unauthorized User");
				return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);
				
			}
			else{
				System.out.println("Is session new for user" + user.getEmail_id()+" "+session.isNew());
				user.setOnline(false);
				userdao.updateUser(user);
				session.removeAttribute("user");
				session.invalidate();
				return new ResponseEntity<Void>(HttpStatus.OK);
			}
			
			
		}
		
		
		@RequestMapping(value="/update",method=RequestMethod.POST)
		public ResponseEntity<?> update(@RequestBody User user, HttpSession session){
			User user1=(User)session.getAttribute("user");
			if(user1==null){
				Error error=new Error(3,"Invalid email-id and password.. please enter valid credentials");
				return new ResponseEntity<Error>(error,HttpStatus.NO_CONTENT);
			}
			else
			{
			userdao.updateUser(user1);
			return new ResponseEntity<Boolean>(true ,HttpStatus.OK);
		}
		}
		
		
		@RequestMapping(value="/getuser", method=RequestMethod.GET)
		public ResponseEntity<?> getuser(){
		 List users = userdao.getAllUser();
		return new ResponseEntity<List>(users,HttpStatus.OK);
		
		}
		
}
		

