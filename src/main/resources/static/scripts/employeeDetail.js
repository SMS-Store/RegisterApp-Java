document.addEventListener("DOMContentLoaded", function(event)
{
	getSaveActionElement().addEventListener("click", saveActionClick);
});

/*function validateForm()
{
	let firstname = document.forms["createEmployee"]["firstname"].value;
	let lastname = document.forms["createEmployee"]["lastname"].value;
	let password = document.forms["createEmployee"]["password"].value;
	let verifyPassword = document.forms["createEmployee"]["verifyPassword"].value;

	if (firstname.length == 0 || lastname.length == 0 || password.length == 0)
	{
		alert("Please fill in all fields!");
		return false;
	}
	else if (password !== verifyPassword)
	{
		alert("Passwords do not match!");
		return false;
	}
	else
	{
		return true;
	}
}*/

function validateSave(event) {
    const firstname = getFirstName();
    const lastname = getLastName();
    const password = getPassword();
    const verifiedPassword = getVerifiedPassword();

    if(firstname == "") {
        displayError("Please enter your first name.");
        getFirstName().focus();
        getFirstName().select();
        return false;
    }

    if(lastname == "") {
        displayError("Please enter your last name.");
        getLastName().focus();
        getLastName().select();
        return false;
    }

    if(password == "") {
        displayError("Please enter your password.");
        getPassword().focus();
        getPassword().select();
        return false; 
    }

    if(password != verifiedPassword) {
        displayError("Passwords do not match. Re-enter your password.");
        getVerifiedPassword().focus();
        getVerifiedPassword().select();
        return false; 
    }
    
    return true; 
}

function saveActionClick(event) {
    if(!validateForm()) {
        return; 
    }

    const saveActionElement = event.target;
	saveActionElement.disabled = true;

    //const saveActionUrl = ("/api/employee");
    
    
}

// Getters
function getSaveActionElement() {
	return document.getElementById("saveButton");
}

function getFirstName() {
    return document.getElementById("firstname");
}

function getLastName() {
    return document.getElementById("lastname");
}

function getPassword() {
    return document.getElementById("password");
}

function getVerifiedPassword() {
    return document.getElementById("verifyPassword");
}

function getEmployeeType() {
    return document.getElementById("classification");
}

