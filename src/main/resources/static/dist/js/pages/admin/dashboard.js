const USER_API = "/api/user";
const DEPARTMENT_FIND_ALL_API = "/api/department/findAll";

async function getAllDepartment(){
	var allDepartment = await fetch(DEPARTMENT_FIND_ALL_API).then(data => data.json()).catch(console.log);
	console.log("all department", allDepartment);
	return allDepartment;
}

async function getAllUser(){
	var allUser = await fetch(USER_API).then(data => data.json()).catch(console.log);
	console.log("all user", allUser);
	return allUser;
}

function getNumberOfProcessed(incomingDocument, outgoingDocument){
    var count = 0;
    incomingDocument.forEach((element) => {
        if(element.baseDocumentEntity.status == "Hoàn thành"){
        count++;
        }
        console.log(element.baseDocumentEntity.status)
    })
    outgoingDocument.forEach((element) => {
        if(element.status == "Hoàn thành"){
        count++;
        }
        console.log(element.status)
    })
    return count;

}

$(document).ready(async function () {
	var allDepartment = await getAllDepartment();
	var allUser = await getAllUser();
	var allIncomingDocument = await getAllIncomingDocument();
	var allOutgoingDocument = await getAllOutgoingDocument();
	var totalDocument = allIncomingDocument.length + allOutgoingDocument.length;
	var totalProcessed = getNumberOfProcessed(allIncomingDocument,allOutgoingDocument);
    var percent = ((totalProcessed/totalDocument)*100).toFixed(2);

	$("#numberOfDepartment").text(allDepartment.length);
	$("#numberOfUser").text(allUser.length);
	$("#numberOfDocument").text(totalDocument);
	$("#percentProcessed").text(percent);
});