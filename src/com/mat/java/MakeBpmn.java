package com.mat.java;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;

import com.alibaba.fastjson.JSON;

public class MakeBpmn {
	int nowFlowId;
	int nowTaskId;
	int nowExclusiveId;
	String bpmnXml; 
	FileWriter fw; 
	public MakeBpmn(){
		//System.out.println(bpmnXml);
		nowFlowId = 4;
		nowTaskId = 2;
		nowExclusiveId = 0;
	
	}
	
	private String nextFlowId(){
		nowFlowId += 1;
		return "flow" + nowFlowId;
	}
	
	private String nowUserTask(){
		return "usertask" + nowTaskId;
	}
	
	private String nextUserTask(){
		nowTaskId += 1;
		return "usertask" + nowTaskId;
	}
	
	private String backTwoUserTask(){
		int tempTaskId = nowTaskId - 2;
		return "usertask" + tempTaskId;
	}
	
	private String backOneUserTask(){
		int tempTaskId = nowTaskId - 1;
		return "usertask" + tempTaskId;
	}
	
	private String nowExclusiveId(){
		return "exclusivegateway" + nowExclusiveId;
	}
	
	private String nextExclusiveId(){
		nowExclusiveId += 1;
		return "exclusivegateway" + nowExclusiveId;
	}
	
	public String make(String deploymentName,String shppath,String filepath) throws IOException{
		bpmnXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<definitions xmlns=\"http://www.omg.org/spec/BPMN/20100524/MODEL\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:activiti=\"http://activiti.org/bpmn\" xmlns:bpmndi=\"http://www.omg.org/spec/BPMN/20100524/DI\" xmlns:omgdc=\"http://www.omg.org/spec/DD/20100524/DC\" xmlns:omgdi=\"http://www.omg.org/spec/DD/20100524/DI\" typeLanguage=\"http://www.w3.org/2001/XMLSchema\" expressionLanguage=\"http://www.w3.org/1999/XPath\" targetNamespace=\"http://www.activiti.org/test\">\n\t<process id=\""+deploymentName+"\" name=\""+deploymentName+"\" isExecutable=\"true\">\n\t<startEvent id=\"startevent1\" name=\"Start\"></startEvent>\n\t<sequenceFlow id=\"flow1\" sourceRef=\"startevent1\" targetRef=\"usertask1\"></sequenceFlow>\n\t<userTask id=\"usertask1\" name=\"任务下达\"></userTask>\n\t<parallelGateway id=\"parallelgateway1\" name=\"Parallel Gateway\"></parallelGateway>\n\t<sequenceFlow id=\"flow2\" sourceRef=\"usertask1\" targetRef=\"parallelgateway1\"></sequenceFlow>\n\t<parallelGateway id=\"parallelgateway2\" name=\"Parallel Gateway\"></parallelGateway>";
		String url = Thread.currentThread().getContextClassLoader().getResource("").getPath();
		url = URLDecoder.decode(url, "UTF-8");
		System.out.println(url);
		System.out.println("shppath"+shppath);
		File wenjianjia = new File(filepath);
		if(!wenjianjia.exists() && !wenjianjia.isDirectory()){
			wenjianjia.mkdir();
		}
		ShapeFileReader shapeFileReader = new ShapeFileReader();
		shppath = shppath.replace("\\", "/");
		HashMap<String, Object> rs = shapeFileReader.readTufuhaoFromShp(shppath);
		ArrayList<HashMap<String, String>> featureList = (ArrayList<HashMap<String, String>>)rs.get("1");
		int len=featureList.size();
		for(int i=0;i<len;i++){
			String objectID = featureList.get(i).get("FID");
			String tufuhao = "";
			if(featureList.get(i).get("新图号") != null)
				tufuhao = featureList.get(i).get("新图号");
			else if(featureList.get(i).get("标准图号") != null)
				tufuhao = featureList.get(i).get("标准图号");
			bpmnXml += "<userTask id=\""+nextUserTask()+"\" name=\""+deploymentName+":"+objectID+":"+tufuhao+":"+"分幅作业\"></userTask>\n\t<sequenceFlow id=\""+nextFlowId()+"\" sourceRef=\"parallelgateway1\" targetRef=\""+nowUserTask()+"\"></sequenceFlow>\n\t<exclusiveGateway id=\""+nextExclusiveId()+"\" name=\"Exclusive Gateway\"></exclusiveGateway>\n\t<sequenceFlow id=\""+nextFlowId()+"\" sourceRef=\""+nowUserTask()+"\" targetRef=\""+nowExclusiveId()+"\"></sequenceFlow>\n\t<userTask id=\""+nextUserTask()+"\" name=\""+deploymentName+":"+objectID+":"+tufuhao+":"+"质量检查\"></userTask>\n\t<sequenceFlow id=\""+nextFlowId()+"\" sourceRef=\""+nowExclusiveId()+"\" targetRef=\""+nowUserTask()+"\">\n\t<conditionExpression xsi:type=\"tFormalExpression\"><![CDATA[${execution.getVariable(\"needCheck\")==0}]]></conditionExpression>\n\t</sequenceFlow>\n\t<userTask id=\""+nextUserTask()+"\" name=\""+deploymentName+":"+objectID+":"+tufuhao+":"+"作业检查\"></userTask>\n\t<sequenceFlow id=\""+nextFlowId()+"\" sourceRef=\""+nowExclusiveId()+"\" targetRef=\""+nowUserTask()+"\">\n\t<conditionExpression xsi:type=\"tFormalExpression\"><![CDATA[${execution.getVariable(\"needCheck\")==1}]]></conditionExpression>\n\t</sequenceFlow>\n\t<exclusiveGateway id=\""+nextExclusiveId()+"\" name=\"Exclusive Gateway\"></exclusiveGateway>\n\t<sequenceFlow id=\""+nextFlowId()+"\" sourceRef=\""+nowUserTask()+"\" targetRef=\""+nowExclusiveId()+"\"></sequenceFlow>\n\t<sequenceFlow id=\""+nextFlowId()+"\" sourceRef=\""+nowExclusiveId()+"\" targetRef=\""+backTwoUserTask()+"\">\n\t<conditionExpression xsi:type=\"tFormalExpression\"><![CDATA[${execution.getVariable(\"needRevise\")==1}]]></conditionExpression>\n\t</sequenceFlow>\n\t<sequenceFlow id=\""+nextFlowId()+"\" sourceRef=\""+nowExclusiveId()+"\" targetRef=\""+backOneUserTask()+"\">\n\t<conditionExpression xsi:type=\"tFormalExpression\"><![CDATA[${execution.getVariable(\"needRevise\")==0}]]></conditionExpression>\n\t</sequenceFlow>\n\t<exclusiveGateway id=\""+nextExclusiveId()+"\" name=\"Exclusive Gateway\"></exclusiveGateway>\n\t<sequenceFlow id=\""+nextFlowId()+"\" sourceRef=\""+backOneUserTask()+"\" targetRef=\""+nowExclusiveId()+"\"></sequenceFlow>\n\t<sequenceFlow id=\""+nextFlowId()+"\" sourceRef=\""+nowExclusiveId()+"\" targetRef=\""+backTwoUserTask()+"\">\n\t<conditionExpression xsi:type=\"tFormalExpression\"><![CDATA[${execution.getVariable(\"qualityNotPass\")==1}]]></conditionExpression>\n\t</sequenceFlow>\n\t<userTask id=\""+nextUserTask()+"\" name=\""+deploymentName+":"+objectID+":"+tufuhao+":"+"作业提交\"></userTask>\n\t<sequenceFlow id=\""+nextFlowId()+"\" sourceRef=\""+nowExclusiveId()+"\" targetRef=\""+nowUserTask()+"\">\n\t<conditionExpression xsi:type=\"tFormalExpression\"><![CDATA[${execution.getVariable(\"qualityNotPass\")==0}]]></conditionExpression>\n\t</sequenceFlow>\n\t<sequenceFlow id=\""+nextFlowId()+"\" sourceRef=\""+nowUserTask()+"\" targetRef=\"parallelgateway2\"></sequenceFlow>\n\t";
		}
		bpmnXml += "<userTask id=\""+nextUserTask()+"\" name=\""+deploymentName+"任务提交\"></userTask>\n\t<sequenceFlow id=\""+nextFlowId()+"\" sourceRef=\"parallelgateway2\" targetRef=\""+nowUserTask()+"\"></sequenceFlow>\n\t<sequenceFlow id=\""+nextFlowId()+"\" sourceRef=\""+nowUserTask()+"\" targetRef=\"endevent1\"></sequenceFlow>\n\t<endEvent id=\"endevent1\" name=\"End\"></endEvent>\n\t</process>\n\t</definitions>";
		deploymentName += ".bpmn";
		String filePathStr = url + deploymentName; 
		System.out.println("filepath"+filePathStr);
		File file = new File(filePathStr);
		if(!file.exists())
			file.createNewFile();
		FileOutputStream out = new FileOutputStream(file,false);
		StringBuffer sb = new StringBuffer();
		sb.append(bpmnXml);
		out.write(sb.toString().getBytes("utf-8"));
		out.close();
		return (String)rs.get("2");
	}
}
