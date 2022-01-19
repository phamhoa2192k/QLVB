const INCOMING_DOCUMENT_API = "/api/incomingdocument/";
const HANDLING_API = "/api/handling/";
async function getListIncomingDocument() {
	var receivedDocuments;
	var role = (await getCurrentUser()).position;
	console.log(role);
	var departmentId = (await getCurrentUser()).departmentId;
	if (role == "Văn thư") {
		receivedDocuments = await fetch(INCOMING_DOCUMENT_API + "all/clericalassistant").then(data => data.json()).catch(console.log);
	}
	if (role == "Lãnh đạo") {
		receivedDocuments = await fetch(INCOMING_DOCUMENT_API + "all/leader").then(data => data.json()).catch(console.log);
	}
	if (role == "Nhân viên") {
		receivedDocuments = await fetch(INCOMING_DOCUMENT_API + "all/employee/" + departmentId).then(data => data.json()).catch(console.log);
	}
	console.log("incoming docs: ", receivedDocuments);
	return receivedDocuments;
}

async function sendNewReceivedDocument(document) {
	var currentUser = await getCurrentUser();
	var id = await fetch(INCOMING_DOCUMENT_API, {
		method: "POST",
		headers: {
			"Content-Type": "application/json"
		},
		body: JSON.stringify(document)
	})
		.then(data => data.json())
	var handle = {
		"time": new Date().toISOString(),
		"action": "Tạo",
		"handlingUserId": currentUser.id,
		"documentId": id.id
	};
	await fetch(HANDLING_API + "handle", {
		method: "POST",
		headers: {
			"Content-Type": "application/json"
		},
		body: JSON.stringify(handle)
	})
		.catch(console.log);
	window.location.reload();
}

async function getNewInfomationFromFormNewReceivedDocument() {
	var file = window.document.querySelector('#newReceivedDocumentFile').files[0];
	var securityLevel = $("input[name=securityLevel]:checked").val();
	var urgencyLevel = $("input[name=urgencyLevel]:checked").val();
	var document = {
		"id": 0,
		"name": $("#newReceivedDocumentFormName").val(),
		"content": $("#newReceivedDocumentFormContent").val(),
		"number": $("#newReceivedDocumentFormNumber").val(),
		"agencyCode": $("#newReceivedDocumentFormAgencyCode").val(),
		"symbol": $("#newReceivedDocumentFormSymbol").val(),
		"issuanceTime": $("#newReceivedDocumentFormIssuanceTime").val(),
		"categoryId": $("#newReceivedDocumentFormType").val(),
		"numberOfPage": $("#newReceivedDocumentFormNumberOfPage").val(),
		"file": await readFileAsDataURL(file),
		"securityLevel": securityLevel,
		"urgencyLevel": urgencyLevel,
		"status": "Tiếp nhận"
	};
	console.log("new document: ", document);
	return document;
}


function setIncomingDocuments(incomingDocuments) {
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
		data: incomingDocuments,
		columnDefs: [
			{
				targets: [0, 1, 2, 3, 4, 5],
				className: 'dt-body-center dt-head-center'
			}
		],
		columns: [
			{ title: "ID", data: 'id' },
			{ title: "Tên văn bản", data: 'baseDocumentEntity.name' },
			{ title: "Loại văn bản", data: 'baseDocumentEntity.category.type' },
			{ title: "Ngày phát hành", data: 'baseDocumentEntity.issuanceTime' },
			{ title: "Trạng thái", data: 'baseDocumentEntity.status' },
			{
				title: "Chi tiết",
				render: function (data, type, row) {
					return `<button style="padding: 0;" type="button" class="btn">
									<a href="/user/incomingDocumentDetail?id=${row.id}" style="cursor: pointer;"><i class="fas fa-edit"></i></a>
									</button>`
				}
			}
		]
	});
}

$(document).ready(async function () {
	var listDocument = await getListIncomingDocument();
	setIncomingDocuments(listDocument);
	var user = await getCurrentUser();
	if (user.position != "Văn thư") {
		$("button[data-target='#modal-themvanban']").hide();
	}
});

$("#newReceivedDocumentForm").submit(async function (e) {
	e.preventDefault();
	var document = await getNewInfomationFromFormNewReceivedDocument();
	if (checkValidate()) {
		sendNewReceivedDocument(document);
	}
});


$("#newReceivedDocumentFormType").change(function (e) {
	var a = $("#newReceivedDocumentFormSymbol");
	var b = $("#newReceivedDocumentFormType");
	switch (b.val()) {
		case "4": a.val("QĐ"); break;
		case "9": a.val("TB"); break;
		case "5": a.val("CT"); break;
		case "7": a.val("QĐ"); break;
		case "8": a.val("TC"); break;
		case "10": a.val("HD"); break;
		case "11": a.val("CT"); break;
		case "12": a.val("KH"); break;
		case "13": a.val("PA"); break;
		case "14": a.val("ĐA"); break;
		case "15": a.val("DA"); break;
		case "16": a.val("BC"); break;
		case "17": a.val("BB"); break;
		case "18": a.val("TT"); break;
		case "19": a.val("HĐ"); break;
		case "20": a.val("CV"); break;
		case "21": a.val("CĐ"); break;
		case "22": a.val("BGN"); break;
		case "23": a.val("BTT"); break;
		case "24": a.val("GUQ"); break;
		case "25": a.val("GGT"); break;
		case "26": a.val("GNP"); break;
		case "27": a.val("PG"); break;
		case "28": a.val("PC"); break;
		case "29": a.val("PB"); break;
		case "30": a.val("TC"); break;
		default: a.val(""); break;
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
	var securityLevel = $("input[name=securityLevel]:checked").val();
	var urgencyLevel = $("input[name=urgencyLevel]:checked").val();
	var special = "~`!@#$%^&*()-_{}[];,.\"\'<>?/\\";
	var document = {
		"name": $("#newReceivedDocumentFormName").val(),
		"content": $("#newReceivedDocumentFormContent").val(),
		"number": $("#newReceivedDocumentFormNumber").val(),
		"agencyCode": $("#newReceivedDocumentFormAgencyCode").val(),
		"symbol": $("#newReceivedDocumentFormSymbol").val(),
		"issuanceTime": $("#newReceivedDocumentFormIssuanceTime").val(),
		"categoryId": $("#newReceivedDocumentFormType").val(),
		"numberOfPage": $("#newReceivedDocumentFormNumberOfPage").val(),
		"securityLevel": securityLevel,
		"urgencyLevel": urgencyLevel,
		"status": "Tiếp nhận"
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
