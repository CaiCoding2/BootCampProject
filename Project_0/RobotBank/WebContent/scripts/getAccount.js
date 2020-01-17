
function customer(){
	detail();
	getAccount();
}
//account detail
function detail(){
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

function getAccount(){
	let url = 'http://localhost:1006/RobotBank/view';
	let xhr = new XMLHttpRequest;
	xhr.open('GET', url,true);
	datas ="";
	active = ""
	xhr.onload=function(){
		if(this.readyState==4 && this.status==200){
			let res =JSON.parse(this.response);
			res.forEach(element => {
				if(element.statusId == 3){
					active= active +"<h6><label style='color:black'><input class='with-gap' name='accNum' type='radio'";
					active = active + "value='" + element.accountNumber+"' checked />";
					active = active + "<span>"+element.accountNumber+ "&emsp; &emsp; $";
					active = active + element.balance + "</span></label></h6>";
					
				}
				datas = datas +"<h6><label style='color:black'><input class='with-gap' name='accNum' type='radio'";
				datas = datas + "value='" + element.accountNumber+"' checked />";
				datas = datas + "<span>"+element.accountNumber+ "&emsp; &emsp; $";
				datas = datas + element.balance + "&emsp; &emsp; ";
				if(element.statusId == 1){
					datas = datas + "Pending"+ "</span></label></h6>";
				}else if(element.statusId == 2){
					datas = datas + "Rejeced"+ "</span></label></h6>";
				}else if(element.statusId == 3){
					datas = datas + "Actived"+ "</span></label></h6>";
				}else {
					datas = datas + "Closed"+ "</span></label></h6>";
				}
			});
			document.getElementById("aLists").innerHTML=datas;
			document.getElementById("aList").innerHTML=active;
		
		}		
	}
	xhr.send();
}












