<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import="com.mat.java.Log"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>地理国情普查底图生产管理系统</title>
	<link href="css/bootstrap.min.css" rel="stylesheet" />
    <link href="ol/css/ol.css" rel="stylesheet"/>
 
	<link rel="stylesheet" href="assets/css/font-awesome.min.css" />

	<link rel="stylesheet" href="js/jstree/dist/themes/default/style.css" />

	<link rel="stylesheet" href="assets/css/ace.min.css" />
	<link rel="stylesheet" href="assets/css/ace-rtl.min.css" />
	<link rel="stylesheet" href="assets/css/ace-skins.min.css" />

	<script src="assets/js/ace-extra.min.js"></script>

</head>
<body>
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
				     <jsp:param name="page" value="mapquery"/> 
				</jsp:include> 

				<div class="main-content">
					<div class="breadcrumbs" id="breadcrumbs">
						<script type="text/javascript">
							try{ace.settings.check('breadcrumbs' , 'fixed')}catch(e){}
						</script>

						<ul class="breadcrumb">
							<li>
								<i class="icon-home home-icon"></i>
								<a href="index.jsp">首页</a>
							</li>
							<li class="active">地图查询</li>
						</ul><!-- .breadcrumb -->

						<div class="nav-search" id="nav-search">
							<form class="form-search">
								<span id="timeshow" class="input-icon">
										<script>setInterval("timeshow.innerHTML=new Date().toLocaleString()+' 星期'+'日一二三四五六'.charAt(new Date().getDay());",1000);</script>
								</span>
							</form>
						</div><!-- #nav-search -->
					</div>
							

					<div class="page-content">
						<div class="page-header">
							<h1>
								地图查询
							</h1>						
						</div><!-- /.page-header -->

						<div class="row">
							<div class="col-xs-2">
								<div id="jstree">
                				</div>			
							</div>	
							<div class="col-xs-10">
								<div id='map-in-query'>
								</div>				
							</div>												
						</div>
						<div class="hr hr32 hr-dotted"></div>	
						<div>
							<table>
								<thead>
									<tr>
										<th>作业编号</th>
										<th>作业编号</th>
										<th>作业编号</th>
									</tr>
								</thead>
							</table>
						</div>										
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
							<span>&nbsp; 选择皮肤</span>
						</div>
					</div>
				</div><!-- /#ace-settings-container -->				
			</div><!-- /.main-container-inner -->


			<a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
				<i class="icon-double-angle-up icon-only bigger-110"></i>
			</a>

		</div><!-- /.main-container -->

		<script src="js/jquery/jquery-1.11.3.min.js"></script>
		
		<script src="assets/js/bootstrap.min.js"></script>
		<script src="assets/js/typeahead-bs2.min.js"></script>

		<script src="assets/js/jquery-ui-1.10.3.custom.min.js"></script>
		<script src="assets/js/jquery.ui.touch-punch.min.js"></script>
		<script src="assets/js/jquery.slimscroll.min.js"></script>		
		<script src="assets/js/jquery.easy-pie-chart.min.js"></script>
		<script src="assets/js/jquery.sparkline.min.js"></script>
		<script src="assets/js/flot/jquery.flot.min.js"></script>
		<script src="assets/js/flot/jquery.flot.pie.min.js"></script>
		<script src="assets/js/flot/jquery.flot.resize.min.js"></script>
		
		<script src="ol/closure-library/closure/goog/base.js"></script>		
		<script src="ol/build//ol-deps.js"></script>
		<script src="ol/build/ol-debug.js"></script>
		<script src="ol/ol/ol/source/scgistilesource.js"></script>
		
	    <script type='text/javascript' src='/arcgisapi/dwr/engine.js'></script>
	    <script type="text/javascript" src="/arcgisapi/dwr/util.js"></script> 
		<script type='text/javascript' src='/arcgisapi/dwr/interface/DwrTask.js'></script>
		<script type='text/javascript' src='/arcgisapi/dwr/interface/DwrDeployment.js'></script>
		
		<script type="text/javascript" src='js/func/func.js'></script>
		<script type="text/javascript" src='js/func/map.js'></script>

		<script src="assets/js/ace-elements.min.js"></script>
		<script src="assets/js/ace.min.js"></script>

		<script src="js/jstree/dist/jstree.min.js"></script>

		<script>
			$(function(){
				queryDeploymentForMapQuery();
				initMap('map-in-query');
			});
		</script>
</body>
</html>