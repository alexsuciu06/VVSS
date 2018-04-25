package salariati.main;

import salariati.model.Employee;
import salariati.repository.implementations.EmployeeRepositoryImpl;
import salariati.repository.interfaces.EmployeeRepositoryInterface;
import salariati.repository.mock.EmployeeRepositoryMock;
import salariati.validator.EmployeeValidator;
import salariati.controller.EmployeeController;
import salariati.enumeration.DidacticFunction;
import salariati.view.console.ConsoleUI;

import java.util.Scanner;

//functionalitati
//i.	 adaugarea unui nou angajat (nume, prenume, CNP, functia didactica, salariul de incadrare);
//ii.	 modificarea functiei didactice (asistent/lector/conferentiar/profesor) a unui angajat;
//iii.	 afisarea salariatilor ordonati descrescator dupa salariu si crescator dupa varsta (CNP).
public class StartApp {
	
	public static void main(String[] args) {
		
		EmployeeRepositoryInterface employeesRepository = new EmployeeRepositoryImpl();
		EmployeeController employeeController = new EmployeeController(employeesRepository);
		ConsoleUI ui = new ConsoleUI(employeeController);

		ui.run();
	}

}
