package com.mat.java;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.gdal.ogr.ogr;

import com.alibaba.fastjson.JSONArray;
import com.mat.model.DeploymentMod;

public class Process {
	
	public static String dateOper(Date date){
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
		return df.format(date);
	}
	
	public static String blob2string(InputStream is){
		ByteArrayInputStream bais = (ByteArrayInputStream)is;
		byte[] byte_data = new byte[bais.available()];
		bais.read(byte_data, 0, byte_data.length);
		String message="";
		try {
			message = new String(byte_data,"utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return message;
	}
	

	public JSONArray queryDeploymentForWorker(String worker){
		RuTask ruTask = new RuTask();
		ArrayList<String> procDefIdList = ruTask.queryProcDefByTaskOwner(worker);
		ReDeployment reDeployment = new ReDeployment();
		ArrayList<String> deploymentIdList = reDeployment.queryDeploymentIdByProcDefId(procDefIdList);
		JSONArray deployQueryList = reDeployment.queryDeploymentByIdList(deploymentIdList);
		return deployQueryList;
	}
	
	public JSONArray queryQualityDeployment(){
		RuTask ruTask = new RuTask();
		ArrayList<String> procDefIdList = ruTask.queryProcDefByQuality();
		ReDeployment reDeployment = new ReDeployment();
		ArrayList<String> deploymentIdList = reDeployment.queryDeploymentIdByProcDefId(procDefIdList);
		JSONArray qualityDepArray = reDeployment.queryDeploymentByIdList(deploymentIdList);
		return qualityDepArray;
	}
	
	public JSONArray queryTijiaoDeployment(){
		RuTask ruTask = new RuTask();
		ArrayList<String> procDefIdList = ruTask.queryProcDefByRenwutijiao();
		ReDeployment reDeployment = new ReDeployment();
		ArrayList<String> deploymentIdList = reDeployment.queryDeploymentIdByProcDefId(procDefIdList);
		JSONArray deployQueryList = reDeployment.queryDeploymentByIdList(deploymentIdList);
		return deployQueryList;
	}
	
	
	public void init(){
		ogr.RegisterAll();
	}
	
}
