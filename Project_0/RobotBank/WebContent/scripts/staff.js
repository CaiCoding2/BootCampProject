function loading(){
	details();
	getPendingAccount();
}
function details(){
	let xhr = new XMLHttpRequest;
	xhr.open('GET','http://localhost:1006/RobotBank/detail' ,true);
	data ="";
	xhr.onload=function(){
		if(this.readyState==4 && this.status==200){
			let res =JSON.parse(this.response);
			data = "<h4> Welcome "+res.fname +" "+ res.lname +"!</p>";
			document.getElementById("profile").innerHTML=data;
		}		
	}
	xhr.send();
}

function getPendingAccount(){
	let xhr = new XMLHttpRequest;
	xhr.open('GET','http://localhost:1006/RobotBank/pending' ,true);
	datas ="";
	
	xhr.onload=function(){
		if(this.readyState==4 && this.status==200){
			let res =JSON.parse(this.response);
	
			res.forEach(element => {
				datas = datas+"<p><label><input type='radio' class='with-gap'"
				datas = datas+" name='accounts' value='" +element.accountNumber +"'/>"
				datas = datas+"<span>"+element.accountNumber +"&emsp;"
				if(element.aType == 1){
					datas = datas +"Checking"
				}else{
					datas = datas +"Saving"
				}
				datas = datas  +"</span> </label></p>"
			});
			
			document.getElementById("pending").innerHTML=datas;
		}		
	}
	xhr.send();
}
/*
function doProcess(value){
	let list = [];
	let checkboxes = document.getElementsByName('accounts');
	for(var x=0;x<checkboxes.length;x++){
		if(checkboxes[x].checked){
			list.push(checkboxes[x].value);
		}
	}
	let type = 0;
	if(value=="approve"){
		type = 3;
	}else{
		type = 4;
	}
	let xhr = new XMLHttpRequest;
	for(var c = 0; c< list.length;c++){
		let url ="http://localhost:1006/RobotBank/staff?accounts="+list[c].value+"&type="+type;
		xhr.open('POST',url ,true);
		xhr.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
		let obj = {};
		obj.accountNumber = list[c].value;
		obj.statusId = type;
		console.log(obj);
		let json = JSON.stringify(obj)
		xhr.send(json);
	}
	
}*/