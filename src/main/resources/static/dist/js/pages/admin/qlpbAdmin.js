function guiDuLieuPhongBan(phongban) {
	fetch("/api/department/insert", {
		method: "POST",
		headers: {
			"Content-Type": "application/json"
		},
		body: JSON.stringify(phongban)
	}).then(console.log)
}

function guiDuLieuNhanVienMoi(user) {
	fetch("/api/user", {
		method: "POST",
		headers: {
			"Content-Type": "application/json"
		},
		body: JSON.stringify(user)
	}).then(console.log)
}

async function layUserCuaPhongBan(id) {
	var users = await fetch(`/api/user`).then(data => data.json())
	var usersOfDepartment = users.filter(user => user.department.id == id)
	return usersOfDepartment;
}

function setDanhSachNhanVien(danhSachNhanVien) {
	if ($.fn.DataTable.isDataTable('#danhSachNhanVien')) {
		$('#danhSachNhanVien').DataTable().destroy();
	}
	$('#danhSachNhanVien tbody').empty();
	$("#danhSachNhanVien").DataTable({
		data: danhSachNhanVien,
		columnDefs: [
			{
				targets: [0, 1, 2, 3],
				className: 'dt-body-center dt-head-center'
			}
		],
		columns: [
			{ title: "Tên thành viên", data: 'fullName' },
			{ title: "Chức vụ", data: 'position' },
			{
				title: "Chi tiết",
				render: function (data, type, row) {
					return `<button style="padding: 0;" type="button" class="btn">
									<a href="/admin/profile?id=${row.id}" style="cursor: pointer;"><i class="fas fa-edit"></i></a>
									</button>`
				}
			},
			{
				title: "Xoá",
				render: function () {
					return `<button style="padding: 0;" type="button" class="btn" data-toggle="modal" data-target="#modal-delete">
									<i class="fas fa-trash"></i>
									</button>`
				}
			},
		]
	})
}

function setThongTinPhongBan(phongban) {
	var tenPhongBan = phongban.name;
	var maPhongBan = phongban.code;
	var soNhanVien = phongban.numberOfStaff;
	var soDienThoaiLienHe = phongban.phonenumber;
	var diaChiTruSo = phongban.address;
	$("#tenPhongBan").text(tenPhongBan);
	$("#maPhongBan").text(maPhongBan);
	$("#soNhanVien").text(soNhanVien);
	$("#soDienThoaiLienHe").text(soDienThoaiLienHe);
	$("#diaChiTruSo").text(diaChiTruSo);
}

function handleSendNewDepartment() {
	var phongban = {};
	phongban.name = $("#newTenPhongBan").val();
	phongban.code = $("#newMaPhongBan").val();
	phongban.address = $("#newDiaChi").val();
	phongban.phonenumber = $("#newSoDienThoai").val();
	return phongban
}

async function handleChangDepartment(id) {
	var department = await fetch(`/api/department/search/${id}`)
		.then(data => data.json()).catch(console.log)
	setThongTinPhongBan(department);
	var usersOfDepartment = await layUserCuaPhongBan(id);
	setDanhSachNhanVien(usersOfDepartment);
}

function setDanhSachPhongBan(phongban) {
	phongban.forEach(element => {
		let tenPhongBan = element.name;
		let idPhongBan = element.id;
		let link = `<a class="nav-link" style="cursor: pointer;" onclick="handleChangDepartment('${idPhongBan}');">${tenPhongBan}</a>`
		$("#danhSachPhongBan").append(link);
	});
}

function setDropDownDepartmentList(phongbans){
	phongbans.forEach(element => {
		let tenPhongBan = element.name;
		let code = element.code;
		let option = `<option value="${code}">${tenPhongBan}</option>`
		$("#formNewUserDepartment").append(option);
	});
}

function handleNewUserFormValue(){
	var user = {
		"userName": $("#formNewUserUserName").val(),
		"password": $("#formNewUserPassword").val(),
		"fullName": $("#formNewUserFullName").val(),
		"gender": $("#formNewUserGender").val(),
		"dob": $("#formNewUserDOB").val(),
		"position": $("#formNewUserPosition").val(),
		"departmentCode": $("#formNewUserDepartment").val(),
		"roleCodes": $("#formNewUserRole").val(),
	}
	return user;
}


$(document).ready(async function () {
	var phongbans = await getAllDepartment();
	setThongTinPhongBan(phongbans[0]);
	setDanhSachPhongBan(phongbans);
	setDanhSachNhanVien(await layUserCuaPhongBan(phongbans[0].id));
	setDropDownDepartmentList(phongbans);
});

$("#formNewDepartment").on('submit', function (e) {
	e.preventDefault();
	var phongban = handleSendNewDepartment();
	guiDuLieuPhongBan(phongban);
	window.location.reload();
	return false;
});

$("#formNewUser").on('submit', function (e) {
	e.preventDefault();
	var newUser = handleNewUserFormValue();
	guiDuLieuNhanVienMoi(newUser);
	window.location.reload();
})