package com.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.Dao.ProfilePictureDao;
import com.model.Error;
import com.model.User;

@RestController
public class ProfilePictureController {
	
	@Autowired
	ProfilePictureDao pfdao;
	
	
/*	@RequestMapping(value="/uploadprofilepicture", method=RequestMethod.POST)
	public ResponseEntity<?> uploadProfilePicture(CommonsMultipartFile profilpic, HttpSession session)
	{
		
		User user = (User) session.getAttribute("user");
		if(user==null){
			Error error = new Error(3, "Please login and try again later");
			return new ResponseEntity<Error>(error, HttpStatus.UNAUTHORIZED);
		}
		ProfilePicture profilepicture= new ProfilePicture();
		profilepicture.setEmail_id(user.getEmail_id());
		profilepicture.setImage(profilpic.getBytes());
		pfdao.saveProfilePicture(profilepicture);
		return new ResponseEntity<Boolean>(HttpStatus.OK);
		
	}
	
	@RequestMapping(value="/getimage/{emailid}", method=RequestMethod.GET )
	public @ResponseBody byte[]  getProfilePicture(@PathVariable String emailid, HttpSession session){
		
		User user = (User) session.getAttribute("user");
		
			if(user==null)
			{
				return null;

		    }
		else
		{
			ProfilePicture pf=pfdao.getProfilePicture(emailid);
			if(pf==null)
				return null;
			else
				return pf.getImage();
		}
		
		
	}*/
	
	
	@RequestMapping(value="/uploadprofilepicture", method=RequestMethod.POST)
	public ResponseEntity<?> uploadProfilePicture(@RequestParam CommonsMultipartFile image, HttpSession session)
	{
		
		User user = (User) session.getAttribute("user");
		if(user==null){
			Error error = new Error(3, "Please login and try again later");
			return new ResponseEntity<Error>(error, HttpStatus.UNAUTHORIZED);
		}
		else
			{
				try{
			
						String path="C:\\Users\\NIKEETA SHAHI\\workspace\\nikita\\backend\\src\\main\\resources\\image\\";
						path=path+user.getEmail_id()+".jpg";
						File file=new  File(path);
						byte[] b=image.getBytes();
						FileOutputStream fos=new FileOutputStream(file);
						BufferedOutputStream bos=new BufferedOutputStream(fos);
						bos.write(b);
						bos.close();
				}
				catch(Exception e)
				{
					System.out.println(e.getMessage());
				}
		}
		HttpHeaders headers = new HttpHeaders();
		headers.add("Location", "/frontend/#!/home");
		return new ResponseEntity<User>(user , headers ,HttpStatus.FOUND);
	}
	
	@RequestMapping(value="/getPic/{useremail}/")
	public @ResponseBody byte[] getPic(@PathVariable("useremail") String useremail , HttpSession session) throws IOException
	{	
			System.out.println(useremail);
			String path = "C:\\Users\\NIKEETA SHAHI\\workspace\\nikita\\backend\\src\\main\\resources\\image\\";
			try{
				String path1= path+ useremail+ ".jpg";
				File file = new File(path1);
				System.out.println(path1);
				byte[] b = Files.readAllBytes(file.toPath());
				return b;
			}catch(Exception e) //if image not present
			{
				String path2 = path +"default-medium.png";
				File file = new File(path2);
				byte[] b = Files.readAllBytes(file.toPath());
				return b;
			}
	}
}
