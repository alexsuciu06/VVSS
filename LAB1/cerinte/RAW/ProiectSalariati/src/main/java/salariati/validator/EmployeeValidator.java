package salariati.validator;

import salariati.enumeration.DidacticFunction;
import salariati.model.Employee;

import java.text.ParseException;

public class EmployeeValidator {
	
	public EmployeeValidator(){}

	public boolean isValid(Employee employee) {
		if ( employee == null
			|| employee.getLastName() == null
			|| employee.getFirstName() == null
			|| employee.getCnp() == null
			|| employee.getFunction() == null
			|| employee.getSalary() == null
				) {
			return false;
		}
		boolean isLastNameValid  = employee.getLastName().matches("[-'a-zA-Z]+") && (employee.getLastName().length() > 2);
		boolean isFirstNameValid  = employee.getFirstName().matches("[-'a-zA-Z]+") && (employee.getLastName().length() > 2);
		boolean isCNPValid       = employee.getCnp().matches("[0-9]+") && (employee.getCnp().length() == 13);
		boolean isFunctionValid  = employee.getFunction().equals(DidacticFunction.ASISTENT) ||
								   employee.getFunction().equals(DidacticFunction.LECTURER) ||
								   employee.getFunction().equals(DidacticFunction.TEACHER) ||
				                   employee.getFunction().equals(DidacticFunction.ASSOCIATE);
		boolean isSalaryValid    = employee.getSalary().matches("[0-9]+") && (employee.getSalary().length() > 1) && (Integer.parseInt(employee.getSalary()) > 0);
		
		return isLastNameValid && isFirstNameValid && isCNPValid && isFunctionValid && isSalaryValid;
	}

	
}
