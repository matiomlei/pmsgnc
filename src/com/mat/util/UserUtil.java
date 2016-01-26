package com.mat.util;


import org.activiti.engine.IdentityService;
import org.activiti.engine.ProcessEngine;

public class UserUtil {

	public static IdentityService getIdentityService(){
		ProcessEngine engine;
		IdentityService identityService;
		engine=org.activiti.engine.ProcessEngines.getDefaultProcessEngine();
		return identityService=engine.getIdentityService();
	}
}
