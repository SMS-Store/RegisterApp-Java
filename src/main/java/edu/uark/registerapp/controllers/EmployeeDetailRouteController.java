package edu.uark.registerapp.controllers;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import edu.uark.registerapp.commands.employees.EmployeeExistsQuery;
import edu.uark.registerapp.commands.employees.EmployeeQuery;
import edu.uark.registerapp.controllers.enums.ViewModelNames;
import edu.uark.registerapp.controllers.enums.ViewNames;
import edu.uark.registerapp.models.api.Employee;
import edu.uark.registerapp.models.entities.ActiveUserEntity;
import edu.uark.registerapp.models.enums.EmployeeClassification;
import edu.uark.registerapp.models.repositories.ActiveUserRepository;
import edu.uark.registerapp.models.repositories.EmployeeRepository;

@Controller
@RequestMapping(value = "/employeeDetail")
public class EmployeeDetailRouteController extends BaseRouteController {
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView start(
		@RequestParam final Map<String, String> queryParameters,
		final HttpServletRequest request
	)
	{
		final Optional<ActiveUserEntity> activeUser =
			this.getCurrentUser(request);
		
		// If nobody is signed in and employees exist...
		if (!activeUser.isPresent() && employeeExistsQuery.execute())
		{
			// Redirect back to sign in page with error code 1001
			return this.buildInvalidSessionResponse();
		}
		// Else if nobody is signed in (and no employees are defined)
		else if (!activeUser.isPresent())
		{
			// Serve default (empty) employee detail page
			return serveDefaultPage();
		}
		// If employee is signed in but is not elevated
		else if (!EmployeeClassification.isElevatedUser(
				activeUser.get().getClassification()))
		{
			// Redirect to main menu
			return buildNoPermissionsResponse();
		}
		// Else (if employee is signed in and elevated)
		else
		{
			// Serve default (empty) employee detail page
			return serveDefaultPage();
		}
	}

	@RequestMapping(value = "/{employeeUUID}", method = RequestMethod.GET)
	public ModelAndView startWithEmployee(
		@PathVariable final UUID employeeUUID,
		final HttpServletRequest request)
	{
		final Optional<ActiveUserEntity> activeUser =
			this.getCurrentUser(request);
		
		if (!activeUser.isPresent())
		{
			return this.buildInvalidSessionResponse();
		}
		else if (!EmployeeClassification.isElevatedUser(
			activeUser.get().getClassification()))
		{
			return buildNoPermissionsResponse();
		}

		final ModelAndView modelAndView =
			new ModelAndView(ViewNames.EMPLOYEE_DETAIL.getViewName());

		try
		{
			modelAndView.addObject(
				ViewModelNames.EMPLOYEE.getValue(),
				this.employeeQuery.setEmployeeId(employeeUUID).execute());
		}
		catch (final Exception e)
		{
			String uuidString = "00000000-0000-0000-0000-000000000000";

			modelAndView.addObject(
				ViewModelNames.ERROR_MESSAGE.getValue(),
				e.getMessage());
			
			modelAndView.addObject(
				ViewModelNames.EMPLOYEE.getValue(),
				(new Employee()).setId(UUID.fromString(uuidString)));
		}

		return modelAndView;
	}

	// Handles AJAX requests for employee class
	@RequestMapping(value = "/getClass", method = RequestMethod.GET)
	public @ResponseBody boolean getUserClass(HttpServletRequest request)
	{
		Optional<ActiveUserEntity> user =this.activeUserRepository
			.findBySessionKey(request.getSession().getId());

		if (user.isPresent() && (user.get().getClassification() == 101))
			return false;
		else
			return true;
	}

	// Returns the model and view for a default (empty) employee detail page
	ModelAndView serveDefaultPage()
	{
		String uuidString = "00000000-0000-0000-0000-000000000000";

		return (new ModelAndView(ViewNames.EMPLOYEE_DETAIL.getViewName()))
					.addObject(ViewModelNames.EMPLOYEE.getValue(),
						(new Employee()).setId(UUID.fromString(uuidString)));
	}

	@Autowired
	EmployeeQuery employeeQuery;

	@Autowired
	EmployeeExistsQuery employeeExistsQuery;

	@Autowired
	EmployeeRepository employeeRepository;

	@Autowired
	ActiveUserRepository activeUserRepository;
}
