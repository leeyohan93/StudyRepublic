// Dashboard 1 Morris-chart
var token = $("meta[name='_csrf']").attr("content");
var header = $("meta[name='_csrf_header']").attr("content");
var countdata;
$(function () {
    $(document).ajaxSend(function (e, xhr, options) {
        xhr.setRequestHeader(header, token);
    });
});

$(document).ready(function () {

	analytics();
});

function analyticsDate(i){
	var today = new Date();
	var year = today.getFullYear()-i;

	return year+"";
}

function getMemberCount(i){
	return countdata.member[2019];
}


function analytics(){
	$.ajax({
        type: 'post',
        url: '/adminPage/analyticsGraph',
        data: "data",

        success: function (res) {
        	countdata = res;
        	showData();
        },
        error: function () {
            alert("error");
        }
    });
}


/*Morris.Area({
        element: 'morris-area-chart',
        data: [{
            period: '2012',
            Python: 0,
            PHP: 0,
            Java: 0
        }, {
            period: '2013',
            Python: 100,
            PHP: 80,
            Java: 65
        }, {
            period: '2014',
            Python: 180,
            PHP: 150,
            Java: 120
        }, {
            period: '2015',
            Python: 100,
            PHP: 70,
            Java: 40
        }, {
            period: '2016',
            Python: 180,
            PHP: 150,
            Java: 120
        }, {
            period: '2017',
            Python: 100,
            PHP: 70,
            Java: 40
        },
         {
            period: '2018',
            Python: 180,
            PHP: 150,
            Java: 120
        }],
        xkey: 'period',
        ykeys: ['Python', 'PHP', 'Java'],
        labels: ['Python', 'PHP', 'Java'],
        pointSize: 0,
        fillOpacity: 0.99,
        pointStrokeColors:['#65b12d', '#933EC5 ', '#006DF0'],
        behaveLikeLine: true,
        gridLineColor: '#e0e0e0',
        lineWidth:0,
        hideHover: 'auto',
        lineColors: ['#65b12d', '#933EC5 ', '#006DF0'],
        resize: true
        
    });*/
	function showData() {
	Morris.Area({
		element : 'extra-area-chart',
		data : [ {
			period : analyticsDate(0),
			회원 : countdata.member[analyticsDate(0)]!=null?countdata.member[analyticsDate(0)]:0,
			강사 : countdata.tutor[analyticsDate(0)]!=null?countdata.tutor[analyticsDate(0)]:0,
			스터디 : countdata.study[analyticsDate(0)]!=null?countdata.study[analyticsDate(0)]:0
		}, {
			period : analyticsDate(1),
			회원 : countdata.member[analyticsDate(1)]!=null?countdata.member[analyticsDate(1)]:0,
			강사 : countdata.tutor[analyticsDate(1)]!=null?countdata.tutor[analyticsDate(1)]:0,
			스터디 : countdata.study[analyticsDate(1)]!=null?countdata.study[analyticsDate(1)]:0
		}, {
			period : analyticsDate(2),
			회원 : countdata.member[analyticsDate(2)]!=null?countdata.member[analyticsDate(2)]:0,
			강사 : countdata.tutor[analyticsDate(2)]!=null?countdata.tutor[analyticsDate(2)]:0,
			스터디 : countdata.study[analyticsDate(2)]!=null?countdata.study[analyticsDate(2)]:0
		}, {
			period : analyticsDate(3),
			회원 : countdata.member[analyticsDate(3)]!=null?countdata.member[analyticsDate(3)]:0,
			강사 : countdata.tutor[analyticsDate(3)]!=null?countdata.tutor[analyticsDate(3)]:0,
			스터디 : countdata.study[analyticsDate(3)]!=null?countdata.study[analyticsDate(3)]:0
		}, {
			period : analyticsDate(4),
			회원 : countdata.member[analyticsDate(4)]!=null?countdata.member[analyticsDate(4)]:0,
			강사 : countdata.tutor[analyticsDate(4)]!=null?countdata.tutor[analyticsDate(4)]:0,
			스터디 : countdata.study[analyticsDate(4)]!=null?countdata.study[analyticsDate(4)]:0
		}, {
			period : analyticsDate(5),
			회원 : countdata.member[analyticsDate(5)]!=null?countdata.member[analyticsDate(5)]:0,
			강사 : countdata.tutor[analyticsDate(5)]!=null?countdata.tutor[analyticsDate(5)]:0,
			스터디 : countdata.study[analyticsDate(5)]!=null?countdata.study[analyticsDate(5)]:0
		} ],
		xkey : 'period',
		ykeys : [ '회원', '강사', '스터디' ],
		labels : [ '회원', '강사', '스터디' ],
		pointSize : 3,
		fillOpacity : 0,
		pointStrokeColors : [ '#006DF0', '#933EC5', '#65b12d' ],
		behaveLikeLine : true,
		gridLineColor : '#e0e0e0',
		lineWidth : 1,
		hideHover : 'auto',
		lineColors : [ '#006DF0', '#933EC5', '#65b12d' ],
		resize : true

	});
}