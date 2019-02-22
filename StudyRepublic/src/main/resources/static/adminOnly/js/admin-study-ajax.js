
var token = $("meta[name='_csrf']").attr("content");
var header = $("meta[name='_csrf_header']").attr("content");
var selectedRow;
var formData;
var selectedId;

$(function () {
    $(document).ajaxSend(function (e, xhr, options) {
        xhr.setRequestHeader(header, token);
    });
});

$(document).ready(function () {

    $("#disband-process").click(function (event) {
        disband();
    });
});

    function makeSelected() {
        formData = "";
        selectedRow = [];
        $(".selected").each(function (i) {
            selectedRow.push($(this).context);
        })

        selectedId = [];
        $("input[name='selectedId']").each(function (i) {
            selectedId.push($(this).val());
        })
        selectedId = selectedId.reduce(function (a, b) { if (a.indexOf(b) < 0) a.push(b); return a; }, []);

        selectedMemberId = [];
        $("input[name='selectedMemberId']").each(function (i) {
            selectedMemberId.push($(this).val());
        })
        selectedMemberId = selectedMemberId.reduce(function (a, b) { if (a.indexOf(b) < 0) a.push(b); return a; }, []);

    }

    function disband() {
        makeSelected();
        var sendId = $("#disband input[name='sendId']").val();
        var messageContent = $("#disband textarea[name='messageContent']").val();
        formData = { "selectedMemberId":selectedMemberId,"selectedId": selectedId, "sendId": sendId, "messageContent": messageContent };
        console.log(formData);

        $.ajax({
            type: 'post',
            url: '/adminPage/study/disband',
            traditional: true,
            data: formData,

            success: function (data) {
                for (var i = 0; i < selectedRow.length; i++) {
                    $('#' + selectedRow[i].id)[0].children[8].innerText = data[i].studyStatusCode.codeValueKorean;
                }
                
            },
            error: function () {
                alert("error");
            }
        });
    }