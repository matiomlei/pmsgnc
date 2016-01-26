package com.mat.java;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpSession;

import org.directwebremoting.WebContextFactory;

public class Log {
	public static void writeLog(String content) {
        try {
        	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
    		HttpSession session = WebContextFactory.get().getSession();//设置操作人员
    		String operator = (String)session.getAttribute("userName");
        	String fileName = Init.getDir("log");
            //打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
            FileWriter writer = new FileWriter(fileName, true);
            writer.write(df.format(new Date()) + ' ' + operator + ' '+ content + "\r\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	 public static String readLog() throws IOException{
		 String fileName = Init.getDir("log");
         File file=new File(fileName);
         if(!file.exists()||file.isDirectory())
             throw new FileNotFoundException();
         BufferedReader br=new BufferedReader(new FileReader(file));
         String temp=null;
         StringBuffer sb=new StringBuffer();
         temp=br.readLine();
         while(temp!=null){
        	 temp += "\r\n";
             sb.insert(0, temp);
             temp=br.readLine();
         }
         br.close();
         return sb.toString();
     } 
}
