const INCOMING_DOCUMENT_ALL_API = "/api/incomingdocument/all";

async function getAllIncomingDocument(){
	var allIncomingDocument = await fetch(INCOMING_DOCUMENT_ALL_API).then(data => data.json()).catch(console.log);
	console.log("all ingoing", allIncomingDocument);
	return allIncomingDocument;
}
function showIncomingChart(data){
    var donutChartCanvas = $('#incomingChart').get(0).getContext('2d')
    var donutData = {
      labels: [
          'Tiếp nhận',
          'Chờ xử lý',
          'Chờ xử lý lại',
          'Đã xử lý',
          'Hoàn thành',
      ],
      datasets: [
        {
          data: data,
          backgroundColor : ['#f56954', '#00a65a', '#f39c12', '#00c0ef', '#3c8dbc', '#d2d6de'],
        }
      ]
    }
    var donutOptions     = {
      maintainAspectRatio : false,
      responsive : true,
    }
    new Chart(donutChartCanvas, {
      type: 'doughnut',
      data: donutData,
      options: donutOptions
    })
}

$(document).ready(async function () {
	var allIncomingDocument = await getAllIncomingDocument();


	var tiepnhan = allIncomingDocument.filter((element) => {
	    return element.baseDocumentEntity.status == "Tiếp nhận";
	})
    var choxuli = allIncomingDocument.filter((element) => {
	    return element.baseDocumentEntity.status == "Chờ xử lý";
	})
	var choxulilai = allIncomingDocument.filter((element) => {
    	return element.baseDocumentEntity.status == "Chờ xử lý lại";
    })
    var daxuli = allIncomingDocument.filter((element) => {
        return element.baseDocumentEntity.status == "Đã xử lý";
    })
    var hoanthanh = allIncomingDocument.filter((element) => {
        return element.baseDocumentEntity.status == "Hoàn thành";
    })

    var data = [tiepnhan.length,choxuli.length,choxulilai.length,daxuli.length,hoanthanh.length];
    showIncomingChart(data);
});
