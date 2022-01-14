const INCOMING_DOCUMENT_API = "/api/incomingdocument/";

function getId(){
	var url  = new URL(window.location.href);
	var id = url.searchParams.get("id");
	console.log("id", id);
	return id;
}

async function getReceivedDocumentById(id){
	var document = await fetch(INCOMING_DOCUMENT_API + id).then(data => data.json()).catch(console.log);
	console.log("document", document);
	return document;
}

function setReceivedDocumentDetail(doc){
	var base = doc.baseDocumentEntity;
	$("#receivedDocumentName").text(base.name);
	$("#receivedDocumentAgentCode").text(base.agentCode);
	$("#receivedDocumentCode").text(base.symbol);
	$("#receivedDocumentNumber").text(base.number);
	$("#receivedDocumentType").text(base.category.type);
	$("#receivedDocumentIssuanceTime").text(base.issuanceTime);
	$("#receivedDocumentContent").text(base.content);
//	$("#receivedDocumentAttachedDocument").attr("href", doc.attachedDocument);
	$("#receivedDocumentDeadline").text(doc.deadline);
	$("#receivedDocumentSecurityLevel").text(doc.securityLevel);
	$("#receivedDocumentUrgencyLevel").text(doc.urgencyLevel);
}

$(document).ready(async function () {
	var id = getId();
	var document = await getReceivedDocumentById(id);
	setReceivedDocumentDetail(document);
});