function getId(){
	var url  = new URL(window.location.href);
	var id = url.searchParams.get("id");
	return id;
}

async function getUserById(id){
	var user = await fetch(`/api/user`).then(data => data.json())
	var u = user.filter(ele => ele.id == id)[0]
	return u;
}


async function putUser(user){
	fetch(`/api/user/${user.id}`, {
		method: "PUT",
		headers: {
			"Content-Type": "application/json"
		},
		body: JSON.stringify(user)
	}).then(console.log)
}


function setDropDownDepartmentList(phongbans){
	phongbans.forEach(element => {
		let tenPhongBan = element.name;
		let code = element.code;
		let option = `<option value="${code}">${tenPhongBan}</option>`
		$("#formNewUserDepartment").append(option);
	});
}


function handleFixUserFormValue(id){
	var user = {
		"id":id,
		"userName": $("#formNewUserUserName").val(),
		"password": $("#formNewUserPassword").val(),
		"fullName": $("#formNewUserFullName").val(),
		"gender": $("#formNewUserGender").val(),
		"phonenumber": $("#formNewUserPhonenumber").val(),
		"dob": $("#formNewUserDOB").val(),
		"position": $("#formNewUserPosition").val(),
		"departmentCode": $("#formNewUserDepartment").val(),
		"roleCodes": $("#formNewUserRole").val(),
	}
	return user;
}

function setUserInformation(user){
	var roleCode = user.roles.map(value => value.code);
	$("#formNewUserUserName").val(user.userName);
	$("#formNewUserPassword").val(user.password);
	$("#formNewUserFullName").val(user.fullName);
	$("#formNewUserGender").val(user.gender);
	$("#formNewUserDOB").val(user.dob);
	$("#formNewUserPosition").val(user.position);
	$("#formNewUserDepartment").val(user.department.code);
	$("#formNewUserPhonenumber").val(user.phonenumber),
	$("#formNewUserRole").val(roleCode);
	$("#userName").text(user.fullName);
	$("#userPosition").text(user.position);
}


$(document).ready(async function () {
	var departments = await getAllDepartment();
	var user = await getUserById(getId());
	setDropDownDepartmentList(departments);
	setUserInformation(user);

});

$("#formFixUser").on('submit', function (e) {
	e.preventDefault();
	var fixUser = handleFixUserFormValue(getId());
	putUser(fixUser);
	window.location.reload();
})