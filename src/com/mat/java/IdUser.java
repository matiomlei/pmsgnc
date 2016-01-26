package com.mat.java;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.activiti.engine.IdentityService;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.directwebremoting.WebContextFactory;

import com.mat.util.UserUtil;

public class IdUser {
	
	private IdentityService identityService;
	public IdUser(){
		identityService = UserUtil.getIdentityService();
	}
	
	public boolean addUser(String id,String firstName,String email,String password,String groupId,String jigouId){
		User user=identityService.newUser(id);
		user.setFirstName(firstName);
		user.setEmail(email);
		user.setPassword(password);
		identityService.saveUser(user);
		identityService.createMembership(id, groupId);
		identityService.createMembership(id, jigouId);
		return true;
	}
	
	public String validUser(String userId,String password,HttpSession httpSession){
		if(!identityService.checkPassword(userId, password)){
			System.out.println("false");
			return "false";
		}
		else{
			String userName = identityService.createUserQuery().userId(userId).singleResult().getFirstName();
			String groupName = identityService.createGroupQuery().groupMember(userId).groupType("role").singleResult().getName();
			System.out.println(httpSession.getId());
			httpSession.setAttribute("userId", userId);
			httpSession.setAttribute("groupName", groupName);
			httpSession.setAttribute("userName", userName);
			Log.writeLog("登录成功");
			return "true";
		}
	}
	
	public boolean changePassword(String userId,String oldPassword,String newPassword){
		if(identityService.checkPassword(userId, oldPassword)){
			User user=identityService.createUserQuery().userId(userId).singleResult();
			user.setPassword(newPassword);
			identityService.saveUser(user);
			Log.writeLog("修改了登录密码");
			return true;
		}
		else return false;
	}
	
	public boolean changeMembership(String userId, String role, String insti){
		List<Group> groups = identityService.createGroupQuery().groupMember(userId).list();
		for(int i=0;i<groups.size();i++){
			identityService.deleteMembership(userId, groups.get(i).getId());
		}
		identityService.createMembership(userId, role);
		identityService.createMembership(userId, insti);
		return true;
	}
	
	public String deleteUser(String userId){
		identityService.deleteUser(userId);
		return "用户已删除";
	}
	
	public void cleanSession(HttpSession httpSession){
		Log.writeLog("退出登录");
		httpSession.removeAttribute("userId");
		httpSession.removeAttribute("groupName");
		httpSession.removeAttribute("userName");
	}
}
