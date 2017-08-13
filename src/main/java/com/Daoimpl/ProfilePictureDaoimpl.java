package com.Daoimpl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.Dao.ProfilePictureDao;
import com.model.ProfilePicture;
@Repository
public class ProfilePictureDaoimpl implements ProfilePictureDao {
	
	@Autowired
	SessionFactory sf;

	public boolean saveProfilePicture(ProfilePicture profilepicture) {
		
		Session sess= sf.openSession();
		sess.saveOrUpdate(profilepicture);
		sess.flush();
		sess.close();
		return true;
	}

	public ProfilePicture getProfilePicture(String Email_id) {
		
		Session sess= sf.openSession();
		ProfilePicture pf= (ProfilePicture) sess.get(ProfilePicture.class, Email_id);
		sess.close();
		return pf;
	}

}
