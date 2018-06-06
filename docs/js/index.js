var pageCounter = 1;
var temperaturas = [];
var resist = [];
var fechas = [];



var ctx = document.getElementById('myChart').getContext('2d');
var myChart = new Chart(ctx, {
  type: 'line',
  data: {
    labels: fechas,
    datasets: [{
      label: 'Temp',
      data: temperaturas,
      backgroundColor: "rgba(153,255,51,0.6)"
    }, {
      label: 'Resistencia',
      data: resist,
      backgroundColor: "rgba(255,153,0,0.6)"
    }]
  }
});

function addData(chart, label, data) {
    chart.data.labels.push(label);
    chart.data.datasets.forEach((dataset) => {
        dataset.data.push(data);
    });
    chart.update();
}

function removeData(chart) {
    chart.data.labels.pop();
    chart.data.datasets.forEach((dataset) => {
        dataset.data.pop();
    });
    chart.update();
}


var btn = document.getElementById("btn");
if (btn){
  btn.addEventListener("click", function() {
    var ourRequest = new XMLHttpRequest();
    ourRequest.open('GET', 'https://iotprojecteafit.herokuapp.com/registers/EquipoCeiba7.json');
    ourRequest.onload = function() {
      if (ourRequest.status >= 200 && ourRequest.status < 400) {
        console.log("We connected to the server");
        var ourData = JSON.parse(ourRequest.responseText);
        renderHTML(ourData,myChart);
      } else {
        console.log("We connected to the server, but it returned an error.");
      }


    };

    ourRequest.onerror = function() {
      console.log("Connection error");
    };

    ourRequest.send();

  });
}


function renderHTML(data,chart) {
  for (i = 0; i < data.length; i++) {
    var x = data[i].var_1;
    var y = data[i].var_2;
    var z = data[i].created_at;
    temperaturas.push(x);
    resist.push(y);
    fechas.push(z);
  }
chart.update();

temperaturas =[];
fechas =[];
resist =[];

}
