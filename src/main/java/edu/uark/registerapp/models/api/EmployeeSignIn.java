package edu.uark.registerapp.models.api;

public class EmployeeSignIn {
	private String employeeId;
	private String password;

	EmployeeSignIn(String employeeId, String password) {
		this.employeeId = employeeId;
		this.password = password;
	}

	public String getId()
	{
		return this.employeeId;
	}

	public String getPassword()
	{
		return this.password;
	}
}
