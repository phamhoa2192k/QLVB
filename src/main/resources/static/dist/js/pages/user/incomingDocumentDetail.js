const INCOMING_DOCUMENT_API = "/api/incomingdocument/";
const HANDLING_API = "/api/handling/";
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
	$("#receivedDocumentAgencyCode").text(base.agencyCode);
	$("#receivedDocumentCode").text(base.symbol);
	$("#receivedDocumentNumber").text(base.number);
	$("#receivedDocumentType").text(base.category.type);
	$("#receivedDocumentIssuanceTime").text(base.issuanceTime);
	$("#receivedDocumentContent").text(base.content);
	$("#receivedDocumentFile").attr("href", base.file);
	$("#receivedDocumentFile").attr("download", "file.docx");
	$("#receivedDocumentDeadline").text(base.deadline);
	$("#receivedDocumentSecurityLevel").text(doc.securityLevel);
	$("#receivedDocumentUrgencyLevel").text(doc.urgencyLevel);
}

$(document).ready(async function () {
	var id = getId();
	var document = await getReceivedDocumentById(id);
	setReceivedDocumentDetail(document);
	setOptionDepartment();
	setRole();
	var timelines = await getTimelines(id);
	setTimelines(timelines);
});

function sendHanleDocument(data){
	fetch(INCOMING_DOCUMENT_API + "update", {
		method:"PUT",
		headers:{
			"Content-Type": "application/json"
		},
		body: JSON.stringify(data)
	})
	.then(() => window.location.href = "/user/vbdUser")
	.catch(console.log);
}

$("#acceptForm").submit(async function (e) { 
	e.preventDefault();
	var currentUser = await getCurrentUser();
	var handle = {
		"time": new Date().toISOString(),
		"action": "Phê duyệt",
		"handlingUserId": currentUser.id,
		"documentId": getId()
	};
	await fetch(INCOMING_DOCUMENT_API + "handle/approve?documentId=" + getId(),{
		method:"PUT"
	})
		.catch(console.log);
	await fetch(HANDLING_API + "handle",{
		method: "POST",
		headers: {
			"Content-Type": "application/json"
		},
		body: JSON.stringify(handle)
	})
		.catch(console.log);
	window.location.href = "/user/vbdUser";
});

$("#rejectForm").submit(async function (e) { 
	e.preventDefault();
	var currentUser = await getCurrentUser();
	var handle = {
		"time": new Date().toISOString(),
		"action": "Từ chối",
		"note": $("#reasonReject").val(),
		"handlingUserId": currentUser.id,
		"documentId": getId()
	};
	await fetch(INCOMING_DOCUMENT_API + "handle/refuse?documentId=" + getId(),{
		method:"PUT"
	})
		.catch(console.log);
	await fetch(HANDLING_API + "handle",{
		method: "POST",
		headers: {
			"Content-Type": "application/json"
		},
		body: JSON.stringify(handle)
	})
		.catch(console.log);
	window.location.href = "/user/vbdUser";
});

$("#handleForm").submit(async function (e) { 
	e.preventDefault();
	var currentUser = await getCurrentUser();
	var data = {
		documentId: getId(),
		file: await readFileAsDataURL(window.document.querySelector("#fileHandle").files[0])
	}
	console.log(data);
	var handle = {
		"time": new Date().toISOString(),
		"action": "Xử lý",
		"handlingUserId": currentUser.id,
		"documentId": getId()
	};
	await fetch(INCOMING_DOCUMENT_API + "handle/upload" ,{
		method:"PUT",
		headers:{
			"Content-Type": "application/json"
		},
		body: JSON.stringify(data)
	})
		.catch(console.log);

	await fetch(HANDLING_API + "handle",{
		method: "POST",
		headers: {
			"Content-Type": "application/json"
		},
		body: JSON.stringify(handle)
	})
		.catch(console.log);
	await fetch(INCOMING_DOCUMENT_API + "handle/finishHandling?documentId=" + getId(),{
		method:"PUT"
	})
	window.location.href = "/user/vbdUser";
});


async function setOptionDepartment(){
	var departments = await fetch("/api/department/findAll").then(data => data.json()).catch(console.log);
	console.log(departments);
	departments.forEach(value => {
		$("#assignDepartment").append(`<option value="${value.id}">${value.name}</option>`)
	})
}

$("#assignForm").submit(async function (e) { 
	e.preventDefault();
	var currentUser = await getCurrentUser();
	var data = {
		"departmentId": $("#assignDepartment").val(),
		"documentId": getId()
	};

	var handle = {
		"time": new Date().toISOString(),
		"note": $("#noteAssign").val(),
		"action": "Chỉ định",
		"handlingUserId": currentUser.id,
		"documentId": getId()
	};

	console.log(handle);
	console.log(data);

	await fetch(INCOMING_DOCUMENT_API + "handle/assign",{
		method:"PUT",
		headers: {
			"Content-Type": "application/json"
		},
		body: JSON.stringify(data)
	})
		.catch(console.log);
	await fetch(HANDLING_API + "handle",{
		method: "POST",
		headers: {
			"Content-Type": "application/json"
		},
		body: JSON.stringify(handle)
	})
		.catch(console.log);
	window.location.href = "/user/vbdUser";
});


async function setRole(){
	var position = (await getCurrentUser()).position;
	var doc = await getReceivedDocumentById(getId());
	var status = doc.baseDocumentEntity.status;
	console.log(doc);
	if(status == "Tiếp nhận"){
		$("button[data-target='#modal-accept'").hide();
		$("button[data-target='#modal-reject'").hide();
	}
	if(status == "Chờ xử lý"){
		$("button[data-target='#modal-assign'").hide();
		$("button[data-target='#modal-accept'").hide();
		$("button[data-target='#modal-reject'").hide();
	}

	if(status == "Đã xử lý"){
		$("button[data-target='#modal-assign'").hide();
		if(position == "Nhân viên"){
            $("button[data-target='#modal-handle'").hide();
        }
	}

	if(status == "Hoàn thành"){
		$("button[data-target='#modal-assign'").hide();
		$("button[data-target='#modal-accept'").hide();
		$("button[data-target='#modal-reject'").hide();
		$("button[data-target='#modal-handle'").hide();
	}

	if(status == "Chờ xử lý lại"){
		$("button[data-target='#modal-assign'").hide();
		$("button[data-target='#modal-accept'").hide();
		$("button[data-target='#modal-reject'").hide();
	}

	if(position == "Lãnh đạo"){
		$("button[data-target='#modal-handle'").hide();
	}

	if(position == "Văn thư"){
		$("button[data-target='#modal-assign'").hide();
		$("button[data-target='#modal-accept'").hide();
		$("button[data-target='#modal-reject'").hide();
		$("button[data-target='#modal-handle'").hide();
	}

	if(position == "Nhân viên"){
		$("button[data-target='#modal-assign'").hide();
		$("button[data-target='#modal-accept'").hide();
		$("button[data-target='#modal-reject'").hide();
	}
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


async function readFileAsDataURL(file) {
	let result_base64 = await new Promise((resolve) => {
			let fileReader = new FileReader();
			fileReader.onload = (e) => resolve(fileReader.result);
			fileReader.readAsDataURL(file);
	});
	console.log(result_base64);
	return result_base64;
}