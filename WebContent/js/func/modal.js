var newDeploymentContent = 
	'<div class="form-group">'+
        '<label for="renwumingchen">任务名称</label>'+
        '<input type="text" class="form-control" id="renwumingchen" placeholder="任务名称">'+
	'</div>'+
	'<div class="form-group">'+
        '<label for="starttime">开始时间</label>'+
        '<input type="date" class="form-control" id="starttime" placeholder="开始时间">'+
    '</div>'+
    '<div class="form-group">'+
        '<label for="endtime">结束时间</label>'+
        '<input type="date" class="form-control" id="endtime" placeholder="结束时间">'+
    '</div>'+
    '<div class="form-group">'+
        '<label for="renwuneirong">任务内容</label>'+
        '<textarea rows="3" class="form-control" id="renwuneirong" placeholder="任务内容"></textarea>'+
    '</div>'+
    '<div class="form-group">'+
        '<label for="shppath">shp文件路径</label>'+
        '<input type="file" id="shppath" name="shppath" accept=".shp"></input>'+
        '<label for="dbfpath">dbf文件路径</label>'+
        '<input type="file" id="dbfpath" name="dbfpath" accept=".dbf"></input>'+
        '<label for="shxpath">shx文件路径</label>'+
        '<input type="file" id="shxpath" name="shxpath" accept=".shx"></input>'+
        '<p class="help-block">选择shp、dbf和shx文件路径，注意文件的文件名应相同</p>'+
    '</div>';

var fentuzuoyeContent =
	'<div class="form-group">'+
         '<label for="zuoyeliebiao">作业列表</label>'+
         '<table class="table table-striped table-bordered table-hover" id="taskTableInfentuzuoye">'+
             '<thead>'+
             	'<tr>'+
                 	'<th>作业编号</th>'+
                    '<th>作业内容</th>'+
                    '<th>所属任务</th>'+
                '</tr>'+
             '</thead>'+
         '</table>'+
     '</div>'+
     '<div class="form-group">'+
         '<label for="starttime">分配给</label>'+
         '<select id="workerSelect"></select>'+
     '</div>';

var zuoyeshangchuanContent = 
	'<div class="form-group">'+
	    '<label for="zuoyepath">作业文件路径</label>'+
	    '<input type="file" id="zuoyepath" name="zuoyepath"></input>'+
	'</div>';

var zuoyejianchaContent = 
	'<div class="form-group">'+
	    '<label for="jianchapath">检查文件路径</label>'+
	    '<input type="file" id="jianchapath" name="jianchapath"></input>'+
	'</div>'+
	'<div class="form-group">'+
	    '<input type="checkbox" id="needReviseCheckbox">原作业是否需要修改'+
	'</div>';

var zhiliangjianchaContent = 
	'<div class="form-group">'+
	    '<label for="zhijianpath">质检文件路径</label>'+
	    '<input type="file" id="zhijianpath" name="zhijianpath"></input>'+
	'</div>'+
	'<div class="form-group">'+
	    '<input type="checkbox" id="qualityNotPassCheckbox">原作业是否需要修改'+
	'</div>'+
	'<div class="form-group">'+
		'<label for="zhijianRadio">质检质量</label>'+
		'<div id="zhijianRadio">'+
	    	'<label><input type="radio" name="zhiliang" value="1"/>优</label><br>'+
	    	'<label><input type="radio" name="zhiliang" value="2"/>良</label><br>'+
	    	'<label><input type="radio" name="zhiliang" value="3"/>合格</label><br>'+
	    	'<label><input type="radio" name="zhiliang" value="4"/>不合格</label>'+
	    '</div>'+
    '</div>';

$("#btnNewDeployment").on('click', function() {
	bootbox.dialog({
		message: newDeploymentContent,
		title:'新建任务',
		buttons: 			
		{
			"click" :
			{
				"label" : "提交",
				"className" : "btn-sm btn-primary",
				"callback": function() {
					addDeployment1();
				}
			}, 
			"button" :
			{
				"label" : "取消",
				"className" : "btn-sm"
			}
		}
	});
});

$("#fentuzuoyeModal").on('click', function() {
	bootbox.dialog({
		message: fentuzuoyeContent,
		title:'分幅作业',
		buttons: 			
		{
			"click" :
			{
				"label"     : "提交",
				"className" : "btn-sm btn-primary",
				"callback"  : function() {
					setTaskOwner();
				} 
			}, 
			"button" :
			{
				"label" : "取消",
				"className" : "btn-sm"
			}
		}
	});
});


/*$("#fentuzuoyeModal").on('click', function(e) {
	e.preventDefault();
	$("#fenTuZuoYeModalContent").removeClass('hide');
	var dialog = $("#fenTuZuoYeModalContent").dialog({
		modal: true,
		width:800, 
		title: "分幅作业",
		title_html: true,
		buttons: [ 
			{
				text: "取消",
				"class" : "btn btn-xs",
				click: function() {
					$( this ).dialog( "close" ); 
				} 
			},
			{
				text: "提交",
				"class" : "btn btn-primary btn-xs",
				click: function() {
					$( this ).dialog( "close" ); 
					setTaskOwner();
				} 
			}
		]
	});
});*/

$("#zuoYeShangChuanModal").on('click', function() {
	bootbox.dialog({
		message: zuoyeshangchuanContent,
		title:'作业上传',
		buttons: 			
		{
			"click" :
			{
				"label" : "提交",
				"className" : "btn-sm btn-primary",
				"callback": function() {
					zuoyeshangchuan();
				}
			}, 
			"button" :
			{
				"label" : "取消",
				"className" : "btn-sm"
			}
		}
	});
});

$("#zuoYeJianChaModal").on('click', function() {
	bootbox.dialog({
		message: zuoyejianchaContent,
		title:'作业检查',
		buttons: 			
		{
			"click" :
			{
				"label" : "提交",
				"className" : "btn-sm btn-primary",
				"callback": function() {
					zuoyejiancha();
				}
			}, 
			"button" :
			{
				"label" : "取消",
				"className" : "btn-sm"
			}
		}
	});
});

$("#zhiLiangJianChaModal").on('click', function(e) {
	bootbox.dialog({
		message: zhiliangjianchaContent,
		title:'质量检查',
		buttons: 			
		{
			"click" :
			{
				"label" : "提交",
				"className" : "btn-sm btn-primary",
				"callback": function() {
					zhiliangjiancha();
				}
			}, 
			"button" :
			{
				"label" : "取消",
				"className" : "btn-sm"
			}
		}
	});
});

			$("#deleteDeploymentModal").on('click', function(e) {
					e.preventDefault();
					$("#deleteDeploymentModalContent").removeClass('hide');
					var dialog = $("#deleteDeploymentModalContent").dialog({
						modal: true,
						width:800, 
					//	title: "<div class='widget-header widget-header-small'><h4 class='smaller'><i class='icon-ok'></i>新建任务</h4></div>",
						title: "删除任务",
						title_html: true,
						buttons: [ 
							{
								text: "取消",
								"class" : "btn btn-xs",
								click: function() {
									$( this ).dialog( "close" ); 
								} 
							},
							{
								text: "提交",
								"class" : "btn btn-primary btn-xs",
								click: function() {
									$( this ).dialog( "close" ); 
									deleteDeployment();
								} 
							}
						]
					});
			
					/**
					dialog.data( "uiDialog" )._title = function(title) {
						title.html( this.options.title );
					};
					**/
				});
			
			
			
			$("#zhijianfentuModal").on('click', function(e) {
				e.preventDefault();
				$("#fenTuZuoYeModalContent").removeClass('hide');
				var dialog = $("#fenTuZuoYeModalContent").dialog({
					modal: true,
					width:800, 
					title: "质检分图",
					title_html: true,
					buttons: [ 
						{
							text: "取消",
							"class" : "btn btn-xs",
							click: function() {
								$( this ).dialog( "close" ); 
							} 
						},
						{
							text: "提交",
							"class" : "btn btn-primary btn-xs",
							click: function() {
								$( this ).dialog( "close" ); 
								setTaskOwner();
							} 
						}
					]
				});
			});
			 

			