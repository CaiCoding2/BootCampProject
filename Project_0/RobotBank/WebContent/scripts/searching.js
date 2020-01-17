function searching(){
	let xhr = new XMLHttpRequest;
	var accountNum  = document.getElementsByName("searchaccount").value;
	xhr.open('GET','http://localhost:1006/RobotBank/searching?searchaccount=' + accountNum ,true);
	data ="";
	xhr.onload=function(){
		if(this.readyState==4 && this.status==200){
			
			let res = JSON.parse(this.repsonse);
			data = data +res.accountNumber;
			alert('getdata')
		}	
		document.getElementById("detail").innerHTML=data;
		
	}
	xhr.send();
}