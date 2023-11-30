package roles;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import courses.*;

/**
 * Represents user of the Student Management System of type Student
 */
public class Student extends User {
	
	//Student specific instance variables
	
	/**
	 * Array list to contain course objects for student
	 */
	ArrayList<Course> studentCourses = new ArrayList<Course>();
	
	/**
	 * Array list to contain grade strings for student
	 */
	HashMap<String, String> grades = new HashMap<>();
	

	/**
	 * Calls constructor for superclass user and takes additional parameters "type", "studentCoursers, & "grades"
	 * @param userName for student user
	 * @param password for student user
	 * @param name of student user
	 * @param id of student user
	 * @param studentCourses ArrayList of Course objects specific to the given student
	 * @param grades HashMap of key CourseID & value grades specific to student
	 * @param courses ArrayList of all course objects in Student Managment System
	 */
	public Student(String userName, String password, String name, String id, ArrayList<Course> studentCourses, 
			HashMap<String, String> grades, ArrayList<Course> courses) {
		
		//Call constructor for superclass
		super(userName, password, name, id, courses);

		//Set additional input variables based on info provided in constructor0
		this.type = "student";
		this.studentCourses = studentCourses;
		this.grades = grades;
		
		//Assign student to related course objects
		//Iterate over list of student courses
		for (Course c: studentCourses) {
			
			//Call addStudent to add student to course
			//Note: add student return value boolean not needed for constructor
			c.getStudentsInCourse().add(this);
			c.incrementStudents();
		}

	}
	
	/**
	 * Prints menu of action items for given subclass of User
	 * Must implement to override abstract method
	 */
	@Override
	public void printActionMenu() {
		// TODO Auto-generated method stub
		
		//Print Action menu message
		System.out.println("--------------------------------");
		System.out.println("Welcome, " + this.getName());
		System.out.println("--------------------------------");
		System.out.println("1 -- View all courses");
		System.out.println("2 -- Add courses to your list");
		System.out.println("3 -- View selected courses");
		System.out.println("4 -- Drop courses in your list");
		System.out.println("5 -- View grades");
		System.out.println("6 -- Return to previous menu");	
		System.out.println("");
		System.out.println("Please enter your option, eg. '1'.");
		System.out.println("");

	}

	/**
	 * Prompts for user input and performs action based on given list of actions for subclass of User
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
			//Return without calling method if 6 is selected
			if (userInput.equals("1")) {
				this.viewAllCourses();
			
			} else if(userInput.equals("2")) {
				this.addCourse(scan);
			
			} else if(userInput.equals("3")) {
				this.viewSelectedCourses();
			
			} else if(userInput.equals("4")) {
				this.dropCourse(scan);
			
			} else if(userInput.equals("5")) {
				this.viewGrades(scan);
			
			} else if(userInput.equals("6")) {	
				return;
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
	 * Add a given course to student schedule.
	 * Courses can only be added if not already done so previously and there is no time conflict with another course
	 * @param scan Scanner for user input
	 */
	public void addCourse(Scanner scan) {
		
		//Create boolean to check if user wants to return to previous screen
		boolean goBack = false;
		
		//Create boolean validID to see if provided courseID is valid
		boolean validID = false;
		
		//While the user has not chosen to return to the previous screen
		while (goBack == false) {
			
			//Prompt for userInput
			System.out.println("");
			System.out.println("Please select the course ID you want to add to your list, eg. 'CIT590'");
			System.out.println("Or enter 'q' to return to the previous menu.");
			String userInput = scan.nextLine();
			
			//Iterate over ArrayList of all courses
			for (Course c: courses) {
				
				//If the given course name matches with a course from courses ArrayList
				if(userInput.toLowerCase().equals(c.getCourseCode().toLowerCase())) {
					
					//Set valid ID to true
					validID = true;
					
					//Try to add the student using the Course objects addStudent method
					if (c.addStudent(this)) {
						
						//If successful, add course to this student's list of Courses
						this.studentCourses.add(c);
						
						//Add "n/a" to grade HashMap
						this.grades.put(c.getCourseCode(), "N/A");
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
	 * Displays all courses in which the student is currently enrolled
	 */
	public void viewSelectedCourses() {
		
		//Iterate over ArrayList of courses for student and print
		for (Course c: studentCourses) {
			System.out.println(c);
		}
	}
	
	/**
	 * Removes given course from student list & removes student from course
	 * @param scan Scanner for user input
	 */
	public void dropCourse(Scanner scan) {
		
		//Create boolean to check if user wants to return to previous screen
		boolean goBack = false;
		
		//Create boolean validID to see if provided courseID is valid
		boolean validID = true;
		
		//While the user has not chosen to return to the previous screen
		while (goBack == false) {
			
			//Prompt user for input
			System.out.println("");
			System.out.println("Please select the course ID you want to remove from your list, e.g. 'CIT590'");
			System.out.println("Or enter 'q' to return to the previous menu.");
			String userInput = scan.nextLine();
			
			//Iterate over ArrayList of all courses
			for (Course c: courses) {
				
				//If the given course name matches with a course from courses ArrayList
				if(userInput.toLowerCase().equals(c.getCourseCode().toLowerCase())) {
					
					//Set valid ID to true
					validID = true;
					
					//Try to remove student using course object's removeStudent() function
					if (c.removeStudent(this)) {
						
						//If successful, remove this course form the student's list of courses
						this.studentCourses.remove(this.studentCourses.indexOf(c));
						
						//Remove grades from grade Hashmap
						this.grades.remove(c.getCourseCode());
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
				//End method
			}
		}
	}
	
	/**
	 * Shows student grade for each course enrolled
	 * @param scan Scanner for user input
	 */
	public void viewGrades(Scanner scan) {
		
		//Rely on default print method for grades HasMap
		System.out.println(grades);
	}
	
	
	/**
	 * Get Arraylist of associated course objects for user
	 * @return ArrayList<Courses> userCourses
	 */
	public ArrayList<Course> getUserCourses() {
		return studentCourses;
	}
}
