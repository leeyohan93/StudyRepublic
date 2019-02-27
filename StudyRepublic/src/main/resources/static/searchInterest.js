var AllselectBox = document.querySelectorAll(".interestClass");
var target;
				var Location = document
						.querySelector(".interest_element_optional");

				var interestArr = [ "데이터베이스", "네트워크보안", "응용프로그래밍", "웹프로그래밍" ];
				var count = 0;

				var sinterest1 = "";
				var pinterest2cd = document.getElementById("pinterest2cd")

				var rad = document.getElementsByName("onoffCode.onoffCode");
				for (var i = 0; i < rad.length; i++) {
					rad[i].addEventListener('change', function() {
						if (this.value == 'F' || this.value == 'B') {
							Location.style.display = 'block'
						} else {
							Location.style.display = 'none'
						}
					});
				}

				function interest1(obj) {
					sinterest1 = obj.options[obj.selectedIndex].text;
					for (var i = 0; i < AllselectBox.length; i++) {
						AllselectBox[i].style.display = 'none';
						if (sinterest1 == interestArr[i]) {
							checkNum = i;
						}
					}
					AllselectBox[checkNum].style.display = 'inline-block';
				}

				var interest2List = new Array();
				var selectedinterest2 = "";
				var addInterest2List;
				var interestInput = "";
				var interestDelete = "";
				function interest2(obj) {

					if (~selectedinterest2
							.indexOf(obj.options[obj.selectedIndex].text)) {
						alert("이미 선택한 분야입니다.");
					} else {
						interest2List.push(obj.options[obj.selectedIndex].text);
						
						selectedinterest2 += interest2List[interest2List.length - 1];
						addInterest2List = "<input type='text' id = 'liisel" + count +"' value="+interest2List[interest2List.length-1]+" >"
								+ "<button id = 'liidel"
								+ count
								+ "' onclick = 'deleteInterest("
								+ count
								+ ")'>X</button>"
								+ "<input type='hidden' name='interest' value="+obj.value+">";
						

            count++;
            var selected = new Array();
            switch(obj.options[obj.selectedIndex].value){
              case "D01": selected=$("#dinterest2cd")[0].children[0].children;
              break;
              case "P01": selected=$("#pinterest2cd")[0].children[0].children;
              break;
              case "W01": selected=$("#winterest2cd")[0].children[0].children;
              break;
              case "N01": selected=$("#ninterest2cd")[0].children[0].children;
              break;
            }
            for(var i=1; i<selected.length; i++){
            	selectedinterest2 += selected[i].text;
            	addInterest2List += "<input type='hidden' name='interest' value="+selected[i].value+" class="+selected[1].text+">"
            	
            }
            console.log(interest2List);
            $('.interest_element_tag').append(addInterest2List);
           
					}

				}

				function deleteInterest(num) {
					var selected = new Array();
		            switch(interest2List[num]){
		              case "DB전체": selected=$("#dinterest2cd")[0].children[0].children;
		              break;
		              case "Programing전체": selected=$("#pinterest2cd")[0].children[0].children;
		              break;
		              case "Web전체": selected=$("#winterest2cd")[0].children[0].children;
		              break;
		              case "Network전체": selected=$("#ninterest2cd")[0].children[0].children;
		              break;
		            }
					if(interest2List[num].indexOf("전체")!=-1){
						$("."+interest2List[num]).remove();
						for(var i=1; i<selected.length;i++){
							selectedinterest2 =selectedinterest2.replace(
									selected[i].text, "")
						}
						}
							selectedinterest2 = selectedinterest2.replace(
									interest2List[num], "") // 중복금지차원 
						
					
						
						$('#liisel' + num).remove();
						$('#liidel' + num).remove();
						console.log(selectedinterest2);
					

				}