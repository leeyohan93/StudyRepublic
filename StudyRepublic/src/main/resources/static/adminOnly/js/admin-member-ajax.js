var token = $("meta[name='_csrf']").attr("content");
var header = $("meta[name='_csrf_header']").attr("content");
var selectedRow;
var formData;
var selectedId;
var selectedEmail;
var number;

$(function() {
    $(document).ajaxSend(function(e, xhr, options) {
        xhr.setRequestHeader(header, token);
    });
});

$(document).ready(function () {
	// paging과 맞물려 사용 중지
    $("#unpause-process").click(function (event) {
        unpause();
    });
    $("#changeGrade-process").click(function (event) {
        changeGrade();
    });
    $("#pause-process").click(function (event) {
        pause();
    });
    $("#exit-process").click(function (event) {
        exit();
    });
    $(".changePassword-process").click(function (event) {
    	changePassword();
    });
    //  전체 체크박스 추가
    /*$("#memberStatus input#allCheck").click(function() {
    	alert("a");
	if ($("#memberStatus input#allCheck").prop("checked")) {
		$("#memberStatus input[type=checkbox]").prop("checked", true);
	} else {
		$("#memberStatus input[type=checkbox]").prop("checked", false);
	}*/
});


function makeSelected(){
    formData="";
    selectedRow = [];
    $(".selected").each(function(i){
        selectedRow.push($(this).context);
    })

    selectedId = [];
    $("input[name='selectedId']").each(function(i){
        selectedId.push($(this).val());
    })
    selectedId = selectedId.reduce(function(a,b){if(a.indexOf(b)<0)a.push(b);return a;},[]);

    selectedEmail = [];
    $("input[name='selectedEmail']").each(function(i){
        selectedEmail.push($(this).val());
    })
    selectedEmail = selectedEmail.reduce(function(a,b){if(a.indexOf(b)<0)a.push(b);return a;},[]);
    
}

function changePassword(number) {
	formData = "";
	var memberId= $("#"+number+" input[name='memberId']").val();
	var memberEmail= $("#"+number+" input[name='memberEmail']").val();
	var memberPhonenumber= $("#"+number+" input[name='memberPhonenumber']").val();
	formData = {"memberId":memberId,"memberEmail":memberEmail,"memberPhonenumber":memberPhonenumber}
	console.log(formData);

	 $.ajax({
	        type: 'post',
	        url: '/adminPage/member/changePassword',
	        traditional: true,
	        data: formData,

	        success: function (data) {
	        },
	        error :function(){
	            alert("error");
	        }
	    });
}

function unpause(){

    makeSelected();
    formData = {"selectedId": selectedId, "selectedEmail": selectedEmail}

    $.ajax({
        type: 'post',
        url: '/adminPage/member/unpause',
        traditional: true,
        data: formData,

        success: function (data) {
            for(var i=0; i<selectedRow.length; i++){
                $('#'+selectedRow[i].id)[0].children[6].innerText = data[i].memberStatusCD.codeValueKorean;
            }  
        },
        error :function(){
            alert("error");
        }
    });
}

function changeGrade(){
    makeSelected();
    var changeGrade= $("#changeGrade select[name='changeGrade']").val();
    formData = {"selectedId": selectedId, "selectedEmail": selectedEmail,"changeGrade":changeGrade};

    $.ajax({
        type: 'post',
        url: '/adminPage/member/changeGrade',
        traditional: true,
        data: formData,

        success: function (data) {
            for(var i=0; i<selectedRow.length; i++){
                $('#'+selectedRow[i].id)[0].children[5].innerText = data[i].gradeCD.codeValueKorean;
            }  
        },
        error :function(){
            alert("error");
        }
    });
}

function pause(){

    makeSelected();
    var subject = $("#pause input[name='subject']").val();
    var text = $("#pause textarea[name='text']").val()
    formData = {"selectedId": selectedId, "selectedEmail": selectedEmail,"subject":subject,"text":text};
    
    $.ajax({
        type: 'post',
        url: '/adminPage/member/pause',
        traditional: true,
        data: formData,

        success: function (data) {
            for(var i=0; i<selectedRow.length; i++){
                $('#'+selectedRow[i].id)[0].children[6].innerText = data[i].memberStatusCD.codeValueKorean;
            }  
        },
        error :function(){
            alert("error");
        }
    });
}

function exit(){
    makeSelected();
    var subject = $("#exit input[name='subject']").val();
    var text = $("#exit textarea[name='text']").val()
    formData = {"selectedId": selectedId, "selectedEmail": selectedEmail,"subject":subject,"text":text};


    $.ajax({
        type: 'post',
        url: '/adminPage/member/exit',
        traditional: true,
        data: formData,

        success: function (data) {
            for(var i=0; i<selectedRow.length; i++){
                $('#'+selectedRow[i].id)[0].children[6].innerText = data[i].memberStatusCD.codeValueKorean;
            }  
        },
        error :function(){
            alert("exiterror");
        }
    });
}