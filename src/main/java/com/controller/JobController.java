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
import com.Dao.JobDao;
import com.model.Job;
import com.model.User;

@RestController
public class JobController {
	
	@Autowired
	JobDao jobdao;
	
	@RequestMapping(value="/savejob",method=RequestMethod.POST)
	public ResponseEntity<?> addJob(@RequestBody Job job, HttpSession session){
		
		User user=(User) session.getAttribute("user");
		if(user==null)
		
		{
			Error error=new Error(3,"UnAuthorized user");
			return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);
		}
		
		try
		
		{
		if(user.getRole().equals("admin"))
		
		{
		DateFormat dateformat = new SimpleDateFormat();
		Date date = new Date();
		job.setPosted_on(dateformat.format(date));
		job.setPosted_by(user.getEmail_id());
		jobdao.saveJob(job);
		return new ResponseEntity<Void>(HttpStatus.OK);
		}
		
		else
		
		{
			Error error = new Error(4,"Access Denied");
			return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);
		}
		}
		catch(Exception e){
			Error error=new Error(5, "Unable to insert job detail");
			return new ResponseEntity<Error>(error,HttpStatus.INTERNAL_SERVER_ERROR);
			
		}
	}
	
	@RequestMapping(value = "/alljobs", method = RequestMethod.GET)
	public ResponseEntity<?> listAllJobs(HttpSession session)	{
		User user=(User) session.getAttribute("user");
		if(user==null)
		
		{
			Error error=new Error(3,"UnAuthorized user");
			return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);
		}
		else
		{

		List<Job> lsts = jobdao.getAllJobs();
		if(lsts.isEmpty()){
			return new ResponseEntity<List<Job>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Job>>(lsts, HttpStatus.OK);
		}
	}
	
	
	@RequestMapping(value="/updatejob",method=RequestMethod.POST)
	public ResponseEntity<?> update(@RequestBody Job job, HttpSession session){
		User user=(User) session.getAttribute("user");
		if(user==null)
		
		{
			Error error=new Error(3,"UnAuthorized user");
			return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);
		}
		else
		{
		jobdao.updateJob(job);
		return new ResponseEntity<Boolean>(true ,HttpStatus.OK);
		}
	}
	
	
/*	@RequestMapping(value="/getjobbyid/{job_id}/", method=RequestMethod.GET)
	public ResponseEntity<?> getJobById(@PathVariable("job_id") int id, HttpSession session){
		
		User user = (User) session.getAttribute("user");
		if(user==null){
			Error error =new Error(3, "Unauthorized");
			return new ResponseEntity<Error>(error, HttpStatus.UNAUTHORIZED);
		}
	    Job job =jobdao.getJobById(id);
		return new ResponseEntity<Job>(job,HttpStatus.OK);
		
	}*/
	

}
