const OUTGOING_DOCUMENT_ALL_API = "/api/outgoingdocument/findAll";

async function getAllOutgoingDocument(){
	var allOutgoingDocument = await fetch(OUTGOING_DOCUMENT_ALL_API).then(data => data.json()).catch(console.log);
	console.log("all outgoing", allOutgoingDocument);
	return allOutgoingDocument;
}

function showOutgoingChart(data){
    var donutChartCanvas = $('#outgoingChart').get(0).getContext('2d')
    var donutData = {
      labels: [
          'Chờ cấp số',
          'Đã cấp số',
          'Chờ chỉnh sửa',
          'Đã chỉnh sửa',
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
	var allOutgoingDocument = await getAllOutgoingDocument();


	var chocapso = allOutgoingDocument.filter((element) => {
	    return element.status == "Chờ cấp số";
	})
    var chochinhsua = allOutgoingDocument.filter((element) => {
	    return element.status == "Chờ chỉnh sửa";
	})
	var dachinhsua = allOutgoingDocument.filter((element) => {
    	return element.status == "Đã chỉnh sửa";
    })
    var dacapso = allOutgoingDocument.filter((element) => {
            return element.status == "Đã cấp số";
        })
    var hoanthanh = allOutgoingDocument.filter((element) => {
        return element.status == "Hoàn thành";
    })

    var data = [chocapso.length,dacapso.length,chochinhsua.length,dachinhsua.length,hoanthanh.length];
    showOutgoingChart(data);
});
