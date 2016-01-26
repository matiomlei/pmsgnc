/**
 * 
 */
$(document).ready(function(){
	getJigou();
	getRole();
	getGroupMember("","");
});

function getJigou(){
	DwrGroup.queryJigou(getJigouCallback);
}

function getJigouCallback(data){
	var element = document.getElementById("selInsti");
	while (element.hasChildNodes()) {
		element.removeChild(element.lastChild);
	}
	var opt = document.createElement("option");
	opt.text = '全部';
	opt.value = 'all';
	element.appendChild(opt);
	for(var i in data){
		var opt = document.createElement("option");
		opt.text = data[i]['name'];
		opt.value = data[i]['name'];
		element.appendChild(opt);
	}
}

function getRole(){
	DwrGroup.queryRole(getRoleCallback);
}

function getJigouCallback(data){
	var element = document.getElementById("selInsti");
	while (element.hasChildNodes()) {
		element.removeChild(element.lastChild);
	}
	var opt = document.createElement("option");
	opt.text = '全部';
	opt.value = '';
	element.appendChild(opt);
	for(var i in data){
		var opt = document.createElement("option");
		opt.text = data[i]['name'];
		opt.value = data[i]['name'];
		element.appendChild(opt);
	}
}

function getRoleCallback(data){
	var element = document.getElementById("selRole");
	while (element.hasChildNodes()) {
		element.removeChild(element.lastChild);
	}
	var opt = document.createElement("option");
	opt.text = '全部';
	opt.value = '';
	element.appendChild(opt);
	for(var i in data){
		var opt = document.createElement("option");
		opt.text = data[i]['name'];
		opt.value = data[i]['name'];
		element.appendChild(opt);
	}
}

function getGroupMember(role, insti){
	DwrGroup.queryGroupMember(role, insti,getGroupMemberCallback);
}

function getGroupMemberCallback(data){
	for(var i=0;i<data.length;i++){
		var newRow = "<tr><td>"+data[i].name+"</td><td>"+data[i].email+"</td><td>"+data[i].juese+"</td><td>"+data[i].jigou+"</td><td><button id='btnUserEdit' onClick='btnUserEditClick(this)'>编辑</button></td></tr>";
		$("#userTable tbody").append(newRow);
	}
}

$("#selInsti").change(function(){
	$("#userTable tbody").empty();
	var role = document.getElementById("selRole").value;
	var insti = document.getElementById("selInsti").value;
	getGroupMember(role,insti);
});

$("#selRole").change(function(){
	$("#userTable tbody").empty();
	var role = document.getElementById("selRole").value;
	var insti = document.getElementById("selInsti").value;
	getGroupMember(role,insti);
});

$("#btnUserEdit").click(function(){
	$(this).html = "确定修改";
});

function getJigouTemp(){
	DwrGroup.queryJigou(getJigouTempCallback);
}

function getRoleTemp(){
	DwrGroup.queryRole(getRoleTempCallback);
}

function getJigouTempCallback(data){
	var element = document.getElementById("tempInstiSel");
	while (element.hasChildNodes()) {
		element.removeChild(element.lastChild);
	}
	for(var i in data){
		var opt = document.createElement("option");
		opt.text = data[i]['name'];
		opt.value = data[i]['name'];
		element.appendChild(opt);
	}
}

function getRoleTempCallback(data){
	var element = document.getElementById("tempRoleSel");
	while (element.hasChildNodes()) {
		element.removeChild(element.lastChild);
	}
	for(var i in data){
		var opt = document.createElement("option");
		opt.text = data[i]['name'];
		opt.value = data[i]['name'];
		element.appendChild(opt);
	}
}

function btnUserEditClick(ele){
	if($(ele).html() == "编辑"){
		$(ele).html("确定修改");
		$(ele).parent().prev().empty();
		var content = "<select id='tempInstiSel'></select>";
		$(ele).parent().prev().append(content);
		getJigouTemp();
		$(ele).parent().prev().prev().empty();
		var content = "<select id='tempRoleSel'></select>";
		$(ele).parent().prev().prev().append(content);
		getRoleTemp();
	}
	else{
		$(ele).html("编辑");
		var userId = $('#divUserId').text();
		var insti = $(ele).parent().prev().children().val();
		var role = $(ele).parent().prev().prev().children().val();
		DwrUser.changeMembership(userId,role,insti,{callback:function (data){changeMembershipCallback(ele,role,insti,data)}});
	}	
}

function changeMembershipCallback(ele,role,insti,data){
	if(data == true){
		$(ele).parent().prev().empty();
		$(ele).parent().prev().append(insti);
		$(ele).parent().prev().prev().empty();
		$(ele).parent().prev().prev().append(role);
	}
}

function changePassword(){
	var userId = $('#divUserId').text();
	var oldPass = $('#inputOriPass').val();
	var newPass = $('#inputNewPass').val();
	DwrUser.changePassword(userId,oldPass,newPass,changePasswordCallback);
}

function changePasswordCallback(data){
	if(data == 1) 
		alert("密码修改成功");
	else
		alert("原密码输入错误");
}

function changeProjectPath(){
	
}