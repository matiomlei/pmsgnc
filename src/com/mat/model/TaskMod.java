package com.mat.model;

import java.util.ArrayList;

public class TaskMod {

	private String id;
	private String objectId;
	private String tufuhao;
	public String getTufuhao() {
		return tufuhao;
	}

	public void setTufuhao(String tufuhao) {
		this.tufuhao = tufuhao;
	}

	private String name;
	private String suoshurenwu;
	private String status;
	private String starttime;
	private String endtime;
	private String worker;
	private String checker;
	private String inspector;
	
	private String workerPath;
	private String checkerPath;
	private String inspectorPath;
	private ArrayList<HistoryTaskMod> historyTaskList;
	private String zhijianResult;
	
	public TaskMod(){
		this.worker = "";
		this.checker = "";
		this.inspector = "";
		this.endtime = "";
		this.workerPath = "";
		this.checkerPath = "";
		this.inspectorPath = "";
		this.zhijianResult = "";
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getObjectId() {
		return objectId;
	}
	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSuoshurenwu() {
		return suoshurenwu;
	}
	public void setSuoshurenwu(String suoshurenwu) {
		this.suoshurenwu = suoshurenwu;
	}
	public String getStarttime() {
		return starttime;
	}
	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}
	public String getEndtime() {
		return endtime;
	}
	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}
	public String getWorker() {
		return worker;
	}
	public void setWorker(String worker) {
		this.worker = worker;
	}
	public String getChecker() {
		return checker;
	}
	public void setChecker(String checker) {
		this.checker = checker;
	}
	public String getInspector() {
		return inspector;
	}
	public void setInspector(String inspector) {
		this.inspector = inspector;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	public String getWorkerPath() {
		return workerPath;
	}

	public void setWorkerPath(String workerPath) {
		this.workerPath = workerPath;
	}

	public String getCheckerPath() {
		return checkerPath;
	}

	public void setCheckerPath(String checkerPath) {
		this.checkerPath = checkerPath;
	}

	public String getInspectorPath() {
		return inspectorPath;
	}

	public void setInspectorPath(String inspectorPath) {
		this.inspectorPath = inspectorPath;
	}

	public ArrayList<HistoryTaskMod> getHistoryTaskList() {
		return historyTaskList;
	}

	public void setHistoryTaskList(ArrayList<HistoryTaskMod> historyTaskList) {
		this.historyTaskList = historyTaskList;
	}

	public String getZhijianResult() {
		return zhijianResult;
	}

	public void setZhijianResult(String zhijianResult) {
		this.zhijianResult = zhijianResult;
	}
}
