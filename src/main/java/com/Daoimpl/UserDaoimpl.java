package com.Daoimpl;

import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.Dao.UserDao;
import com.model.User;
@Repository
public class UserDaoimpl implements UserDao {
	@Autowired
	SessionFactory sf;

	
	public void userRegister(User user) {
        Session sess = sf.openSession();
		sess.save(user);
		sess.flush();
		sess.close();
	}

	public List<User> getAllUser() {
		Session sess=sf.openSession();
		Query query=sess.createQuery("from User");
		List<User> user=query.list();		
		sess.close();
        return user;
	}

	public User login(User user) {
		System.out.println(user.getEmail_id()+ "  " + user.getPassword());
		Session sess=sf.openSession();
		Query query=sess.createQuery("from User where email_id=? and password=? and enabled=?");
		query.setString(0, user.getEmail_id());
		query.setString(1, user.getPassword());
		query.setBoolean(2, true);
		User validuser =(User) query.uniqueResult();
		sess.close();
	    return validuser;
	}

	public User updateUser(User validUser) {
		Session sess=sf.openSession();
		sess.update(validUser);
		sess.flush();
		sess.close();
		return validUser;
	}

}
