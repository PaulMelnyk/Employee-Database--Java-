import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.*;
import java.util.regex.*;
import javax.swing.*;
/**
 * @author PaulMelnyk
 * @serial StudentID 14008771
 * @version Programming Assignment
 */
public class MainForm extends JFrame{
	private static final long serialVersionUID = 1L;
	EmployeeDAO dao = new EmployeeDAO(); //allows linking to EmployeeDAO 
	JMenuBar menuBar; //swaps in menu
	JMenu file; //creates the menu to load in
	JMenuItem insert,delete;
	JLabel label,photo; //labels for GUI
	JTextArea nameBox,salaryBox,ninBox,emailBox,addressBox, postcodeBox,jobBox,searchBox,idBox; //text boxes
	ButtonGroup gen; //button group to store radio buttons
	JRadioButton mb,fb; //buttons for gender
	JButton enter, clear, back, forward, search; //buttons for constraints
	JOptionPane deletePane, searchFail, insFail; //allows pop-ups to display
	private JComboBox<Integer> dobDayList,dobMonthList,dobYearList,startDayList,startMonthList,startYearList;
	ArrayList<Employee> empList; //arrayList to scan through
    public Integer[] days= new Integer[32]; //allows for printing to comboBox dates
    public Integer[] months= new Integer[13]; //allows for printing to comboBox dates
    public Integer[] years= new Integer[118]; //allows for printing to comboBox dates
	Employee current,sEmp,insEmp; //holds info for displayed employee, allows for search, allows for insert 
	int cEmp=0; //allows next/previous buttons to run
	int searchCount=0; //allows pop-up for searchError to run correctly
	int dayInt,monthInt, sDayInt,sMonthInt, insError; //used to add 0s to beginning of strings, insError for pop-up
	int dYearConvert,sYearConvert; //allows year to input
	String regex = "^(.+)@(.+)$"; //string to show format for email
	String regexNin = "^[a-zA-Z]{2}[0-9]{6}[a-zA-Z]{1}$";
	Pattern pattern = Pattern.compile(regex);  
	Pattern ninPattern = Pattern.compile(regexNin);
	Matcher matcher;
	String errorMessage = "Error inserting into Database. Could include: \n ID error \n Start Date & DOB \n Email Incorrect";
	String updateMessage = "Error updating Database. Could include: \n ID error \n Start Date & DOB \n Email Incorrect";
	String dDayIn,dMonthIn,dYearIn, sDayIn,sMonthIn,sYearIn,searchTerm,errorCheck, dateInput; //allows date inputting
	String dash = "-"; //used to put date back into database
	GridBagConstraints c = new GridBagConstraints(); //enables use of constraints
	myEventHandler h = new myEventHandler(); //links buttons to myEventHandler
	
	public MainForm(){
		super("Employee Record System Assignment"); //names the GUI 
		try{
			dao.getConnection(); //connects to database
			empList = dao.selectAllEmployees(); //stores employee info in arrayList
			System.out.println("Connection DB-GUI Established"); //prints in console to show connection
		}catch(Exception e){
			JOptionPane.showMessageDialog(this, "Error: " + e, "Error",JOptionPane.ERROR_MESSAGE); //prints error if not
		}
		setLayout(new GridBagLayout()); //creates GridBagLayout
		setSize(800,400); 
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //closes frame on X
		displayGui(); //allows GUI to show
	}
	
	public void displayGui(){
		c.weighty = 1; // places gaps between gridBoxes on Y axis
		c.weightx = 0.5; // places weighting on x axis to position boxes
			menu();
			label();
			box();
			genButton();
			buttons();
			date();
			displayEmp(cEmp); //shows employee info
		}
	
	public void menu(){
		menuBar = new JMenuBar(); //creates menuBar
		file = new JMenu("File"); //menu item
		insert = new JMenuItem("Insert New Record");
		delete = new JMenuItem("Delete Current Record");
		
		menuBar.add(file); //adds menu item to menuBar
		file.add(insert); //adds insert to the file menu
		file.add(delete); //adds delete to the file menu
		c.anchor = GridBagConstraints.EAST; //puts the button in the east of grid section
		c.gridx = 4; 
		c.gridy = 0; 
		
		add(menuBar,c); //adds menuBar to the form
		
		delete.addActionListener(h); //listens for clicks on delete and will follow its actionListener
		insert.addActionListener(h); //allows GUI to add employee into database
	}
	
	public void label(){
		
	    c.fill = GridBagConstraints.HORIZONTAL; //GridBag = stretch horizontal to show info
	    label = new JLabel(" Enter Employee Information: ");
	    c.gridx = 0;
	    c.gridy =0;
	    add(label,c);
	    
		label = new JLabel(" Employee No: ");
		c.gridy = 11;
		add(label,c);
		
		label = new JLabel(" Enter Name: ");
		c.gridy = 1;
		add(label,c);
		
		label = new JLabel(" Enter Job Title: ");
		c.gridy = 2;
		add(label,c);
		
		label = new JLabel(" Enter Date Of Birth: ");
		c.gridy = 3;
		add(label,c);
		
		label = new JLabel(" Enter Salary: ");
		c.gridy = 4;
		add(label,c);
		
	    label = new JLabel(" Enter National Insurance: ");
		c.gridy = 5;
		add(label,c);
		
		label = new JLabel(" Enter Email: ");
		c.gridy = 6;
		add(label,c);
		
		label = new JLabel(" Enter Start Date: ");
		c.gridy = 7;
		add(label,c);	
		
		label = new JLabel(" Enter Gender: ");
		c.gridy = 8;
		add(label,c);
		
		label = new JLabel (" Enter Address: ");
		c.gridy = 9;
		add(label,c);
		
		label = new JLabel (" Enter Postcode: ");
		c.gridy = 10;
		add(label,c);
		
		c.gridheight = 6;
		c.insets= new Insets(0,20,0,0);
		photo = new JLabel();
		c.gridx = 4;
		c.gridy = 1;

		add(photo,c);
		c.gridheight = 1;
	}

	public void genButton(){
		gen = new ButtonGroup(); 
		mb = new JRadioButton("M");
		fb = new JRadioButton("F");

		gen.add(mb); // adds to group
		gen.add(fb); 
		c.fill = GridBagConstraints.EAST; // sets condition for east in grid section
		c.gridx = 0; //sets location x for grid
		c.gridy = 8; //sets location y for grid
		add(mb,c); //adds conditions
		c.gridx = 1;
		c.anchor = GridBagConstraints.WEST;
		add(fb,c); 
		
	}
		
	public void box(){
		c.weightx = 2;
		nameBox = new JTextArea();
		c.gridx = 1;
		c.gridy = 1;
		add(nameBox,c);
		
		jobBox = new JTextArea();
		c.gridy = 2;
		add(jobBox,c);
		
		salaryBox = new JTextArea();
		c.gridy = 4;
		add(salaryBox,c);
		
		ninBox = new JTextArea();
		c.gridy = 5;
		add(ninBox,c);
		
		emailBox = new JTextArea();
		c.gridy = 6;
		add(emailBox,c);
		
		addressBox = new JTextArea();
		c.gridy = 9;
		add(addressBox,c);
		
		postcodeBox = new JTextArea();
		c.gridy = 10;
		add(postcodeBox,c);
		
		idBox = new JTextArea();
		c.gridy = 11;
		add(idBox,c);

		searchBox = new JTextArea();
		c.gridx = 0;
		c.gridy = 13;
		add(searchBox,c);
		
	}
	
	public void buttons(){
		c.anchor = GridBagConstraints.FIRST_LINE_START; //places buttons on left of grid
		back = new JButton("Back <");
		c.gridx = 0;
		c.gridy = 12;
		add(back,c);
		
		clear = new JButton("Clear");
		c.gridx = 1;
		c.gridy = 12;
		add(clear,c);
		
		c.anchor = GridBagConstraints.FIRST_LINE_END; //places buttons on right of grid
		forward = new JButton("> Forward");
		c.gridx = 0;
		c.gridy = 12;
		add(forward,c);
		
		enter = new JButton("Update Current");
		c.gridx = 1;
		c.gridy = 12;
		add(enter,c);

		c.anchor = GridBagConstraints.FIRST_LINE_START;
		search = new JButton("< Search");
		c.gridx = 1;
		c.gridy = 13;
		add(search,c);
		
		
		back.addActionListener(h); //adds listen to allow back button to run
		forward.addActionListener(h); //adds listen to allow forward button to run
		clear.addActionListener(h); //adds clear to listen
		enter.addActionListener(h); //allows the enter button to listen for updates
		search.addActionListener(h); //makes search listen 
	}
	
	public void date(){
		for(int i=1;i<days.length;i++){
			days[i]=i;
		}
		dobDayList = new JComboBox<>(days); //creates comboBox with days in
		c.gridx = 1;
		c.gridy = 3;
		c.anchor = GridBagConstraints.WEST; //anchors the comboBox to the west of given grid
		add(dobDayList,c);
		c.gridy = 7;
		startDayList = new JComboBox<>(days); //creates comboBox with days in
		add(startDayList,c);
		
		for(int i=1;i<months.length;i++){
			months[i]=i;
		}
		dobMonthList = new JComboBox<>(months); //creates comboBox with months in
		c.insets = new Insets(0,0,0,20); //moves it to the left, closer to day box
		c.gridy = 3;
		c.anchor = GridBagConstraints.CENTER; //puts it in the centre of the section
		add(dobMonthList,c);
		c.gridy = 7;
		startMonthList = new JComboBox<>(months); //creates comboBox with months in
		add(startMonthList,c);
		
		int a=0;
		for(int i=1900;i<=2016;i++){
			a +=1;
			years[a]=i;
		}
		dobYearList = new JComboBox<>(years);
		c.insets = new Insets(0,0,0,4);
     	c.gridy = 3;
		c.anchor = GridBagConstraints.EAST; //anchors the comboBox to the east of the given box
		add(dobYearList,c); //adds day combo list to file
		c.gridy = 7;
		startYearList = new JComboBox<>(years);
		add(startYearList,c);
		
		
	}
	
	/**
	 * @param cEmp - takes in arrayList location, allows display to work
	 */
	public void displayEmp(int cEmp){ //takes in current employee value
		current = empList.get(cEmp); //sets current to get from all employee @ specific location
		nameBox.setText(current.getName()); //sets name box to the employee name
		emailBox.setText(current.getEmail());
		jobBox.setText(current.getTitle());
		ninBox.setText(current.getNatInscNo());
		salaryBox.setText(current.getSalary());
		addressBox.setText(current.getAddress());
		postcodeBox.setText(current.getPostcode());
		idBox.setText(current.getId());
		char g = current.getGender(); //stores gender char as g
		if(g=='m' || g =='M'){ 
			mb.setSelected(true); //sets male radio button true if char = m/M
		}
		else{
			fb.setSelected(true); //sets female radio button true if char = f/F
		}

		String[] dob = current.getDob().split("-"); //splits date of birth into array whenever there is a -
		int day = Integer.parseInt(dob[0]);//day=1st in date of birth
		int month = Integer.parseInt(dob[1]); //month=2nd in date of birth
		int year = Integer.parseInt(dob[2]); //year=3rd in date of birth
		dobDayList.setSelectedItem(day); //sets selected
		dobMonthList.setSelectedItem(month);
 		dobYearList.setSelectedItem(year);
		
		String[] start = current.getStartDate().split("-");
		int sDay = Integer.parseInt(start[0]);
		int sMonth = Integer.parseInt(start[1]);
		int sYear = Integer.parseInt(start[2]);
		startDayList.setSelectedItem(sDay);
		startMonthList.setSelectedItem(sMonth);
 		startYearList.setSelectedItem(sYear);
		
 		dao.imageDisplay(photo,empList.get(cEmp).getId());
	}

	public void clearEmp(){
		nameBox.setText(null);
		emailBox.setText(null);
		jobBox.setText(null);
		ninBox.setText(null);
		salaryBox.setText(null);
		idBox.setText(null);
		addressBox.setText(null);
		postcodeBox.setText(null);
		dobDayList.setSelectedIndex(0);
		dobMonthList.setSelectedIndex(0);
		dobYearList.setSelectedIndex(0);
		startDayList.setSelectedIndex(0);
		startMonthList.setSelectedIndex(0);
		startYearList.setSelectedIndex(0);
		photo.setIcon(null);
	}
	
	private class myEventHandler implements ActionListener{ //listens for clicks
		public void actionPerformed(ActionEvent e){
			if(e.getSource()==forward){ //if forward pressed
				if(cEmp!=empList.size()-1){ //checks to see if arrayList has value to move to
					cEmp += 1; //adds 1 to current employee
					displayEmp(cEmp); //runs display info with new value
				}
			}
			if(e.getSource()==back){	//if back pressed
				if(cEmp!=0){ //sees if arrayList not at start
					cEmp -= 1; //removes 1 from current employee
					displayEmp(cEmp); //runs display with new value
				}
			}
			if(e.getSource()==clear){
					clearEmp();
			}
			if(e.getSource()==enter){
				Employee update = new Employee(); //creates employee to update current
				insError = 0; //sets errors to 0
				update.setId(empList.get(cEmp).getId()); //sets the id of the updating to the same as that of current
				 insertable(update); //checks to see if employee has errors
					check(update);	//checks the int's of the employee (e.g.- negative or contains letters)
					if(insError==0){ //will input if no errors return
				try {
					dao.insertEmployeeAtId(update); 
					empList = dao.selectAllEmployees(); //refresh list so update takes effect in GUI
					displayEmp(cEmp); //displays the employee with updates
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
					else{
						insFail = new JOptionPane(); //creates option panel
						JOptionPane.showMessageDialog(getContentPane(), updateMessage ,"Update Error",JOptionPane.PLAIN_MESSAGE);
					}
			}
			
			if(e.getSource()==search){
				searchCount = 0; //so error message will only appear once
				searchTerm = searchBox.getText();
				for(int i=0;i<empList.size()-1;i++){
					errorCheck = empList.get(i).getName(); //holds name for current emp
					if(errorCheck.toLowerCase().contains(searchTerm.toLowerCase())){ //converts strings to lower and will see if match
						searchCount+=1; //allows search to proceed
					}
				}
				if(searchCount==0){ //no one matches the search
					searchFail = new JOptionPane(); //creates option panel
					JOptionPane.showMessageDialog(getContentPane(), "Name not in the Database","Search Error",JOptionPane.PLAIN_MESSAGE);
					searchBox.setText(null); 

				}
				else{ //search function in here
					try {
						sEmp = dao.selectEmployeeByName(searchTerm); //sets sEmp to the searchTerm 
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} //selects the employee if matches
					nameBox.setText(sEmp.getName());
					emailBox.setText(sEmp.getEmail());
					jobBox.setText(sEmp.getTitle());
					ninBox.setText(sEmp.getNatInscNo());
					salaryBox.setText(sEmp.getSalary());
					addressBox.setText(sEmp.getAddress());
					postcodeBox.setText(sEmp.getPostcode());
					idBox.setText(sEmp.getId());
					char g = sEmp.getGender();
					if(g=='m' || g =='M'){ 
						mb.setSelected(true);
					}
					else{
						fb.setSelected(true);
					}
					String[] dob = sEmp.getDob().split("-"); //splits date of birth into array whenever there is a -
					int dDay = Integer.parseInt(dob[0]);//day=1st in date of birth
					int dMonth = Integer.parseInt(dob[1]); //month=2nd in date of birth
					int dYear = Integer.parseInt(dob[2]); //year=3rd in date of birth
					dobDayList.setSelectedItem(dDay); //sets selected
					dobMonthList.setSelectedItem(dMonth);
			 		dobYearList.setSelectedItem(dYear);
					
					String[] start = sEmp.getStartDate().split("-");
					int sDay = Integer.parseInt(start[0]);
					int sMonth = Integer.parseInt(start[1]);
					int sYear = Integer.parseInt(start[2]);
					startDayList.setSelectedItem(sDay);
					startMonthList.setSelectedItem(sMonth);
			 		startYearList.setSelectedItem(sYear);
					for(int j=0;j<empList.size()-1;j++){
						Employee move = empList.get(j); //as loop moves takes on new employee
						if(move.getName().equals(sEmp.getName())){ //compares names to see if same
							cEmp = j; //if names same cEmp moves, so forward and back go from current spot
							}
						}
					dao.imageDisplay(photo,empList.get(cEmp).getId());
				}
			}
			if(e.getSource()==insert){
				insEmp = new Employee();
				insError = 0; //used to check for errors within person inputs
				int errorCount = 0; //prevents same ID being input
				int popupCount = 1; //so pop-up can only appear once 
				for(int i=0;i<empList.size();i++){
					 if(idBox.getText().equals(empList.get(i).getId())){ //loops through arrayList to see if id matches one in system
						 errorCount += 1;
					 }
				}
					 if(errorCount !=0 && popupCount==1){
						 insFail = new JOptionPane(); //creates option panel
						 JOptionPane.showMessageDialog(getContentPane(), "ID already present in system" ,"Insert Error",JOptionPane.PLAIN_MESSAGE);
						 idBox.setText(null);
						 popupCount = 0;
					 }
					 if (errorCount==0 && popupCount ==1) {	
						 insEmp.setId(idBox.getText());
						 insertable(insEmp);
							check(insEmp);	
					  
				
					if(insError==0){
						try {
							dao.insertEmployee(insEmp);
							empList = dao.selectAllEmployees();
							displayEmp(cEmp);
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
					 
					else{
						insFail = new JOptionPane(); //creates option panel
						JOptionPane.showMessageDialog(getContentPane(), errorMessage ,"Insert Error",JOptionPane.PLAIN_MESSAGE);
					}
					 }
		}
			
			if(e.getSource()==delete){
				deletePane = new JOptionPane(); //pop up generator
				String[] options = new String[2]; //creates an array string
				options[0] = new String ("Agree"); 
				options[1] = new String ("Disagree");
				int deleteOpt = JOptionPane.showOptionDialog(getContentPane(),"You are about to delete a record, \n Are you sure you want to do this?", "Are You Sure?", 0, JOptionPane.INFORMATION_MESSAGE, null, options, null);
				if(deleteOpt == 0){ //Agree Statement
						try {
							dao.deleteEmployeeById(idBox.getText()); //deletes current employee showing
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						if(cEmp==0){ //if employee is first in array
							cEmp+=1; //moves forward
						}
						else{
							cEmp-=1; //if employee isn't the first in array
						}
						displayEmp(cEmp); //prints the new employee
						try {
							empList = dao.selectAllEmployees(); //updates the list so deleted doesn't show
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				}
			
		}
	}
	
	/**
	 * @param testable - takes in value, true = all numbers, else false
	 * @return - true/false, prevents incorrect types
	 */
	public static boolean intTest(String testable){

        for(int j = 0; j < testable.length(); j++){
            if(!Character.isDigit(testable.charAt(j))){
                 return false; //if not all letters will error
            }
        }
        return true;
	}

	/**
	 * @param testable - employee to check for errors
	 * @return - insError, if !=0 cannot proceed due to errors
	 */
	public int check(Employee testable){
		if(intTest(testable.getId())==false){ //checks to see if ID entered is integer
			insError +=1; //adds to value, prevents input of incorrect
		}
		if(testable.getId().trim().length()==0){ //checks the id, trims blank space out
			insError+=1;
		}
		if(testable.getName().matches(".*\\d.*")){ //checks to see if numbers in name
			insError +=1;
		}
		matcher = ninPattern.matcher(testable.getNatInscNo()); //checks the matcher that national insurance matches format
		if(matcher.matches()!=true){
			insError+=1;
		}
		if(intTest(testable.getSalary())==false){ //tests for salary being numbers
			insError +=1;
		}
		Matcher matcher = pattern.matcher(testable.getEmail()); //saves the email pattern into matcher
		if(matcher.matches()!=true){ //sees if valid email
			insError +=1;
		}
		return insError; //if error wont allow run
		
	}
	
	/**
	 * @param insEmp - takes in employee, allowing for insert to database
	 */
	public void insertable(Employee insEmp){
		dayInt = 0; //resets value so user can do another search
		monthInt = 0; //resets value so user can do another search
		sDayInt = 0;
		sMonthInt = 0;
			insEmp.setName(nameBox.getText());
			if(mb.isSelected()){
				insEmp.setGender('M');
			}
			else{
				insEmp.setGender('F');
			}
			if(dobDayList.getSelectedIndex()<10){
				dayInt = 1; //used to add 0 to beginning of days
			}
			if(dobMonthList.getSelectedIndex()<10){
				monthInt = 1; //used to add 0 to beginning of months
			}
			if(startDayList.getSelectedIndex()<10){
				sDayInt = 1; //used to add 0 to beginning of days
			}
			if(startMonthList.getSelectedIndex()<10){
				sMonthInt = 1; //used to add 0 to beginning of months
			}
			dDayIn = Integer.toString(dobDayList.getSelectedIndex());
			dMonthIn = Integer.toString(dobMonthList.getSelectedIndex());
			dYearConvert= dobYearList.getSelectedIndex()+1899;
			dYearIn = Integer.toString(dYearConvert);
			if(dayInt==1){ //if day is before 10
				dDayIn = "0"+dDayIn; //adds a 0 to the beginning
			}
			if(monthInt==1){
				dMonthIn = "0"+dMonthIn;
			}
			dateInput = dDayIn+dash+dMonthIn+dash+dYearIn;
			insEmp.setDob(dateInput);
			insEmp.setAddress(addressBox.getText());
			insEmp.setPostcode(postcodeBox.getText());
			insEmp.setNatInscNo(ninBox.getText());
			insEmp.setTitle(jobBox.getText());
			sDayIn = Integer.toString(startDayList.getSelectedIndex());
			sMonthIn = Integer.toString(startMonthList.getSelectedIndex());
			sYearConvert = startYearList.getSelectedIndex()+1899;
			sYearIn = Integer.toString(sYearConvert);
			if(sDayInt==1){
				sDayIn = "0"+sDayIn;
			}
			if(sMonthInt==1){
				sMonthIn = "0"+sMonthIn;
			}
			dateInput = sDayIn+dash+sMonthIn+dash+sYearIn;
			insEmp.setStartDate(dateInput);
			insEmp.setSalary(salaryBox.getText());
			insEmp.setEmail(emailBox.getText());
			
			if(dYearConvert>=sYearConvert){
				insError+=1;
			}
	}
}

