/**
 * 
 */


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
		window.location.href="index2.jsp";
	}
}