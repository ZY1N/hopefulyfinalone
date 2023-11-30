import java.util.ArrayList;
import java.util.Scanner;

import courses.*;
import files.*;
import roles.*;

/**
 * Class controls the operation of the student management system including:
 * Importing and parsing data required for the operation of the system (i.e. course & user info)
 * Prompting for and reading user input
 * Executing user actions and printing results
 */
public class Controller {
	
	//Instance variables
	
	/**
	 * Array list of User objects.
	 * Identifies users who are allowed to access the student management system.
	 */
	public static ArrayList<User> users = new ArrayList<User>();
	
	/**
	 * Array list of Course objects.
	 * Identifies courses available in the student management system.
	 */
	public static ArrayList<Course> courses = new ArrayList<Course>();
	
	/**
	 * FileInfoReader object for importing data into system.
	 */
	static FileInfoReader reader;
	
	/**
	 * Identifies current user operating the system
	 * Set to null by default
	 */
	static User currentUser;
	
	/**
	 * Identifies whether or not the user has decided to quit the system
	 * Set to false by default
	 */
	static boolean quit = false;
	
	/**
	 * Scanner for user input
	 */
	static Scanner scan;
	
	//Constructor
	/**
	 * Initializes management system by creating new Controller object
	 * Imports course and user information
	 */
	public Controller() {
		
		//Create file info reader
		reader = new FileInfoReader();
		
		//Initialize course data by calling readCourse function for reader
		//Append courses returned by function to ArrayList courses
		reader.readCourse("courseInfo.txt", courses);
		
		//Initialize user data by calling readUsers function for each user data file
		//Append all users to ArrayList users
		reader.readUsers("adminInfo.txt", "admin", users, courses);
		reader.readUsers("studentInfo.txt", "student", users, courses);
		reader.readUsers("profInfo.txt", "professor", users, courses);
		
	}
	
	//Helper methods for operation of Controller Object
	
	/**
	 * Helper method checks if user input is valid based on given array of options & given user input
	 * @param options for valid input
	 * @param userInput to check
	 * @return true if user input is within options, otherwise false
	 */
	public boolean checkValidInput(String[] options, String userInput) {
		
		//Iterate over list of options
		for (String o: options) {
			//If userInput in list, return true
			if (o.equals(userInput)) {
				return true;
			}
		}
		//Otherwise return false
		return false;
	}
	
	/**
	 * Iterates over given list of users to determine whether given username is valid
	 * If username is valid, sets currentUser variable equal to the respective user object & returns true
	 * If username is invalid, returns false
	 * @param userName
	 */
	public boolean findUser(String userName) {
		
		//Iterate over ArrayList of all user objects
		for (User u: users) {
			//If the given userName matches a user in the ArrayList, return true & set to current user
			if(u.getUserName().equals(userName)) {
				//Set current user equal to matching user
				currentUser = u;
				return true;
			}
		}
		
		//Otherwise return false
		return false;
	}
	
	/**
	 * Helper method to print system login message
	 */
	public void printLoginMessage() {
		
		//Print login message
		System.out.println("--------------------------------");
		System.out.println("Students Management System");
		System.out.println("--------------------------------");
		System.out.println("1 -- Login as a student");
		System.out.println("2 -- Login as a professor");
		System.out.println("3 -- Login as a admin");
		System.out.println("4 -- Quit the system");
		System.out.println("");
		System.out.println("Please enter your option, eg. '1'.");
		System.out.println("");
	}
	
	
	/**
	 * Prompts for userId and password and confirms each match given info in ArrayList users
	 * Returns userID as String
	 * @return
	 */
	public void login() {
		
		//Create boolean for tracking log in status
		boolean login = false;
		
		//Create empty string for storing userInput
		String userInput = "";
		
		//Create empty string for storing loginType (i.e. student, prof, admin)
		String loginType = "";
		
		//Create array of valid login options
		String[] loginOptions = {"1", "2", "3", "4"};
		
		//Initialize scanner object
		scan = new Scanner(System.in);
		
		//Iterate through steps below until login == true
		while (login == false) {
			
			//Print login message
			this.printLoginMessage();
			
			//Take initial user input
			userInput = scan.nextLine();
			
			//Check for valid input by calling checkValidInput() helper method. If input is invalid, continue loop
			if(this.checkValidInput(loginOptions, userInput) == false) {
				System.out.println("Invalid input! Please try again!");
				System.out.println("");
				continue;
			}

			//If option 4 was selected to quit, print thank you message and end method
			//Set boolean quit to true to pass on all other actions in main method
			if (userInput.equals("4")) {
				
				System.out.println("Thank you for using the student managment system!");
				quit = true;
				
				//Return to end login() method
				return;
			} //for other valid inputs, update string loginType to type of login 
			else if (userInput.equals("1")){
				loginType = "student";
				
			} else if (userInput.equals("2")){
				loginType = "professor";
				
			} else if (userInput.equals("3")){
				loginType = "admin";
				
			}
			
			//Prompt for userName
			System.out.println("Please enter your username, or type 'q' to quit.");	
			userInput = scan.nextLine();
			
			//If 'q' selected, print message and restart loop
			if(userInput.equals("q")) {
				System.out.println("Login quit.");
				System.out.println("");
				continue;
			}
			
			//Call findUser helper function to see if given user exists, if so prompt for password
			//Also confirm correct type of user login was selected
			if (this.findUser(userInput) == true && currentUser.getType().equals(loginType)) {
				
				//Prompt for password
				System.out.println("Please enter your password, or type 'q' to quit.");
				userInput = scan.nextLine();
				
				//Call checkPassword() function for user using next line
				if(currentUser.checkPassword(userInput) == false) {
					
					//If password is invalid, print message & continue loop
					System.out.println("Invalid password, login quit.");
					System.out.println("");
					continue;
					
				}//If password check works, set boolean login to true, ending loop 
				else {
					System.out.println("Login successful!");
					System.out.println("");
					login = true;
				}
				
			} //If given user does not exist, print message & continue loop
			else {
				System.out.println(loginType + " user not found, login quit.");
				System.out.println("");
				continue;
			}
		}

	}
	
	/**
	 * Main method
	 */
	public static void main(String[] args) {
				
		//Initialize controller class
		Controller controller = new Controller();

		//Perform login and specific user actions
		//Run while the user has not selected the option to quit the system
		while (quit == false) {
			
			//Call login function
			controller.login();
			
			//If user did not quit system during login function, proceed to actionMenu
			if (quit == false) {
//				
//				User.setCourse(Controller.courses);
//				System.out.println(currentUser.getUserCourses());
				
				
				//Call performAction() for currentUser object
				currentUser.performAction(scan);
			}
		}
		
		scan.close();
	}
}
