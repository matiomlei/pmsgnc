<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import="com.mat.java.Log"
    import="com.mat.java.Init"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>地理国情普查底图生产管理系统</title>
	<link href="css/bootstrap.min.css" rel="stylesheet" />

	<link href="assets/css/bootstrap.min.css" rel="stylesheet" />
	<link rel="stylesheet" href="assets/css/font-awesome.min.css" />

	<link rel="stylesheet" href="assets/css/ace.min.css" />
	<link rel="stylesheet" href="assets/css/ace-rtl.min.css" />
	<link rel="stylesheet" href="assets/css/ace-skins.min.css" />

	<script src="assets/js/ace-extra.min.js"></script>
</head>
<body>
	<div style="display: none">
       	<div id='divUserId'><%=(String)session.getAttribute("userId")%></div>
        <div id='divUserName'><%=(String)session.getAttribute("userName")%></div>
        <div id='divGroupInfo'><%=(String)session.getAttribute("groupName")%></div>
    </div>
		<div class="navbar navbar-default" id="navbar">
			<script type="text/javascript">
				try{ace.settings.check('navbar' , 'fixed')}catch(e){}
			</script>

			<div class="navbar-container" id="navbar-container">
				<div class="navbar-header pull-left">
					<a href="#" class="navbar-brand">
						<small>
							地理国情普查底图生产管理系统
						</small>
					</a><!-- /.brand -->
				</div><!-- /.navbar-header -->

				<div class="navbar-header pull-right" role="navigation">
					<ul class="nav ace-nav">
						
						<li class="light-blue">
							<a data-toggle="dropdown" href="#" class="dropdown-toggle">
								<img class="nav-user-photo" src="assets/avatars/user.jpg" alt="Jason's Photo" />
								<span class="user-info">
									<small>欢迎光临,</small>
									<%=(String)session.getAttribute("userName")%>
								</span>

								<i class="icon-caret-down"></i>
							</a>

							<ul class="user-menu pull-right dropdown-menu dropdown-yellow dropdown-caret dropdown-close">
								<li>
									<a href="#">
										<i class="icon-cog"></i>
										设置
									</a>
								</li>

								<li>
									<a href="#">
										<i class="icon-user"></i>
										个人资料
									</a>
								</li>

								<li class="divider"></li>

								<li>
									<a href="#" onclick="loginout()">
										<i class="icon-off"></i>
										退出
									</a>
								</li>
							</ul>
						</li>
					</ul><!-- /.ace-nav -->
				</div><!-- /.navbar-header -->
			</div><!-- /.container -->
		</div>

		<div class="main-container" id="main-container">
			<script type="text/javascript">
				try{ace.settings.check('main-container' , 'fixed')}catch(e){}
			</script>

			<div class="main-container-inner">
				<a class="menu-toggler" id="menu-toggler" href="#">
					<span class="menu-text"></span>
				</a>

				<div class="sidebar" id="sidebar">
					<script type="text/javascript">
						try{ace.settings.check('sidebar' , 'fixed')}catch(e){}
					</script>

					<div class="sidebar-shortcuts" id="sidebar-shortcuts">
						<div class="sidebar-shortcuts-large" id="sidebar-shortcuts-large">
							<button class="btn btn-success">
								<i class="icon-signal"></i>
							</button>

							<button class="btn btn-info">
								<i class="icon-pencil"></i>
							</button>

							<button class="btn btn-warning">
								<i class="icon-group"></i>
							</button>

							<button class="btn btn-danger">
								<i class="icon-cogs"></i>
							</button>
						</div>

						<div class="sidebar-shortcuts-mini" id="sidebar-shortcuts-mini">
							<span class="btn btn-success"></span>

							<span class="btn btn-info"></span>

							<span class="btn btn-warning"></span>

							<span class="btn btn-danger"></span>
						</div>
					</div><!-- #sidebar-shortcuts -->
					
					<ul class="nav nav-list">
						<li>
							<a href="index.jsp">
								<i class="icon-dashboard"></i>
								<span class="menu-text"> 我的首页 </span>
							</a>
						</li><!-- 我的首页 -->

						<li>
							<a href="#" class="dropdown-toggle">
								<i class="icon-edit"></i>
								<span class="menu-text"> 生产管理 </span>

								<b class="arrow icon-angle-down"></b>
							</a>

							<ul id="shengchanmenu" class="submenu">
								<li class="active" onclick="menuButtonClick('shengchan',0)">
									<a href="shengchanguanli.jsp">
										<i class="icon-double-angle-right"></i>
										任务设计
									</a>
								</li>
	
								<li onclick="menuButtonClick('shengchan',1)">
									<a href="shengchanguanli.jsp">
										<i class="icon-double-angle-right"></i>
										任务作业
									</a>
								</li>
	
								<li onclick="menuButtonClick('shengchan',2)">
									<a href="shengchanguanli.jsp">
										<i class="icon-double-angle-right"></i>
										任务提交
									</a>
								</li>
							</ul>
						</li><!-- 生产管理 -->

						<li>
							<a href="zhiliang.jsp" class="dropdown-toggle">
								<i class="icon-list-alt"></i>
								<span class="menu-text"> 质量监督 </span>
	
								<b class="arrow icon-angle-down"></b>
							</a>
	
							<ul id="zhiliangmenu" class="submenu">
								<li onclick="menuButtonClick('zhiliang',0)">
									<a href="zhiliang.jsp">
										<i class="icon-double-angle-right"></i>
										质量检查
									</a>
								</li>
	
								<li onclick="menuButtonClick('zhiliang',1)">
									<a href="zhiliang.jsp">
										<i class="icon-double-angle-right"></i>
										质检结果
									</a>
								</li>								
							</ul>
						</li><!-- 质量监督 -->	

						<li>
							<a href="#" class="dropdown-toggle">
								<i class="icon-picture"></i>
								<span class="menu-text"> 地图查询 </span>
	
								<b class="arrow icon-angle-down"></b>
							</a>
	
							<ul class="submenu">
								<li>
									<a href="mapquery.jsp">
										<i class="icon-double-angle-right"></i>
										地图查询
									</a>
								</li>								
							</ul>
						</li><!-- 图形查询 -->

						<li class="active open">
							<a href="#" class="dropdown-toggle">
								<i class="icon-desktop"></i>
								<span class="menu-text"> 系统管理 </span>

								<b class="arrow icon-angle-down"></b>
							</a>

							<ul class="submenu">
								<li class="active">
									<a href="#">
										<i class="icon-double-angle-right"></i>
										用户管理
									</a>
								</li>

								<li>
									<a href="#">
										<i class="icon-double-angle-right"></i>
										日志管理
									</a>
								</li>

								<li>
									<a href="#">
										<i class="icon-double-angle-right"></i>
										密码修改
									</a>
								</li>
							</ul>
						</li><!-- 系统管理 -->						
					</ul><!-- /.nav-list -->

					<div class="sidebar-collapse" id="sidebar-collapse">
						<i class="icon-double-angle-left" data-icon1="icon-double-angle-left" data-icon2="icon-double-angle-right"></i>
					</div>

					<script type="text/javascript">
						try{ace.settings.check('sidebar' , 'collapsed')}catch(e){}
					</script>					
				</div>

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
							<li>
								<a href="#">系统管理</a>
							</li>
							<li class="active">日志管理</li>
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
								系统设置
							</h1>						
						</div><!-- /.page-header -->

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

						<div class="row">
							<div class="col-sm-6">
								<h4 class="lighter smaller">日志</h4>
								<textarea rows="20" cols="100px"><%=(String)Log.readLog()%></textarea>
								<button class="btn btn-warning col-sm-6">清空日志</button>
								<button class="btn btn-warning col-sm-6">更改路径</button>
							</div><!-- /span -->
						</div><!-- /row -->	
								
						<div class="row">
							<div class="col-sm-6">
								<h4 class="lighter smaller">
									项目存放路径修改
								</h4>
								<label>当前存放路径: </label>
						        <label><%=(String)Init.getDir("project")%></label>
						        <button class="btn btn-lg btn-primary btn-block" type="button" onclick='changeProjectPath()'>修改当前路径</button>
							</div><!-- /span -->
						</div><!-- /row -->	
						
						<div class="row">
							<div class="col-sm-6">
								<h4 class="lighter smaller">
									密码修改
								</h4>
								<label for="inputOriPass" class="sr-only">原密码</label>
						        <input type="password" id="inputOriPass" name="inputOriPass" class="form-control" placeholder="原密码" required autofocus>
						        <label for="inputNewPass" class="sr-only">新密码</label>
						        <input type="password" id="inputNewPass" name="inputNewPass" class="form-control" placeholder="新密码" required>
						        <button class="btn btn-lg btn-primary btn-block" type="button" onclick='changePassword()'>确认修改</button>
							</div><!-- /span -->
						</div><!-- /row -->								
					</div><!-- /.col -->													
				</div><!-- /.row -->												
			</div><!-- /.page-content -->					
		</div><!-- /.main-content -->

		<a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
			<i class="icon-double-angle-up icon-only bigger-110"></i>
		</a>
		
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

		<script src="assets/js/ace-elements.min.js"></script>
		<script src="assets/js/ace.min.js"></script>
		
		<script type='text/javascript' src='/arcgisapi/dwr/engine.js'></script>
	    <script type="text/javascript" src="/arcgisapi/dwr/util.js"></script> 
		<script type='text/javascript' src='/arcgisapi/dwr/interface/DwrGroup.js'></script>
		<script type='text/javascript' src='/arcgisapi/dwr/interface/DwrUser.js'></script>
		
		<script type="text/javascript" src='js/func/setup.js'></script>
</body>
</html>