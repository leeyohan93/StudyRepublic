<<<<<<< HEAD
var count = 0;
var allSpan = document.querySelectorAll("span");
    function addInterestLocation() {
    	
       
    	new daum.Postcode({
            oncomplete: function(data) {

                var roadAddr = data.roadAddress; // 도로명 주소 변수
                var jibunAddress = data.jibunAddress;
                var road = data.roadname;
                var sido = data.sido;
                var sigungu = data.sigungu;
                var dong = data.bname;
                var upmyeonli = data.bname1;
                
                var dongaddress = sido + "" + sigungu  + "" + dong + "" + upmyeonli;
                var doroaddress = sido + sigungu + road;
                
                var extraRoadAddr = ''; // 참고 항목 변수
					
                if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                    extraRoadAddr += data.bname;
                }

                if(data.buildingName !== '' && data.apartment === 'Y'){
                   extraRoadAddr += (extraRoadAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                }

                

                var guideTextBox = document.getElementById("guide");
                
                if(data.autoRoadAddress) {
                    var expRoadAddr = data.autoRoadAddress + extraRoadAddr;
                    guideTextBox.innerHTML = '(예상 도로명 주소 : ' + expRoadAddr + ')';
                    guideTextBox.style.display = 'block';

                } else {
                    guideTextBox.innerHTML = '';
                    guideTextBox.style.display = 'none';
                   	
                    var text = "<span id = 'selLoc" + count +  
                	"'> <input type = 'text' id = 'interestlocation" +count+ 
                	"' name = 'location'> <button id = 'del1' value = '삭제' onclick = 'deleteLocation(" + count + ")'>X</button> </span>"
                	$('#loc').append(text);
                					document.getElementById("interestlocation"+count).value = dongaddress;
					count++;
					
                }
            	
            } 
        }).open();
    }
    
     function deleteLocation(num) {
		var standard = num;
		$('#selLoc' + standard).remove(); 
	/* 	count--; */

    } 
     
     
     
     
     
     
     
=======
var count = 0;
var allSpan = document.querySelectorAll("span");
    function addInterestLocation() {
    	
       
    	new daum.Postcode({
            oncomplete: function(data) {

                var roadAddr = data.roadAddress; // 도로명 주소 변수
                var jibunAddress = data.jibunAddress;
                var road = data.roadname;
                var sido = data.sido;
                var sigungu = data.sigungu;
                var dong = data.bname;
                var upmyeonli = data.bname1;
                
                var dongaddress = sido + "" + sigungu  + "" + dong + "" + upmyeonli;
                var doroaddress = sido + sigungu + road;
                
                var extraRoadAddr = ''; // 참고 항목 변수
					
                if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                    extraRoadAddr += data.bname;
                }

                if(data.buildingName !== '' && data.apartment === 'Y'){
                   extraRoadAddr += (extraRoadAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                }

                

                var guideTextBox = document.getElementById("guide");
                
                if(data.autoRoadAddress) {
                    var expRoadAddr = data.autoRoadAddress + extraRoadAddr;
                    guideTextBox.innerHTML = '(예상 도로명 주소 : ' + expRoadAddr + ')';
                    guideTextBox.style.display = 'block';

                } else {
                    guideTextBox.innerHTML = '';
                    guideTextBox.style.display = 'none';
                   	
                    var text = "<span id = 'selLoc" + count +  
                	"'> <input type = 'text' id = 'interestlocation" +count+ 
                	"' name = 'location'> <button id = 'del1' value = '삭제' onclick = 'deleteLocation(" + count + ")'>X</button> </span>"
                	$('#loc').append(text);
                					document.getElementById("interestlocation"+count).value = dongaddress;
                					
					count++;
					
                }
            	
            } 
        }).open();
    }
    
     function deleteLocation(num) {
		var standard = num;
		$('#selLoc' + standard).remove(); 
	/* 	count--; */

    } 
     
     
     
     
     
     
     
>>>>>>> refs/heads/Sungho
     