//Input field
const username = document.getElementById('username');
const password = document.getElementById('password');
const confirmPassword = document.getElementById('confirmPassword');
const fname = document.getElementById('fname');
const lname = document.getElementById('lname');
const dob = document.getElementById('dob');
const gender = document.getElementById('gender');

//Validation colors
const green = '#4CAF50';
const red = '#F44336';
function finalCheck(form){
	var check = true;
	if(!valUsername()){check = false;alert('user')}
	else if (!valPassword()){check = false;alert('pass')}
	else if (!valConfirmPassword()){check = false;alert('pass2')}
	else if (!valFName()){check = false;alert('fname')}
	else if (!valLName()){check = false;alert('lname')}
	else if (!valDOB()){check = false;alert('dob')}
	else{check =true;}
	if(check == true){
		
	}
		
	
}




//validators
function valUsername()
{
	if(checkIfEmpty(username))return;
	if(!lengthRequirement(username, 4,20))return;
	check(username);
	return true;
}
function valPassword()
{
	if(checkIfEmpty(password))return;
	if(!lengthRequirement(password, 6,30))return;
	if(!containCharacter(password,2)) return;
	return true;
}
function valConfirmPassword()
{
	if(password.className !== 'valid') {
		setInvalid(confirmPassword, 'Password must be valid');
		return;
	} 
	if(password.value !== confirmPassword.value) {
		setInvalid(confirmPassword, 'Passwords must match');
	    return;
	}
	else {
		setValid(confirmPassword);
	}
	return true;
}
function valFName()
{
	if(checkIfEmpty(fname))return;
	if(!isAlpha(fname))return;
	return true;
}
function valLName()
{
	if(checkIfEmpty(lname))return;
	if(!isAlpha(lname))return;
	return true;
}
function valDOB(){
	if(checkIfEmpty(dob))return;
	if(!formatCheck(dob)) return;
	return true;
	
}

function formatCheck(field){
	regEx = "(0?[1-9]|[12][0-9]|3[01]).(0?[1-9]|1[012]).((19|20)[0-9]{2})"
	return matchWithRegEx(regEx, field, 'Must be in {dd.MM.yyyy');
}

//Utility function
function checkIfEmpty(field) 
{
	if(isEmpty(field.value.trim())) {
		//set field invalid
		setInvalid(field, `${field.name} must not be empty`);
		return true;
	} else {
		setValid(field);
		return false;
	}
}

function isEmpty(value) 
{
	if(value ==='') return true;
	return false;
}

function setInvalid(field, message) 
{
	field.className = 'invalid';
	field.nextElementSibling.innerHTML = message;
	field.nextElementSibling.style.color = red;
}
function setValid(field) 
{
	field.className = 'valid';
	field.nextElementSibling.innerHTML = '';
}
function isAlpha(field) 
{
	if (/^[a-zA-Z ]+$/.test(field.value)) {
		setValid(field);
		return true;
	} else {
		setInvalid(field, `${field.name} must contain only letters`);
		return false;
	}
}
function isNum(field) 
{
	if (/^[0-9]+$/.test(field.value)) {
		setValid(field);
		return true;
	} else {
		setInvalid(field, `${field.name} must contain only number`);
		return false;
	}
}
function lengthRequirement(field,min,max) 
{
	if(field.value.length >= min && field.value.length < max) {
		setValid(field);
		return true;
	}
	else if(field.value.length < min) {
		setInvalid(field, `${field.name} must be at least ${min} characters long`);
		return false;
	} else {
		setInvalid(field, `${field.name} must be shorter than ${max} chacters`);
	}
	return false;
}


function containCharacter(field, code)
{
	let regEx
	switch (code){
	
	case 1: 
		// At least one letter (any case)
		regEx = /(?=.*[a-zA-Z])/;
		matchWithRegEx(regEx, field, 'Must contain at least one letter');
	case 2:
	// At least one letter and one number
	regEx = /(?=.*\d)(?=.*[a-zA-Z])/;
	return matchWithRegEx(regEx,field,
	       'Must contain at least one letter and one number');
	case 3:
	// At least one uppercase letter, one lowercase letter and one number
	regEx = /(?=.*\d)(?=.*[a-z])(?=.*[A-Z])/;
	 return matchWithRegEx(regEx,field,
	 		'Must contain at least one uppercase, one lowercase letter and one number');
	
	case 4:
	// At least one uppercase letter, one lowercase letter, one number and one special character (symbol)
	regEx = /(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*\W)/;
	return matchWithRegEx(regEx,field,
	        'Must contain at least one uppercase, one lowercase letter, one number and one special character');
	case 5:
	// Email regular expression pattern
	regEx = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/
		return matchWithRegEx(regEx, field, 'Must be a valid email address');
	default:
		return false
	}
	
}
function matchWithRegEx(regEx, field, message) {
	  if (field.value.match(regEx)) {
	    setValid(field);
	    return true;
	  } else {
	    setInvalid(field, message);
	    return false;
	  }
}
function check(username){
	let xhr = new XMLHttpRequest;
	let url = 'http://localhost:1006/RobotBank/register?username='+ username.value;
	xhr.open('GET',url,true);
	var data = ''
	xhr.onreadystatechange=function(){
		
		if(this.readyState==4 && this.status==200){
			data = this.response;
			if(data == "#"){
				setValid(username);
				return true;
				
			}else{
				setInvalid(username, `${username.name} had been used`);
				return false
			}
			
		} 
	}
	xhr.send();
}
