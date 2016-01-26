package com.mat.dwr.access;

import com.mat.java.IdUser;
import com.mat.model.UserMod;

public class UserAccess {
	IdUser idUser = new IdUser();
	public boolean addUser(UserMod user){
		System.out.println(user.getFirstname());
		return idUser.addUser(user.getFirstname(), user.getFirstname(), user.getEmail(), user.getPassword(), user.getGroupId(), user.getJigouId());
	}
}
