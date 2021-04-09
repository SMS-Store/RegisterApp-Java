document.addEventListener("DOMContentLoaded", function(event)
{
	getSaveActionElement().addEventListener("click", );
});

// Attempts to save user input
function saveActionClick(event)
{
	if (!validateSave()) {
		return;
	}

	const saveActionElement = event.target;
	saveActionElement.disabled = true;


}

// Validates user input
function validateSave()
{
	let firstname = document.forms["createEmployee"]["firstname"];
	let lastname = document.forms["createEmployee"]["lastname"];
	let password = document.forms["createEmployee"]["password"];
	let verifyPassword = document.forms["createEmployee"]["verifyPassword"];

	if (firstname.value.trim() === "")
	{
		displayError("Please enter first name!");
		firstname.focus();

		return false;
	}
	else if (lastname.value.trim() === "")
	{
		displayError("Please enter last name!");
		lastname.focus();

		return false;
	}
	else if (password.value.trim() === "")
	{
		displayError("Please enter a password!");
		password.focus();

		return false;
	}
	else if (password.value !== verifyPassword.value)
	{
		displayError("Passwords do not match!");
		verifyPassword.focus();
		verifyPassword.select();

		return false;
	}
	else
	{
		return true;
	}
}

// ----- GETTERS AND SETTERS ----- //

// Gets the save button element
function getSaveActionElement()
{
	return document.getElementById("saveButton");
}

// Gets the record ID
function getRecordId()
{
	return getRecordIdElement().value;
}

// Gets the employee ID
function getEmployeeId()
{
	return getEmployeeIdElement().value;
}

// Gets the first name
function getFirstName()
{
	return getFirstNameElement().value;
}

// Gets the last name
function getLastName()
{
	return getLastNameElement().value;
}

// Gets the password
function getPassword()
{
	return getPasswordElement().value;
}

// Gets the verify password
function getVerifyPassword()
{
	return getVerifyPasswordElement().value;
}

// Gets the employee classification
function getEmployeeClass()
{
	return getEmployeeClassElement().value;
}

// Gets the record ID element
function getRecordIdElement()
{
	return document.getElementById("recordId");
}

// Gets the employee ID element
function getEmployeeIdElement()
{
	return document.getElementById("employeeId");
}

// Gets the first name element
function getFirstNameElement()
{
	return document.getElementById("firstName");
}

// Gets the last name element
function getLastNameElement()
{
	return document.getElementById("lastName");
}

// Gets the password element
function getPasswordElement()
{
	return document.getElementById("password");
}

// Gets the verify password element
function getVerifyPasswordElement()
{
	return document.getElementById("verifyPassword");
}

// Gets the employee classification element
function getEmployeeClassElement()
{
	return document.getElementById("employeeClass");
}