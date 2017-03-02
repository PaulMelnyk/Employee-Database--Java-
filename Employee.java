/**
 * @author PaulMelnyk
 * @serial StudentID 14008771
 * @version Programming Assignment
 */
public class Employee extends Person{
	private String id; //string id creates
	private String salary; //string salary creates
	private String startDate; //string start date creates
	private String title; //string title creates
	private String email; //string email creates
	
	public Employee(){
		
	}
	
	/**
	 * 
	 * @param name - takes parameter name from person
	 * @param gen - takes parameter gender from person
	 * @param nin - takes parameter national insurance from person
	 * @param dob - takes parameter date of birth from person
	 * @param add - takes parameter address from person
	 * @param post - takes parameter post code from person
	 * @param id - input id to employee
	 * @param sal - input salary to employee
	 * @param start - input start date to employee
	 * @param title -input job title to employee
	 * @param email - input email to employee
	 */
	public Employee(String name, char gen, String nin, String dob, String add, String post,String id, String sal, String start,String title, String email){
		super(name,gen,nin,dob,add,post); //takes values from super
		this.id = id;
		sal = salary;
		start = startDate;
		this.title = title;
		this.email = email;
	}
	
	/**
	 * @param email - sets email
	 */
	public void setEmail(String email){
		this.email = email; //sets email to value in brackets
	}
	
	/**
	 * @return - returns value for email
	 */
	public String getEmail(){
		return this.email; //returns set email
	}
	
	/**
	 * @param title - sets job title for employee
	 */
	public void setTitle(String title){
		this.title = title; //sets title to value in brackets
	}
	
	/**
	 * @return - returns job title of employee
	 */
	public String getTitle(){
		return this.title; //returns set title
	}
	
	/**
	 * @param id - allows set of employee id
	 */
	public void setId(String id){
		this.id = id; //sets id to value in brackets
	}
	
	/**
	 * @return - brings back the employees id no.
	 */
	public String getId(){
		return this.id; //returns set id
	}
	
	/**
	 * @param salary - allows set of salary
	 */
	public void setSalary(String salary){
		this.salary = salary; //sets salary to value in brackets
	}
	
	/**
	 * @return- allows salary to be returned
	 */
	public String getSalary(){
		return this.salary; //returns set salary
	}
	
	/**
	 * @param start - allows start date to be set 
	 */
	public void setStartDate(String start){
		this.startDate = start; //sets start date to value in brackets
	}
	
	/**
	 * @return - brings back employee start date value
	 */
	public String getStartDate(){
		return this.startDate; //returns set start date
	}

	/**
	 * @return - allows print of data to console line
	 */
	public String toString(){
		return super.toString() + " Email: " + getEmail() + " Title: " + getTitle() + " Id: " + getId() + " Salary: " + getSalary() +
				" Start Date: " + getStartDate() + "\n"; //prints super toString + value for gets
	}
}
