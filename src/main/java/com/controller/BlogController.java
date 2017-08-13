package com.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.model.Error;
import com.Dao.BlogDao;
import com.model.Blog;
import com.model.User;

@RestController
public class BlogController {
	
	
	
	@Autowired
	BlogDao blogdao;
	
	boolean update=false;
	
	
	@RequestMapping(value="/addblog", method=RequestMethod.POST)
	public ResponseEntity<?> addBlog(@RequestBody Blog blog , HttpSession session){
		
		User user=(User) session.getAttribute("user");
		if(user==null){
			Error error = new Error(3, "Please login and try again later");
			return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);
		}
	    else{
	    	DateFormat dateformat=new SimpleDateFormat();
	    	Date date=new Date();
	    	blog.setPosted_by(user.getEmail_id());
	    	blog.setPosted_on(dateformat.format(date));
	    	blog.setApproved('N');
	    	boolean issave=blogdao.saveBlog(blog);
	    	
	    	if(issave==true){
	    		
	    		return new ResponseEntity<Boolean>(HttpStatus.OK);
	    	}
	    
	    		Error error = new Error(4, "Blog is not saved");
	    		return new ResponseEntity<Error>(error,HttpStatus.INTERNAL_SERVER_ERROR);
	
	    }
		
	}
	
	@RequestMapping(value="/getbloguser" , method=RequestMethod.GET)
	public ResponseEntity<?> getAllBlockForUser(HttpSession session){
		
		User user = (User)session.getAttribute("user");
		if(user==null){
			Error error = new Error(4, "Please login and try again later");
			return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);
		}
		
		List bloguser=blogdao.getAllBlogUser();
		return new ResponseEntity<List>(bloguser,HttpStatus.OK);
		
	}
	
	@RequestMapping(value="/getblogadmin",method=RequestMethod.GET)
	public ResponseEntity<?> getAllBlogForAdmin(HttpSession session){
		
		User user=(User) session.getAttribute("user");
		if(user==null){
			Error error = new Error(4, "Please login and try again later");
			return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);
		}
		
		List blogadmin=blogdao.getAllBlogAdmin();
		return new ResponseEntity<List>(blogadmin, HttpStatus.OK);
		
	}
	
	
	@RequestMapping(value="/updateblog", method=RequestMethod.POST)
	public ResponseEntity<?> updateBlog(@RequestBody Blog blog,HttpSession session){
		
		User user=(User) session.getAttribute("user");
		if(user==null){
			Error error = new Error(4, "Please login and try again later");
			return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);
		}
		
		blogdao.updateBlog(blog);
		return new ResponseEntity<Boolean>(true ,HttpStatus.OK);
		
	}
	
	
	@RequestMapping(value="/enableblog", method=RequestMethod.GET)
	public ResponseEntity<?> enableBlog(@RequestParam("blogid") int id, @RequestParam("status") char status,HttpSession session){
		
		User user=(User) session.getAttribute("user");
		
		if(user==null){
			Error error = new Error(4, "Please login and try again later");
			return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);
		}
		
		boolean b=blogdao.enableBlog(id, status);
		if(b==true){
			return new ResponseEntity<Boolean>(true,HttpStatus.OK);
		}
		else{
			Error error = new Error(4, "Blog is not approved");
    		return new ResponseEntity<Error>(error,HttpStatus.INTERNAL_SERVER_ERROR);

		}
		
		
		
	}
	
	

}
