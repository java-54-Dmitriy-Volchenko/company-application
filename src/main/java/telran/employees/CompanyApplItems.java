package telran.employees;

import java.util.*;

import telran.io.Persistable;
import telran.view.InputOutput;
import telran.view.Item;
//public void addEmployee(Employee empl) ;
//public Employee getEmployee(long id) ;
//public Employee removeEmployee(long id) ;
//public int getDepartmentBudget(String department) ;
//public String[] getDepartments() ;
//public Manager[] getManagersWithMostFactor() ;

public class CompanyApplItems {
	static Company company;
	static HashSet<String> departments;
public static List<Item> getCompanyItems(Company company,
		HashSet<String> departments) {
	CompanyApplItems.company = company;
	CompanyApplItems.departments = departments;
	Item[] items = {
		Item.of("add employee", CompanyApplItems::addEmployee)	,
		Item.of("display employee data", CompanyApplItems::getEmployee),
		Item.of("remove employee", CompanyApplItems::removeEmployee),
		Item.of("display department budget", CompanyApplItems::getDepartmentBudget),
		Item.of("display departments", CompanyApplItems::getDepartments),
		Item.of("display managers with most factor", CompanyApplItems::getManagersWithMostFactor),
	};
	return new ArrayList(List.of(items));
	
}
static void addEmployee(InputOutput io) {
	Employee empl = readEmployee(io);
	String type = io.readStringOptions("Enter employee type",
			"Wrong Employee Type", new HashSet<String>
	(List.of("WageEmployee", "Manager", "SalesPerson")));
	Employee result = switch(type) {
	case "WageEmployee" -> getWageEmployee(empl, io);
	case "Manager" -> getManager(empl, io);
	case "SalesPerson" -> getSalesPerson(empl, io);
	default -> null;
	};
	company.addEmployee(result);
	((Persistable) company).save(CompanyAppl.FILE_NAME);
	io.writeLine("Employee has been added");
}
private static Employee getSalesPerson(Employee empl, InputOutput io) {
	WageEmployee wageEmployee = (WageEmployee) getWageEmployee(empl, io);
	float percents = io.readNumberRange("Enter percents", "Wrong percents value", 0.5, 2).floatValue();
	long sales = io.readNumberRange("Enter sales", "Wrong sales value", 500, 50000).longValue();
	return new SalesPerson(empl.getId(), empl.getBasicSalary(), empl.getDepartment(),
			wageEmployee.getHours(), wageEmployee.getWage(),
			percents, sales);
}
private static Employee getManager(Employee empl, InputOutput io) {
	
	float factor = io.readNumberRange("Enter factor",
			"Wrong factor value", 1.5, 5).floatValue();
	return new Manager(empl.getId(), empl.getBasicSalary(), empl.getDepartment(), factor );
}
private static Employee getWageEmployee(Employee empl, InputOutput io) {
	
	int hours = io.readNumberRange("Enter working hours",
			"Wrong hours value", 10, 200).intValue();
	int wage = io.readNumberRange("Enter hour wage",
			"Wrong wage value", 100, 1000).intValue();;
	return new WageEmployee(empl.getId(), empl.getBasicSalary(), empl.getDepartment(), hours, wage);
}
private static Employee readEmployee(InputOutput io) {
	
	long id = io.readNumberRange("Enter id value", "Wrong id value", 1000, 10000).longValue();
	int basicSalary = io.readNumberRange("Enter basic salary", "Wrong basic salary", 2000, 20000).intValue();
	String department = io.readStringOptions("Enter department " + departments, "Wrong department", departments);
	return new Employee(id, basicSalary, department);
	
	
}
private static long readId(InputOutput io) {
	long id = io.readLong("Enter employee ID", "Invalid ID");
	return id;
}
static void getEmployee( InputOutput io) {
		long id = readId(io);
	    Employee employee = company.getEmployee(id);
	    if (employee == null) {
	        io.writeLine("Employee not found");
	    } else {
	        io.writeLine(employee.toString());
	    }
}


static void removeEmployee(InputOutput io) {
	long id = readId(io);
		try {
	        Employee employee = company.removeEmployee(id);
	        io.writeLine("Employee was successfully deleted: " + employee);
	       
	        ((Persistable) company).save(CompanyAppl.FILE_NAME);
	    } catch (NoSuchElementException e) {
	        io.writeLine("Employee not found");
	    }
   
	}
static void getDepartmentBudget(InputOutput io) {
	// TODO
}
static void getDepartments(InputOutput io) {
	//TODO
	
}
static void getManagersWithMostFactor(InputOutput io) {
	// TODO
	
}
}
