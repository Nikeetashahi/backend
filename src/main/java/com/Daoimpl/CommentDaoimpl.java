package com.Daoimpl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.Dao.CommentDao;
import com.model.Comment;


@Repository
public class CommentDaoimpl implements CommentDao {
	
	@Autowired
	SessionFactory sf;

	public boolean addComment(Comment comment) {
		
		Session sess= sf.openSession();
		try
		{
		sess.save(comment);
		sess.flush();
		sess.close();
		return true;
		}
		catch(Exception e)
		{
			sess.close();
			System.out.println(e.getMessage());
			return false;
		}
	}

	public List<Comment> getComments(int id) {
		
		Session sess= sf.openSession();
		Query query =sess.createQuery("From Comment where blog_id=?");
		query.setInteger(0, id);
		List<Comment> listcomment =query.list();
		sess.close();		
		return listcomment;
	}

}
