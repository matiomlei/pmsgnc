package com.mat.java;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class FileUploadDownload {
	
	public String uploadFile(ArrayList<HashMap<String,Object>> uploadList) throws IOException {
        try {
        	String _taskId = "";
        	for(int i=0;i<uploadList.size();i++){
        		HashMap<String, Object> uploadFile = uploadList.get(i);
        	//	System.out.println(i+ ":"+(String)uploadFile.get("name") );
        		if(((String)uploadFile.get("name")).equals("taskId")){
        		//	System.out.println(true);
        			_taskId = (String)uploadFile.get("data");
        		//	System.out.println(true+" 1");
        			break;
        		}
        	}
        	RuTask ruTask = new RuTask();
        	System.out.println("taskId:"+_taskId);
        	String taskName = ruTask.queryTaskNameByTaskId(_taskId);
        	String filePath = "";
        	System.out.println("taskName"+taskName);
        	if(taskName.contains("分幅作业")){
        		filePath = ruTask.queryFilePathByTaskId(_taskId);
        	}
        	else if(taskName.contains("作业检查")){
        		String execId = ruTask.queryExecIdByTaskId(_taskId);
        		String tempTaskId = ruTask.queryFenFuZuoYeIdByExecId(execId);
        		filePath = ruTask.queryFilePathByTaskId(tempTaskId);
        		System.out.println("作业检查:"+taskName+":"+filePath);
        	}
        	else if(taskName.contains("质量检查")){
        		String execId = ruTask.queryExecIdByTaskId(_taskId);
        		String tempTaskId = ruTask.queryFenFuZuoYeIdByExecId(execId);
        		filePath = ruTask.queryFilePathByTaskId(tempTaskId);
        		filePath = filePath.substring(0, filePath.lastIndexOf('\\'));
        		filePath += "\\质量检查";
        		System.out.println("质量检查:"+taskName+":"+filePath);
        	}
        	String tempFilePath = "";
            for(int i=0;i<uploadList.size();i++){
            	HashMap<String, Object> uploadFile = uploadList.get(i);
            	String fileName = (String)uploadFile.get("name");
            	System.out.println("fileName:"+fileName);
            	if(!((String)uploadFile.get("name")).equals("taskId")){
                	byte[] content = (byte[])uploadFile.get("data");
                	FileOutputStream stream;
                	if(taskName.contains("分幅作业")){
                		tempFilePath = filePath + "\\作业提交";
                		stream = new FileOutputStream(tempFilePath + "\\" + fileName);
                		 if (content != null)
                             stream.write(content);
                         stream.close();
                	}
                	else if(taskName.contains("作业检查")){
                		tempFilePath = filePath + "\\作业检查";
                    	stream = new FileOutputStream(tempFilePath + "\\" + fileName);
                    	 if (content != null)
                             stream.write(content);
                         stream.close();
                	}
                	else if(taskName.contains("质量检查")){
                		int tempNum = 1;
        				while(true){
        					tempFilePath = filePath + "\\第" + tempNum + "次质量检查";
        					tempNum++;
        					File jianchaWenjianjia = new File(tempFilePath);
        					if(jianchaWenjianjia.list().length == 0){
        						stream = new FileOutputStream(tempFilePath + "\\" + fileName);
        	                   	if (content != null)
        	                   		stream.write(content);
        	                    stream.close();
        						break;
        					}
        				}
                	}
            	}
            }
            
            return tempFilePath;
 
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
    }
}
