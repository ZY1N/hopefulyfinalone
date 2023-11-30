package roles;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import courses.Course;

/**
 * Represents user of the Student Management System of type Admin
 */
public class Admin extends User {

	//Instance variables
	/**
	 * Arraylist of users to pass to Admin object
	 */
	static ArrayList<User> users;
	
	/**
	 * Calls constructor for superclass user and takes additional parameter "type" and ArrayList Users
	 * @param userName for User
	 * @param password for User
	 * @param name for User
	 * @param id for User
	 * @param courses ArrayList of all courses available
	 * @param usersLst ArrayList of all users in system
	 */
	public Admin(String userName, String password, String name, String id, ArrayList<Course> courses, ArrayList<User> usersLst) {
		
		//Call constructor in superclass User
		super(userName, password, name, id, courses);
		
		//Create arraylist users for modification of passed usersLst by reference
		Admin.users = usersLst;
		
		
		//Set variable type to admin
		this.type = "admin";
	}

	/**
	 * Prints menu of action items for given subclass of User
	 * Must implement to override abstract method
	 */
	@Override
	public void printActionMenu() {
		
		//Print Action menu message
		System.out.println("--------------------------------");
		System.out.println("Welcome, " + this.getName());
		System.out.println("--------------------------------");
		System.out.println("1 -- View all courses");
		System.out.println("2 -- Add new courses");
		System.out.println("3 -- Delete courses");
		System.out.println("4 -- Add new professor");
		System.out.println("5 -- Delete professor");
		System.out.println("6 -- Add new student");
		System.out.println("7 -- Delete student");
		System.out.println("8 -- Return to previous menu");	
		System.out.println("");
		System.out.println("Please enter your option, eg. '1'.");
		System.out.println("");


	}

	/**
	 * Prompts for user input and performs action based on given list of actions for subclass of User\
	 * @param scan Scanner for user input
	 */
	@Override
	public void performAction(Scanner scan) {
		
		//Create array of valid user actions
		String[] loginOptions = {"1", "2", "3", "4", "5", "6", "7", "8"};
				
		//Create boolean for returning to previous menu
		boolean goBack = false;
		
		//While user hasn't selected to back to previous menu
		while (goBack == false){
			
			//Print menu for list of actions
			this.printActionMenu();

			//Prompt for user input
			String userInput = scan.nextLine();
		
			//While the user has not provided valid input (check by calling checkValidInput() method)
			while (this.checkValidInput(loginOptions, userInput) == false) {
				System.out.println("Please enter a valid response!");
				userInput = scan.nextLine();
			}
		
			//Call respective class method based on action selected by user
			//End loop without calling method if 8 is selected
			if (userInput.equals("1")) {
				this.viewAllCourses();
			
			} else if(userInput.equals("2")) {
				this.addNewCourse(scan);
			
			} else if(userInput.equals("3")) {
				this.deleteCourse(scan);
			
			} else if(userInput.equals("4")) {
				this.addProfessor(scan);
			
			} else if(userInput.equals("5")) {
				this.deleteProfessor(scan);
			
			} else if(userInput.equals("6")) {
				this.addStudent(scan);
			
			} else if(userInput.equals("7")) {
				this.deleteStudent(scan);
			
			} else if(userInput.equals("8")) {
				goBack = true;
			}
		}
	}
	
	/**
	 * Displays a list of all courses in management system
	 */
	public void viewAllCourses() {		
		
		//Iterate over ArrayList of courses and print
		for(Course c : courses) {
			System.out.println(c);
		}		
	}
	
	/**
	 * Prompts user for required information to create new course.
	 * Confirms course does not already exist, otherwise asks user to start over
	 * Confirms professor Object exists for new course, otherwises calls addProfessor()
	 * Confirms no scheduling conflict exists for professor, otherwise asks user to start over
	 * @param scan Scanner for user input
	 */
	public void addNewCourse(Scanner scan) {
		
		//Create boolean to check if user wants to return to previous screen
		boolean goBack = false;
		
		//Create booleans validCourseID & validProf to see if user inputs are valid for creation of course object
		boolean validCourseID = true;
		boolean validProf = false;
		
		//Create temp variable to store existing professor object, if user adds course for existing professor
		User existingProf = null;
		
		//Create temp variable to store new course object to add
		Course tempCourse = null;
		
		//While the user has not chosen to return to the previous screen
		while (goBack == false) {
			
			//Prompt for user input of courseCode
			System.out.println("Please enter the new Course ID, or type 'q' to end.");
			String courseCode = scan.nextLine();
			
			//If user types q, break loop
			if(courseCode.equals("q")) {
				goBack = true;
				break;
			}
			
			//Iterate over Arraylist of existing course objects
			for (Course c: courses) {
				
				//If the given course name matches with a course from courses ArrayList
				if(courseCode.toLowerCase().equals(c.getCourseCode().toLowerCase())) {
					
					//Set valid ID to true & break for loop
					validCourseID = false;
					break;
				}
			} //If new courseCode was determined to be valid, prompt for remaining inputs
			if (validCourseID == true) {
				
				//Prompt for course Name
				System.out.println("Please enter the new course name, or type 'q' to end.");
				String courseName = scan.nextLine();
				
				//If user types q, break loop
				if(courseName.equals("q")) {
					goBack = true;
					break;
				}
				
				//Prompt for course Instructor
				System.out.println("Please enter the new course Instructor, or type 'q' to end.");
				String courseInstructor = scan.nextLine();	
				
				//If user types q, break loop
				if(courseInstructor.equals("q")) {
					goBack = true;
					break;
				}
				
				//Prompt for course days
				System.out.println("Please enter the new course days, or type 'q' to end.");
				String days = scan.nextLine();	
				
				//If user types q, break loop
				if(days.equals("q")) {
					goBack = true;
					break;
				}
				
				//Prompt for course start time
				System.out.println("Please enter the new course start time, or type 'q' to end.");
				String timeStart = scan.nextLine();	
				
				//If user types q, break loop
				if(timeStart.equals("q")) {
					goBack = true;
					break;
				}
				
				//Prompt for course end time
				System.out.println("Please enter the new course end time, or type 'q' to end.");
				String timeEnd = scan.nextLine();	
				
				//If user types q, break loop
				if(timeEnd.equals("q")) {
					goBack = true;
					break;
				}
				
				//Prompt for capacity
				System.out.println("Please enter the new course capitcy, or type 'q' to end.");
				String capacity = scan.nextLine();	
				
				//If user types q, break loop
				if(capacity.equals("q")) {
					goBack = true;
					break;
				}
				
				//Confirm courseInstructor is created as User, if so, set validProf to true	
				//Iterate over arraylist of existing users
				for (User u: users) {
					//If user is type professor
					if (u.getType().equals("professor")) {
						
						//Check if name of Professor equals userInput name, if so set validProf to true
						if(u.getName().equals(courseInstructor)) {
							validProf = true;
							
							//Set variable existingProf to u
							existingProf = u;
						}
					}
				}
				
				//Create temporary Course object for new course
				tempCourse = new Course(courseCode, courseName, courseInstructor, days, timeStart, timeEnd, capacity);
				
				//If course instructor does not exist, call add Professor
				if(validProf == false) {
					System.out.println("The professor isn't in the system, please add this professor first!\n");
					
					//Calls addProfessor method to add new professor, if method returns false, notes professor & course were not added & beaks loop
					//AddProfessor returns false if user selects "quit" option
					if(this.addProfessor(scan, courseInstructor) == false) {
						System.out.println("Course & professor not added, please start over!");
						
					}
				} //Otherwise check if professor has course conflict by calling checkSchedulingConflict() for trempCourse using existingProf
				else {
					
					//If no scheduling conflict, add tempCourse to courses ArrayList
					if(!tempCourse.checkSchedulingConflict(existingProf)) {
						System.out.println("Course " + tempCourse.getCourseName() + " has been added.\n");
						courses.add(tempCourse);
						
					} //Otherwise notify user course was not added
					else {	
						System.out.println("Course not added due to scheduling conflict, please start over!");
					}
				}
				
			} //If ID provided is invalid, notify user
			else {
				System.out.println("Provided course ID is not valid - please start over!");
			}
		}
	}
	
	/**
	 * Removes course from courses ArrayList
	 */
	public void deleteCourse(Scanner scan) {
		
		//Create boolean to check if user wants to return to previous screen
		boolean goBack = false;
		
		//Create boolean validCourseID to see if user input is valid for creation of course object
		boolean validCourseID = true;
		
		//While the user has not chosen to return to the previous screen
		while (goBack == false) {
			
			//Prompt for user input of courseCode
			System.out.println("Please enter the Course ID to delete, or type 'q' to end.");
			String courseCode = scan.nextLine();
			
			//If user types q, break loop
			if(courseCode.equals("q")) {
				goBack = true;
				break;
			}
			
			System.out.println(courseCode);
			
			//Iterate over Arraylist of existing course objects
			for (Course c: courses) {
				
				//If the given course name matches with a course from courses ArrayList
				if(courseCode.toLowerCase().equals(c.getCourseCode().toLowerCase())) {
					
					//Set valid ID to true, remove course object from Courses ArrayList & break for loop
					validCourseID = true;
					System.out.println( c.getCourseCode() + "|" + c.getCourseName() + " has been removed.\n");
					courses.remove(c);
					break;				
				}
			}
			//If ID provided is invalid, notify user
			if (validCourseID == false) {
				System.out.println("Provided course ID is not valid - please start over!");
				
			}
		} 
	}
	
	/**
	 * Prompts for user input and adds new professor Object to professor array list
	 * @param scan Scanner for user input
	 */
	public void addProfessor(Scanner scan) {
		
		//Prompt for new professor name
		
		System.out.println("Please enter the new professor name , or type 'q' to end.");
		String profName = scan.nextLine();
		
		//If user types q, end method
		if(profName.equals("q")) {
			return;
		}
		
		//Call overloaded prof method to populate remaining fields and create object
		this.addProfessor(scan, profName);
	}
	
	/**
	 * Prompts for user input and adds new professor Object to professor array list.
	 * Returns false if user elects to quit method by inputting 'q'
	 * @param scan for taking user input
	 * @param profName to add
	 * @return False if user elects to quit method by inputting 'q'
	 */
	public boolean addProfessor (Scanner scan, String profName) {
		
		//Create boolean validID and validUsername to confirm usernames are valid
		boolean validID = false;
		boolean validUsername = false;
		String password = "";
		
		//Create temp variables for creating new proff object
		String profId = null;
		String profUsername = null;
		
		//Prompt for new professor ID while valid id has not been provided
		while (validID == false) {
			
			//Prompt for new professor ID
			System.out.println("Please enter the new professor ID , or type 'q' to end.");
			profId = scan.nextLine();
			
			//Iniittally set validID to true
			validID = true;
			
			//If user types q, end method & return false
			if(profId.equals("q")) {
				System.out.println("Cancelled creation\n");
				return false;
			}
			
			//Iterate over list of users and confirm user ID does not exist for any user of type "professor"
			for (User u: users) {
				
				//If ID is already in use, change validID to false to continue while loop
				if (u.getType().equals("professor") && u.getId().equals(profId)) {
					
					validID = false;
					System.out.println("This ID already exists, please try again!");
				}
			}
		}
		
		//Prompt for new professor username while valid id has not been provided
		while (validUsername == false) {
			
			//Prompt for new professor username
			System.out.println("Please enter the new professor username , or type 'q' to end.");
			profUsername = scan.nextLine();
			
			//Iniittally set validUsername to true
			validUsername = true;
			
			//If user types q, end method & return false
			if(profUsername.equals("q")) {
				System.out.println("Cancelled creation\n");
				return false;
			}
			
			//Iterate over list of users and confirm username does not exist for any user of type "professor"
			for (User u: users) {
				
				//If ussername is already in use, change validID to false to continue while loop
				if (u.getType().equals("professor") && u.getUserName().equals(profUsername)) {
					
					validID = false;
					System.out.println("This username already exists, please try again!");
				}
			}
		}
		
		//Prompt for new student password
		System.out.println("Please enter the new student password or press 'q' to quit.");
		password = scan.nextLine();
						
		//If user types q, end method
		if(password.equals("q")) {
			System.out.println("Cancelled creation\n");
			return false;
		}
		
		//Add new professor object to users ArrayList & return true
		System.out.println(profUsername + " has been created.");
		users.add(new Professor(profUsername, password, profName, profId, courses));
		return true;
	}
	
	/**
	 * Remove professor from user ArrayList
	 */
	public void deleteProfessor(Scanner scan) {
		
		//Create boolean to check if user wants to return to previous screen
		boolean goBack = false;
		
		//Create boolean validUsername to see if user input represents valid Professor object
		boolean validUsername = true;
		
		//While the user has not chosen to return to the previous screen
		while (goBack == false) {
			
			//Prompt for user input of Professor username
			System.out.println("Please enter the username of the professor to delete, or type 'q' to end.");
			String profUsername = scan.nextLine();
			
			//If user types q, break loop
			if(profUsername.equals("q")) {
				goBack = true;
				break;
			}
			
			//Iterate over Arraylist of existing user objects
			for (User u: users) {
				
				//Check if the user is type professor
				if(u.getType().equals("professor")) {
					
//					System.out.println(u.getUserName());
					//If the given username matches with a professor from the users ArrayList
					if(profUsername.toLowerCase().equals(u.getUserName())) {
						
						//Set validUsername to true, remove object from Users ArrayList & break for loop
						validUsername = true;
						System.out.println(u.getName() + " has been removed.\n");
						users.remove(u);
						break;
					}
				}
			}
			//If username provided is invalid, notify user
			if (validUsername == false) {
				System.out.println("Provided username is not valid - please start over!");
				
			}
		} 
	}
	
	/**
	 * Prompts for user input and adds new professor Object to professor array list.
	 * @param scan Scanner for user input
	 */
	public void addStudent(Scanner scan) {
		
		//Create boolean validID and validUsername to confirm usernames are valid
		boolean validID = false;
		boolean validUsername = false;
		boolean validCourse = false;
		
		//Create temp variables for creating new Student object
		String stuId = null;
		String stuUsername = null;
		String stuName = null;
		String password = "";
		ArrayList<Course> stuCourses = new ArrayList<Course>();
		HashMap<String, String> stuGrades = new HashMap<>();
		
		
		//Prompt for new student name
		System.out.println("Please enter the new student name , or type 'q' to end.");
		stuName = scan.nextLine();
		
		//If user types q, return to end method
		if(stuName.equals("q")) {
			System.out.println("Cancelled creation\n");
			return;
		}
		
		//Prompt for new student ID while valid id has not been provided
		while (validID == false) {
			
			//Prompt for new student ID
			System.out.println("Please enter the new student ID , or type 'q' to end.");
			stuId = scan.nextLine();
			
			//Iniittally set validID to true
			validID = true;
			
			//If user types q, end method & return to end method
			if(stuId.equals("q")) {
				System.out.println("Cancelled creation\n");
				return;
			}
			
			//Iterate over list of users and confirm user ID does not exist for any user of type "student"
			for (User u: users) {
				
				//If ID is already in use, change validID to false to continue while loop
				if (u.getType().equals("student") && u.getId().equals(stuId)) {
					
					validID = false;
					System.out.println("This ID already exists, please try again!");
				}
			}
		}
		
		//Prompt for new student username while valid id has not been provided
		while (validUsername == false) {
			
			//Prompt for new student username
			System.out.println("Please enter the new student username , or type 'q' to end.");
			stuUsername = scan.nextLine();
			
			//Iniittally set validUsername to true
			validUsername = true;
			
			//If user types q, end method
			if(stuUsername.equals("q")) {
				System.out.println("Cancelled creation\n");
				return;
			}
			
			//Iterate over list of users and confirm username does not exist for any user of type "professor"
			for (User u: users) {
				
				//If ussername is already in use, change validID to false to continue while loop
				if (u.getType().equals("student") && u.getUserName().equals(stuUsername)) {
					
					validID = false;
					System.out.println("This username already exists, please try again!");
				}
			}
		}
		

			
		//Prompt for new student password
		System.out.println("Please enter the new student password or press 'q' to quit.");
		password = scan.nextLine();
						
		//If user types q, end method
		if(password.equals("q")) {
			return;
		}
		
		//Prompt for new student courses and grades, restart loop if invalid courseID provided
		while (validCourse == false) {
			
			//Prompt for new student course
			System.out.println("Please enter ID of a course which this student already too, one at a time.");
			System.out.println("Type 'q' to quit, type 'n' to stop adding");
			String courseID = scan.nextLine();
			
			//If user types q, end method
			if(courseID.equals("q")) {
				System.out.println("Cancelled creation\n");
				return;
			}
			
			//If user types n, break the while loop
			//Note: the user can choose to add no courses or grades
			if(courseID.equals("n")) {
				
				break;
			}
			
			//Iterate over ArrayList of all courses to check if student provided a valid course
			for (Course c: courses) {
				
				//If the given course name matches with a course from courses ArrayList
				if(courseID.toLowerCase().equals(c.getCourseCode().toLowerCase())) {
					
					//Set valid ID to true & add course object to student courses ArrayList
					validCourse = true;
					stuCourses.add(c);
					
				}
			}
			
			//If course ID is valid, prompt for grade to add
			if (validCourse == true) {
				System.out.println("Please enter your grade for this course, e.g. 'A'.");
				System.out.println("Type 'q' to quit, type 'n' to stop adding");
				String grade = scan.nextLine();
				
				//Add courseID and grade to stuGrades HashMap as key & value, respectively
				stuGrades.put(courseID, grade);
			}
			validCourse = false;
		}
		//Add new student object to users ArrayList 
		users.add(new Student(stuUsername, password, stuName, stuId, stuCourses, stuGrades, courses));
	}
		
		
	
	/**
	 * Remove student from arrayList
	 */
	public void deleteStudent(Scanner scan) {
		
		//Create boolean to check if user wants to return to previous screen
		boolean goBack = false;
		
		//Create boolean validUsername to see if user input represents valid Student object
		boolean validUsername = true;
		
		//While the user has not chosen to return to the previous screen
		while (goBack == false) {
			
			//Prompt for user input of student username
			System.out.println("Please enter the username of the student to delete, or type 'q' to end.");
			String stuUsername = scan.nextLine();
			
			//If user types q, break loop
			if(stuUsername.equals("q")) {
				goBack = true;
				break;
			}
			
			//Iterate over Arraylist of existing user objects
			for (User u: users) {
				
				//Check if the user is type student
				if(u.getType().equals("student")) {
					
					//If the given username matches with a professor from the users ArrayList
					if(stuUsername.toLowerCase().equals(u.getUserName().toLowerCase())) {
						
						System.out.println(u.getUserName() + " has been removed");
						//Set validUsername to true, remove object from Users ArrayList & break for loop
						validUsername = true;
						users.remove(u);
						break;
					}
				}
			}
			//If username provided is invalid, notify user
			if (validUsername == false) {
				System.out.println("Provided username is not valid - please start over!");
			}
		} 
	}
}
