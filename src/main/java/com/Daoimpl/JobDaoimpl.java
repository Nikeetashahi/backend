package com.Daoimpl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.Dao.JobDao;
import com.model.Job;
@Repository
public class JobDaoimpl implements JobDao {
	
	@Autowired
	SessionFactory sf;
	

	public void saveJob(Job job) {
		
		Session sess= sf.openSession();
		sess.save(job);
		sess.flush();
		sess.close();

		
	}

	public List<Job> getAllJobs() {
		
		Session sess= sf.openSession();
		Query query=(Query) sess.createQuery("FROM Job");
		List<Job> job=query.list();
		sess.flush();
		sess.close();		
		return job;
	}

	public boolean updateJob(Job job) {
		
		Session sess=sf.openSession();
		sess.update(job);
		sess.flush();
		sess.clear();
		return true;
	}

	public boolean getApprovedJob() {
		
		Session sess=sf.openSession();
		Query query =sess.createQuery("From Job where position = 'T' ");
		List<Job> job=query.list();
		sess.flush();
		sess.close();
		return true;
	}

	public Job getJobById(int id) {
		
		Session sess=sf.openSession();
		Job job=(Job) sess.get(Job.class, id);
		sess.close();
		return job;
	}

}
