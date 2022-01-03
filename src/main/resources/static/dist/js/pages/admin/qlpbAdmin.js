function guiDuLieuPhongBan(phongban) {
	fetch("/api/department/insert", {
		method: "POST",
		headers: {
			"Content-Type": "application/json"
		},
		body: JSON.stringify(phongban)
	}).then(window.location.reload()).catch(console.log);
}

function guiDuLieuNhanVienMoi(user) {
	fetch("/api/user", {
		method: "POST",
		headers: {
			"Content-Type": "application/json"
		},
		body: JSON.stringify(user)
	}).then(window.location.reload()).catch(console.log);
}

function sendUpdateDepartment(department) {
	console.log(department)
	fetch("/api/department/update", {
		method: "PUT",
		headers: {
			"Content-Type": "application/json"
		},
		body: JSON.stringify(department)
	})
		.then(window.location.reload())
		.catch(console.log);
}

function sendDeleteDepartment() {
	var id = $("#fixIdPhongBan").val();
	fetch(`/api/department/delete/${id}`, {
		method: "DELETE"
	})
		.then(window.location.reload())
		.catch(console.log)
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
				render: function (data, type, row) {
					return `<div>
										<button style="padding: 0;" type="button" class="btn" data-toggle="modal" data-target="#modal-delete-${row.id}">
											<i class="fas fa-trash"></i>
										</button>
									<div class="modal fade" id="modal-delete-${row.id}">
											<div class="modal-dialog">
												<div class="modal-content">
														<div class="modal-header">
															<h4 class="modal-title">Xóa</h4>
															<button type="button" class="close" data-dismiss="modal" aria-label="Close">
																<span aria-hidden="true">&times;</span>
															</button>
														</div>
														<div class="modal-body">
															<p>Bạn có chắc chắn muốn xóa ?</p>
														</div>
														<div class="modal-footer justify-content-between">
															<button type="button" class="btn btn-default" data-dismiss="modal">Đóng</button>
															<button type="button" onclick="handleDeleteUser(${row.id})" class="btn btn-danger">Xoá</button>
														</div>
												</div>
												<!-- /.modal-content -->
											</div>
											<!-- /.modal-dialog -->
										</div> 
									</div>`
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

	$("#fixIdPhongBan").val(phongban.id)
	$("#fixTenPhongBan").val(tenPhongBan);
	$("#fixMaPhongBan").val(maPhongBan);
	$("#fixSoDienThoai").val(soDienThoaiLienHe);
	$("#fixDiaChi").val(diaChiTruSo);
}

function handleDeleteUser(id) {
	fetch("/api/user", {
		method: "DELETE",
		headers: {
			"Content-Type": "application/json"
		},
		body: JSON.stringify([id])
	}).then(window.location.reload()).catch(console.log);
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

function setDropDownDepartmentList(phongbans) {
	phongbans.forEach(element => {
		let tenPhongBan = element.name;
		let code = element.code;
		let option = `<option value="${code}">${tenPhongBan}</option>`
		$("#formNewUserDepartment").append(option);
	});
}

function handleNewUserFormValue() {
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
	console.log(user);
	return user;
}

function getFixDepartmentInfomation() {
	var department = {};
	department.id = $("#fixIdPhongBan").val();
	department.name = $("#fixTenPhongBan").val();
	department.code = $("#fixMaPhongBan").val();
	department.phonenumber = $("#fixSoDienThoai").val();
	department.address = $("#fixDiaChi").val();
	return department;
}

async function handleOpenModalDeleteDepartment() {
	var id = $("#fixIdPhongBan").val();
	var user = await layUserCuaPhongBan(id);
	console.log(user);
	if (user.length > 0) {
		$("#modalBodyDepartment").empty();
		$("#modalBodyDepartment").append("<p> Phòng này còn user nên không thể xoá được.");
	}
	else {
		$("#modalBodyDepartment").empty();
		$("#modalBodyDepartment").append("<p>Bạn có chắc muốn xoá chứ?");
		$("#btnDeleteDepartment").attr("disabled", false);
	}
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
	return false;
});

$("#formNewUser").on('submit', function (e) {
	e.preventDefault();
	var newUser = handleNewUserFormValue();
	guiDuLieuNhanVienMoi(newUser);
	return false;
})

$("#formFixDepartment").on('submit', function (e) {
	e.preventDefault();
	var department = getFixDepartmentInfomation();
	sendUpdateDepartment(department);
	return false;
});