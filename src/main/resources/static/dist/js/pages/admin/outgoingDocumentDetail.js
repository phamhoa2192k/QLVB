const OUTGOING_DOCUMENT_API = "/api/outgoingdocument/";
const HANDLING_API = "/api/handling";

function getId(){
	var url  = new URL(window.location.href);
	var id = url.searchParams.get("id");
	return id;
}

function sendAssignNumber(data){
	fetch(OUTGOING_DOCUMENT_API + "assignnumber", {
		method:"PUT",
		headers:{
			"Content-Type": "application/json"
		},
		body: JSON.stringify(data)
	})
	.then(() => window.location.reload())
	.catch(console.log);
}

function sendAcceptDocument(data){
	fetch(OUTGOING_DOCUMENT_API + "accept", {
		method:"PUT",
		headers:{
			"Content-Type": "application/json"
		},
		body: JSON.stringify(data)
	})
	.then(() => window.location.href = "/admin/vbphAdmin")
	.catch(console.log);
}

function sendRejectDocument(data){
	fetch(OUTGOING_DOCUMENT_API + "refuse", {
		method:"PUT",
		headers:{
			"Content-Type": "application/json"
		},
		body: JSON.stringify(data)
	})
	.then(() => window.location.href = "/admin/vbphAdmin")
	.catch(console.log);
}

async function getAssignNumber(){
	var id = await getCurrentUser();
	var data = {
		"id": getId(),
		"number": $("#outGoingDocumentNumber").val(),
		"assigneeId": id.id
	}
	console.log("assign number: " , data);
	return data;
}

async function getOutGoingDocumentById(id){
	var document = await fetch(OUTGOING_DOCUMENT_API + "search/" + id).then(data => data.json()).catch(console.log);
	console.log("document", document);
	return document;
}

function setOutGoingDocumentDetail(doc){
	var base = doc;
	$("#receivedDocumentName").text(base.name);
	$("#receivedDocumentAgencyCode").text(base.agencyCode);	
	$("#receivedDocumentCode").text(base.symbol);
	$("#receivedDocumentNumber").text(base.number);
	$("#receivedDocumentType").text(base.type);
	$("#receivedDocumentIssuanceTime").text(base.issuanceTime);
	$("#receivedDocumentContent").text(base.content);
	$("#receivedDocumentAttachedDocument").attr("href", doc.file);
	$("#receivedDocumentDeadline").text(doc.deadline);
	$("#receivedDocumentSecurityLevel").text(doc.securityLevel);
	$("#receivedDocumentUrgencyLevel").text(doc.urgencyLevel);
}

$("#assignNumberForm").submit(async function (e) { 
	e.preventDefault();
	if($("#outGoingDocumentNumber").val() > 0){
		var data = await getAssignNumber();
		sendAssignNumber(data);
	}
	
});

$("#acceptForm").submit(async function (e) { 
	e.preventDefault();
	var data = {
		userId: (await getCurrentUser()).id,
		baseDocumentId: getId()
	}
	console.log("accept: ", data);
	sendAcceptDocument(data);
});

$("#rejectForm").submit(async function (e) { 
	e.preventDefault();
	var data = {
		userId: (await getCurrentUser()).id,
		baseDocumentId: getId(),
		note: $("#reasonReject").val()
	}
	console.log("accept: ", data);
	sendRejectDocument(data);
});

$("#handleForm").submit(async function (e) { 
	e.preventDefault();
	var data = {
		id: getId(),
		file: $("#fileHandle").val()
	}
	console.log("file hanlde: ", data);
	sendHanleDocument(data);
});

function sendHanleDocument(data){
	fetch(OUTGOING_DOCUMENT_API + "update", {
		method:"PUT",
		headers:{
			"Content-Type": "application/json"
		},
		body: JSON.stringify(data)
	})
	.then(() => window.location.href = "/admin/vbphAdmin")
	.catch(console.log);
}

function setTimelines(timelines){
	timelines.forEach(timeline => {
		let time = timeline.time;
		let username = timeline.user.fullName;
		let action = timeline.action ;
		let note = timeline.note ;
		let color = ["red", "green", "blue"];
		let template = `<div>
											<i class="fas fa-user bg-${color[Math.floor(Math.random() * 3)]}"></i>
											<div class="timeline-item">
												<span class="time"><i class="fas fa-clock"></i> ${time}</span>
												<h3 class="timeline-header"><a href="#">${username}</a> ${action}</h3>
												<div class="timeline-body">
													${note?note:""}
												</div>
											</div>
										</div>`;
		$("#timeline").append(template);
	});
	$("#timeline").append(`<div><i class="fas fa-clock bg-gray"></i></div>`);
}

async function getTimelines(id){
	var timelines = await fetch(HANDLING_API + "?documentId=" + id)
									.then(data => data.json())
									.catch(console.log)
	console.log(timelines);
	return timelines;
}

$(document).ready(async function () {
	var id = getId();
	var document = await getOutGoingDocumentById(id);
	setOutGoingDocumentDetail(document);
	var timelines = await getTimelines(id);
	setTimelines(timelines);
});
