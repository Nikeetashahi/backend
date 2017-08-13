package com.Dao;

import java.util.List;

import com.model.Job;

public interface JobDao {
	
	void saveJob( Job job);
	
	List<Job> getAllJobs();
	
	public boolean updateJob(Job job);
	
	public boolean getApprovedJob();
	
	public Job getJobById(int id);

}
