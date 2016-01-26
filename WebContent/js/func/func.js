/**
 * 
 */
var fentuzuoyeSet = new Array();
var renwuData;
var taskData;

var userId;
var groupId;

$(document).ready(function(){
	userId = $('#divUserId').text();
	groupId = $('#divGroupInfo').text();
	if(groupId != "workerGroup")
		userId = "";
	
	$('#btnQueryTaskInRenwuzuoye').click(function(){
	/*	if(groupId.localeCompare("workerGroup") == 0){
			queryTaskByDeploymentIdForWorker(userId);
		}
		else{*/
			queryTaskByDeploymentId();
	//	}
	});
});
/**
 *  init functions
 */
function init(){
	workerInitProcess();
	DwrProcess.init();
	menuButtonClick(0);
}

/**table init functions
 * 
 */
function cleanRenwuTable(){
	getRenwuTable(null);
}

function cleanTaskTable(){
	getTaskTable(null);
}

$.extend( true, $.fn.dataTable.defaults, {
	oLanguage: {
		"sLengthMenu": "每页显示 _MENU_ 条记录",
		"sZeroRecords": "抱歉， 没有找到",
		"sInfo": "显示第 _START_ 到 _END_ 条记录/共 _TOTAL_ 条记录",
		"sInfoEmpty": "没有数据",
		"sInfoFiltered": "(从 _MAX_ 条记录中检索)",
		"sSearch":"在记录中搜索:",
		"oPaginate": {
			"sFirst": "首页",
			"sPrevious": "前一页",
			"sNext": "后一页",
			"sLast": "尾页"
		},
		"sZeroRecords": "没有检索到数据"
	}
} );

function getRenwuTable(data){
	var renwuTable = $('#renwuTable').DataTable({
		destroy: true,
		stateSave: true,
		select:{
			style:	'api'
		},
		data:data,
		order:[[1, 'asc']],
		columns:[
					{
					    "orderable":      false,
					    "searchable":     false,
					    "data":           null,
					    "defaultContent": '<label><input type="checkbox" class="ace" onclick="tbodyCheckboxClick(this)"/><span class="lbl"></span></label>',	
					    "width": "3%"
					},
		         	{data:'id',"width": "7%"},
					{data:'name'},
					{data:'starttime',"width": "7%"},
					{data:'endtime',"width": "7%"},
					{data:'content'},
					{data:'datapath'},
					{data:'shppath'},
					{data:'iscompleted',"width": "7%"},
					{
		                "class":          'details-control',
		                "orderable":      false,
		                "data":           null,
		                "defaultContent": '<div class="visible-md visible-lg hidden-sm hidden-xs action-buttons"><a title="查看详情" class="blue" href="#" onclick="queryTaskByDeploymentId(this)"><i class="icon-zoom-in bigger-130"></i></a>&nbsp;<a title="删除任务" class="red" href="#" onclick="deleteDeployment(this)"><i class="icon-trash bigger-130"></i></a></div>',
		                "width": "6%"
					}
				],
				"columnDefs": [{
					"render": function(data, type, row, meta) {
			            return getStatusColor(data);
			        },
			        "targets":8
			    }]
	});
	return renwuTable;
}

function getTaskTable(data){
	var taskTable = $('#taskTable').DataTable({
		destroy:true,
		stateSave: true,
		bAutoWidth: false,
		select:{
			style:	'api'
		},
		order:[[1, 'asc']],
		data:data,
		columns:[
					{
					    "orderable":      false,
					    "searchable":     false,
					    "data":           null,
					    "defaultContent": '<label><input type="checkbox" class="ace" onclick="tbodyCheckboxClick(this)"/><span class="lbl"></span></label>',
					    "width": "3%"
					},
		         	{data:'tufuhao',"width": "7%"},
					{data:'name',"width": "7%"},
					{data:'suoshurenwu',"width": "7%"},
					{data:'starttime',"width": "7%"},
					{data:'endtime',"width": "7%"},
					{data:'worker',"width": "6%"},
					{data:'workerPath',"width": "15%"},
					{data:'checker',"width": "6%"},
					{data:'checkerPath',"width": "15%"},
					{data:'inspector',"width": "6%"},
					{data:'inspectorPath',"width": "15%"},
					{data:'status',"width": "8%"},
					{
		                "orderable":      false,
		                "data":           null,
		                "defaultContent": '<div class="visible-md visible-lg hidden-sm hidden-xs action-buttons"><a title="查看详情" class="blue details-control" href="#" onclick="showInfoForTask($(this))"><i class="icon-zoom-in bigger-130"></i></a><a title="分图作业" class="green" href="#" onclick="deleteDeployment()"><i class="icon-pencil bigger-130"></i></a></div>',
		                "width": "5%"
					}
				],
				"columnDefs": [{
					"render": function(data, type, row, meta) {
			            return getStatusColor(data);
			        },
			        "targets":12
			    },
			    {
			        "render": function(data, type, row, meta) {
			            return '<a href="#" title="' + data + '"onclick="downloadFile(' + "'" + data.replace(/\\/g,"\\\\") + "'" +')">' + data + '</a>';
			        },
			        "targets":[7,9,11]
			    }]
	});
	return taskTable;
}

function getStatusColor(data){
	switch(data){
	case '未完成':
		return '<span class="label">' + data + '</span>';
		break;
	case '已完成':
		return '<span class="label label-success">' + data + '</span>';
		break;
	case '分幅作业未分配':
		return '<span class="label">' + data + '</span>';
		break;
	case '分幅作业中':
		return '<span class="label label-yellow">' + data + '</span>';
		break;
	case '作业检查未分配':
		return '<span class="label label-grey">' + data + '</span>';
		break;
	case '作业检查中':
		return '<span class="label label-warning">' + data + '</span>';
		break;
	case '质量检查未分配':
		return '<span class="label label-info">' + data + '</span>';
		break;
	case '质量检查中':
		return '<span class="label label-primary">' + data + '</span>';
		break;
	case '待提交':
		return '<span class="label label-pink">' + data + '</span>';
		break;
	case '已提交':
		return '<span class="label label-success">' + data + '</span>';
		break;
	}
}

$('table tr:gt(0)').click(function () {
    $(this).closest('tr').toggleClass('info');
});

//表格标题checkbox控制
$('table th input:checkbox').on('click' , function(){
	var that = this;
	$(this).closest('table').find('tr > td:first-child input:checkbox')
	.each(function(){
		this.checked = that.checked;
		var tableType = $(this).closest('table').attr('id');
		selRow(this,tableType);
	});	
});

function tbodyCheckboxClick(tthis){
	var that = tthis;
	tthis.checked = that.checked;
	var tableType = $(tthis).closest('table').attr('id');
	selRow(tthis,tableType);
}

function selRow(tthis,tableType){
	var table = $('#'+tableType).DataTable();
	var ind = $(tthis).closest('tr').index();
	if($(tthis).closest('tr').hasClass('selected')){
		table.row(':eq('+ind+')', {page:'current'}).deselect();
		$(tthis).closest('tr').removeClass('selected');
		$(tthis).closest('tr').removeClass('info');
	}
	else{
		table.row(':eq('+ind+')', {page:'current'}).select();
		$(tthis).closest('tr').addClass('selected');
		$(tthis).closest('tr').addClass('info');
	}
}

function workerInitProcess(){
	if($('#divGroupInfo').text() == 'workerGroup'){
		$('#btnNewDeployment').hide();
		$('#btnDeleteDeployment').hide();
		$('#btnFentuzuoye').hide();
		$('#renwutijiaoButtonList').hide();
	}
}

function menuButtonClick(type, index) {
    $divList = $("#funcButtons").children("div");
    $divList.hide();
    $divList.eq(index).show();
    
    $("#navTabs a:first").tab("show");

	var str;
	if(type == "shengchan"){
		$submenuList = $('#shengchanmenu').children("li");
		$submenuList.removeClass("active");
		$submenuList.eq(index).addClass("active");
		
		switch(index){
		case 0:
			str= '任务设计';
			break;
		case 1:
			str = '任务作业';
			break;
		case 2:
	        str = '任务提交';
	        break;
		}
	}else if(type == "zhiliang"){
		$submenuList = $('#zhiliangmenu').children("li");
		$submenuList.removeClass("active");
		$submenuList.eq(index).addClass("active");
		
		switch(index){
		case 0:
			str = '质量检查';
			break;
		case 1:
	        str = '质检结果';
	        break;
		}
	}
	
	if($('#breadcrumb').children().size() == 3){
		$('#breadcrumb').children().last().remove();
	}
	if($('#breadcrumb').children().size() == 2){
    	var li=document.createElement("li");
    	var node = document.createTextNode(str);
    	li.appendChild(node);
    	var element=document.getElementById("breadcrumb");
    	element.appendChild(li);
	}
	$('#header').children().remove();
	var h=document.createElement("h1");
	var node = document.createTextNode(str);
	h.appendChild(node);
	var element = document.getElementById("header");
	element.appendChild(h);
	
	queryDeploymentProcess(type,index);
}

function queryDeploymentProcess(type,index){
	if(index == 2 && type == "shengchan"){
		if($('#divGroupInfo').text() == 'managerGroup'){
			DwrTask.queryKetijiaoDeployment(queryKetijiaoDeploymentCallback);
		}
	}
	else{
		if($('#divGroupInfo').text() == 'workerGroup'){
			var userId = $('#divUserId').text();
			queryDeploymentForWorker(userId);
		}
		else if($('#divGroupInfo').text() == 'managerGroup'){
			queryDeployment();
		}
	}
}

function queryKetijiaoDeploymentCallback(data){
	renwuData = data;
	getRenwuTable(data);
}

/**
 * login functions
 */
function loginout(){
	DwrUser.cleanSession();
	window.location.href="login.jsp";
}

/**
 *  deployment functions
 */
var deploymentName;
var shpPath;
//upload files 
function addDeployment1() {
	 shpPath = uploadFile();
}

function addDeployment1Callback(deploymentName, starttime, endtime, renwuneirong, dataPath, shpPath, filePath, data) {
    DwrDeployment.addDeployment2(deploymentName, starttime, endtime, renwuneirong, dataPath, shpPath, filePath, data,addDeployment2Callback);
}

function addDeployment2Callback(data){
	if (data == "succeed add Deployment2") {
	        alert("新建任务成功");
	        queryDeployment();
    }
    else{
        alert("新建任务失败");
    }
}

function queryDeployment(){
	DwrDeployment.queryDeployment(queryDeploymentCallback);
}

function queryDeploymentCallback(data){
	renwuData = data;
	getRenwuTable(data);
}

function queryDeploymentForWorker(userId){
	DwrProcess.queryDeploymentForWorker(userId,queryDeploymentForWorkerCallback);
}

function queryDeploymentForWorkerCallback(data){
	renwuData = data;
	getRenwuTable(data);
}

function queryDeploymentForMapQuery(){
	DwrDeployment.queryDeployment(queryDeploymentForMapQueryCallback);
}

function queryDeploymentForMapQueryCallback(data){
		renwuData = data;
		var treeData = new Array();
		for(var i=0;i<data.length;i++){
			var time = data[i].starttime.slice(0,4) + '年' + data[i].starttime.slice(5,7) + '月';
			if(treeData.length == 0){
				var y = new Object();
				y.text = time;
				var children = new Array();
				var child = new Object();
				child.text = data[i].name;
				child.id = data[i].id;
				children.push(child);
				y.children = children;
				treeData.push(y);
			}
			else{
				for(var j=0;j<treeData.length;j++){
					if(time == treeData[j].text){
						var child = new Object();
						child.text = data[i].name;
						child.id = data[i].id;
						treeData[j].children.push(child);
						break;
					}
					if(j+1 == treeData.length){
						var y = new Object();
						y.text = time;
						var children = new Array();
						var child = new Object();
						child.text = data[i].name;
						child.id = data[i].id;
						children.push(child);
						y.children = children;
						treeData.push(y);
						break;
					}
				}
			}
		}
		$('#jstree').jstree({
			'core':{
				'data' : treeData
			}
		});
	    // 7 bind to events triggered on the tree
	    $('#jstree').on("changed.jstree", function (e, data) {
	    	var selObj = data.instance.get_node(data.selected);
	    	if(selObj.parents.length > 1){
	    		var id = selObj.id;
	    		DwrTask.queryTask(id,"",{callback:function (data){queryTaskInMapQueryCallback(id,data)}});
	    	}
	    });
	    // 8 interact with the tree - either way is OK
	    $('button').on('click', function () {
	      $('#jstree').jstree(true).select_node('child_node_1');
	      $('#jstree').jstree('select_node', 'child_node_1');
	      $.jstree.reference('#jstree').select_node('child_node_1');
	   });
}

function queryTaskInMapQueryCallback(id,data){
	taskData = data;
	addVectorLayerToMapByDepId(id,'map-in-query');	
}

function queryQualityDeployment(){
	DwrProcess.queryQualityDeployment(queryQualityDeploymentCallback);
}

function queryQualityDeploymentCallback(data){
	renwuData = data;
	getRenwuTable(data);
}

function deleteDeployment(tthis){
	$(tthis).closest("tr").children().first().click();
	bootbox.confirm({
		size:     'small',
		message:  "确定删除此任务？",
		callback: function(result){
			if(result == true){
				var renwuTable = $('#renwuTable').DataTable();
				var deploymentId = renwuTable.rows({selected:true}).data()[0].id;
				DwrDeployment.deleteDeployment(deploymentId,deleteDeploymentCallback);
			}
		},
		buttons:{
			"confirm":{
				"label":"确定"
			},
			"cancel":{
				"label":"取消"
			}	
		}
	});
}

function deleteDeploymentCallback(data){
	queryDeploymentProcess("shengchan",0);
}
/**
 *  task functions
 */

function queryTaskByDeploymentId(tthis){
	$(tthis).closest("tr").children().first().click();
	var renwuTable = $('#renwuTable').DataTable();
	var deploymentId = renwuTable.rows({selected:true}).data()[0].id;
	DwrTask.queryTask(deploymentId,userId,{callback:function (data){queryTaskByDeploymentIdCallback(deploymentId,data)}});
}

function format ( d ) {
    // `d` is the original data object for the row
	var str = '<table class="table table-striped table-bordered table-hover">'+
					'<thead><tr><th></th><th>作业编号</th><th>作业内容</th><th>作业员</th><th>完成时间</th><th>上传路径</th></tr></thead>';
	for(var i=0;i<d.historyTaskList.length;i++){
		var obj = d.historyTaskList[i];
		var temp = obj.name.split(':');
		var id = temp[2];
		var name = temp[3];
		str += '<tr>'+
					'<td></td>'+
			        '<td>'+id+'</td>'+
			        '<td>'+name+'</td>'+
			        '<td>'+obj.owner+'</td>'+
			        '<td>'+obj.endtime+'</td>'+
			        '<td onclick="downloadFile(' + "'" + obj.filepath.replace(/\\/g,"\\\\") + "'" +')"><a href="#">'+obj.filepath+'</a></td>'+
			    '</tr>';
	}
	str += '</table>';
    return str;
}

function showInfoForTask(data) {
    var tr = data.closest('tr');
    var row = $('#taskTable').DataTable().row( tr );

    if (row.child.isShown()) {
        // This row is already open - close it
        row.child.hide();
        tr.removeClass('shown');
    }
    else {
        // Open this row
        row.child(format(row.data())).show();
        tr.addClass('shown');
    }
}

function queryTaskByDeploymentIdCallback(deploymentId, data){
	taskData = data;
	var taskTable = getTaskTable(data);
//	$('#taskTable').dataTable().children().last().children().each(dochild(this));
	 $("#navTabs li:eq(2) a").tab("show");
	 initMap('map');
	 addVectorLayerToMapByDepId(deploymentId,'map');
}

function queryQualityTaskCallback(deploymentId, data){
	taskData = data;
	var taskTable = getTaskTable(data);
//	$('#taskTable').dataTable().children().last().children().each(dochild(this));
	 $("#navTabs li:eq(2) a").tab("show");
	 initMap('map');
	 addVectorLayerToMapByDepId(deploymentId,'map');
	 $('#taskTable').dataTable( {
	        "createdRow": function ( row, data, index ) {
	            if ( data[4] =="分幅作业未分配" ) {
	                $('td', row).eq(4).css('font-weight',"bold").css("color","red");
	            }
	        }
	 } );
}

function dochild(tthis){
	$("tthis td:eq(5)");
}

function fentuzuoyePrepare(){
//	$('#taskTableInfentuzuoye').dataTable().children().first().children().first().children().first().attr("style","width:30px");
	if(groupId == "workerGroup"){
		alert("您没有权限分图作业");
	}
	else{
		var taskTable = $('#taskTableInfentuzuoye').DataTable({
			destroy:true,
			bAutoWidth: false,
			select:{
				style:	'os'
			},
			order:[[0, 'asc']],
			data:$('#taskTable').DataTable().rows({selected:true}).data(),
			columns:[
			         	{data:'tufuhao'},
						{data:'name'},
						{data:'suoshurenwu'}
					]
		});
		DwrGroup.showGroupMember("workerGroup",showGroupMemberCallback);
	}
}

function showGroupMemberCallback(data){
	var element = document.getElementById("workerSelect");
	while (element.hasChildNodes()) {
		element.removeChild(element.lastChild);
	}
	for(var i in data){
		var opt = document.createElement("option");
		opt.text = data[i]['name'];
		opt.value = data[i]['id'];
		element.appendChild(opt);
	}
}

function setTaskOwner(){
	$('FenTuZuoYeModal').modal('hide');
	var rows = $('#taskTableInfentuzuoye').DataTable().rows();
	var length = rows[0].length;
	var array = new Array();
	for(var i=0;i<length;i++){
		array.push(rows.data()[i]['id']);
	}
	var userid = document.getElementById("workerSelect").value;
	DwrTask.setTaskOwner(array,userid,setTaskOwnerCallback);
}

function setTaskOwnerCallback(data){
	alert("已成功分配任务");
	queryTaskByDeploymentId();
}

function zuoyeshangchuan(){
	var variable = '';
	var taskTable = $('#taskTable').DataTable();
	var taskId = taskTable.rows({selected:true}).data()[0].id;
	DwrTask.completeTask(taskId,variable,zuoyeshangchuanCallback);
}

function zuoyejiancha(){
	var variable = '';
	if($('#needReviseCheckbox').get(0).checked)
		variable = "needRevise";
	var taskTable = $('#taskTable').DataTable();
	var taskId = taskTable.rows({selected:true}).data()[0].id;
	DwrTask.completeTask(taskId,variable,zuoyejianchaCallback);
}

function zhiliangjiancha(){
	var taskTable = $('#taskTable').DataTable();
	var taskId = taskTable.rows({selected:true}).data()[0].id;
	var zhiliangpingjia = $("input[name='zhiliang']:checked").val();
	DwrTask.zhiliangpingjia(taskId,zhiliangpingjia,zhiliangpingjiaCallback);
}

function zhiliangpingjiaCallback(data){
	var taskTable = $('#taskTable').DataTable();
	var taskId = taskTable.rows({selected:true}).data()[0].id;
	var variable = '';
	if($('#qualityNotPassCheckbox').get(0).checked)
		variable = "qualityNotPass";
	var taskId = taskTable.rows({selected:true}).data()[0].id;
	DwrTask.completeTask(taskId,variable,zhiliangjianchaCallback);
}

function zuoyeshangchuanCallback(data){
	var taskfile = dwr.util.getValue("zuoyepath");
	DwrFileService.uploadings(taskfile,data,taskUploadCallback);
}

function zuoyejianchaCallback(data){
	var taskfile = dwr.util.getValue("jianchapath");
	DwrFileService.uploadings(taskfile,data,taskUploadCallback);
}

function zhiliangjianchaCallback(data){
	var taskfile = dwr.util.getValue("zhijianpath");
	DwrFileService.uploadings(taskfile,data,taskUploadCallback);
}

function taskUploadCallback(data){
	bootbox.alert({
		size: 'small',
		message:"上传成功",
		callback:function(){queryTaskByDeploymentId()}
	});
}

function queryQualityTaskByDeploymentId(){
	var renwuTable = $('#renwuTable').DataTable();
	var deploymentId = renwuTable.rows({selected:true}).data()[0].id;
	DwrTask.queryTask(deploymentId,"",queryQualityTaskByDeploymentIdCallback);
}

function queryQualityTaskByDeploymentIdCallback(data){
	taskData = data;
	var taskTable = getTaskTable(data);
	$("#navTabs li:eq(1) a").tab("show");
}

$("#NewDeploymentModal").on("hidden.bs.modal", function() {  
    $(this).removeData("bs.modal");  
});  


/**
 *    util functions
 */

function uploadFile(){  
  var shpfile=dwr.util.getValue("shppath");
  var dbffile=dwr.util.getValue("dbfpath");
  var shxfile=dwr.util.getValue("shxpath");
  deploymentName = document.getElementById("renwumingchen").value;
  var str=shpfile.files[0].name;
/*  var fileArr =  new Array();//注意这里是用的集合，无论一个附件还是多个附件都可以了  
  for(var i=0;i<file.files.length;i++)
	  fileArr.push(file.files[i]);
//  fileArr = file.files;  */
/*  for(var i=0;i<fileArr.length;i++)
	  DwrFileService.fileUploadForDwr(fileArr[i],{callback:function (data){back(str,data)}});  */
  DwrFileService.fileUploadForDwr(shpfile,dbffile,shxfile,deploymentName,{callback:function (data){uploadFileCallback(str,data)}});
  return str;
}  
  
function uploadFileCallback(shpPath,data){  
	 deploymentName = document.getElementById("renwumingchen").value;
     starttime = document.getElementById("starttime").value;
     endtime = document.getElementById("endtime").value;
     renwuneirong = document.getElementById("renwuneirong").value;
     dataPath = data + "\\" + deploymentName;
	 shpPath = data + "\\" + deploymentName + "\\接合表";
     filePath = data + "\\" + deploymentName;
     DwrDeployment.addDeployment1(deploymentName, shpPath, filePath,{callback:function(data){addDeployment1Callback(deploymentName, starttime, endtime, renwuneirong, dataPath, shpPath, filePath, data)}}); 
}  

function renwutijiao(){
	var renwuTable = $('#renwuTable').DataTable();
	var deploymentId = renwuTable.rows({selected:true}).data()[0].id;
	DwrTask.renwutijiao(deploymentId);
}