package salariati.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import salariati.exception.EmployeeException;
import salariati.model.Employee;

import org.junit.Before;
import org.junit.Test;

import salariati.repository.interfaces.EmployeeRepositoryInterface;
import salariati.repository.mock.EmployeeRepositoryMock;
import salariati.validator.EmployeeValidator;
import salariati.controller.EmployeeController;
import salariati.enumeration.DidacticFunction;

import java.text.ParseException;

public class AddEmployeeTest {

	private EmployeeRepositoryInterface employeeRepository;
	private EmployeeController controller;
	private EmployeeValidator employeeValidator;

	@Before
	public void setUp() {
		employeeRepository = new EmployeeRepositoryMock();
		controller         = new EmployeeController(employeeRepository);
		employeeValidator  = new EmployeeValidator();
	}

	@After
	public void tearDown() {
		System.out.println("Yet another test run");
	}

	@Test
	public void TC1_ECP() {
		int length = controller.getEmployeesList().size();
		assertTrue( controller.addEmployee(
					new Employee("Suciu", "Alex", "1960207125804", DidacticFunction.LECTURER, "1000")
				)
		);
		assertTrue( controller.getEmployeesList().size() == length + 1 );
	}

	@Test
	public void TC2_ECP() {
		int length = controller.getEmployeesList().size();
		assertFalse(
			controller.addEmployee(
					new Employee("Danciu", "Daniel", "196", DidacticFunction.ASISTENT, "900")
			)
		);
		assertTrue( controller.getEmployeesList().size() == length );
	}

	@Test
	public void TC3_ECP() {
		int length = controller.getEmployeesList().size();
		assertFalse(
				controller.addEmployee(
						new Employee("Strajan", "Sebastian", "a1960207125801", DidacticFunction.LECTURER, "9000")
				)
		);
		assertTrue( controller.getEmployeesList().size() == length );
	}

	@Test
	public void TC4_ECP() {
		int length = controller.getEmployeesList().size();
		assertFalse(
				controller.addEmployee(
						new Employee("111", "-2", "1960207125804", DidacticFunction.TEACHER, "1000")
				)
		);
		assertTrue( controller.getEmployeesList().size() == length );
	}

	@Test
	public void TC5_ECP() {

		try {
			controller.addEmployee(
					Employee.getEmployeeFromString("Brisan;Anca;2031567100100;invalida;1000", 0)
				);
			assertTrue(false);
		} catch (EmployeeException e) {
			assertTrue(true);
		} catch (Exception ex) {
            ex.printStackTrace();
		    assertTrue(false);
        }
	}

	@Test
	public void TC1_BVA() {
		int length = controller.getEmployeesList().size();
		try {
			controller.addEmployee(
					Employee.getEmployeeFromString(";Anca;2031567100100;LECTURER;1000", 0)
			);
			assertTrue(false);
		} catch (EmployeeException e) {
			assertTrue(true);
		} finally {
			assertTrue(length == controller.getEmployeesList().size());
		}
	}

	@Test
	public void TC6_BVA() {
		String test = "";
		try {

			for (int i = 0; i < 64; i++) {
				test += "MMMM"; // generating 256 M's
			}
			boolean success = controller.addEmployee(
					Employee.getEmployeeFromString(test + ";Anca;2031567100100;LECTURER;1000", 0)
			);
			assertTrue(success);
		} catch (Exception e) {
			assertTrue(false);
		}
	}

	@Test
	public void TC13_BVA() {
		int length = controller.getEmployeesList().size();
		assertTrue( controller.addEmployee(
				new Employee("Suciu", "Alex", "1960207125804", DidacticFunction.LECTURER, "1000")
				)
		);
		assertTrue( controller.getEmployeesList().size() == length + 1 );
	}

	@Test
	public void TC14_BVA() {
		int length = controller.getEmployeesList().size();
		assertFalse( controller.addEmployee(
				new Employee("Suciu", "Alex", "196020712580", DidacticFunction.LECTURER, "1000")
				)
		);
		assertTrue( controller.getEmployeesList().size() == length );
	}

	@Test
	public void TC15_BVA() {
		int length = controller.getEmployeesList().size();
		assertFalse( controller.addEmployee(
				new Employee("Suciu", "Alex", "19602071258045", DidacticFunction.LECTURER, "1000")
				)
		);
		assertTrue( controller.getEmployeesList().size() == length );
	}


}
