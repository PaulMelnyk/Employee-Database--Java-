import java.sql.SQLException;
import java.util.ArrayList;
/**
 * @author PaulMelnyk
 * @serial StudentID 14008771
 * @version Programming Assignment
 */
public class CrudTestMain {
/**
 * @throws SQLException - prevents errors occurring from SQL queries
 */
	public static void main(String[] args) throws SQLException{
		//Main For CRUD tests, comment out individual blocks to see affects, as they all act upon the same employee - 
		// IMPLEMENTED - Retrieve All, Create employee, Update employee, Retrieve single employee, Delete Employee
		EmployeeDAO dao = new EmployeeDAO(); //allows connection to EmployeeDAO/database
		ArrayList<Employee>empList = new ArrayList<Employee>(); //creation of arrayList
		
		dao.getConnection(); // connects to database
		empList = dao.selectAllEmployees(); //stores all employee information in empList
		System.out.println(empList.toString()); // prints empList to system (RETRIEVE)
		

		Employee emp = new Employee("Tester Class",'F',"AT125162A","12-12-1973","Brighton","BR12 2AT", "8","18000","23-02-2007","Cleaner","test@test.com"); //allows instance of employee, give it set values
		if(emp.getName()!=null){ //makes sure name is input
		dao.insertEmployee(emp); //adds the tester employee to db (CREATE)
		}
		empList = dao.selectAllEmployees(); //refreshes empList
		System.out.println(empList.toString()); // prints the new empList
		
		emp.setGender('M'); // sets gender of employee to m instead of f (UPDATE)
		if(emp.getName()!=null){ //makes sure name is input
		dao.insertEmployeeAtId(emp); //updates employee
		}
		Employee ret = new Employee(); //creates new employee
		if(emp.getName()!=null && emp.getId()!=null){ // checks to see if name and Id not null
		ret = dao.selectEmployeeByName(emp.getName()); //retrieves data of updated employee with name matching employee
		}
		System.out.println(ret); //prints out updated employee
		
		dao.deleteEmployeeById(ret.getId()); // deletes employee at test (DELETE)
		empList = dao.selectAllEmployees(); //refreshes empList
		System.out.println(empList.toString()); //prints empList with employee deleted
	}
	
}
