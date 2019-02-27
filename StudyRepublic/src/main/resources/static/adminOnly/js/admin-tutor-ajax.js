
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

    $("#permission-process").click(function (event) {
        permission();
    });
    $("#prohibition-process").click(function (event) {
        prohibition();
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
    
}

function permission(){
    makeSelected();
    var sendId= $("#permission input[name='sendId']").val();
    var messageContent = $("#permission textarea[name='messageContent']").val();
    formData = {"selectedId": selectedId, "sendId":sendId, "messageContent":messageContent};
    console.log(formData);

    $.ajax({
        type: 'post',
        url: '/adminPage/tutor/permission',
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

function prohibition(){
    makeSelected();
    var sendId= $("#prohibition input[name='sendId']").val();
    var messageContent = $("#prohibition textarea[name='messageContent']").val();
    formData = {"selectedId": selectedId, "sendId":sendId, "messageContent":messageContent};
    console.log(formData);

    $.ajax({
        type: 'post',
        url: '/adminPage/tutor/prohibition',
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