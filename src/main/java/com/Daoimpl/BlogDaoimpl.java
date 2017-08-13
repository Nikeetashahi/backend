package com.Daoimpl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.Dao.BlogDao;
import com.model.Blog;
@Repository
public class BlogDaoimpl implements BlogDao {
	
	@Autowired
	SessionFactory sf;

	public boolean saveBlog(Blog blog) {
		
		Session sess= sf.openSession();
		try{
			
			sess.save(blog);
			sess.flush();
			sess.close();
			return true;
		}catch(Exception e){
			
			sess.close();
			System.out.println("something went wrong");
			
		}
		
		

		return false;
	}

	public List getAllBlogUser() {
		
		Session sess=sf.openSession();
		try{
			Query query = sess.createQuery(" From Blog where approved='Y' ");
			List bloguser=query.list();
			sess.close();
			return bloguser;
			
		}
		catch(Exception e){
			sess.close();
			System.out.println("something went wrong during get all block" + e.getMessage());
		    return null;
		    
		}
	}

	public List getAllBlogAdmin() {
		Session sess= sf.openSession();
		try{
			Query query= sess.createQuery("From Blog b WHERE b.approved='N' ");
			List blogadmin=query.list();
			System.out.println(blogadmin);
			sess.close();
			return blogadmin;
			
		}
		catch(Exception e){
			sess.close();
			System.out.println(e.getMessage());
			
		}
		return null;
	}

	public boolean updateBlog(Blog blog) {
		
		Session sess= sf.openSession();
		try{
			sess.update(blog);
			sess.flush();
			sess.close();
			return true;
		}
		catch(Exception e){
			sess.close();
			System.out.println(e.getMessage());
		}

		return false;
	}

	public boolean enableBlog(int id, char status) {
		
		Session sess= sf.openSession();
		try{
			Transaction t= sess.beginTransaction();
			Blog b= (Blog) sess.get(Blog.class, id);
			b.setApproved('Y');
		    t.commit();
			return true;
		}
		catch(Exception e){
			sess.close();
			System.out.println(e.getMessage());
		}

		return false;
	}

}
