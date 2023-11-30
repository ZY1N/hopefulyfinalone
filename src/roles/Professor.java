package roles;

import java.util.ArrayList;
import java.util.Scanner;

import courses.*;

/**
 * Represents user of the Student Management System of type Professor
 */
public class Professor extends User {
	
	//Professor specific instance variables
	
	/**
	 * Array list of courses specific to professor
	 */
	ArrayList<Course> profCourses = new ArrayList<Course>();
	
	ArrayList<Student> profStudents = new ArrayList<Student>();
	
	/**
	 * Calls constructor for superclass user and takes additional parameter "type"
	 * Sets type to "professor" and populates arraylist of profCourses by iterating over given list of courses
	 * @param userName for User
	 * @param password for User
	 * @param name for User
	 * @param id for User
	 * @param courses ArrayList of all courses available in Student Management System
	 */
	public Professor(String userName, String password, String name, String id, ArrayList<Course> courses) {
		
		//Call constructor for superclass
		super(userName, password, name, id, courses);
		
		//Set variable type to professor
		this.type = "professor";
		
		//Iterate over given arraylist of all courses
		for (Course c: courses) {
			
			//Populate professor specific list of courses with each course object which contains the professor's name
			if(this.getName().equals(c.getCourseInstructor())) {
				this.profCourses.add(c);
			}
		}	
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
		System.out.println("1 -- View given courses");
		System.out.println("2 -- View student list of the given course");
		System.out.println("3 -- Return to previous menu");	
		System.out.println("");
		System.out.println("Please enter your option, eg. '1'.");
		System.out.println("");

	}
	
	
	/**
	 * Prompts for user input and performs action based on given list of actions for subclass of User
	 * @param scan Scanner for userInput
	 */
	@Override
	public void performAction(Scanner scan) {
		
		//Create array of valid user actions
		String[] loginOptions = {"1", "2", "3"};
		
		//Create boolean for returning to previous menu
		boolean goBack = false;
		
		//While user hasn't selected to back to previous menu
		while (goBack == false){
			
			//Print menu for list of actions
			this.printActionMenu();
			
			//String for prompt for user input
			String userInput = scan.nextLine();
			
			//While the user has not provided valid input (check by calling checkValidInput() method)
			while (this.checkValidInput(loginOptions, userInput) == false) {
				System.out.println("Please enter a valid response!");
				userInput = scan.nextLine();
			}
		
			//Call respective class method based on action selected by user
			//End loop without calling method if 3 is selected
			if (userInput.equals("1")) {
				this.viewGivenCourses();
			} 
			else if(userInput.equals("2")) {
				this.viewStudentList(scan);
			} 
			else if(userInput.equals("3")) {
				goBack = true;
			}
		}
	}
	
	/**
	 * Displays all assigned courses for professor
	 */
	public void viewGivenCourses() {
		
		//Iterate over list of courses for professor
		for (Course c: profCourses) {
			
			//Print each course by calling toString method for Course object
			System.out.println(c);
		}
	}
	
	/**
	 * Prompts for course, checks if course is valid & displays list of students for course
	 * @param scan
	 */
	public void viewStudentList(Scanner scan) {
		
		//Create boolean to check if user wants to return to previous screen
		boolean goBack = false;
		
		//Create boolean validID to see if provided courseID is valid
		boolean validID = false;
		
		//While the user has not chosen to return to the previous screen
		while (goBack == false) {
			
			//Prompt for userInput
			System.out.println("");
			System.out.println("Please enter the course ID, e.g. 'CIT590'");
			System.out.println("Or enter 'q' to return to the previous menu.");
			String userInput = scan.nextLine();
			
			//Iterate over ArrayList of all courses
			for (Course c: courses) {
				//If the given course name matches with a course from courses ArrayList
				if(userInput.toLowerCase().equals(c.getCourseCode().toLowerCase())) {
					
					//Set valid ID to true
					validID = true;
					
					//Print header
					System.out.println("Students in course " + userInput + ":");
					
					//Get ArrayLIst of students for course object by calling getStudentsInCourse'
					//Iterate over ArrayList and each student
					for(Student s: c.getStudentsInCourse()) {
						System.out.println(s);
					}
				}
			}
			//If ID provided is invalid, notify user
			if (validID == false) {
				System.out.println("Provided course ID is not valid - please try again!");
			}
			//If user input is q, set goBack to true and end while loop
			if (userInput.equals("q")) {
				goBack = true;
			}
		}
	}
	
	/**
	 * Get Arraylist of associated course objects for user
	 * @return ArrayList<Courses> userCourses
	 */
	public ArrayList<Course> getUserCourses() {
		return profCourses;
	}
	
}
