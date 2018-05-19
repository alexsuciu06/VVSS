package salariati.test;

import org.junit.Before;
import org.junit.Test;
import salariati.controller.EmployeeController;
import salariati.enumeration.DidacticFunction;
import salariati.exception.EmployeeException;
import salariati.model.Employee;
import salariati.repository.interfaces.EmployeeRepositoryInterface;
import salariati.repository.mock.EmployeeRepositoryMock;
import salariati.validator.EmployeeValidator;

import static org.junit.Assert.assertTrue;

public class IntegrationTopDown {
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
    public void testUnitA() {
        // P->A
        int length = controller.getEmployeesList().size();
        assertTrue( controller.addEmployee(
                new Employee("Suciu", "Alex", "1960207125804", DidacticFunction.LECTURER, "1000")
                )
        );
        assertTrue( controller.getEmployeesList().size() == length + 1 );
    }

    @Test
    public void testAB() {
        // P->A->B
        int length = controller.getEmployeesList().size();
        Employee je = new Employee("Suciu", "Alex", "1960207125804", DidacticFunction.LECTURER, "1000");
        boolean added = controller.addEmployee(je);

        je.setFunction(DidacticFunction.ASISTENT);
        controller.modifyEmployee(je,je);
        boolean found = false;
        for (Employee maybe_je : controller.getEmployeesList()) {
            if (maybe_je.getCnp().equals(je.getCnp()) && maybe_je.getFunction().equals(je.getFunction()))
                found = true;
        }
        assertTrue(added);
        assertTrue( controller.getEmployeesList().size() == length + 1 );
        assertTrue(found);
    }

    @Test
    public void testABC() {
        // P->A->B
        int length = controller.getEmployeesList().size();
        Employee je = new Employee("Suciu", "Alex", "1960207125804", DidacticFunction.LECTURER, "1000");
        boolean added = controller.addEmployee(je);

        je.setFunction(DidacticFunction.ASISTENT);
        controller.modifyEmployee(je,je);
        boolean found_1 = false;
        for (Employee maybe_je : controller.getEmployeesList()) {
            if (maybe_je.getCnp().equals(je.getCnp()) && maybe_je.getFunction().equals(je.getFunction()))
                found_1 = true;
        }

        je.setSalary("1500");
        controller.modifyEmployee(je,je);
        boolean found_2 = false;
        for (Employee maybe_je : controller.getEmployeesList()) {
            if (maybe_je.getCnp().equals(je.getCnp()) && maybe_je.getSalary().equals(je.getSalary()))
                found_2 = true;
        }

        assertTrue(added);
        assertTrue( controller.getEmployeesList().size() == length + 1 );
        assertTrue(found_1);
        assertTrue(found_2);
    }
}
