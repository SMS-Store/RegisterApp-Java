package edu.uark.registerapp.controllers;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import edu.uark.registerapp.controllers.enums.ViewModelNames;
import edu.uark.registerapp.controllers.enums.ViewNames;
import edu.uark.registerapp.models.api.Employee;
import edu.uark.registerapp.models.entities.ActiveUserEntity;
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
		// I'm really sorry for using null values, but NOTHING ELSE
		// WORKS. We'll just have to suck it up unless you figure
		// out something better...
		String idParam = queryParameters.get("id");
		UUID recordId = null;

		if (idParam != null)
		{
			recordId = UUID.fromString(queryParameters.get("id"));
		}

		if (recordId != null)
		{
			System.out.println(recordId);
			return (new ModelAndView(ViewNames.EMPLOYEE_DETAIL.getViewName()))
				.addObject(ViewModelNames.EMPLOYEE.getValue(),
					(new Employee()).setId(recordId));
		}
		else
		{
			return (new ModelAndView(ViewNames.EMPLOYEE_DETAIL.getViewName()))
					.addObject(ViewModelNames.EMPLOYEE.getValue(),
					(new Employee()).setId(UUID.fromString(
						"00000000-0000-0000-0000-000000000000")));
		}
	}

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

	@Autowired
	EmployeeRepository employeeRepository;

	@Autowired
	ActiveUserRepository activeUserRepository;
}
