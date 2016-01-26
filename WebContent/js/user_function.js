/**
 * 
 */

function saveFun(data) {
	alert(data);
	if (data) {
	  alert("注册成功！");
	} else {
	  alert("用户名已经存在！");
	}
}

function OnSave(){
	var userMap={};
	userMap.firstname=regForm.username.value;
	userMap.password=regForm.password.value;
	userMap.email=regForm.email.value;
	userMap.jigouId=regForm.jigouId.value;
	userMap.groupId=regForm.groupId.value;
	alert(userMap.firstname);
	JUserAccess.addUser(userMap,saveFun);
}