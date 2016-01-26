<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="css/bootstrap.min.css" rel="stylesheet">
<link href="css/signin.css" rel="stylesheet">
<title>地理国情数据生产管理系统——请登录</title>
</head>
<body>
	<div class="container">
	
      <form class="form-signin">
        <h2 class="form-signin-heading">请登录</h2>
        <label for="inputEmail" class="sr-only">用户名</label>
        <input type="text" id="inputUserId" name="inputUserId" class="form-control" placeholder="用户名" required autofocus>
        <label for="inputPassword" class="sr-only">密码</label>
        <input type="password" id="inputPassword" name="inputPassword" class="form-control" placeholder="密码" required>
        <div class="checkbox">
          <label>
            <input type="checkbox" value="remember-me">记住我
          </label>
        </div>
        <button class="btn btn-lg btn-primary btn-block" type="button" onclick="validUser()">登录</button>
      </form>
    </div> 
    
    <script src="js/jquery/jquery-1.11.3.min.js"></script>
    <script src="js/bootstrap/bootstrap.min.js"></script>
    <script type='text/javascript' src='/arcgisapi/dwr/engine.js'></script>
	<script type='text/javascript' src='/arcgisapi/dwr/interface/DwrUser.js'></script>
	<script>
		function validUser(){
			var userid = document.getElementById("inputUserId").value;
			var password = document.getElementById("inputPassword").value;
			DwrUser.validUser(userid,password,validUserCallback);
		}
	
		function validUserCallback(data){
			if(data.localeCompare("false") == 0){
				alert("用户名或密码错误！");
			}
			else{
				window.location.href="index.jsp";
			}
		}
	</script>
</body>
</html>