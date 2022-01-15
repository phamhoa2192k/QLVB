const INCOMING_DOCUMENT_API = "/api/incomingdocument/";

async function getListIncomingDocument(){
	var receivedDocuments;
	var role = (await getCurrentUser()).position;
	console.log(role);
	var departmentId = (await getCurrentUser()).departmentId;
	if(role == "Văn thư"){
		receivedDocuments = await fetch(INCOMING_DOCUMENT_API + "all/clericalassistant").then(data => data.json()).catch(console.log);
	}
	if(role == "Lãnh đạo"){
		receivedDocuments = await fetch(INCOMING_DOCUMENT_API + "all/leader").then(data => data.json()).catch(console.log);
	}
	if(role == "Nhân viên"){
		receivedDocuments = await fetch(INCOMING_DOCUMENT_API + "all/employee/" + departmentId).then(data => data.json()).catch(console.log);
	} 
	console.log("incoming docs: ", receivedDocuments);
	return receivedDocuments;
}

async function sendNewReceivedDocument(document){
	var currentUser = await getCurrentUser();
	var id = await fetch( INCOMING_DOCUMENT_API, {
		method: "POST",
		headers:{
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
		await fetch(HANDLING_API + "handle",{
			method: "POST",
			headers: {
				"Content-Type": "application/json"
			},
			body: JSON.stringify(handle)
		})
			.catch(console.log);
		window.location.reload();
}

function getNewInfomationFromFormNewReceivedDocument(){
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
			"categoryId":  $("#newReceivedDocumentFormType").val(),
			"numberOfPage": $("#newReceivedDocumentFormNumberOfPage").val(),
			"file": $("#newReceivedDocumentFile").val(),
			"securityLevel": securityLevel,
			"urgencyLevel": urgencyLevel,
			"status": "Tiếp nhận"
	};
	console.log("new document: ", document);
	return document;
}


function setIncomingDocuments(incomingDocuments){
	$("#listDocuments").DataTable({
		data: incomingDocuments,
		columnDefs: [
			{
				targets: [0, 1, 2, 3, 4, 5 ],
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
	if(user.position != "Văn thư"){
		$("button[data-target='#modal-themvanban']").hide();
	}
});

$("#newReceivedDocumentForm").submit(function (e) { 
	e.preventDefault();
	var document = getNewInfomationFromFormNewReceivedDocument();
	sendNewReceivedDocument(document);
});