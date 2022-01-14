const INCOMING_DOCUMENT_API = "/api/incomingdocument/";

async function getListIncomingDocument(){
	var receivedDocuments = await fetch(INCOMING_DOCUMENT_API + "all").then(data => data.json()).catch(console.log);
	console.log("incoming docs: ", receivedDocuments);
	return receivedDocuments;
}

async function sendNewReceivedDocument(document){
	fetch( INCOMING_DOCUMENT_API, {
		method: "POST",
		headers:{
			"Content-Type": "application/json"
		},
		body: JSON.stringify(document)
	})
	//	.then(window.location.reload())
		.catch(console.log);
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
			"file": "string",
			"securityLevel": securityLevel,
			"urgencyLevel": urgencyLevel
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
									<a href="/admin/incomingDocumentDetail?id=${row.id}" style="cursor: pointer;"><i class="fas fa-edit"></i></a>
									</button>`
				}
			}
		]
	});
}

$(document).ready(async function () {
	var listDocument = await getListIncomingDocument();
	setIncomingDocuments(listDocument);
});

$("#newReceivedDocumentForm").submit(function (e) { 
	e.preventDefault();
	var document = getNewInfomationFromFormNewReceivedDocument();
	//sendNewReceivedDocument(document);
});