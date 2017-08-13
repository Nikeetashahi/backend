package com.Dao;

import com.model.ProfilePicture;

public interface ProfilePictureDao {
	
	public boolean saveProfilePicture(ProfilePicture profilepicture);
	
	public ProfilePicture getProfilePicture(String Email_id);

}
