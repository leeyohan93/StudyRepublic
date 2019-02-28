var optionalId = document.getElementsByClassName("optionalId");
var optionalEmail = document.getElementsByClassName("optionalEmail")

var selectedObject;
var inputCommand;

//로딩 바 및 modal hide()
$(function () {
	var loading = $('#loading-bar');
	
	
	$("form").on("submit", function() {
		loading.show(); //submit시 로딩바를 보여준다.
		});
	
	$(document).ajaxStart(function(){
		$('#loading-bar').show(); //미리 만들어둔 loadingBar
	}).ajaxStop(function(){
		$('#loading-bar').hide();   
		$('.modal').modal('hide');
	});
});


function selected() {
	selectedObject = document.getElementsByClassName("selected");
	inputCommand = "";
	if (selectedObject.length === 0) {
		Swal.fire({
			  type: 'error',
			  title: 'Error!',
			  text: '회원을 먼저 선택하여 주세요.',
		})
		this.event.stopPropagation();
	}
	else {
		for (var i = 0; i < selectedObject.length; i++) {
			inputCommand += "<input type='hidden' name='selectedId' value=" + selectedObject[i].cells[1].innerText + ">"
			inputCommand += "<input type='hidden' name='selectedMemberId' value="+selectedObject[i].cells[5].innerText+">";
		}
		for (var i = 0; i < optionalId.length; i++) {
			optionalId[i].innerHTML = inputCommand;
		}
	}

}

function sendEmail() {
	selected();
	inputCommand = "";
	for (var i = 0; i < selectedObject.length; i++) {
		inputCommand += "<input type='hidden' name='selectedEmail' value=" + selectedObject[i].cells[7].innerText + ">"
	}
	for (var i = 0; i < optionalEmail.length; i++) {
		optionalEmail[i].innerHTML = inputCommand;
	}

}

