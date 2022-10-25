let context = document
    .getElementById('myChart')
    .getContext('2d');

// ajax 통신
let header = $("meta[name='_csrf_header']").attr('content');
let token = $("meta[name='_csrf']").attr('content');

$.ajax({
    async: true,
    type : "get",
    url : "/chart/data",
    beforeSend: function(xhr){
        xhr.setRequestHeader(header, token);
    },
    success:
        function (data) {
            drawChart(data);
        },
    error :
        function (request, status, error){
            alert("실패"+ "code:"+request.status+"\n"+" message : " + request.responseText +"\n"+"error:"+error);
        }
});
let labels = [];
let literature = [];
let philosophy = [];
let ss = [];
let ts = [];
let ns = [];
let art = [];
let lan = [];
let his = [];
let ex = [];

let myChart = new Chart(context, {
    type: 'line', // 차트의 형태
    data: { // 차트에 들어갈 데이터
        //x 축
        labels: labels,

        datasets: [
            { //데이터
                label: '문학', //차트 제목
                fill: false, // line 형태일 때, 선 안쪽을 채우는지 안채우는지
                data: literature,
                backgroundColor:  'rgba(255, 99, 132, 0.2)', //색상
                borderColor:  'rgba(255, 99, 132, 1)',//경계선 색상
                borderWidth: 1 //경계선 굵기
            },
            {
                label: '철학',
                fill: false,
                data: philosophy,
                backgroundColor: 'rgba(255,175,0, 0.2)',
                borderColor: 'rgb(255,175,0)',
                borderWidth: 1 //경계선 굵기
            },
            {
                label: '사회과학',
                fill: false,
                data:ss,
                backgroundColor: 'rgba(75, 192, 192,0.2)',
                borderColor: 'rgb(75, 192, 192)',
                borderWidth: 1 //경계선 굵기
            },
            {
                label: '기술과학',
                fill: false,
                data: ts,
                backgroundColor: 'rgba(54, 162, 235, 0.2)',
                borderColor: 'rgb(54, 162, 235)',
                borderWidth: 1 //경계선 굵기
            },
            {
                label: '자연과학',
                fill: false,
                data: ns,
                backgroundColor: 'rgba(153, 102, 255,0.2)',
                borderColor: 'rgb(153, 102, 255)',
                borderWidth: 1 //경계선 굵기
            },
            {
                label: '예술',
                fill: false,
                data: art,
                backgroundColor: 'rgba(34,150,53, 0.2)',
                borderColor: 'rgb(34,150,53)',
                borderWidth: 1 //경계선 굵기
            },
            {
                label: '어학',
                fill: false,
                data: lan,
                backgroundColor: 'rgba(114,157,12, 0.2)',
                borderColor: 'rgb(110,134,19)',
                borderWidth: 1 //경계선 굵기
            },
            {
                label: '역사',
                fill: false,
                data:his,
                backgroundColor: 'rgba(157, 109, 12, 0.2)',
                borderColor: 'rgb(157, 109, 12)',
                borderWidth: 1 //경계선 굵기
            },
            {
                label: '기타',
                fill: false,
                data: ex,
                backgroundColor: 'rgba(75,69,69, 0.2)',
                borderColor: 'rgb(75,69,69)',
                borderWidth: 1 //경계선 굵기
            }

        ]
    },
    options: {
        tooltips: {
            mode: 'index',
            intersect: false
        },
        title: {
            display: true,
            text: '카테고리 별 업로드된 게시글'
        },
        hover: {
            mode: 'index',
            intersect: false
        },
        scales: {
            yAxes: [
                {
                    scaleLabel: {
                        display: true,
                        labelString: 'Value'
                    },
                    ticks: {
                        beginAtZero: true,
                        stepSize: 1
                    }
                }
            ]
        }
    }
});
function drawChart(data) {
    for(let i=0; i< data.days.length; i++){
        labels.push(data.days[i]);
        literature.push(data.literature[i]);
        philosophy.push(data.philosophy[i]);
        ss.push(data.ss[i]);
        ts.push(data.ts[i]);
        ns.push(data.ns[i]);
        art.push(data.art[i]);
        lan.push(data.lan[i]);
        his.push(data.his[i]);
        ex.push(data.ex[i]);
    }
    console.log(lan);
    console.log(ex);
    myChart.update();
}
