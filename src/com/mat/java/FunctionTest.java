package com.mat.java;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.ExtensionAttribute;
import org.activiti.bpmn.model.GraphicInfo;
import org.activiti.bpmn.model.UserTask;
import org.activiti.bpmn.model.Process;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.impl.util.json.JSONArray;
import org.activiti.engine.task.Attachment;

import com.mat.util.FileService;

public class FunctionTest {
	public static void main(String[] args) throws Exception {
	//	RuTask task = new RuTask();
	//	com.alibaba.fastjson.JSONArray a = task.queryTask("290001", "");
	//	System.out.println(a);
		//Init init = new Init();
		//Init.getDir("project");
		//Log.readLog();
	/*	IdGroup idGroup = new IdGroup();
		idGroup.addGroup("insti1", "insti");
		idGroup.addGroup("insti2", "insti");
		
		idGroup.addGroup("managerGroup", "role");
		idGroup.addGroup("workerGroup", "role");*/
		
	//	IdUser iduser = new IdUser();
	//	iduser.addUser("1", "老王", "manager2@map.com", "", "managerGroup","insti1");
	//	iduser.addUser("manager1", "老王", "manager1@map.com", "manager1", "managerGroup","insti1");
	/*	iduser.addUser("worker1", "张三", "worker1@map.com", "worker1", "workerGroup","insti1");
		iduser.addUser("worker2", "李四", "worker2@map.com", "worker2", "workerGroup","insti1");
		iduser.addUser("worker3", "赵五", "worker3@map.com", "worker3", "workerGroup","insti2");
		iduser.addUser("worker4", "孙六", "worker4@map.com", "worker4", "workerGroup","insti2");
	//	System.out.println(System.getProperty("java.library.path"));
	/*	com.mat.java.Process pro = new com.mat.java.Process();
		pro.queryQualityDeployment();*/
	/*	IdUser iduser = new IdUser();
		iduser.deleteUser("1");
		iduser.deleteUser("worker1");
	/*	iduser.deleteUser("worker2");
		iduser.deleteUser("worker3");
		iduser.deleteUser("worker4");*/
	//	RuTask ruTask = new RuTask();
		///ruTask.queryTask("10001", "");
		//task.queryProcDefByRenwutijiao();
		//task.queryQualityTask("10001");
//		task.test();
	//	ruTask.queryTask("117501","worker4");
	//	ReDeployment dep = new ReDeployment();
	//	dep.deleteDeployment("12501");
	//	dep.queryDeployment();
	//	iduser.validUser("1", "");
	//	System.out.print(ruTask.queryFilePathByTaskId("117582"));
	//	String execId = ruTask.queryExecIdByTaskId("82819");
	//	String tempTaskId = ruTask.queryFenFuZuoYeIdByExecId(execId);
	//	String filePath = ruTask.queryFilePathByTaskId("77619");
	//	System.out.println(filePath);
	//	 ruTask.queryTaskNameByTaskId("132501");
	/*	
		IdUser user = new IdUser();
		user.validUser("worker1", "worker1");*/
	/*	com.mat.java.Process p = new com.mat.java.Process();
		p.init();
		MakeBpmn m=new MakeBpmn();
		m.make("ww", "d:\\qqq\\纳溪区-1万作业分幅.shp", "qq");*/
		//ShapeFileReader sfr = new ShapeFileReader();
	//	FileService fs = new FileService();
	/*	String a = "d:\\temp\\sige\\H48G077058\\第2次提交\\作业提交";
		String[] tempStr = a.split("\\\\");
		System.out.println(a);*/
	//	fs.downloadFile("d:\\temp\\sige\\H48G077058\\第2次提交\\作业提交");
//		fs.zip("D:\\22\\", "D:/Data1.zip");
	//	sfr.readTufuhaoFromShp("d:\\qqq\\纳溪区-1万作业分幅.shp");
//		IdUser iu = new IdUser();
	//	System.out.println(iu.changePassword("worker1", "worker1", "111"));
		
	}
}
