import java.sql.*; //allows SQL to be used
import java.util.ArrayList; //allows arrayLists to be used
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 * @author PaulMelnyk
 * @serial StudentID 14008771
 * @version Programming Assignment
 */
public class EmployeeDAO {
	Connection c = null; //sets c to null
	Statement s = null ; 
	ResultSet rs = null; 
	
	/**
	 * @return - connection, allows SQLite to be accessed
	 */
public Connection getConnection(){ //method to connect to database
	try{
		Class.forName("org.sqlite.JDBC"); //loads the class
	} catch (ClassNotFoundException e){ //catches exception
		System.out.println(e.getMessage()); //prints if error is found
	}
	try{
		String dbURL = "jdbc:sqlite:empdb.sqlite"; //attempts to connect to database
		c = DriverManager.getConnection(dbURL); //saves the connection under dbConnection
		System.out.println("Database Successfully Opened"); //print to make sure database opened correctly
		return c; //returns connection
	} catch (SQLException e){ //catches exception
		System.out.println(e.getMessage()); //prints error
	}
	return c; //returns connection
}
	
	/**
	 * @throws SQLException - prevents error, allows database connection to close
	 */
public void close() throws SQLException { //method to close database
    if (c != null) { //checks to see if c is open
        try { //if c is open
            c.close(); //closes database
            System.out.println("Database Successfully Closed"); //print if successful
        } catch (SQLException e) { //catches if close does not work
        	System.out.println(e.getMessage()); //prints error if not close occurs
        }
    }
}

	/**
	 * @return - arrayList containing employees stored
	 * @throws SQLException - prevents error, allows info to store
	 */
public ArrayList<Employee> selectAllEmployees() throws SQLException{ //method to get all employee info
    ArrayList<Employee> employees = new ArrayList<Employee>(); //creates arrayList employees
        s = c.createStatement();  // allows statement to execute
        String query = "SELECT * FROM employees"; //creates a query
        rs = s.executeQuery(query); //Executes query

        while (rs.next()) { //keeps running until all of database is gone through
            Employee e = new Employee(); //creates a new employee (e) to save details to
            e.setId(rs.getString("Id"));; //gets id from database
            e.setName(rs.getString("name")); //gets name from database
            e.setGender(rs.getString("gender").charAt(0)); //gets char0 of gender from database, M or F
            e.setDob(rs.getString("DOB")); //gets date of birth from database
            e.setAddress(rs.getString("Address")); //gets address from database
            e.setPostcode(rs.getString("Postcode")); //gets post code from database
            e.setNatInscNo(rs.getString("NIN")); //gets national insurance from database
            e.setTitle(rs.getString("JobTitle")); //gets job title from database
            e.setStartDate(rs.getString("StartDate")); //gets 
            e.setSalary(rs.getString("Salary")); //gets salary from database
            e.setEmail(rs.getString("Email")); //gets email from database
            employees.add(e); //adds the employee details to the arrayList
        }
    return employees; //returns the value for employees
}

	/**
	 * @param emp - collects name, allowing search
	 * @return - employee return, used to print data to GUI
	 * @throws SQLException - prevents error when connecting to SQLite
	 */
public Employee selectEmployeeByName(String emp) throws SQLException{ //method to find an employee by name, takes parameter in
	PreparedStatement s = c.prepareStatement("Select * FROM employees WHERE name LIKE ?"); //creates a query, ? is parameter
	s.setString(1, "%"+emp+"%"); //sets the parameter to = ?, and searches if name contains
	rs = s.executeQuery(); //executes the query
			Employee e = new Employee(); //creates employee(e)
            e.setId(rs.getString("Id"));; //gets id from database
            e.setName(rs.getString("name")); //gets name from database
            e.setGender(rs.getString("gender").charAt(0)); //gets char0 in gender from database
            e.setDob(rs.getString("DOB")); //gets date of birth from database
            e.setAddress(rs.getString("Address")); //gets address from database
            e.setPostcode(rs.getString("Postcode")); //gets post code from database
            e.setNatInscNo(rs.getString("NIN")); //gets national insurance from database
            e.setTitle(rs.getString("JobTitle")); //gets job title from database
            e.setStartDate(rs.getString("StartDate")); //gets start date from database
            e.setSalary(rs.getString("Salary")); //gets salary from database
            e.setEmail(rs.getString("Email")); //gets email from database
            System.out.println("Employee found with name: " + e.getName());
			return e; //returns the chosen employee
}

	/**
	 * @param e - takes employee, allowing insert
	 * @return - boolean, true = success 
	 * @throws SQLException - allows employee to be inserted, prevents error
	 */
public boolean insertEmployee(Employee e) throws SQLException{
	PreparedStatement s = c.prepareStatement("INSERT INTO employees" +"(ID,Name,Gender,DOB,Address,Postcode,NIN,JobTitle,StartDate,Salary,Email)" + "VALUES" + "(?,?,?,?,?,?,?,?,?,?,?)");
	String gen = Character.toString(e.getGender());
	
	s.setString(1, e.getId());
	s.setString(2, e.getName());
	s.setString(3, gen);
	s.setString(4, e.getDob());
	s.setString(5, e.getAddress());
	s.setString(6, e.getPostcode());
	s.setString(7, e.getNatInscNo());
	s.setString(8, e.getTitle());
	s.setString(9, e.getStartDate());
	s.setString(10, e.getSalary());
	s.setString(11, e.getEmail());
	Boolean cState = s.execute();
	System.out.println("Employee: " + e.getName() + " Successfully added at Id: " + e.getId());
	return cState;
}

	/**
	 * @param e - takes employee parameters in, allowing for update
	 * @throws SQLException - allows employee to update with no errors
	 */
public void insertEmployeeAtId(Employee e) throws SQLException{
	PreparedStatement s = c.prepareStatement("UPDATE employees SET Name = ?, Gender = ?, DOB = ?, Address = ?,"
			+ "Postcode = ?, NIN = ?, JobTitle = ?, StartDate = ?, Salary = ?, Email = ? "+ "WHERE ID = ?");
	String gen = Character.toString(e.getGender()); //takes the employee gender to change
	s.setString(1, e.getName()); //gets the new name
	s.setString(2, gen); //takes in new gender char
	s.setString(3, e.getDob()); 
	s.setString(4, e.getAddress());  
	s.setString(5, e.getPostcode());
	s.setString(6, e.getNatInscNo());
	s.setString(7, e.getTitle());
	s.setString(8, e.getStartDate());
	s.setString(9, e.getSalary());
	s.setString(10, e.getEmail());
	s.setString(11, e.getId());
	System.out.println("Update on ID: " + e.getId() + " successful"); //prints to system so user knows update success
	s.executeUpdate(); //executes update
}

	/**
	 * @param id - takes id in from GUI, allowing delete to run
	 * @return - boolean, true means success
	 * @throws SQLException - prevents SQL error
	 */
public boolean deleteEmployeeById(String id) throws SQLException {
	 PreparedStatement s = c.prepareStatement("DELETE FROM employees WHERE ID = ? "); //runs q
	 s.setString(1, id); //sets the ? to id from statement
	 Boolean cState = s.execute();
	 System.out.println("Employee at Id: " + id + " successfully deleted" ); //prints to show delete success
	 return cState;
}

	/**
	 * @param photo - parameter for where to display image
	 * @param id - parameter for which employee photo to display
	 */
public void imageDisplay(JLabel photo, String id){
	try{
		ImageIcon format = null; //sets image to null
		PreparedStatement s = c.prepareStatement("SELECT Image FROM employees WHERE ID = ?");
		s.setString(1,id); //sets ? to id
		rs = s.executeQuery(); //execute
		
		
		if(rs.next()){
			byte[]imagedata = rs.getBytes("Image"); //loads in image from database
			if(imagedata == null){ //checks to see if the user has image attached
				System.out.println("No Image In System For Id: " +id );
				photo.setIcon(null); //clears the image if nothing in system
			}
			else{
				format = new ImageIcon(imagedata);  //puts image into format
				if(format.getIconWidth()>100  || format.getIconHeight()>100){ //checks to see if image too big for boundaries
					System.out.println("Image Too Large To Display"); //prints if too large
					
				}
				else{ // if image is not too large 
					photo.setIcon(format);  //loads
				}
			}
		}
		
	}catch(Exception e){
		e.printStackTrace();
	}
	
}

}

		

