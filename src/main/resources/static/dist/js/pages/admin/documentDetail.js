$(document).ready(async function () {
	var currentUser = await getCurrentUser();
	if (currentUser.postion != "") {
		$("changeButton").prop('disabled', true);
	}

});

$("#approveButton").click(function (e) {
	$("#chooseNextButton").prop("disabled", false);
});

async function setUserToNextUser(){
	var users = await getAllUser();
	users.forEach(user => {
		$("#userNext").append(
			`<option value="${user.id}">${user.fullName}</option>`
		);
	})
}