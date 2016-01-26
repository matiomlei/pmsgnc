<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-cn">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>地理国情普查底图生产管理系统</title>
		<link href="css/bootstrap.min.css" rel="stylesheet" />
		
	    <link href="ol/css/ol.css" rel="stylesheet"/>

		<link rel="stylesheet" href="assets/css/font-awesome.min.css" />

		<link rel="stylesheet" href="assets/css/ace.min.css" />
		<link rel="stylesheet" href="assets/css/ace-rtl.min.css" />
		<link rel="stylesheet" href="assets/css/ace-skins.min.css" />

		<script src="assets/js/ace-extra.min.js"></script>
		
		<style>
			.table-hover>tbody>tr.active:hover>td {
			        background-color: green;
			}
			
			.table>tbody>tr.active>td {
			        background-color: green;
			}		
			
			td{width:100%;word-break:keep-all;white-space:nowrap;overflow:hidden;text-overflow:ellipsis"}
		</style>
	</head>

	<body>
		<div style="display: none">
        	<div id='divUserId'><%=(String)session.getAttribute("userId")%></div>
            <div id='divUserName'><%=(String)session.getAttribute("userName")%></div>
            <div id='divGroupInfo'><%=(String)session.getAttribute("groupName")%></div>
	    </div>
		
		<%@include file="header.jsp"%>
	
		<div class="main-container" id="main-container">
			<script type="text/javascript">
				try{ace.settings.check('main-container' , 'fixed')}catch(e){}
			</script>
	
			<div class="main-container-inner">
				<a class="menu-toggler" id="menu-toggler" href="#">
					<span class="menu-text"></span>
				</a>
	
				<jsp:include page="sidebar.jsp" flush="true">     
				     <jsp:param name="page" value="zhiliang+${param.type}"/> 
				</jsp:include>
				
				<div class="main-content">
					<div class="breadcrumbs" id="breadcrumbs">
						<script type="text/javascript">
							try{ace.settings.check('breadcrumbs' , 'fixed')}catch(e){}
						</script>
	
						<ul class="breadcrumb" id="breadcrumb">
							<li>
								<i class="icon-home home-icon"></i>
								<a href="index.jsp">首页</a>
							</li>
							<li>
								<a href="#">系统管理</a>
							</li>
						</ul><!-- .breadcrumb -->
	
						<div class="nav-search" id="nav-search">
							<form class="form-search">
								<span id="timeshow" class="input-icon">
										<script>setInterval("timeshow.innerHTML=new Date().toLocaleString()+' 星期'+'日一二三四五六'.charAt(new Date().getDay());",1000);</script>
								</span>
							</form>
						</div>
					</div>
	
					<div class="page-content">
						<div class="row">
							<div class="col-sm-6">
								<h4 class="lighter smaller">
									用户管理
								</h4>
								<label>机构:</label>
								<select id="selInsti">
								</select>
								<label>角色:</label>
								<select id="selRole">
								</select>
								<table id="userTable" class="table table-striped table-bordered table-hover">
									<thead>
										<tr>
											<th>用户名</th>
											<th>邮箱</th>
											<th>角色</th>
											<th>机构</th>
											<th></th>
										</tr>
									</thead>
									<tbody></tbody>
								</table>
							</div><!-- /span -->
						</div><!-- /row -->					
					</div><!-- /.page-content -->
				</div><!-- /.main-content -->
			
				<div class="ace-settings-container" id="ace-settings-container">
					<div class="btn btn-app btn-xs btn-warning ace-settings-btn" id="ace-settings-btn">
						<i class="icon-cog bigger-150"></i>
					</div>
	
					<div class="ace-settings-box" id="ace-settings-box">
						<div>
							<div class="pull-left">
								<select id="skin-colorpicker" class="hide">
									<option data-skin="default" value="#438EB9">#438EB9</option>
									<option data-skin="skin-1" value="#222A2D">#222A2D</option>
									<option data-skin="skin-2" value="#C6487E">#C6487E</option>
									<option data-skin="skin-3" value="#D0D0D0">#D0D0D0</option>
								</select>
							</div>
							<span>&nbsp; Choose Skin</span>
						</div>
					</div>
				</div><!-- /#ace-settings-container -->
			</div><!-- /.main-container-inner -->
	
			<a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
				<i class="icon-double-angle-up icon-only bigger-110"></i>
			</a>
		</div><!-- /.main-container -->
	
		<script src="js/jquery/jquery-1.11.3.min.js"></script>
		
		<script src="js/bootstrap/bootstrap.min.js"></script>
	    <script src="js/bootstrap/sco.modal.js"></script>
	    <script src="js/bootstrap/sco.confirm.js"></script>
		<script src="assets/js/typeahead-bs2.min.js"></script>
		
		<script src="ol/closure-library/closure/goog/base.js"></script>
		<script src="ol/build//ol-deps.js"></script>
		<script src="ol/build/ol-debug.js"></script>
		<script src="ol/ol/ol/source/scgistilesource.js"></script>

	    <script src="js/datatables/jquery.dataTables.min.js"></script>
		<script src="js/datatables/dataTables.bootstrap.min.js"></script>
	  	<script src="js/datatables/dataTables.select.min.js"></script>

		<script src="assets/js/ace-elements.min.js"></script>
		<script src="assets/js/ace.min.js"></script>

	    <script type='text/javascript' src='/arcgisapi/dwr/engine.js'></script>
	    <script type="text/javascript" src="/arcgisapi/dwr/util.js"></script> 
	    <script type='text/javascript' src='/arcgisapi/dwr/interface/JUserAccess.js'></script>
	    <script type='text/javascript' src='/arcgisapi/dwr/interface/DwrFileService.js'></script>
		<script type='text/javascript' src='/arcgisapi/dwr/interface/DwrDeployment.js'></script>
		<script type='text/javascript' src='/arcgisapi/dwr/interface/DwrProcess.js'></script>
		<script type='text/javascript' src='/arcgisapi/dwr/interface/DwrTask.js'></script>
		<script type='text/javascript' src='/arcgisapi/dwr/interface/DwrGroup.js'></script>
		<script type='text/javascript' src='/arcgisapi/dwr/interface/DwrUser.js'></script>
		
	    <script type="text/javascript" src='js/user_function.js'></script>
		<script type="text/javascript" src='js/func/func.js'></script>
		<script type="text/javascript" src='js/func/modal.js'></script>

		<!-- maybe no use -->
		<script src="assets/js/bootbox.min.js"></script>
		<script src="assets/js/jquery-ui-1.10.3.full.min.js"></script>
		<script src="assets/js/jquery-ui-1.10.3.custom.min.js"></script>
		<script src="assets/js/jquery.ui.touch-punch.min.js"></script>
		<script src="assets/js/jquery.slimscroll.min.js"></script>		
		<script src="assets/js/jquery.easy-pie-chart.min.js"></script>
		<script src="assets/js/jquery.sparkline.min.js"></script>
		<script src="assets/js/flot/jquery.flot.min.js"></script>
		<script src="assets/js/flot/jquery.flot.pie.min.js"></script>
		<script src="assets/js/flot/jquery.flot.resize.min.js"></script>
		<!-- maybe no use -->
		
		<script type="text/javascript">
			jQuery(function($) {
				window.prettyPrint && prettyPrint();
				$('#id-check-horizontal').removeAttr('checked').on('click', function(){
					$('#dt-list-1').toggleClass('dl-horizontal').prev().html(this.checked ? '&lt;dl class="dl-horizontal"&gt;' : '&lt;dl&gt;');
				});
			})
		</script>
	
		<script>
			$(document).ready(function(){
				var param = window.location.search;
				param = parseInt(param[param.length-1]);
				menuButtonClick('zhiliang',param);
				DwrProcess.init();
				$('[data-toggle="tooltip"]').tooltip();
			});
			
			function downloadFile(data){
			    DwrFileService.downloadFile(data,function (data){
		    		dwr.engine.openInDownload(data);
		    	});
			}
		</script>
</body>
</html>
