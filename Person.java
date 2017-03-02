/**
 * @author PaulMelnyk
 * @serial StudentID 14008771
 * @version Programming Assignment
 */
public class Person {
	private String name; 
	private char gender; 
	private String natInscNo;  
	private String dob; 
	private String address; 
	private String postcode; 

	public Person(){ 
		
	}
	
	/**
	 * @param name - sets the name of person
	 * @param gen - sets gender to char
	 * @param nin - sets national insurance no.
	 * @param dob - sets date of birth for person
	 * @param add - sets the address 
	 * @param post - sets persons post code
	 */
	public Person(String name, char gen, String nin, String dob, String add, String post){
		this.name = name;
		gen = gender;
		nin = natInscNo;
		this.dob = dob;
		add = address;
		post = postcode;
	}
	
	/**
	 * @param newName - sets name to string newName
	 */
	public void setName(String newName){
		this.name = newName; //sets name to value in brackets
	}
	
	/**
	 * @return - returns name
	 */
	public String getName(){
		return this.name;  //returns set name
	}
	
	/**
	 * @param gender - sets persons gender
	 */
	public void setGender(char gender){
		this.gender = gender; //sets gender to value in brackets
	}
	
	/**
	 * @return - returns gender
	 */
	public char getGender(){
		return this.gender; //returns set gender
	}
	
	/**
	 * @param newNat - sets national insurance
	 */
	public void setNatInscNo(String newNat){
		this.natInscNo = newNat; //sets national insurance to value in brackets
	}
	
	/**
	 * @return - returns national insurance
	 */
	public String getNatInscNo(){
		return this.natInscNo; //returns set national insurance number
	}
	
	/**
	 * @param dob - sets date of birth
	 */
	public void setDob(String dob){
		this.dob = dob; //sets date of birth to value in brackets
	}
	
	/**
	 * @return - returns date of birth  
	 */
	public String getDob(){
		return this.dob; //returns set date of birth
	}
	
	/**
	 * @param newAdd - sets address
	 */
	public void setAddress(String newAdd){
		this.address = newAdd; //sets address to value in brackets
	}
	
	/**
	 * @return - returns value of address
	 */
	public String getAddress(){
		return this.address; //returns set address
	}
	
	/**
	 * @param newPost - sets post code
	 */
	public void setPostcode(String newPost){
		this.postcode = newPost; //sets post code to value in brackets
	}
	
	/**
	 * @return - returns post code
	 */
	public String getPostcode(){
		return this.postcode; //returns set post code
	}
	
	/**
	 * @return - returns the values to a string, for a print to console
	 */
	public String toString(){
		return "Name :"+getName()+ " Gender: " +getGender() + " NatInscNo: " + getNatInscNo() + " Date Of Birth: " + getDob() +
				" Address: " + getAddress() + " Postcode: " + getPostcode(); //prints value for gets
	}
}
