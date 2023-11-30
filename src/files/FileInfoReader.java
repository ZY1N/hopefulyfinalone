package files;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import roles.*;
import courses.*;


/**
 * Utility class for reading and inputing certain information files into the StudentManagment system.
 */
public class FileInfoReader {
	
	//Instance variables
	/**
	 * File object for file to read
	 */
	File file = null;
	
	/**
	 * File reader object for creating buffered reader
	 */
	FileReader fileReader = null;
	
	/**
	 * BufferedReader object for reading file
	 */
	BufferedReader bufferedReader = null;
	
	//Constructor 
	/**
	 * Blank constructor as no input parameters needed
	 */
	public FileInfoReader() {
	}
	
	//Reader methods
	/**
	 * Creates file, fileReader, and bufferedReader objects to read given fileName
	 * Creates User objects based on given file and userType and appends to users ArrayList
	 * @param fileName file to read
	 * @param userType type of User object to create
	 * @param users ArrayList to append new user to & passing to User Object constructors (where required)
	 * @param courses ArrayList of courses for passing to User Object constructors (where required)
	 */
	public void readUsers(String fileName, String userType, ArrayList<User> users, ArrayList<Course> courses) {
		
		//Initialize file object
		this.file = new File(fileName);
		
		//Try to create file reader & catch FileNotFoundException
		try {
			this.fileReader = new FileReader(this.file);
		} catch (FileNotFoundException e) {
			//Print message to notify file was not found
			System.out.println("Sorry given file: " + fileName + " was not found");
		}
		//Initialize buffered reader
		this.bufferedReader = new BufferedReader(this.fileReader);
		
		//Create string for next line to read
		String line;
		
		//Read lines in file using budderedReader, catching any IOException
		try {
			//While there are lines in the file to read
			while ((line = bufferedReader.readLine()) != null) {
				
				//Split each line into an array of Strings based on "; " delimiter.
				String []lineArray = line.split("; ");
				
				//If given user type is admin, create admin object and append to users ArrayList
				if (userType == "admin") {
					
					//Create new admin object based on the position of the constructor parameters in lineArray
					Admin newUser = new Admin(lineArray[2], lineArray[3], lineArray[0], lineArray[1], courses, users);
					
					//Append admin object to userList
					users.add(newUser);
					
				}//If given user type is student, create Student object and append to users ArrayList
				//Call parseCourses() and parseGrades() functions to account for additional "," & ":" delimiters in studentInfo file
				else if (userType == "student") {
					
					//Create array coursesAndGrades for storing course & grade info after splitting for additional "," delimiter
					//Array is passed to parseCourses() and parseGrades() helper functions
					String[] coursesAndGrades = lineArray[4].trim().split(",");
					
					//Create new Student object based on the position of the constructor parameters in lineArray
					//Call parseCourses() & parseGrades() methods address additional ":" delimiter in studentInfo file
					Student newUser = new Student(lineArray[2], lineArray[3], lineArray[1], lineArray[0], 
							this.parseCourses(coursesAndGrades, courses), this.parseGrades(coursesAndGrades), courses);
					
					//Append Student object to userList
					users.add(newUser);
					
				} //If given user type is professor, create professor object and append to users ArrayList
				else if (userType == "professor") {
					
					//Create new professor object based on the position of the constructor parameters in lineArray
					Professor newUser = new Professor(lineArray[2], lineArray[3], lineArray[0], lineArray[1], courses);
					
					//Append Professor object to userList
					users.add(newUser);
					
				} //Otherwise notify that an invalid user type was not provided
				else {
					System.out.println("Invalid user type provided!");
					break;
				}
			}
		} //If IOException caught, print error message 
		catch (IOException e) {
			e.printStackTrace();
			
		} //Finally, close fileReader and buffered reader, again catching any IOexception 
		finally {
			try {
				this.fileReader.close();
				this.bufferedReader.close();
			} catch (IOException e) {
				//Print caught IO exception
				e.printStackTrace();
			}
		}
	}
	
	
	
	
	/**
	 * Creates file, fileReader, and bufferedReader objects to read given fileName
	 * Creates Course objects based on given file and appends to courses ArrayList
	 * @param fileName
	 * @param courseList
	 */
	public void readCourse(String fileName, ArrayList<Course> courseList) {

		try {
			String currLine;
			//open file
			//create file object
			File courseFile = new File(fileName);
			//check filenotfoundexception
			FileReader fileReader = new FileReader(courseFile);
			//temp admin object
			Course tempCourse;
					
			//open buffered reader
			BufferedReader bufferedReader = new BufferedReader(fileReader);
						
			//read the data into adminList
			while((currLine = bufferedReader.readLine()) != null) {
				
				//splits string based on collon
				String []strArray = currLine.split("; ");
				
				//check to see if currLine is valid
				//must be 4 otherwise ignore that line
				if(strArray.length != 7) {
					continue ;
				}
				
				//turn currline into admin object
				tempCourse = new Course(strArray[0], strArray[1], strArray[2], strArray[3], strArray[4], strArray[5], strArray[6]);
				
				//
				
				courseList.add(tempCourse);
			}
			fileReader.close();
			bufferedReader.close();
		}
		//file fail to open
		catch(FileNotFoundException e) {
			System.out.println(e);
		}
		//stream fail, line read fail
		catch(IOException e) {
			System.out.println(e);
		}
	}
	
	//Helper methods
	
	/**
	 * Takes in array of Strings "coursesAndGrades" and parses based on ":" delimiter 
	 * Matches parsed course to respective course object in ArrayList of all course objects
	 * Appends each course object to & returns new Course ArrayList of student's specific courses
	 * @param coursesAndGrades - String[] of coursesAndGrades in "Course : grade" format (e.g. "CIT590 : B-")
	 * @param courses - ArrayList of all course objects in StudentManagmentSystem
	 * @return new ArrayList of Course objects for student's specific courses
	 */
	public ArrayList<Course> parseCourses (String[] coursesAndGrades, ArrayList<Course> courses){
		
		//Create ArrayList for storing parsed courses
		ArrayList<Course> studentCourses = new ArrayList<Course>();
		
		//Iterate over given arraylist of strings
		for (String s: coursesAndGrades) {
			
			//Iterate over arraylist of all courses
			for (int i = 0; i < courses.size(); i++) {
				
				//Split the given string based on the ":" delimiter
				//If the string at index [0] of the split string is equal to the courseCode for courses.get(i) course object
				if(s.trim().split(":")[0].equals(courses.get(i).getCourseCode())) {
					
					//Add the course object to the arraylist of studentCourses
					studentCourses.add(courses.get(i));
					
				}
			}
		}
		
		//Return arraylist of course objects for student
		return studentCourses;
		
	}
	
	/**
	 * Takes in arraylist of "coursesAndGrades" and parses based on ":" delimiter 
	 * Creates HashMap studentGrades and places first value of parsed string as key and second as value
	 * @param coursesAndGrades coursesAndGrades - String[] of coursesAndGrades in "Course : grade" format (e.g. "CIT590 : B-")
	 * @return HashMap<String, String> studentGrades
	 */
	public HashMap<String, String> parseGrades (String[] coursesAndGrades){
		
		//Create hashmap for parsing student grades
		HashMap<String, String> studentGrades = new HashMap<>();
		
		//Iterate over given string and split based on ":" delimeter
		for (String s: coursesAndGrades) {
			//Assign first value of split string to key and second to value
			studentGrades.put(s.trim().split(":")[0].trim(), s.trim().split(":")[1].trim());
		}
		
		//Retern HashMap studentGrades
		return studentGrades;
		
	}
	

}
