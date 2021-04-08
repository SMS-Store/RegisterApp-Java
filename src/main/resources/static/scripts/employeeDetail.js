document.addEventListener("DOMContentLoaded", function(event)
{
	document.getElementById("employeeID").style.display = "none";
	document.getElementById("employeeIDLabel").style.display = "none";
});

function validateForm()
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

		return false;
	}
	else
	{
		return true;
	}
}