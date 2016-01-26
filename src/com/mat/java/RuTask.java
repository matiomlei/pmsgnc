package com.mat.java;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.activiti.engine.HistoryService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.task.Attachment;
import org.activiti.engine.task.Task;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.mat.model.DeploymentMod;
import com.mat.model.HistoryTaskMod;
import com.mat.model.TaskMod;

public class RuTask {
	
	ProcessEngine engine;
	TaskService taskService;
	HistoryService historyService;
	RepositoryService repositoryService;

	public RuTask() {
		engine = ProcessEngines.getDefaultProcessEngine();
		taskService = engine.getTaskService();
		historyService = engine.getHistoryService();
		repositoryService = engine.getRepositoryService();
	}
	
	public JSONArray queryTask(String deploymentId,String owner){
		List<HistoricTaskInstance> tempTaskCQuery = new ArrayList<HistoricTaskInstance>();
		List<HistoricTaskInstance> tempTaskCQuery2 = new ArrayList<HistoricTaskInstance>();
		List<HistoricTaskInstance> taskCQuery = new ArrayList<HistoricTaskInstance>();
		if(owner.equals("")){
			tempTaskCQuery = historyService.createHistoricTaskInstanceQuery().deploymentId(deploymentId).orderByExecutionId().desc().list();
			//taskCQuery = taskService.createTaskQuery().deploymentId(deploymentId).list();
		}
		else{
			tempTaskCQuery = historyService.createHistoricTaskInstanceQuery().deploymentId(deploymentId).taskOwner(owner).orderByExecutionId().desc().list();
			//taskCQuery = taskService.createTaskQuery().deploymentId(deploymentId).taskOwner(owner).list();
		}
		String tempExecId = "";
		for(int i=0;i<tempTaskCQuery.size();i++){
			HistoricTaskInstance task = tempTaskCQuery.get(i);
			if(!tempExecId.equals(task.getExecutionId())){
				int cnt = (int)taskService.createTaskQuery().executionId(task.getExecutionId()).count();
				System.out.println(task.getExecutionId() + ' ' + cnt);
				if(cnt > 0)
					taskCQuery.add(historyService.createHistoricTaskInstanceQuery().taskId(taskService.createTaskQuery().executionId(task.getExecutionId()).singleResult().getId()).singleResult());
				else
					tempTaskCQuery2.add(task);
				tempExecId = task.getExecutionId();
			}
		}
		for(int i=0;i<tempTaskCQuery2.size();i++){
			HistoricTaskInstance tempTask;
			HistoricTaskInstance task = tempTaskCQuery2.get(i);
			if(historyService.createHistoricTaskInstanceQuery().deploymentId(deploymentId).executionId(task.getExecutionId()).taskNameLike("%作业提交%").count() > 0){
				tempTask = historyService.createHistoricTaskInstanceQuery().deploymentId(deploymentId).executionId(task.getExecutionId()).taskNameLike("%作业提交%").singleResult();
				taskCQuery.add(tempTask);
			}
		}
		//结果集
		ArrayList<TaskMod> taskQueryList = new ArrayList<TaskMod>();
		for(int i=0;i<taskCQuery.size();i++){
			TaskMod taskMod = new TaskMod();
			HistoricTaskInstance task = taskCQuery.get(i);
			String taskOwner = task.getOwner();
			String taskName = task.getName();
			if(!taskName.contains("任务提交") && !taskName.contains("任务下达")){
				String[] strArray = taskName.split(":");
				String taskSuoShuRenWu = strArray[0];
				String objectId = strArray[1];
				String tufuhao = strArray[2];
				taskName = strArray[3];
				String id = task.getId();
				taskMod.setId(id);
				taskMod.setObjectId(objectId);
				taskMod.setTufuhao(tufuhao);
				taskMod.setName(taskName);
				taskMod.setSuoshurenwu(taskSuoShuRenWu);
				String processInstanceId = historyService.createHistoricTaskInstanceQuery().taskId(task.getId()).singleResult().getProcessInstanceId();
				try{
					Date starttime = historyService.createHistoricTaskInstanceQuery().processInstanceId(processInstanceId).taskNameLike("%任务下达%").singleResult().getCreateTime();
					taskMod.setStarttime(Process.dateOper(starttime));
				}
				catch(Exception e){
					
				}
				String nowTaskExecId = task.getExecutionId();
				List<HistoricTaskInstance> histTaskList = historyService.createHistoricTaskInstanceQuery().executionId(nowTaskExecId).finished().orderByTaskId().desc().list();
				String workerName = "";		String workerUpdatePath = "";
				String checkerName = "";	String checkerUpdatePath = "";
				String inspector = "";		String inspectorPath ="";
				String taskFinishTime = ""; String pingjia = "";
				ArrayList<HistoryTaskMod> historyTaskModList = new ArrayList<HistoryTaskMod>();
				for(int j=0;j<histTaskList.size();j++){
					HistoryTaskMod historyTask = new HistoryTaskMod();
					String histTaskId = histTaskList.get(j).getId();
					String histTaskName = histTaskList.get(j).getName();
					historyTask.setFilepath("");
					historyTask.setPingjia("");
					if(histTaskName.contains("分幅作业") && workerName.equals("")){
						workerName = histTaskList.get(j).getOwner();
						workerUpdatePath = taskService.getTaskAttachments(histTaskId).get(0).getUrl();
						historyTask.setFilepath(workerUpdatePath);
					}
					if(histTaskName.contains("作业检查") && checkerName.equals("")){
						checkerName = histTaskList.get(j).getOwner();
						checkerUpdatePath = taskService.getTaskAttachments(histTaskId).get(0).getUrl();
						historyTask.setFilepath(checkerUpdatePath);
					}
					if(histTaskName.contains("质量检查") && inspector.equals("")){
						inspector = histTaskList.get(j).getOwner();
						List<Attachment> attachments = taskService.getTaskAttachments(histTaskId);
						System.out.println("histTaskId:"+histTaskId);
						for(int k=attachments.size()-1;k>=0;k--){
							String name = attachments.get(k).getName();
							if(name.equals("filePath"))
								inspectorPath = attachments.get(k).getUrl();
							else if(name.equals("pingjia"))
								pingjia = attachments.get(k).getUrl();
						}
						historyTask.setFilepath(inspectorPath);
						historyTask.setPingjia(pingjia);
					}
					if(histTaskName.contains("作业提交") && taskFinishTime == ""){
						taskFinishTime = Process.dateOper(histTaskList.get(j).getEndTime());
					}
					if(!histTaskName.contains("任务提交") && !histTaskName.contains("任务下达")){
						historyTask.setName(histTaskName);
						historyTask.setOwner(histTaskList.get(j).getOwner());
						historyTask.setEndtime((histTaskList.get(j).getEndTime() == null) ? "" : Process.dateOper(histTaskList.get(j).getEndTime()));
						historyTaskModList.add(historyTask);
					}
				}
				taskMod.setHistoryTaskList(historyTaskModList);
				if(taskName.contains("分幅作业")){
					if(taskOwner == null){
						taskMod.setStatus("分幅作业未分配");
					}
					else{
						taskMod.setWorker(taskOwner);
						taskMod.setStatus("分幅作业中");
					}
				}
				else if(taskName.contains("作业检查")){
					taskMod.setWorker(workerName);
					taskMod.setWorkerPath(workerUpdatePath);
					if(taskOwner == null){
						taskMod.setStatus("作业检查未分配");
					}
					else{
						taskMod.setChecker(taskOwner);
						taskMod.setStatus("作业检查中");
					}
				}
				else if(taskName.contains("质量检查")){
					taskMod.setWorker(workerName);
					taskMod.setWorkerPath(workerUpdatePath);
					taskMod.setChecker(checkerName);
					taskMod.setCheckerPath(checkerUpdatePath);
					if(!pingjia.equals(""))
						taskMod.setZhijianResult(pingjia);
					if(taskOwner == null){
						taskMod.setStatus("质量检查未分配");
					}
					else{
						taskMod.setInspector(taskOwner);
						taskMod.setStatus("质量检查中");
					}
				}
				else if(taskName.contains("作业提交")){
					taskMod.setWorker(workerName);
					taskMod.setWorkerPath(workerUpdatePath);
					taskMod.setChecker(checkerName);
					taskMod.setCheckerPath(checkerUpdatePath);
					taskMod.setInspector(inspector);
					taskMod.setInspectorPath(inspectorPath);
					if(task.getEndTime() == null)
						taskMod.setStatus("待提交");
					else{
						taskMod.setStatus("已提交");
						taskMod.setEndtime(Process.dateOper(task.getEndTime()));
					}
				}
				taskQueryList.add(taskMod);
			}
		}

		String jsonString = JSON.toJSONString(taskQueryList);
		JSONArray jsonArray = JSON.parseArray(jsonString);
		return jsonArray;
	}
	
	public JSONArray queryQualityTask(String deploymentId, String owner){
		List<Task> taskCQuery= new ArrayList<Task>();
		taskCQuery=taskService.createTaskQuery().deploymentId(deploymentId).taskNameLike("%质量检查%").list();
		ArrayList<TaskMod> taskQueryList = new ArrayList<TaskMod>();
		for(int i=0;i<taskCQuery.size();i++){
			TaskMod taskMod = new TaskMod();
			Task task = taskCQuery.get(i);
			String taskOwner = task.getOwner();
			String taskName = task.getName();
			String[] strArray = taskName.split(":");
			String taskSuoShuRenWu = strArray[0];
			String objectId = strArray[1];
			String tufuhao = strArray[2];
			taskName = strArray[3];
			taskMod.setId(task.getId());
			taskMod.setObjectId(objectId);
			taskMod.setTufuhao(tufuhao);
			taskMod.setName(taskName);
			taskMod.setSuoshurenwu(taskSuoShuRenWu);
			taskMod.setStarttime(Process.dateOper(task.getCreateTime()));
			taskMod.setInspector(taskOwner);
			if(taskOwner == "")
				taskMod.setStatus("质检未分配");
			else
				taskMod.setStatus("质检中");
			String nowTaskExecId = taskService.createTaskQuery().taskId(task.getId()).singleResult().getExecutionId();
			List<HistoricTaskInstance> histTaskList = historyService.createHistoricTaskInstanceQuery().executionId(nowTaskExecId).list();
			ArrayList<HistoryTaskMod> historyTaskModList = new ArrayList<HistoryTaskMod>();
			for(int j=0;j<histTaskList.size();j++){
				HistoryTaskMod historyTask = new HistoryTaskMod();
				String histTaskId = histTaskList.get(j).getId();
				String histTaskName = histTaskList.get(j).getName();
				if(histTaskName.contains("质量检查")){
					historyTask.setName(histTaskName);
					historyTask.setOwner(histTaskList.get(j).getOwner());
					historyTask.setEndtime((histTaskList.get(j).getEndTime() == null) ? "" : Process.dateOper(histTaskList.get(j).getEndTime()));
					historyTask.setFilepath((taskService.getTaskAttachments(histTaskId).size() == 0) ? "" : taskService.getTaskAttachments(histTaskId).get(0).getUrl());
					historyTaskModList.add(historyTask);
				}
			}
			taskMod.setHistoryTaskList(historyTaskModList);
			taskQueryList.add(taskMod);
		}
		String jsonString = JSON.toJSONString(taskQueryList);
		JSONArray jsonArray = JSON.parseArray(jsonString);
		return jsonArray;
	}
	
	public String setTaskOwner(ArrayList<String> itemList,String userId){
		for(int i=0;i<itemList.size();i++){
			String taskId = itemList.get(i);
			taskService.setOwner(taskId, userId);
			Log.writeLog("将编号为" + taskId + "的作业分配给" + userId);
			//以下为每次为分幅作业设置Owner时，建立提交文件夹
			String tufuhao = taskService.createTaskQuery().taskId(taskId).singleResult().getName();
			String[] shuzu = tufuhao.split(":");
			tufuhao = shuzu[2];
			String taskName = shuzu[3];
			if(taskName.contains("作业检查")){
			}
			else{
				String tempProDefId = taskService.createTaskQuery().taskId(taskId).singleResult().getProcessDefinitionId();
				String tempProInsId = taskService.createTaskQuery().taskId(taskId).singleResult().getProcessInstanceId();
				String tempDepId = repositoryService.createProcessDefinitionQuery().processDefinitionId(tempProDefId).singleResult().getDeploymentId();
				String filePath = Process.blob2string(repositoryService.getResourceAsStream(tempDepId, "filepath"));
				
				filePath = filePath + "\\" + tufuhao;
				int tempNum = 1;
				if(taskName.contains("分幅作业")){
					while(true){
						String tempFilePath = filePath + "\\第" + tempNum + "次提交";
						tempNum++;
						File tijiaoWenjianjia = new File(tempFilePath);
						if(!tijiaoWenjianjia.exists() && !tijiaoWenjianjia.isDirectory()){
							tijiaoWenjianjia.mkdirs();
							File zuoyeTijiaoWenjianjia = new File(tempFilePath+"\\作业提交");
							zuoyeTijiaoWenjianjia.mkdir();
							File zuoyeJianChaWenjianjia = new File(tempFilePath+"\\作业检查");
							zuoyeJianChaWenjianjia.mkdir();
							//taskService.createAttachment("filePath", taskId, tempProInsId, "filePath", "", tempFilePath);
							break;
						}
					}
				}
				if(taskName.contains("质量检查")){
					tempNum = 1;
					while(true){
						String tempJianChaPath = filePath + "\\质量检查\\第" + tempNum + "次质量检查";
						tempNum++;
						File jianchaWenjianjia = new File(tempJianChaPath);
						if(!jianchaWenjianjia.exists() && !jianchaWenjianjia.isDirectory()){
							jianchaWenjianjia.mkdirs();
							break;
						}
					}
				/*	else if(jianchaWenjianjia.list().length == 0){
						System.out.println("what the fuck");
						break;
					}*/
			/*		else
						taskService.createAttachment("filePath", taskId, tempProInsId, "filePath", "", tempFilePath);
						break;
					*/
				}
			}
		}
		return "已成功分配任务";
	}
	
	public ArrayList<String> queryProcDefByTaskOwner(String owner){
		Set<String> proDefIdSet = new HashSet<String>();
		List<HistoricTaskInstance> taskCQuery=historyService.createHistoricTaskInstanceQuery().taskOwner(owner).list();
	//	List<Task> taskCQuery=taskService.createTaskQuery().taskOwner(owner).list();
		for(int i=0;i<taskCQuery.size();i++){
			HistoricTaskInstance task = taskCQuery.get(i);
			proDefIdSet.add(task.getProcessDefinitionId());
		}
		ArrayList<String> proDefIdList = new ArrayList<String>();
		proDefIdList.addAll(proDefIdSet);
		return proDefIdList;
	}
	
	public ArrayList<String> queryProcDefByQuality(){
		Set<String> proDefIdSet = new HashSet<String>();
		List<Task> taskCQuery=taskService.createTaskQuery().taskNameLike("%质量检查%").list();
		for(int i=0;i<taskCQuery.size();i++){
			Task task = taskCQuery.get(i);
			proDefIdSet.add(task.getProcessDefinitionId());
		}
		ArrayList<String> proDefIdList = new ArrayList<String>();
		proDefIdList.addAll(proDefIdSet);
		return proDefIdList;
	}
	
	public ArrayList<String> queryProcDefByRenwutijiao(){
		Set<String> proDefIdSet = new HashSet<String>();
		List<Task> taskCQuery=taskService.createTaskQuery().taskNameLike("%任务提交%").list();
		for(int i=0;i<taskCQuery.size();i++){
			Task task = taskCQuery.get(i);
			proDefIdSet.add(task.getProcessDefinitionId());
		}
		ArrayList<String> proDefIdList = new ArrayList<String>();
		proDefIdList.addAll(proDefIdSet);
		return proDefIdList;
	}
	
	public HashMap<String,String> queryNeedCheck(String taskId){
		HashMap<String,String> map = new HashMap<String,String>();
		map.put("taskId", taskId);
		String taskName = taskService.createTaskQuery().taskId(taskId).singleResult().getName();
		map.put("taskName", taskName);
		String nowTaskExecId = taskService.createTaskQuery().taskId(taskId).singleResult().getExecutionId();
		long fenfuzuoyecishu = historyService.createHistoricTaskInstanceQuery().executionId(nowTaskExecId).taskNameLike("%分幅作业%").count();
		if(fenfuzuoyecishu != 1)
			map.put("needCheck", "true");
		else
			map.put("needCheck", "false");
		return map;
	}
	
	public String completeTask(String taskId,String var){
		String returnFilePath = "";
		String taskName = taskService.createTaskQuery().taskId(taskId).singleResult().getName();
		System.out.println("taskName:"+taskName);
		String processInstanceId = taskService.createTaskQuery().taskId(taskId).singleResult().getProcessInstanceId();
		String prodefId = taskService.createTaskQuery().taskId(taskId).singleResult().getProcessDefinitionId();
		ArrayList<String> prodefIdList = new ArrayList<String>();
		prodefIdList.add(prodefId);
		ReDeployment dep = new ReDeployment();
		ArrayList<String> depIdList = dep.queryDeploymentIdByProcDefId(prodefIdList);
		String deploymentId = depIdList.get(0);
		InputStream is=repositoryService.getResourceAsStream(deploymentId,"filepath");
		String filePath = Process.blob2string(is);
		String[] tempTaskName = taskName.split(":");
		String tufuhao = tempTaskName[2];
		filePath = filePath + "\\" + tufuhao;
		HashMap<String,Object> vars = new HashMap<String,Object>();
		if(taskName.contains("质量检查")){
			filePath += "\\质量检查";
			int tempNum = 1;
			while(true){
				String tempFilePath = filePath + "\\第" + tempNum + "次质量检查";
				tempNum++;
				File tijiaoWenjianjia = new File(tempFilePath);
				if(!tijiaoWenjianjia.exists() && !tijiaoWenjianjia.isDirectory()){
					break;
				}
			}
			tempNum -= 2;
			String tempFilePath = filePath + "\\第" + tempNum + "次质量检查";
			System.out.println("tempFilePath:"+tempFilePath);
			taskService.createAttachment("filePath", taskId, processInstanceId, "filePath", "", tempFilePath);
			returnFilePath = tempFilePath;
		}
		else{
			int tempNum = 1;
			while(true){
				String tempFilePath = filePath + "\\第" + tempNum + "次提交";
				tempNum++;
				File tijiaoWenjianjia = new File(tempFilePath);
				if(!tijiaoWenjianjia.exists() && !tijiaoWenjianjia.isDirectory()){
					break;
				}
			}
			tempNum -= 2;
			String tempFilePath = filePath + "\\第" + tempNum + "次提交";
			if(taskName.contains("分幅作业")){
				tempFilePath = tempFilePath + "\\作业提交";
				taskService.createAttachment("filePath", taskId, processInstanceId, "filePath", "", tempFilePath);
			}
			else if(taskName.contains("作业检查")){
				tempFilePath = tempFilePath + "\\作业检查";
				taskService.createAttachment("filePath", taskId, processInstanceId, "filePath", "", tempFilePath);
			}
			System.out.println("tempFilePath:"+tempFilePath);
			returnFilePath = tempFilePath;
		}
		//has vars ,need revise, need redo
		if(!var.equals("")){
			String nowTaskExecId = taskService.createTaskQuery().taskId(taskId).singleResult().getExecutionId();
			if(var.equals("needRevise")){
				vars.put("needCheck", 1);
				vars.put("needRevise", 1);
				vars.put("qualityNotPass", 0);
				taskService.complete(taskId, vars); 
				String histTaskOwner = historyService.createHistoricTaskInstanceQuery().executionId(nowTaskExecId).taskNameLike("%分幅作业%").list().get(0).getOwner();
				String nowTaskId = historyService.createHistoricTaskInstanceQuery().executionId(nowTaskExecId).unfinished().singleResult().getId();
				ArrayList<String> tempList = new ArrayList<String>();
				tempList.add(nowTaskId);
				setTaskOwner(tempList,histTaskOwner);
				Log.writeLog("提交作业检查，原作业需修改，已通知" + histTaskOwner + "修改");
			}
			else if(var.equals("qualityNotPass")){
				vars.put("needCheck", 1);
				vars.put("needRevise", 0);
				vars.put("qualityNotPass", 1);
				taskService.complete(taskId, vars); 
				String histTaskOwner = historyService.createHistoricTaskInstanceQuery().executionId(nowTaskExecId).taskNameLike("%分幅作业%").list().get(0).getOwner();
				String nowTaskId = historyService.createHistoricTaskInstanceQuery().executionId(nowTaskExecId).unfinished().singleResult().getId();
				ArrayList<String> tempList = new ArrayList<String>();
				tempList.add(nowTaskId);
				setTaskOwner(tempList,histTaskOwner);
				Log.writeLog("提交质量检查，原作业需修改，已通知" + histTaskOwner + "修改");
			}
			else
				System.out.println("err");
		}
		else{
			vars.put("needCheck", 1);
			vars.put("needRevise", 0);
			vars.put("qualityNotPass", 0);
			taskService.complete(taskId, vars); 
			Log.writeLog("分幅作业" + taskId + "提交成功");
		}
		return returnFilePath;
	}
	
	public JSONArray queryKetijiaoDeployment(){
		List<Task> tasks = taskService.createTaskQuery().taskNameLike("%任务提交%").list();
		InputStream is;
		List<Deployment> deps;
		ArrayList<String> depIdList = new ArrayList<String>();
		ArrayList<String> proDefIdList = new ArrayList<String>();
		for(int i=0;i<tasks.size();i++){
			proDefIdList.add(tasks.get(i).getProcessDefinitionId());
		}
		ReDeployment reDep = new ReDeployment();
		depIdList = reDep.queryDeploymentIdByProcDefId(proDefIdList);
		deps = reDep.queryDepByIdList(depIdList);
		ArrayList<DeploymentMod> deployQueryList = new ArrayList<DeploymentMod>();
		for(int i=0;i<deps.size();i++){
			DeploymentMod deployment = new DeploymentMod();
			Deployment dep = deps.get(i);
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
	
	public String renwutijiao(String renwuId){
		String taskId = taskService.createTaskQuery().deploymentId(renwuId).taskNameLike("%任务提交%").singleResult().getId();
		taskService.complete(taskId);
		Log.writeLog("任务" + renwuId + "提交成功");
		return "任务已提交!";
	}
	
	public Boolean deploymentIsCompleted(String deployment){
		long isCompleted = historyService.createHistoricTaskInstanceQuery().deploymentId(deployment).taskNameLike("%任务提交%").finished().count();
		if(isCompleted == 1)
			return true;
		else if(isCompleted == 0)
			return false;
		return false;
	}
	
	public String queryFilePathByTaskId(String taskId){
		return taskService.getTaskAttachments(taskId).get(0).getUrl();
	}
	
	public String queryTaskNameByTaskId(String taskId){
		return taskService.createTaskQuery().taskId(taskId).singleResult().getName();
	}
	
	public String queryExecIdByTaskId(String taskId){
		return taskService.createTaskQuery().taskId(taskId).singleResult().getExecutionId();
	}
	
	public String queryFenFuZuoYeIdByExecId(String execId){
		List<HistoricTaskInstance> tempList = historyService.createHistoricTaskInstanceQuery().executionId(execId).taskNameLike("%分幅作业%").orderByTaskCreateTime().asc().list();
		return tempList.get(tempList.size()-1).getId();
	}
	
	public int querytaskNum(String owner){
		return 1;
	}
	
	public boolean zhiliangpingjia(String taskId, int type){
		String content = "";
		String proInsId = taskService.createTaskQuery().taskId(taskId).singleResult().getProcessInstanceId();
		switch(type){
		case 1:
			content = "优";
			break;
		case 2:
			content = "良";
			break;
		case 3:
			content = "合格";
			break;
		case 4:
			content = "不合格";
			break;
		}
		taskService.createAttachment("pingjia", taskId, proInsId, "pingjia", "", content);
		return true;
	}
}
