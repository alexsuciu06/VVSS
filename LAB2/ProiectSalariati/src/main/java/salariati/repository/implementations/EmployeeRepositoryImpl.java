package salariati.repository.implementations;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import salariati.exception.EmployeeException;

import salariati.model.Employee;

import salariati.repository.interfaces.EmployeeRepositoryInterface;
import salariati.validator.EmployeeValidator;

public class EmployeeRepositoryImpl implements EmployeeRepositoryInterface {
	
	private final String employeeDBFile;
	private EmployeeValidator employeeValidator;

	public EmployeeRepositoryImpl() {
		this(false);
	}

	public EmployeeRepositoryImpl(boolean test) {
		employeeDBFile = test
				? "src/main/java/employeeDB/employeesTest.txt"
				: "src/main/java/employeeDB/employees.txt";
		employeeValidator = new EmployeeValidator();
	}

	@Override
	public boolean addEmployee(Employee employee) {
		if( employeeValidator.isValid(employee) ) {
			BufferedWriter bw = null;
			try {
				bw = new BufferedWriter(new FileWriter(employeeDBFile, true));
				bw.write(employee.toString());
				bw.newLine();
				return true;
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					bw.close();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
		}
		return false;
	}

	@Override
	public void deleteEmployee(Employee employee) {
		List<Employee> all = getEmployeeList();
		List<Employee> _new = new ArrayList<Employee>();
		for (Employee e : all) {
			if (!e.getCnp().equals(employee.getCnp())) {
				_new.add(e);
			}
		}
		this.writeEmployees(_new);
	}

	@Override
	public void modifyEmployee(Employee oldEmployee, Employee newEmployee) {
		if ( employeeValidator.isValid(newEmployee) ) {
			List<Employee> all = getEmployeeList();
			List<Employee> _new = new ArrayList<Employee>();
			for (Employee e : all) {
				if (!e.getCnp().equals(oldEmployee.getCnp())) {
					_new.add(e);
				}
			}
			_new.add(newEmployee);
			this.writeEmployees(_new);
		}
		
	}

	@Override
	public List<Employee> getEmployeeList() {
		List<Employee> employeeList = new ArrayList<Employee>();
		
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(employeeDBFile));
			String line;
			int counter = 0;
			while ((line = br.readLine()) != null) {
				Employee employee;
				try {
					employee = Employee.getEmployeeFromString(line, counter);
					employeeList.add(employee);
				} catch(EmployeeException ex) {
					System.err.println("Error while reading: " + ex.toString());
				}
			}
		} catch (FileNotFoundException e) {
			System.err.println("Error while reading: " + e);
		} catch (IOException e) {
			System.err.println("Error while reading: " + e);
		} finally {
			if (br != null)
				try {
					br.close();
				} catch (IOException e) {
					System.err.println("Error while closing the file: " + e);
				}
		}
		
		return employeeList;
	}

	private void writeEmployees(List<Employee> employees) {
		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new FileWriter(employeeDBFile, false));
			for (Employee employee : employees) {
				bw.write(employee.toString());
				bw.newLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				bw.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

}
