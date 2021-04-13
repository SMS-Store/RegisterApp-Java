package edu.uark.registerapp.commands.employees;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.uark.registerapp.models.repositories.EmployeeRepository;

@Service
public class EmployeeExistsQuery {
	public boolean execute()
	{
		if (this.employeeRepository.count() == 0)
			return false;
		else
			return true;
	}

	@Autowired
	private EmployeeRepository employeeRepository;
}
