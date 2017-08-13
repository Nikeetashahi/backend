package com.Daoimpl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.Dao.FriendDao;
import com.model.Friend;
import com.model.User;
@Repository
public class FriendDaoimpl implements FriendDao {
	
	@Autowired
	SessionFactory sf;
	
	boolean update=false;


	public boolean sendFriendRequest(Friend friend) {
		try{
		Session sess=sf.openSession();
		Transaction t = sess.beginTransaction();
		sess.saveOrUpdate(friend);
		t.commit();
		sess.close();
        return true;
	}
		catch(Exception e){
			return update;
			
	}
		
	}

	public List listFriendRequest(String email) {
		Session sess= sf.openSession();
		String qry = "Select * from users where email_id in (select from_id from Friend where status='P' AND to_id=?)";
		SQLQuery sqlquery = sess.createSQLQuery(qry);
		sqlquery.setString(0, email);
		List listoffriendrequest=sqlquery.list();
		sess.close();
        return listoffriendrequest;
	}

	public List listOfFriend(String email) {
		Session sess=sf.openSession();
		SQLQuery query = sess.createSQLQuery("Select * from Users Where email_id in(Select to_id from Friend Where from_id=? AND status='A' UNION Select from_id from Friend where to_id=? AND status='A')");
		query.setString(0, email);
		query.setString(1, email);
		
		List listoffriend = query.list();
		System.out.println(listoffriend);
		sess.close();
		return listoffriend;
	}

	

	public List<User> listOfSuggestedUsers(String email) {
		
		Session sess=sf.openSession();
		SQLQuery sqlquery=sess.createSQLQuery("select * from users Where email_id in"
				 +"(select email_id from users Where email_id!=? minus "
				+"(select from_id from Friend Where to_id=? union "
				+"select to_id from Friend Where from_id=?))");
		sqlquery.setString(0, email);
		sqlquery.setString(1, email);
		sqlquery.setString(2, email);
		sqlquery.addEntity(User.class);
		List<User> suggestUsersList = sqlquery.list();
		sess.close();
		return suggestUsersList;

		
	}

	public boolean updateFriendRequest(String fromid, String toid, char status) {
		
		Session sess=sf.openSession();
		Query query =sess.createQuery("From Friend where from_id=? and to_id=?");
		query.setString(0, fromid );
		query.setString(1, toid);
		Friend friend = (Friend) query.uniqueResult();
		friend.setStatus(status); // status either accept or reject
		sess.update(friend);
		sess.flush();
		sess.close();
        return true;
	}

}
