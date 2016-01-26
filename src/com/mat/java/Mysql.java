package com.mat.java;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Mysql {
	private Connection conn = null;
	PreparedStatement statement = null; 
	
	public boolean conn() throws ClassNotFoundException{
		String driver = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://localhost:3306/activititest?useUnicode=true&amp;characterEncoding=UTF-8";
		String user = "root";
		String password = "111";
		try{
			Class.forName(driver);
			conn = DriverManager.getConnection(url, user, password);
			if(!conn.isClosed())
				return true;
		//捕获加载驱动程序异常  
		}catch ( ClassNotFoundException cnfex ) {  
             System.err.println(  
             "装载 JDBC/ODBC 驱动程序失败。" );  
             cnfex.printStackTrace(); 
             return false;
         }   
         //捕获连接数据库异常  
         catch ( SQLException sqlex ) {  
             System.err.println( "无法连接数据库" );  
             sqlex.printStackTrace();   
             return false;
         } 
		return false;
	}
	
	ResultSet selectSQL(String sql) {  
        ResultSet rs = null;  
        try {  
            statement = conn.prepareStatement(sql);  
            rs = statement.executeQuery(sql);  
        } catch (SQLException e) {  
            e.printStackTrace();  
        }  
        return rs;  
    }  
	
	boolean updateSQL(String sql) {  
	    try {  
	        statement = conn.prepareStatement(sql);  
	        statement.executeUpdate();  
	        return true;  
	    } catch (SQLException e) {  
	        System.out.println("插入数据库时出错：");  
	        e.printStackTrace();  
	    } catch (Exception e) {  
	        System.out.println("插入时出错：");  
	        e.printStackTrace();  
	    }  
	    return false;  
	}  
	 
	 void deconn() {  
	    try {  
	        if (conn != null)  
	            conn.close();  
	    } catch (Exception e) {  
	        System.out.println("关闭数据库问题 ：");  
	        e.printStackTrace();  
	    }  
	}  
}
