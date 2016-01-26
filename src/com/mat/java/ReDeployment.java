package com.mat.java;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.activiti.engine.HistoryService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.task.Task;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.mat.model.DeploymentMod;

public class ReDeployment {
	
	ProcessEngine engine;
	RepositoryService repositoryService;
	HistoryService historyService;
	
	public ReDeployment(){
		engine = ProcessEngines.getDefaultProcessEngine();
		repositoryService = engine.getRepositoryService();
		historyService = engine.getHistoryService();
	}
	
	private String readStream(InputStream is){
		String result="";
		try{
			int count=is.available();
			byte[] contents=new byte[count];
			is.read(contents);
			result =new String(contents);
		}
		catch(Exception e){
		}
		return result;
	}
	
	public String addDeployment1(String deploymentName,String shppath,String filepath){
		String geojson=""; 
		try {
			MakeBpmn make = new MakeBpmn();
			geojson = make.make(deploymentName, shppath, filepath);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return geojson;
	}
	
	public String addDeployment2(String deploymentName,String createDate,String deadline,String content,String datapath,String shppath,String filepath,String jsonString){
		DeploymentBuilder builder = repositoryService.createDeployment();
		builder.name(deploymentName);
		builder.enableDuplicateFiltering();
		builder.addString("create_date", createDate);
		builder.addString("deadline", deadline);
		builder.addString("content", content);
		builder.addString("datapath", datapath);
		builder.addString("shppath", shppath);
		builder.addString("filepath", filepath);
		if(jsonString != ""){
			builder.addString("geometry", jsonString);
		}
		String bpmnPath="";
		bpmnPath = deploymentName + ".bpmn";
		builder.addClasspathResource(bpmnPath);
		Deployment dep=builder.deploy();
		ProcessDefinition def=repositoryService.createProcessDefinitionQuery().deploymentId(dep.getId()).singleResult();
		RuntimeService runtimeService=engine.getRuntimeService();
		runtimeService.startProcessInstanceById(def.getId());
		TaskService taskService=engine.getTaskService();
		Task task=taskService.createTaskQuery().deploymentId(dep.getId()).singleResult();
		taskService.complete(task.getId());
		Log.writeLog("添加任务：" + deploymentName);
		return "succeed add Deployment2";
	}
	
	public JSONArray queryDeployment(){
		InputStream is;
		ArrayList<DeploymentMod> deployQueryList = new ArrayList<DeploymentMod>();
		List<HistoricProcessInstance> depCQuery = historyService.createHistoricProcessInstanceQuery().list();
		for(int i=0;i<depCQuery.size();i++){
			DeploymentMod deployment = new DeploymentMod();
			HistoricProcessInstance processInstance = depCQuery.get(i);
			String deploymentId=repositoryService.createProcessDefinitionQuery().processDefinitionId(processInstance.getProcessDefinitionId()).singleResult().getDeploymentId();
			Deployment dep = repositoryService.createDeploymentQuery().deploymentId(deploymentId).singleResult();
			deployment.setId(dep.getId()); 
			deployment.setName(dep.getName());
			long isCompleted = historyService.createHistoricTaskInstanceQuery().deploymentId(dep.getId()).taskNameLike("%任务提交%").finished().count();
			if(isCompleted == 1)
				deployment.setIscompleted("已完成");
			else if(isCompleted == 0)
				deployment.setIscompleted("未完成");
			is=repositoryService.getResourceAsStream(dep.getId(),"create_date");
			deployment.setStarttime(Process.blob2string(is));
			is=repositoryService.getResourceAsStream(dep.getId(),"deadline");
			deployment.setEndtime(Process.blob2string(is));
			is=repositoryService.getResourceAsStream(dep.getId(),"content"); 
			deployment.setContent(Process.blob2string(is));
			is=repositoryService.getResourceAsStream(dep.getId(),"datapath");
			deployment.setDatapath(Process.blob2string(is));
			is=repositoryService.getResourceAsStream(dep.getId(),"shppath");
			deployment.setShppath(Process.blob2string(is));
			deployQueryList.add(deployment);
		}
		String jsonString = JSON.toJSONString(deployQueryList);
		JSONArray jsonArray = JSON.parseArray(jsonString);
		return jsonArray;
	}
	
	public JSONArray queryDeploymentByIdList(ArrayList<String> deploymentIdList){
		InputStream is;
		ArrayList<DeploymentMod> deployQueryList = new ArrayList<DeploymentMod>();
		for(int i=0;i<deploymentIdList.size();i++){
			DeploymentMod deployment = new DeploymentMod();
			Deployment dep = repositoryService.createDeploymentQuery().deploymentId(deploymentIdList.get(i)).singleResult();
			deployment.setId(dep.getId());
			deployment.setName(dep.getName());
			long isCompleted = historyService.createHistoricTaskInstanceQuery().deploymentId(dep.getId()).taskNameLike("%任务提交%").finished().count();
			if(isCompleted == 1)
				deployment.setIscompleted("已完成");
			else if(isCompleted == 0)
				deployment.setIscompleted("未完成");
			is=repositoryService.getResourceAsStream(dep.getId(),"create_date");
			deployment.setStarttime(Process.blob2string(is));
			is=repositoryService.getResourceAsStream(dep.getId(),"deadline");
			deployment.setEndtime(Process.blob2string(is));
			is=repositoryService.getResourceAsStream(dep.getId(),"content");
			deployment.setContent(Process.blob2string(is));
			is=repositoryService.getResourceAsStream(dep.getId(),"datapath");
			deployment.setDatapath(Process.blob2string(is));
			is=repositoryService.getResourceAsStream(dep.getId(),"shppath");
			deployment.setShppath(Process.blob2string(is));
			deployQueryList.add(deployment);
		}
		String jsonString = JSON.toJSONString(deployQueryList);
		JSONArray jsonArray = JSON.parseArray(jsonString);
		return jsonArray;
	}
	
	public  ArrayList<String> queryDeploymentIdByProcDefId(ArrayList<String> procDefIdList){
		ArrayList<String> deploymentIdList=new ArrayList<String>();
		for(int i=0;i<procDefIdList.size();i++){
			String deploymentId=repositoryService.createProcessDefinitionQuery().processDefinitionId(procDefIdList.get(i)).singleResult().getDeploymentId();
			deploymentIdList.add(deploymentId);
		}
		return deploymentIdList;
	}
	
	public String getJson(String deploymentId){
		InputStream is = repositoryService.getResourceAsStream(deploymentId, "geometry");
		return Process.blob2string(is);
	}
	
	public String deleteDeployment(String deploymentId){
		repositoryService.deleteDeployment(deploymentId, true);
		Log.writeLog("删除任务：" + deploymentId);
		return deploymentId+"已删除";
	}
	
	public List<Deployment> queryDepByIdList(ArrayList<String> ids){
		List<Deployment> deps = new ArrayList<Deployment>();
		for(int i=0;i<ids.size();i++){
			deps.add(repositoryService.createDeploymentQuery().deploymentId(ids.get(i)).singleResult());
		}
		return deps;
	}
}
