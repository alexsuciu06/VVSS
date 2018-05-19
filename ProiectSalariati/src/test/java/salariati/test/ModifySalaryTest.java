package salariati.test;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import salariati.controller.EmployeeController;
import salariati.enumeration.DidacticFunction;
import salariati.model.Employee;
import salariati.repository.interfaces.EmployeeRepositoryInterface;
import salariati.repository.mock.EmployeeRepositoryMock;
import salariati.validator.EmployeeValidator;

public class ModifySalaryTest {
    private EmployeeRepositoryInterface employeeRepository;
    private EmployeeController controller;
    private EmployeeValidator employeeValidator;

    @Before
    public void setUp() {
        employeeRepository = new EmployeeRepositoryMock();
        controller         = new EmployeeController(employeeRepository);
        employeeValidator  = new EmployeeValidator();
    }

    @Test
    public void modifySalaryForEmployeeInvalid() {
        Employee oldie = new Employee();
        // Always insert this CNP in mocked repo !
        oldie.setCnp("1234567890871");
        Employee new_guy = new Employee();
        new_guy.setCnp("123");
        new_guy.setFirstName("A");
        new_guy.setLastName("B");
        new_guy.setFunction(DidacticFunction.LECTURER);

        controller.modifyEmployee( oldie, new_guy );
        boolean not_found = true;
        for ( Employee e : controller.getEmployeesList() ) {
            if (e.getCnp().equals("123")) {
                not_found = false;
            }
        }
        assertTrue(not_found);
    }

    @Test
    public void modifySalaryForEmployeeValid() {
        Employee oldie = new Employee();
        // Always insert this CNP in mocked repo !
        oldie.setCnp("1234567890871");
        Employee new_guy = new Employee();
        new_guy.setCnp("1111111111111");
        new_guy.setFirstName("Asad");
        new_guy.setLastName("Basdasd");
        new_guy.setFunction(DidacticFunction.LECTURER);
        new_guy.setSalary("123");

        controller.modifyEmployee( oldie, new_guy );
        boolean found = false;
        for ( Employee e : controller.getEmployeesList() ) {
            if (e.getCnp().equals("1111111111111")) {
                found = true;
            }
        }
        assertTrue(found);
    }

    @Test
    public void modifySalaryForInexistentEmployee() {
        Employee oldie = new Employee();
        // Always insert this CNP in mocked repo !
        oldie.setCnp("9999999999999");
        Employee new_guy = new Employee();
        new_guy.setCnp("1111111111111");
        new_guy.setFirstName("A");
        new_guy.setLastName("B");
        new_guy.setFunction(DidacticFunction.LECTURER);
        new_guy.setSalary("123");

        controller.modifyEmployee( oldie, new_guy );
        boolean not_found = true;
        for ( Employee e : controller.getEmployeesList() ) {
            if (e.getCnp().equals("1111111111111")) {
                not_found = false;
            }
        }
        assertTrue(not_found);
    }
}
