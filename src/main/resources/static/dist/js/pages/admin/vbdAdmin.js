async function getListIncomingDocument(){
	var appointments = [];
	return appointments;
}

function setIncomingDocuments(incomingDocuments){
	$("#listDocuments").DataTable({
		data: incomingDocuments,
		columnDefs: [
			{
				targets: [0, 1, 2, 3, 4, 5, 6 ],
				className: 'dt-body-center dt-head-center'
			}
		],
		columns: [
			{ title: "ID", data: 'id' },
			{ title: "Tên văn bản", data: 'position' },
			{ title: "Loại văn bản", data: 'position' },
			{ title: "Ngày tạo", data: 'position' },
			{ title: "Trạng thái", data: 'position' },
			{
				title: "Chi tiết",
				render: function (data, type, row) {
					return `<button style="padding: 0;" type="button" class="btn">
									<a href="/admin/documentDetail?id=${row.id}" style="cursor: pointer;"><i class="fas fa-edit"></i></a>
									</button>`
				}
			},
			{
				title: "Xoá",
				render: function (data, type, row) {
					return `<button style="padding: 0;" type="button" class="btn">
									<a href="/admin/documentDetail?id=${row.id}" style="cursor: pointer;"><i class="fas fa-edit"></i></a>
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