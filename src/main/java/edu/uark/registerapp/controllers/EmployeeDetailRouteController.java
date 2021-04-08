package edu.uark.registerapp.controllers;

import java.util.Optional;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import edu.uark.registerapp.controllers.enums.ViewModelNames;
import edu.uark.registerapp.controllers.enums.ViewNames;
import edu.uark.registerapp.models.api.Employee;
import edu.uark.registerapp.models.entities.ActiveUserEntity;
import edu.uark.registerapp.models.entities.EmployeeEntity;
import edu.uark.registerapp.models.repositories.ActiveUserRepository;
import edu.uark.registerapp.models.repositories.EmployeeRepository;

@Controller
@RequestMapping(value = "/employeeDetail")
public class EmployeeDetailRouteController extends BaseRouteController {
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView start()
	{
		return (new ModelAndView(ViewNames.EMPLOYEE_DETAIL.getViewName()))
			.addObject(
				ViewModelNames.EMPLOYEE.getValue(),
				(new Employee()).setId(UUID.fromString("00000000-0000-0000-0000-000000000000")));
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public ModelAndView makeNewEmployee(
		EmployeeEntity employee,
		HttpServletRequest request
	)
	{
		employee = new EmployeeEntity();

		employee.setFirstName(request.getParameter("firstname"));
		employee.setLastName(request.getParameter("lastname"));
		employee.setPassword(request.getParameter("password").getBytes());
		employee.setClassification(Integer.parseInt(
			request.getParameter("classification")));

		employeeRepository.save(employee);

		if (this.employeeRepository.count() == 1)
		{
			ModelAndView modelAndView = new ModelAndView(
				REDIRECT_PREPEND.concat(ViewNames.SIGN_IN.getRoute()));

			modelAndView.addObject("Method", "GET");
			
			return modelAndView;
		}
		else
			return new ModelAndView(ViewNames.EMPLOYEE_DETAIL.getViewName());
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
