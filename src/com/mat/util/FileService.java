package com.mat.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.apache.commons.fileupload.FileItem;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;
import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;
import org.directwebremoting.io.FileTransfer;
import org.directwebremoting.ui.browser.Document;

import com.mat.java.Init;

public class FileService {

	private String saveurl;
	private String url;
	public FileService(){
		saveurl = Init.getDir("project");
	}
	
	public String fileUploadForDwr(FileTransfer shpfile, FileTransfer dbffile, FileTransfer shxfile, String deploymentName) throws IOException {  
		  url = saveurl + "\\" + deploymentName + "\\接合表";
	        File wenjianjia = new File(url);
	 		if(!wenjianjia.exists() && !wenjianjia.isDirectory()){
	 			wenjianjia.mkdirs();
	 		}
         uploading(shpfile);
         uploading(dbffile);
         uploading(shxfile);
        return saveurl;  
    } 
	
	public void uploading(FileTransfer obj) throws IOException{
		String tmp = "eror";  
		 
        String fileName = ""; 
 
        String newFileName = ""; 
 
        InputStream streamIn = null;  
 
        OutputStream streamOut = null; 
        
         if (obj.getFilename() != null && !obj.getFilename().equals("")) {  
             fileName = obj.getFilename();  
             if (fileName.lastIndexOf("\\") > -1) {  
                 fileName = fileName.substring(fileName.lastIndexOf("\\") + 1);  
             }    
             try {  
                 streamIn = obj.getInputStream();  
                 streamOut = new FileOutputStream(new File(url, fileName));  
                 int bytesRead = 0;  
                 byte[] buffer = new byte[81920];  
                 while ((bytesRead = streamIn.read(buffer, 0, 81920)) != -1) {  
                     streamOut.write(buffer, 0, bytesRead);  
                 }  
                 streamOut.close();  
                 streamIn.close();  
                 tmp="success";  
             } catch (FileNotFoundException ex) {  
             } catch (IOException e) {  
             } finally {  
                 streamIn = null;  
                 streamOut = null;  
             }  
         }
	}
	
	public void uploadings(FileTransfer obj,String saveurl){
		 String tmp = "eror";  
		 
       String fileName = ""; 

       String newFileName = ""; 

       InputStream streamIn = null;  

       OutputStream streamOut = null; 
			 if (obj.getFilename() != null && !obj.getFilename().equals("")) {  
		            fileName = obj.getFilename();  
		            if (fileName.lastIndexOf("\\") > -1) {  
		                fileName = fileName.substring(fileName.lastIndexOf("\\") + 1);  
		            }    
		            try {  
		                streamIn = obj.getInputStream();  
		                streamOut = new FileOutputStream(new File(saveurl, fileName));  
		                int bytesRead = 0;  
		                byte[] buffer = new byte[81920];  
		                while ((bytesRead = streamIn.read(buffer, 0, 81920)) != -1) {  
		                    streamOut.write(buffer, 0, bytesRead);  
		                }  
		                streamOut.close();  
		                streamIn.close();  
		                tmp="success";  
		            } catch (FileNotFoundException ex) {  
		            } catch (IOException e) {  
		            } finally {  
		                streamIn = null;  
		                streamOut = null;  
		            }  
		        }
		 }
	
	public void zip(String souceFileName, String destFileName) {  
        File file = new File(souceFileName);  
        try {  
            zip(file, destFileName);  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
    }  
  
    private void zip(File souceFile, String destFileName) throws IOException {  
        FileOutputStream fileOut = null;  
        try {  
            fileOut = new FileOutputStream(destFileName);  
        } catch (FileNotFoundException e) {  
            e.printStackTrace();  
        }  
        ZipOutputStream out = new ZipOutputStream(fileOut);  
        zip(souceFile, out, "");  
        out.close();  
    }  
  
    private void zip(File souceFile, ZipOutputStream out, String base)  
            throws IOException {  
  
        if (souceFile.isDirectory()) {  
            File[] files = souceFile.listFiles();  
            out.putNextEntry(new ZipEntry(base + "/"));  
            base = base.length() == 0 ? "" : base + "/";  
            for (File file : files) {  
                zip(file, out, base + file.getName());  
            }  
        } else {  
            if (base.length() > 0) {  
                out.putNextEntry(new ZipEntry(base));  
            } else {  
                out.putNextEntry(new ZipEntry(souceFile.getName()));  
            }  
            System.out.println("filepath=" + souceFile.getPath());  
            FileInputStream in = new FileInputStream(souceFile);  
  
            int b;  
            byte[] by = new byte[1024];  
            while ((b = in.read(by)) != -1) {  
                out.write(by, 0, b);  
            }  
            in.close();  
        }  
    }
    
    public FileTransfer downloadFile(String realPath) {
    	String[] tempStr = realPath.split("\\\\");
    	int len = tempStr.length;
    	String fileName = tempStr[len-3] + tempStr[len-2].substring(0, 3) + tempStr[len-1];
    //	System.out.println(fileName);
    	zip(realPath, "d:/Data.zip");
    	realPath = "d:/Data.zip";
		File file = null;
		FileInputStream fis = null;
		try {
			file = new File(realPath);
			fis = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
		//InputStreamFactory inputStreamFactory = new SimpleInputStreamFactory(fileInputStream);
	//	String reportName = fileName.substring(0, fileName.length());
		return new FileTransfer(fileName + ".zip", "text/plain", file.length(),fis);
		//FileTransfer为DWR的一个接口。可以接受inputStream，inputStreamFactory和btye[]类型，相当的好用。
	}
}

