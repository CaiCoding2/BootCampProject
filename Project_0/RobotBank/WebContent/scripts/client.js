function getTransaction(){
	let select = document.getElementByName("accNum").value;
	let url = 'http://localhost:1006/RobotBank/client?accNum=' +select;
	let xhr = new XMLHttpRequest;
	xhr.open('POST', url ,true);
	result ="";
	xhr.onload=function(){
	
		if(this.readyState==4 && this.status===200){
			let res =JSON.parse(this.response);
			
			res.forEach(element => {
			
				result = result + "<p>" + element.AccountNum +"</p>";
			});
			document.getElementById("logs").innerHTML=data;
		}	
	}
	xhr.send();
}

function transcation(){
	let select = document.getElementById("accNum").value;
	alert(select);
	
}
