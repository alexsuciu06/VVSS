package salariati.view.console;

import salariati.controller.EmployeeController;
import salariati.exception.EmployeeException;
import salariati.model.Employee;
import java.util.List;
import java.util.Scanner;

public class ConsoleUI {
    private EmployeeController ctr;
    private Scanner in = new Scanner(System.in);

    public ConsoleUI(EmployeeController ctr) {
        this.ctr = ctr;
    }

    public void run() {
        while(true) {
            showOptions();
            String opt = in.next();
            if (opt.startsWith("x"))
                break;
            handleOption(opt);
        }
    }

    public void showOptions() {
        System.out.println(
                "\t 1.Adauga angajat \n" +
                "\t 2.Modifica angajat \n" +
                "\t 3.Afisare angajati \n" +
                "\t 4.Stergere salariati \n" +
                "\t x.Inchide \n"
            );
        System.out.print("\t Optiune: ");
    }

    public void handleOption(String option) {
        switch (option.charAt(0)) {
            case '1' : this.addEmployeeHandler();break;
            case '2' : this.modifyEmployeeHandler();break;
            case '3' : this.showEmployeesHandler();break;
            case '4' : this.deleteEmployeeHandler();break;
            default  : System.out.println("\nOptiune invalida");
        }
    }

    private void addEmployeeHandler() {
        String[] attribs = {"prenume", "nume", "CNP", "Functie", "Salariu"};
        String str = "";
        for (String attrib : attribs) {
            System.out.print("\nDati " + attrib + ": ");
            str += in.next() + ";";
        }
        str.replaceFirst(";$", "");
        try {
            ctr.addEmployee(
                    Employee.getEmployeeFromString(str, 1)
            );
            System.out.println("Adaugare efectuata");
        } catch (EmployeeException e) {
            System.out.println("Eroare la introducerea datelor");
        }
    }

    private void modifyEmployeeHandler() {

        System.out.println("Dati CNP-ul angajatului ce va fi modificat: ");
        String cnp = in.next();
        Employee emp_old = new Employee();
        emp_old.setCnp(cnp);

        String[] attribs = {"prenume", "nume", "CNP", "Functie", "Salariu"};
        String str = "";
        for (String attrib : attribs) {
            System.out.print("\nDati " + attrib + ": ");
            str += in.next() + ";";
        }
        str.replaceFirst(";$", "");

        try {
            ctr.modifyEmployee(emp_old, Employee.getEmployeeFromString(str, 0));
            System.out.println("Modifcarea a avut succes");
        } catch(Exception e) {
            System.out.println("Eroare la modificarea angajatului!");
        }
    }

    private void showEmployeesHandler() {
        List<Employee> all = ctr.getEmployeesList();

        for (Employee e : all) {
            System.out.println(e);
        }
    }

    private void deleteEmployeeHandler() {
        System.out.println("Dati CNP-ul angajatului: ");
        String cnp = in.next();
        Employee emp = new Employee();
        emp.setCnp(cnp);
        try {
            ctr.deleteEmployee(emp);
            System.out.println("Stergere efectuata");
        } catch (Exception e) {
            System.out.println("Eroare la stergere! Reincercati");
            e.printStackTrace();
        }
    }
}
