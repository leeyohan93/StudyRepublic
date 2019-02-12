
var token = $("meta[name='_csrf']").attr("content");
var header = $("meta[name='_csrf_header']").attr("content");
var selectedRow;
var formData;
var selectedId;
var selectedEmail;

$(function() {
    $(document).ajaxSend(function(e, xhr, options) {
        xhr.setRequestHeader(header, token);
    });
});

$(document).ready(function () {

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