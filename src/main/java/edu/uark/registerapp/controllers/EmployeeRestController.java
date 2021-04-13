package edu.uark.registerapp.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import edu.uark.registerapp.commands.employees.EmployeeCreateCommand;
import edu.uark.registerapp.commands.employees.EmployeeDeleteCommand;
import edu.uark.registerapp.commands.employees.EmployeeExistsQuery;
import edu.uark.registerapp.commands.employees.EmployeeUpdateCommand;
import edu.uark.registerapp.models.api.ApiResponse;
import edu.uark.registerapp.models.api.Employee;

@RestController
@RequestMapping(value = "/api/employee")
public class EmployeeRestController {
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public @ResponseBody ApiResponse createEmployee(
        @RequestBody final Employee employee
    ) {
		employee.setIsActive(false);

		if (this.employeeExistsQuery.execute())
		{
			return this.employeeCreateCommand
            .setApiEmployee(employee)
            .execute();
		}
		else
		{
			Employee initialEmployee =
				this.employeeCreateCommand.setApiEmployee(employee).execute();

			return initialEmployee.setIsInitialEmployee(true);
		}
    }

    @RequestMapping(value = "/{employeeId}", method = RequestMethod.PUT)
    public @ResponseBody ApiResponse updateEmployee(
        @PathVariable final UUID employeeId,
        @RequestBody final Employee employee
    ) {

        return this.employeeUpdateCommand
            .setEmployeeId(employeeId)
            .setApiEmployee(employee)
            .execute();
    }

    @RequestMapping(value = "/{employeeId}", method = RequestMethod.DELETE)
    public @ResponseBody ApiResponse deleteEmployee(
        @PathVariable final UUID employeeId
    ) {

        this.employeeDeleteCommand
            .setEmployeeId(employeeId)
            .execute();

        return new ApiResponse();
    }

    // Properties
	@Autowired
	private EmployeeExistsQuery employeeExistsQuery;

    @Autowired
    private EmployeeCreateCommand employeeCreateCommand;

    @Autowired
    private EmployeeDeleteCommand employeeDeleteCommand;

    @Autowired
    private EmployeeUpdateCommand employeeUpdateCommand;
}

