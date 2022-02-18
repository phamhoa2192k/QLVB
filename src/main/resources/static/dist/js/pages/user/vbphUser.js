const OUTGOING_DOCUMENT_API = "/api/outgoingdocument/";

async function getAllOutGoingDocument() {
	var currentUser = await getCurrentUser();
	var allDocuments = [];
	allDocuments = await fetch(OUTGOING_DOCUMENT_API + "findAll")
		.then(data => data.json())
		.catch(console.log);
	console.log("all outgoing doc: " , allDocuments);
	switch(currentUser.position){
<<<<<<< HEAD
		case "Lãnh đạo":
			allDocuments = allDocuments.filter(value => value.status == "Đã cấp số" | value.status == "Đã chỉnh sửa" | value.status == "Hoàn thành");
=======
		case "Lãnh đạo": 
			allDocuments = allDocuments.filter(value => value.status == "Đã cấp số" | value.status == "Đã chỉnh sửa" | value.status == "Hoàn thành" | value.status == "Chờ chỉnh sửa");
>>>>>>> aaeb9af5febb09fb737c39aa767b0c5cf820883a
			break;
		case "Nhân viên":
			allDocuments = allDocuments.filter(value => value.status == "Chờ cấp số" | value.status == "Chờ chỉnh sửa" | value.status == "Đã chỉnh sửa");
			break;
		case "Văn thư":
			allDocuments = allDocuments.filter(value => value.status == "Chờ cấp số" | value.status == "Đã cấp số");
			break;
		default:
			break;
	}
	return allDocuments;
}

function sendNewOutGoingDocument(doc){
	fetch(OUTGOING_DOCUMENT_API + 'insert',{
		method:"POST",
		headers:{
			"Content-Type": "application/json"
		},
		body:JSON.stringify(doc)
	})
<<<<<<< HEAD
		.then(window.location.reload())
		.catch(console.log);
=======
	.then(() => window.location.reload())
	.catch(console.log);
>>>>>>> aaeb9af5febb09fb737c39aa767b0c5cf820883a
}

function setOutGoingDocument(documents) {
	$("#listDocuments").DataTable({
		"language": {
			"emptyTable": "Không có tài liệu nào cần xử lý",
			"info": "Từ _START_ đến _END_ trên _TOTAL_ tài liệu",
			"infoEmpty": "Không có tài liệu",
			"lengthMenu": "Hiện _MENU_ tài liệu",
			"search": "Tìm kiếm:",
			"paginate": {
				"first": "Đầu",
				"last": "Cuối",
				"next": "Tiếp theo",
				"previous": "Trước đó"
			}
		},
		data: documents,
		columnDefs: [
			{
				targets: [0, 1, 2, 3, 4, 5],
				className: 'dt-body-center dt-head-center'
			}
		],
		columns: [
			{ title: "ID", data: 'id' },
			{ title: "Tên văn bản", data: 'name' },
			{ title: "Loại văn bản", data: 'type' },
			{ title: "Ngày tạo", data: 'createdDate' },
			{ title: "Trạng thái", data: 'status' },
			{
				title: "Chi tiết",
				render: function (data, type, row) {
					return `<button style="padding: 0;" type="button" class="btn">
									<a href="/user/outgoingDocumentDetail?id=${row.id}" style="cursor: pointer;"><i class="fas fa-edit"></i></a>
									</button>`
				}
			}
		]
	});
}

async function getNewOutGoingDocument(){
	var assigneeId = (await getCurrentUser()).id;
	var security = $("input[name=securityLevel]:checked").val();
	var urgency = $("input[name=urgencyLevel]:checked").val();
	var doc = {
		"id": 0,
		"deadline": "2022-01-13T10:01:36.924Z",
		"securityLevel": security,
		"urgencyLevel": urgency,
		"name": $("#newOutGoingDocumentName").val(),
		"content": $("#newOutGoingDocumentContent").val(),
		"number": $("#newOutGoingDocumentNumber").val(),
		"agencyCode": $("#newOutGoingDocumentAgencyCode").val(),
		"numberOfPage": $("#newOutGoingDocumentNumberOfPage").val(),
		"symbol":  $("#newOutGoingDocumentSymbol").val(),
		"issuanceTime":  $("#newOutGoingDocumentIssuanceTime").val(),
		"file": await readFileAsDataURL(window.document.querySelector('#newOutGoingDocumentFile').files[0]),
		"assigneeId": assigneeId,
		"categoryId": $("#newOutGoingDocumentType").val()
	};
	console.log("new doc", doc);
	return doc;
}

$(document).ready(async function () {
	var allDocuments = await getAllOutGoingDocument();
	setOutGoingDocument(allDocuments);
	await setRole();
});

$("#newOutGoingDocument").submit(async function (e) {
	e.preventDefault();
	var doc = await getNewOutGoingDocument();
	if(checkValidate()){
		sendNewOutGoingDocument(doc);
	}
});

async function setRole(){
	var role = (await getCurrentUser()).position;
	console.log(role);
	switch (role){
		case "Lãnh đạo":
			$("button[data-target='#modal-themvanban']").hide();
			break;
		case "Văn thư":
			$("button[data-target='#modal-themvanban']").hide();
			break;
		default:
			break;
	}
<<<<<<< HEAD
}
=======
}

$("#newOutGoingDocumentType").change(function (e) {
	var a = $("#newOutGoingDocumentSymbol");
	var b = $("#newOutGoingDocumentType");
	switch (b.val()) {
		case "31": a.val("NQ"); break;
		case "32": a.val("QĐ"); break;
		case "37": a.val("TB"); break;
		case "33": a.val("CT"); break;
		case "35": a.val("QĐ"); break;
		case "36": a.val("TC"); break;
		case "38": a.val("HD"); break;
		case "39": a.val("CT"); break;
		case "40": a.val("KH"); break;
		case "41": a.val("PA"); break;
		case "42": a.val("ĐA"); break;
		case "43": a.val("DA"); break;
		case "44": a.val("BC"); break;
		case "45": a.val("BB"); break;
		case "46": a.val("TT"); break;
		case "47": a.val("HĐ"); break;
		case "48": a.val("CV"); break;
		case "49": a.val("CĐ"); break;
		case "50": a.val("BGN"); break;
		case "51": a.val("BTT"); break;
		case "52": a.val("GUQ"); break;
		case "53": a.val("GGT"); break;
		case "54": a.val("GNP"); break;
		case "55": a.val("PG"); break;
		case "56": a.val("PC"); break;
		case "57": a.val("PB"); break;
		case "58": a.val("TC"); break;
		default: a.val("");break;
	}
});


async function readFileAsDataURL(file) {
	let result_base64 = await new Promise((resolve) => {
			let fileReader = new FileReader();
			fileReader.onload = (e) => resolve(fileReader.result);
			fileReader.readAsDataURL(file);
	});
	console.log(result_base64);
	return result_base64;
}


function checkValidate() {
	var special = "~`!@#$%^&*()-_{}[];,.\"\'<>?/\\";
	var security = $("input[name=securityLevel]:checked").val();
	var urgency = $("input[name=urgencyLevel]:checked").val();
	var document = {
		"id": 0,
		"deadline": "2022-01-13T10:01:36.924Z",
		"securityLevel": security,
		"urgencyLevel": urgency,
		"name": $("#newOutGoingDocumentName").val(),
		"content": $("#newOutGoingDocumentContent").val(),
		"number": $("#newOutGoingDocumentNumber").val(),
		"agencyCode": $("#newOutGoingDocumentAgencyCode").val(),
		"numberOfPage": $("#newOutGoingDocumentNumberOfPage").val(),
		"symbol":  $("#newOutGoingDocumentSymbol").val(),
		"issuanceTime":  $("#newOutGoingDocumentIssuanceTime").val(),
		"categoryId": $("#newOutGoingDocumentType").val()
	};

	if (document.number < 0) {
		toastr["error"]("Số văn bản không được âm", "Dữ liệu không đúng định dạng");
		return false;
	}

	for (let i = 0; i < special.length; i++) {
		if (document.agencyCode.includes(special[i])) {
			toastr["error"]("Mã định danh cơ quan không chứa kí tự đặc biệt", "Dữ liệu không đúng định dạng");
			return false;
		}
	}

	for (let i = 0; i < special.length; i++) {
		if (document.name.includes(special[i])) {
			toastr["error"]("Tên văn bản không chứa kí tự đặc biệt", "Dữ liệu không đúng định dạng");
			return false;
		}
	}

	if (new Date() < new Date(document.issuanceTime)) {
		toastr["error"]("Thời gian phát hành không chính xác", "Dữ liệu không đúng định dạng");
		return false;
	}

	if (document.numberOfPage <= 0) {
		toastr["error"]("Số trang văn bản không được âm", "Dữ liệu không đúng định dạng");
		return false;
	}

	return true;

}
>>>>>>> aaeb9af5febb09fb737c39aa767b0c5cf820883a
