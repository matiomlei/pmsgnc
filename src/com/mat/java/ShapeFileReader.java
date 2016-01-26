package com.mat.java;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.gdal.ogr.*;
import org.gdal.osr.*;
import org.gdal.gdal.*;

public class ShapeFileReader {

	public ShapeFileReader(){
		
	}
/*	public static void readShapeFile(){
		String strFilePath = "C:\\temp\\GDALtest\\test\\test\\仪陇县.shp";
		ogr.RegisterAll();
		gdal.SetConfigOption("GDAL_FILENAME_IS_UTF8", "YES");
        //读取字段属性值时设置，否则有中文乱码
        gdal.SetConfigOption("SHAPE_ENCODING", "CP936");
		DataSource ds = ogr.Open(strFilePath, 0);
		if(ds == null){
			System.out.println("1打开文件"+strFilePath+"失败");
			return;
		}
		System.out.println("1打开文件"+strFilePath+"成功");
		int iLayerCount = ds.GetLayerCount();
		Layer oLayer = ds.GetLayerByIndex(0);
		if(oLayer ==null){
			System.out.println("读取图层0失败");
			return;
		}
		oLayer.ResetReading();
		
		FeatureDefn oDefn = oLayer.GetLayerDefn();
		int iFieldCount = oDefn.GetFieldCount();
		for(int i=0;i<iFieldCount;i++){
			FieldDefn oField = oDefn.GetFieldDefn(i);
			System.out.println(oField.GetNameRef()+":"+oField.GetFieldTypeName(oField.GetFieldType())+ "(" +
                    oField.GetWidth()+"."+ oField.GetPrecision() + ")");
		}
		
		// 输出图层中的要素个数
        System.out.println("要素个数 = " + oLayer.GetFeatureCount(0));
        Feature oFeature = null;
        // 下面开始遍历图层中的要素
        while ((oFeature =oLayer.GetNextFeature()) != null)
        {
                  System.out.println("当前处理第" + oFeature.GetFID() + "个:\n属性值：");
                  // 获取要素中的属性表内容
                  for (int iField = 0; iField< iFieldCount; iField++)
                  {
                           FieldDefn oFieldDefn= oDefn.GetFieldDefn(iField);
                           int type =oFieldDefn.GetFieldType();
                           switch (type)
                           {
                                    case ogr.OFTString:
                                              System.out.println(oFeature.GetFieldAsString(iField)+ "\t");
                                              break;
                                    case ogr.OFTReal:
                                              System.out.println(oFeature.GetFieldAsDouble(iField)+ "\t");
                                              break;
                                    case ogr.OFTInteger:
                                              System.out.println(oFeature.GetFieldAsInteger(iField)+ "\t");
                                              break;
                                    default:
                                              System.out.println(oFeature.GetFieldAsString(iField)+ "\t");
                                              break;
                           }
                  }
                  // 获取要素中的几何体
                  Geometry oGeometry =oFeature.GetGeometryRef();
                  // 为了演示，只输出一个要素信息
                  break;
        }
        System.out.println("数据集关闭！");
	}
	
	public static ArrayList<String> readTufuhaoFromShp(){
		String strFilePath = "C:\\temp\\GDALtest\\test\\test\\仪陇县.shp";
		ArrayList<String> tufuhaoList = new ArrayList<String>();
		gdal.SetConfigOption("GDAL_FILENAME_IS_UTF8", "YES");
        //读取字段属性值时设置，否则有中文乱码
        gdal.SetConfigOption("SHAPE_ENCODING", "CP936");
		DataSource ds = ogr.Open(strFilePath, 0);
		if(ds == null){
			System.out.println("1打开文件"+strFilePath+"失败");
			return tufuhaoList;
		}
		System.out.println("1打开文件"+strFilePath+"成功");
		int iLayerCount = ds.GetLayerCount();
		Layer oLayer = ds.GetLayerByIndex(0);
		if(oLayer ==null){
			System.out.println("读取图层0失败");
			return tufuhaoList;
		}
		oLayer.ResetReading();
		
		FeatureDefn oDefn = oLayer.GetLayerDefn();
		int iFieldCount = oDefn.GetFieldCount();
	
		// 输出图层中的要素个数
        System.out.println("要素个数 = " + oLayer.GetFeatureCount(0));
        Feature oFeature = null;
        // 下面开始遍历图层中的要素
        while ((oFeature =oLayer.GetNextFeature()) != null)
        {
                  System.out.println("当前处理第" + oFeature.GetFID() + "个:\n");
                  // 获取要素中的属性表内容
                  String tufuhao = oFeature.GetFieldAsString("标准图号");
                  tufuhaoList.add(tufuhao);
                  System.out.println(tufuhao);
        }
        System.out.println("数据集关闭！");
        
        return tufuhaoList;
	}
	*/
	public HashMap<String, Object> readTufuhaoFromShp(String strFilePath){
		//结果集 “1” featureList "2" geoJson
		HashMap<String, Object> rs = new HashMap<String, Object>();
		ArrayList<String> tufuhaoList = new ArrayList<String>();
		ArrayList<HashMap<String, String>> featureList = new ArrayList<HashMap<String, String>>();
		gdal.SetConfigOption("GDAL_FILENAME_IS_UTF8", "YES");
        //读取字段属性值时设置，否则有中文乱码
        gdal.SetConfigOption("SHAPE_ENCODING", "CP936");
		DataSource ds = ogr.Open(strFilePath, 0);
		System.out.println("shpFilePath: =  "+strFilePath);
		if(ds == null){
			System.out.println("1打开文件"+strFilePath+"失败");
			return rs;
		}
		System.out.println("1打开文件"+strFilePath+"成功");
		int iLayerCount = ds.GetLayerCount();
		Layer oLayer = ds.GetLayerByIndex(0);
		if(oLayer ==null){
			System.out.println("读取图层0失败");
			return rs;
		}
		oLayer.ResetReading();
		
		FeatureDefn oDefn = oLayer.GetLayerDefn();
		int iFieldCount = oDefn.GetFieldCount();
	
		// 输出图层中的要素个数
        System.out.println("要素个数 = " + oLayer.GetFeatureCount(0));
        Feature oFeature = null;
        
        String geoJson = "{\"type\": \"FeatureCollection\","
				+ "	\"features\": ["
				+ " ";
		int count=0;
		
        // 下面开始遍历图层中的要素
        while ((oFeature =oLayer.GetNextFeature()) != null)
        {
        	HashMap<String, String> featureMap = new HashMap<String, String>();
        	for(int i=0;i<iFieldCount;i++){

        		FieldDefn oField = oDefn.GetFieldDefn(i);
        		if(oField.GetName() == "标准图号" || oField.GetName() == "新图号"){

                	System.out.println(oField.GetName());
                	
        			String tufuhao = oFeature.GetFieldAsString(i);
        			
        			System.out.println(tufuhao);
        			tufuhaoList.add(tufuhao);
        		}
        		featureMap.put(oField.GetName(), oFeature.GetFieldAsString(i));
        	}
        	String xyStr="";
        	if(count!=0)
            	geoJson += ',';
        	geoJson += "{"
        			+  " \"type\": \"Feature\","
    				+  " \"id\": \""+oFeature.GetFID()+"\","
        			+  " \"geometry\": {"
        			+  "		\"type\": \"Polygon\","
        			+  "		\"coordinates\": [[";
        	System.out.println("GetFID():"+oFeature.GetFID());
        	Integer fid = (Integer)oFeature.GetFID();
        	featureMap.put("FID",fid.toString());
        	Geometry geometry = oFeature.GetGeometryRef();
        	int geoType = geometry.GetGeometryType();
        	//System.out.println("geoType:"+geoType);
        	Geometry polygon = geometry.GetGeometryRef(0);
        	for(int j=0;j<polygon.GetPointCount();j++){
        		double x = polygon.GetX(j);
        		double y = polygon.GetY(j);
        		String tempStr = x+","+y+",";
        		xyStr +=tempStr;
        		if(j != polygon.GetPointCount()-1)
        			geoJson += "["+x+", "+y+"],";
        		else
        			geoJson += "["+x+", "+y+"]]]"
        					+ "}"
        					+ "}";
        	}
        	featureMap.put("geometry", xyStr);
        	featureList.add(featureMap);
        	count++;
        }
       geoJson +="]}";
       
       // String jsonString = JSON.toJSONString(featureList);
        System.out.println("数据集关闭！");
        rs.put("1", featureList);
        rs.put("2", geoJson);
        return rs;
	}
}
