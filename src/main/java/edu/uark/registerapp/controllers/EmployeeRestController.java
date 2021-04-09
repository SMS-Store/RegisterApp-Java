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
import edu.uark.registerapp.commands.employees.EmployeeUpdateCommand;
import edu.uark.registerapp.controllers.enums.ViewNames;
import edu.uark.registerapp.models.api.ApiResponse;
import edu.uark.registerapp.models.api.Employee;

@RestController
@RequestMapping(value = "/api/employee")
public class EmployeeRestController extends BaseRestController {
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public @ResponseBody ApiResponse createEmployee(
        @RequestBody final Employee employee,
        final HttpServletRequest request, 
        final HttpServletResponse response
    ) {

        final ApiResponse elevatedUserResponse =
        this.redirectUserNotElevated(
            request,
            response,
            ViewNames.EMPLOYEE_DETAIL.getRoute());

    if (!elevatedUserResponse.getRedirectUrl().equals(StringUtils.EMPTY)) {
        return elevatedUserResponse;
    }

        return this.employeeCreateCommand
            .setApiEmployee(employee)
            .execute();
    }

    @RequestMapping(value = "/{employeeId}", method = RequestMethod.PUT)
    public @ResponseBody ApiResponse updateEmployee(
        @PathVariable final UUID employeeId,
        @RequestBody final Employee employee,
        final HttpServletRequest request,
        final HttpServletResponse response
    ) {

        final ApiResponse elevatedUserResponse =
        this.redirectUserNotElevated(
            request,
            response,
            ViewNames.EMPLOYEE_DETAIL.getRoute()); 

        if (!elevatedUserResponse.getRedirectUrl().equals(StringUtils.EMPTY)) {
            return elevatedUserResponse;
        }

        return this.employeeUpdateCommand
            .setEmployeeId(employeeId)
            .setApiEmployee(employee)
            .execute();
    }

    @RequestMapping(value = "/{employeeId}", method = RequestMethod.DELETE)
    public @ResponseBody ApiResponse deleteEmployee(
        @PathVariable final UUID employeeId, 
        final HttpServletRequest request,
        final HttpServletResponse response
    ) {

        final ApiResponse elevatedUserResponse =
        this.redirectUserNotElevated(
            request,
            response,
            ViewNames.EMPLOYEE_DETAIL.getRoute());

        if (!elevatedUserResponse.getRedirectUrl().equals(StringUtils.EMPTY)) {
            return elevatedUserResponse;
        }

        this.employeeDeleteCommand
            .setEmployeeId(employeeId)
            .execute();

        return new ApiResponse();
    }

    // Properties
    @Autowired
    private EmployeeCreateCommand employeeCreateCommand;

    @Autowired
    private EmployeeDeleteCommand employeeDeleteCommand;

    @Autowired
    private EmployeeUpdateCommand employeeUpdateCommand;
}


