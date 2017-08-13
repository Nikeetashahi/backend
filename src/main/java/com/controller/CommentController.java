package com.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.model.Error;
import com.Dao.CommentDao;
import com.model.Comment;
import com.model.User;

@RestController
public class CommentController {
	
	@Autowired
	CommentDao cmtdao;
	@RequestMapping(value="/savecomment", method=RequestMethod.POST)
	public ResponseEntity<?> addcomment(@RequestBody Comment cmt, HttpSession session){
		
		System.out.println(cmt.getBlog_id() + " "+ cmt.getComments() + " " + "hjhjhjhjhj");
		User user =(User) session.getAttribute("user");
		
		if(user==null){
			
			Error error =new Error(2,"Please login and try again later");
			return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);
			
		}else{
			DateFormat dateformat = new SimpleDateFormat();
			Date date = new Date();
			cmt.setComment_on(dateformat.format(date));
			cmt.setComment_by(user.getEmail_id());
			cmt.setComment_byperson(user.getName());
			boolean issave=cmtdao.addComment(cmt);
			if(issave){
				return new ResponseEntity<Boolean>(true,HttpStatus.OK);
			}else{
				Error error= new Error(3, "something went wrong");
				return new ResponseEntity<Error>(error, HttpStatus.UNAUTHORIZED);
			}
		}
		
	}
	
	
	@RequestMapping(value="/getcmnt/{blog_id}", method=RequestMethod.GET)
	public ResponseEntity<?> getcomment(@PathVariable("blog_id")int blog_id,HttpSession session){
		
		User user=(User) session.getAttribute("user");
		
		if(user==null){
			Error error=new Error(3,"Please login and try again later");
			return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);
		}
		else
		{
			List<Comment> listofcomment = cmtdao.getComments(blog_id);
			return new ResponseEntity<List<Comment>>(listofcomment,HttpStatus.OK);
			
		}
		
	}
	
	


}
