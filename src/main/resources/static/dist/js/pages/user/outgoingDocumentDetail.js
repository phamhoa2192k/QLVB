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
	.then(() => window.location.href = "/user/vbphUser")
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
	.then(() => window.location.href = "/user/vbphUser")
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
	$("#receivedDocumentAttachedDocument").attr("download", "file.docx");
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
	else {
		toastr["error"]("Số văn bản không được âm", "Dữ liệu không đúng định dạng");
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
	console.log("reject: ", data);
	sendRejectDocument(data);
});

$("#handleForm").submit(async function (e) { 
	e.preventDefault();
	var data = {
		id: getId(),
		file:await (readFileAsDataURL(window.document.querySelector("#fileHandle").files[0])),
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
	.then(() => window.location.href = "/user/vbphUser")
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
													${note}
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
	await setRole();
	var id = getId();
	var document = await getOutGoingDocumentById(id);
	setOutGoingDocumentDetail(document);
	var timelines = await getTimelines(id);
	setTimelines(timelines);
});


async function setRole(){
	var user = await getCurrentUser();
	var doc = await getOutGoingDocumentById(getId());
	var assignId = doc.assigneeId;
	var role = user.position;
	console.log(role);
	if(doc.status == "Chờ cấp số"){
		$("button[data-target='#modal-handle']").hide();
		$("button[data-target='#modal-accept']").hide();
        $("button[data-target='#modal-reject']").hide();
	}

	if(doc.status == "Chờ chỉnh sửa"){
		$("button[data-target='#modal-accept']").hide();
		$("button[data-target='#modal-reject']").hide();
		$("button[data-target='#modal-assignnumber']").hide();
	}

	if(doc.status == "Hoàn thành"){
		$("button[data-target='#modal-accept']").hide();
		$("button[data-target='#modal-reject']").hide();
		$("button[data-target='#modal-handle']").hide();
		$("button[data-target='#modal-assignnumber']").hide();
		}

    if(doc.status == "Đã cấp số"){
        $("button[data-target='#modal-assignnumber']").hide();
    }

    if(doc.status == "Đã chỉnh sửa"){
        $("button[data-target='#modal-assignnumber']").hide();
    }

	switch(role){
		case "Lãnh đạo": 
		    $("button[data-target='#modal-handle']").hide();
		break;
		case "Nhân viên":
			if(user.id === assignId){
				$("button[data-target='#modal-accept']").hide();
				$("button[data-target='#modal-reject']").hide();
				$("button[data-target='#modal-assignnumber']").hide();
			}
			else{
				$("button[data-target='#modal-accept']").hide();
				$("button[data-target='#modal-reject']").hide();
				$("button[data-target='#modal-handle']").hide();
				$("button[data-target='#modal-assignnumber']").hide();
			}
			break;
		case "Văn thư":
			$("button[data-target='#modal-accept']").hide();
			$("button[data-target='#modal-reject']").hide();
			$("button[data-target='#modal-handle']").hide();
			break;
		default:
			break;
	}
}

async function readFileAsDataURL(file) {
	let result_base64 = await new Promise((resolve) => {
			let fileReader = new FileReader();
			fileReader.onload = (e) => resolve(fileReader.result);
			fileReader.readAsDataURL(file);
	});
	console.log(result_base64);
	return result_base64;
}