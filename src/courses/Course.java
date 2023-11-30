package courses;

import java.util.ArrayList;

import roles.*;

public class Course {
	
	//Instance Variables
	
	/**
	 * Unique identifier for course
	 */
	private String courseCode;
	
	/**
	 * Name of course
	 */
	private String courseName;
	
	/**
	 * Instructor for course
	 */
	private String courseInstructor;
	
	/**
	 * Max student capacity for course
	 */
	private int courseCapacity;
	
	/**
	 * Arraylist for storing days on which course occurs
	 */
	private ArrayList<Character> daysList = new ArrayList<Character>();
	
	/**
	 * Ending time for course
	 */
	private int timeEnd;
	
	/**
	 * Start time for course
	 */
	private int timeStart;
	
	/**
	 * Number of students in course
	 */
	private int studentNum;
	
	/**
	 * Array list of student objects for students in course
	 */
	private ArrayList<Student> studentsInCourse = new ArrayList<Student>();


	//Constructor
	
	/**
	 * Construct course object based on given parameters
	 * @param courseCode Unique code for identifying course
	 * @param courseName Name of course
	 * @param courseInstructor Professor for course
	 * @param days Days on which course occurs
	 * @param timeStart Time at which course starts
	 * @param timeEnd Time at which course ends
	 * @param capacity Max students for course
	 */
	public Course(String courseCode, String courseName, String courseInstructor, String days, String timeStart, String timeEnd, String capacity) {
			
		//Initialize string instance variables based on given constructor parameters
		this.courseCode = courseCode;
		this.courseName = courseName;
		this.courseInstructor = courseInstructor;
		
		//Parse days into arrayList with each specific day
		//Note Tuesday/Thrusday split into T & R
		for (int i = 0; i < days.length(); i++) {
			this.daysList.add(days.charAt(i));
		}
		
		
		//Cast course start & end time to int after removing ":" delimiter (i.e. display as military time)
		try {
			this.timeEnd = Integer.parseInt(timeEnd.replaceAll(":", ""));
			this.timeStart = Integer.parseInt(timeStart.replaceAll(":", ""));

			
		}//If numberformatexception, print error message and set each value to 0 
		catch (NumberFormatException e) {
			courseCapacity = 0;
			System.out.println("Course time could not be converted to type double");
		}

		
		//Cast course capacity to int
		try { 
			courseCapacity = Integer.parseInt(capacity.trim());
			
		} //If numberformatexception, print error message and set capacity to 0 
		catch (NumberFormatException e) {
			courseCapacity = 0;
			System.out.println("Course capacity could not be converted to type int");
		}

		//Number of students initially set to 0
		this.studentNum = 0;
	}
	
	//Class Methods
	
	/**
	 * Checks if student is currently enrolled in course. Returns true or false.
	 * @param student to check.
	 * @return True if student is in enrolled, otherwise false.
	 */
	public boolean checkIfEnrolled(Student student) {
		
		//Iterate over ArrayList of students in course
		for (Student s: studentsInCourse) {
			
			//If student is already in ArrayList, return true
			if(student.equals(s)) {
				
				return true;
			}
		}
		//Otherwise, return false
		return false;
		
	}
	
	
	/**
	 * Checks if capacity exists to add given student. Returns false if no capacity.
	 * @param student to check
	 * @return True if student can be added, false otherwise
	 */
	public boolean checkCapacity(Student student) {
		
		//Check if course has capacity to add student
		if (studentNum < courseCapacity) {

			//Return true
			return true;
		}
		
		//Otherwise return false
		return false;
	}
	
	/**
	 * Checks if given user has a scheduling conflict for course. Returns false if no conflict
	 * @param student to check
	 * @return false if student can be added, true otherwise
	 */
	public boolean checkSchedulingConflict(User user) {
		

		//Iterate over list of courses for student
		for (Course c : user.getUserCourses()) {

			//Iterate over list of days for student course		
			for (char day : c.getDaysList()) {
				
				//Iterate over list of days for this course object
				for (char conflictDay : this.getDaysList()) {
					
					//Check for conflicting days
					//If a conflicting day is found, check for conflicting time
					if (day == conflictDay) {

						//If the start time for this course is between the start & end time for existing course, return true
						if(this.getTimeStart() >= c.getTimeStart() && this.getTimeStart() <= c.getTimeEnd()){ 
							
							//Print error message noting the conflicting course
							System.out.println("Sorry! The course you selected has a time conflict with " + c.getCourseCode() + " " + this.getCourseName());
							//Return false
							return true;
						}
						//If the end time for this course is between the start & end time for existing course, return true
						else if (this.getTimeEnd() >= c.getTimeStart() && this.getTimeEnd() <= c.getTimeEnd()){
							//Print error message noting the conflicting course
							System.out.println("Sorry! The courese you selected has a time conflict with " + c.getCourseCode() + " " + this.getCourseName());
							//Return false
							return true;
						}
						//If the start time for this course is before the start time for an existing course
						//& if the end time for this course is after the end time for existing course, return true
						else if(this.getTimeStart() <= c.getTimeStart() && this.getTimeEnd() >= c.getTimeEnd()){
							
							//Print error message noting the conflicting course
							System.out.println("Sorry! The courese you selected has a time conflict with " + c.getCourseCode() + " " + this.getCourseName());
							//Return false
							return true;
						}
					}
				}	
			}
		}
		//Otherwise return false
		return false;
	}
	
	
	/**
	 * Adds given student to course
	 * @param student to add
	 */
	public boolean addStudent(Student student) {
		
		//First, check if the student is currently enrolled in this course by calling checkIfEnrolled()
		//If true, notify user and return false
		if(this.checkIfEnrolled(student) == true) {
			
			System.out.println("Sorry, user is already enrolled in " + this.getCourseCode() + "! Course not added.");
			return false;
		}
		//Second, check if course has capacity for student by calling checkCapacity()
		//If false, notify user & return false
		else if (this.checkCapacity(student) == false){
			System.out.println("Sorry, " + this.getCourseCode() + " is at capcity! Course not added.");
			return false;
			
		} //Third, check if student has scheduling conflict by calling checkSchedulingConflic()
		//If true, return false. Note: method prints notification for user
		else if (this.checkSchedulingConflict(student) == true){
			
			return false;
			
		} //Otherwise add student & return true		
		else {
			
			//Add student to array list of students in course
			studentsInCourse.add(student);
		
			//Increment number of students
			studentNum++;
			
			//Print notification
			System.out.println("Course added successfully!");
			
			//Return true
			return true;
		}
	}
	
	public void incrementStudents() {
		this.studentNum+= 1;
	}
	
	/**
	 * Checks if given student is in course. If so, removes student.
	 * Returns true if student removed, otherwise false
	 * @param student to remove
	 * @return True if student removed, otherwise false.
	 */
	public boolean removeStudent(Student student) {
		
		//Call checkIfEnrolled() to see if student is ok to remove. Save value as boolean okToRemove
		boolean okToRemove = this.checkIfEnrolled(student);
		
		//If student is okToRemove, remove student
		if (okToRemove) {
			
			//Remove student from array list of students for course
			this.studentsInCourse.remove(this.studentsInCourse.indexOf(student));
			
			//Decrease number of students
			studentNum--;
			
			//Print notification
			System.out.println("Course removed successfully!");
		} 
		//If student is not okToRemove, notify user
		else {
			System.out.println("Student is not enrolled in " + this.getCourseCode() + "! Could not remove student");
		}
		
		//Return boolean okToRemove
		return okToRemove;
	}
	
	
	/**
	 * Overrides default toString method to display Course object in readable format for user
	 */
	public String toString() {
		
		//Concatenate instance variables for display as string
		return (courseCode + "|" + courseName + "," + timeStart + "-" + timeEnd + " on " + daysList + ", with course capacity: "
		+ courseCapacity + " students: " + studentNum + ", lecturer: " + courseInstructor);
	}
	
	//Getter methods
	
	/**
	 * Get courseCode variable
	 * @return courseCode
	 */
	public String getCourseCode() {
		return courseCode;
	}
	
	/**
	 * Get courseName variable
	 * @return courseName
	 */
	public String getCourseName() {
		return courseName;
	}
	
	/**
	 * Get Array list days for course
	 * @return Arraylist daysList
	 */
	public ArrayList<Character> getDaysList() {
		
		return this.daysList;
	}
	
	/**
	 * Get start time for course
	 */
	public int getTimeStart() {
		return this.timeStart;
	}
	
	/**
	 * Get end time for course
	 */
	public int getTimeEnd() {
		return this.timeEnd;
	}
	
	/**
	 * Get courseInstructor variable
	 * @return courseInstructor
	 */
	public String getCourseInstructor() {
		return courseInstructor;
	}
	
	/**
	 * Get ArrayList studentsInCourse
	 * @return studentsInCourse
	 */ 
	public ArrayList<Student> getStudentsInCourse() {
		return studentsInCourse;
	}

	public Integer getStudentNum() {
		// TODO Auto-generated method stub
		return studentNum;
	}

	public Integer getCourseCapacity() {
		// TODO Auto-generated method stub
		return courseCapacity;
	}

	
}
