package com.mat.model;

import org.activiti.engine.identity.User;

public class UserMod {

	private String id;
	private String firstname;
	private String email;
	private String password;
	private String jigouId;
	private String groupId;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getJigouId() {
		return jigouId;
	}
	public void setJigouId(String jigouId) {
		this.jigouId = jigouId;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
}
