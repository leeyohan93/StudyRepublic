var optionalId = document.getElementsByClassName("optionalId");
var optionalEmail = document.getElementsByClassName("optionalEmail")

var selectedObject;
var inputCommand;

function selected() {
	selectedObject = document.getElementsByClassName("selected");
	inputCommand = "";
	if (selectedObject.length === 0) {
		alert("선택대상을 지정해주세요.");
		this.event.stopPropagation();
	}
	else {
		for (var i = 0; i < selectedObject.length; i++) {
            inputCommand += "<input type='hidden' name='selectedId' value=" + selectedObject[i].cells[2].innerText + ">";
            inputCommand += "<input type='hidden' name='selectedMemberId' value="+selectedObject[i].cells[6].innerText+">";
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

