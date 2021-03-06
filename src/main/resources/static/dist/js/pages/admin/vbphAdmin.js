const OUTGOING_DOCUMENT_API = "/api/outgoingdocument/";

async function getAllOutGoingDocument() {
	var allDocuments = [];
	allDocuments = await fetch(OUTGOING_DOCUMENT_API + "findAll")
		.then(data => data.json())
		.catch(console.log);
	console.log("all outgoing doc: " , allDocuments);
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
	.then(() => window.location.reload())
	.catch(console.log);
}

function setOutGoingDocument(documents) {
	$("#listDocuments").DataTable({
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
									<a href="/admin/outgoingDocumentDetail?id=${row.id}" style="cursor: pointer;"><i class="fas fa-edit"></i></a>
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
		"issuanceTime":  $("#newOutGoingDocumentIssuanceTime").val(), //"2022-01-13T10:01:36.924Z",
		"file": $("#newOutGoingDocumentFile").val(),
		"assigneeId": assigneeId,
		"categoryId": $("#newOutGoingDocumentType").val()
	};
	console.log("new doc", doc);
	return doc;
}

$(document).ready(async function () {
	var allDocuments = await getAllOutGoingDocument();
	setOutGoingDocument(allDocuments);
});

$("#newOutGoingDocument").submit(async function (e) { 
	e.preventDefault();
	var doc = await getNewOutGoingDocument();
	sendNewOutGoingDocument(doc);
});