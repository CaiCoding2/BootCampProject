const username = document.getElementById('username');
const password = document.getElementById('password');

//Validation colors
const green = '#4CAF50';
const red = '#F44336';

//validators
function valCheck(){
	valUsername();
	valPassword();
}

function valUsername()
{
	if(checkIfEmpty(username))return;
	if(!lengthRequirement(username, 4,20))return;
	return true;
}
function valPassword()
{
	if(checkIfEmpty(password))return;
	if(!lengthRequirement(password, 2,30))return;
	return true;
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

