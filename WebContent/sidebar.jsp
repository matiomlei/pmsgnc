<%@page pageEncoding="UTF-8"%>


<div class="sidebar" id="sidebar">
	<script>
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
				<span class="menu-text">我的首页</span>
			</a>
		</li><!-- 我的首页 -->
	<% if (request.getParameter("page").contains("shengchan")) { %>
		<li class="active open">
	<% } else { %>
		<li>
	<% } %>	
			<a href="#" class="dropdown-toggle">
				<i class="icon-edit"></i>
				<span class="menu-text"> 生产管理 </span>

				<b class="arrow icon-angle-down"></b>
			</a>

			<ul id="shengchanmenu" class="submenu">
			<% if (request.getParameter("page").equals("shengchan0")) {%>
				<li class="active">
			<% } else { %>
				<li>
			<% } %>	
					<a href="shengchanguanli.jsp?type=0">
						<i class="icon-double-angle-right"></i>
						任务设计
					</a>
				</li>

			<% if (request.getParameter("page").equals("shengchan1")) {%>
				<li class="active">
			<% } else { %>
				<li>
			<% } %>	
					<a href="shengchanguanli.jsp?type=1">
						<i class="icon-double-angle-right"></i>
						任务作业
					</a>
				</li>

			<% if (request.getParameter("page").equals("shengchan2")) {%>
				<li class="active">
			<% } else { %>
				<li>
			<% } %>		
					<a href="shengchanguanli.jsp?type=2">
						<i class="icon-double-angle-right"></i>
						任务提交
					</a>
				</li>
			</ul>
		</li><!-- 生产管理 -->

		<% if (request.getParameter("page").contains("zhiliang")) { %>
			<li class="active open">
		<% } else { %>
			<li>
		<% } %>	
			<a href="#" class="dropdown-toggle">
				<i class="icon-list-alt"></i>
				<span class="menu-text"> 质量监督 </span>
				<b class="arrow icon-angle-down"></b>
			</a>

			<ul id="zhiliangmenu" class="submenu">
				<% if (request.getParameter("page").equals("zhiliang0")) {%>
					<li class="active">
				<% } else { %>
					<li>
				<% } %>	
					<a href="zhiliang.jsp?type=0">
						<i class="icon-double-angle-right"></i>
						质量检查
					</a>
				</li>

				<% if (request.getParameter("page").equals("zhiliang1")) { %>
					<li class="active">
				<% } else { %>
					<li>
				<% } %>	
					<a href="zhiliang.jsp?type=1">
						<i class="icon-double-angle-right"></i>
						质检结果
					</a>
				</li>								
			</ul>
		</li><!-- 质量监督 -->	

		<% if (request.getParameter("page").contains("mapquery")) { %>
			<li class="active open">
		<% } else { %>
			<li>
		<% } %>	
			<a href="#" class="dropdown-toggle">
				<i class="icon-picture"></i>
				<span class="menu-text"> 地图查询 </span>
				<b class="arrow icon-angle-down"></b>
			</a>

			<ul class="submenu">
				<% if (request.getParameter("page").contains("zhiliang")) { %>
					<li class="active">
				<% } else { %>
					<li>
				<% } %>	
					<a href="mapquery.jsp">
						<i class="icon-double-angle-right"></i>
						地图查询
					</a>
				</li>								
			</ul>
		</li><!-- 图形查询 -->

		<li>
			<a href="#" class="dropdown-toggle">
				<i class="icon-desktop"></i>
				<span class="menu-text"> 系统管理 </span>

				<b class="arrow icon-angle-down"></b>
			</a>

			<ul class="submenu">
				<li>
					<a href="usermanage.jsp">
						<i class="icon-double-angle-right"></i>
						用户管理
					</a>
				</li>
				<li>
					<a href="log.jsp">
						<i class="icon-double-angle-right"></i>
						日志管理
					</a>
				</li>
				<li>
					<a href="log.jsp">
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

	<script>
		try{ace.settings.check('sidebar' , 'collapsed')}catch(e){}
	</script>					
</div>