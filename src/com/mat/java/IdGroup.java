package com.mat.java;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.activiti.engine.IdentityService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.activiti.engine.identity.UserQuery;

public class IdGroup {

	ProcessEngine engine;
	IdentityService identityService;
	
	public IdGroup(){
		engine=ProcessEngines.getDefaultProcessEngine();
		identityService=engine.getIdentityService();
	}
	
	public String addGroup(String groupName,String groupType){
		Group group=identityService.newGroup(groupName);
		group.setName(groupName);
		group.setType(groupType);
		identityService.saveGroup(group);
		return "succeed add group";
	}
	
	public ArrayList<HashMap<String,String>> queryGroup(String groupType){
		List<Group> groups = identityService.createGroupQuery().groupType(groupType).list();
		ArrayList<HashMap<String,String>> groupQueryList = new ArrayList<HashMap<String,String>>();
		for(int i=0;i<groups.size();i++){
			HashMap<String,String> map = new HashMap<String,String>();
			Group group = groups.get(i);
			map.put("name", group.getId());
			groupQueryList.add(map);
		}
		return groupQueryList;
	}
	
	public ArrayList<HashMap<String,String>> queryJigou(){
		List<Group> groups = identityService.createGroupQuery().groupType("insti").list();
		ArrayList<HashMap<String,String>> groupQueryList = new ArrayList<HashMap<String,String>>();
		for(int i=0;i<groups.size();i++){
			HashMap<String,String> map = new HashMap<String,String>();
			Group group = groups.get(i);
			map.put("name", group.getId());
			groupQueryList.add(map);
		}
		return groupQueryList;
	}
	
	public ArrayList<HashMap<String,String>> queryRole(){
		List<Group> groups = identityService.createGroupQuery().groupType("role").list();
		ArrayList<HashMap<String,String>> groupQueryList = new ArrayList<HashMap<String,String>>();
		for(int i=0;i<groups.size();i++){
			HashMap<String,String> map = new HashMap<String,String>();
			Group group = groups.get(i);
			map.put("name", group.getId());
			groupQueryList.add(map);
		}
		return groupQueryList;
	}
	
	public ArrayList<HashMap<String,String>> showGroupMember(String groupId){
		List<User> users = identityService.createUserQuery().memberOfGroup(groupId).list();
		ArrayList<HashMap<String,String>> userQueryList = new ArrayList<HashMap<String,String>>();
		for(int i=0;i<users.size();i++){
			HashMap<String,String> map = new HashMap<String,String>();
			User user = users.get(i);
			map.put("id", user.getId());
			map.put("name", user.getFirstName());
			map.put("email", user.getEmail());
			userQueryList.add(map);
		}
		return userQueryList;
	}
	
	public ArrayList<HashMap<String,String>> queryGroupMember(String role,String insti){
		List<User> users = new ArrayList<User>();
		if((!role.equals("")) && (!insti.equals(""))){
			List<User> users1,users2;
			users1 = identityService.createUserQuery().memberOfGroup(role).list();
			users2 = identityService.createUserQuery().memberOfGroup(insti).list();
			for(int i=0;i<users1.size();i++)
				for(int j=0;j<users2.size();j++){
					if(users1.get(i).getFirstName().equals(users2.get(j).getFirstName())){
						users.add(users1.get(i));
						break;
					}
				}
		}
		else if((!role.equals("")) && (insti.equals("")))
			users = identityService.createUserQuery().memberOfGroup(role).list();
		else if((role.equals("")) && (!insti.equals("")))
			users = identityService.createUserQuery().memberOfGroup(insti).list();
		else
			users = identityService.createUserQuery().list();
		ArrayList<HashMap<String,String>> userQueryList = new ArrayList<HashMap<String,String>>();
		for(int i=0;i<users.size();i++){
			HashMap<String,String> map = new HashMap<String,String>();
			User user = users.get(i);
			map.put("id", user.getId());
			map.put("name", user.getFirstName());
			map.put("email", user.getEmail());
			String jueseStr = identityService.createGroupQuery().groupMember(user.getId()).groupType("role").singleResult().getName();
			String jigouStr = identityService.createGroupQuery().groupMember(user.getId()).groupType("insti").singleResult().getName();
			map.put("juese", jueseStr);
			map.put("jigou", jigouStr);
			userQueryList.add(map);
		}
		return userQueryList;
	}
}
