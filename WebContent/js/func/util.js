/**
 * 
 */

function uploadFile(){  
  var shpfile=dwr.util.getValue("shppath");  
  var dbffile=dwr.util.getValue("dbfpath");
  var shxfile=dwr.util.getValue("shxpath");
  var str=shpfile.files[0].name;
/*  var fileArr =  new Array();//注意这里是用的集合，无论一个附件还是多个附件都可以了  
  for(var i=0;i<file.files.length;i++)
	  fileArr.push(file.files[i]);
//  fileArr = file.files;  */
/*  for(var i=0;i<fileArr.length;i++)
	  DwrFileService.fileUploadForDwr(fileArr[i],{callback:function (data){back(str,data)}});  */
  DwrFileService.fileUploadForDwr(shpfile,dbffile,shxfile,{callback:function (data){uploadFileCallback(str,data)}});
  return str;
}  
  
function uploadFileCallback(shpPath,data){  
	 deploymentName = document.getElementById("renwumingchen").value;
	 shpPath = "d:\\qqq\\" + shpPath;
     filePath = "d:\\temp\\" + deploymentName;
     DwrDeployment.addDeployment1(deploymentName, shpPath, filePath,addDeployment1Callback); 
}  