package com.mat.java;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Init {
	
	public static String getDir(String type){
		String dir = "";
		Mysql mysql = new Mysql();
		try {
			if(mysql.conn()){
				String sql = "SELECT * FROM catalog WHERE name='" + type + "'";
				try{
					ResultSet rs = mysql.selectSQL(sql);
					rs.next();
					dir = rs.getString("dir");
				}catch (SQLException e) {  
			        System.out.println("插入数据库时出错：");  
			        e.printStackTrace(); 
				}
			}
		} catch (ClassNotFoundException e) {
			System.err.println( "无法连接数据库" );  
			e.printStackTrace();
		}
		mysql.deconn();
		return dir;
	}
}
