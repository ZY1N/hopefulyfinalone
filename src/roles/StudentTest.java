package roles;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import courses.Course;
import files.FileInfoReader;

class StudentTest {

	 static public ArrayList <Course> courses;
	 static public ArrayList <Student> students;
	 static public ArrayList <User> users;
	 static public FileInfoReader reader = new FileInfoReader();
	 Scanner scan;
	 ByteArrayInputStream in;
	 
	@BeforeEach                                         
	void setUp() {
		FileInfoReader reader = new FileInfoReader();
	    	
		courses = new ArrayList <Course>();
		users = new ArrayList <User>();
		reader.readCourse("courseTest.txt", courses);
		//Initialize user data by calling readUsers function for each user data file
		//Append all users to ArrayList users
		reader.readUsers("studentTest.txt", "student", users, courses);
	    }

	//takes a scanner
	@Test
	public void addCourse() {		
	 	String case1 = "id7\nq\r";
    	in = new ByteArrayInputStream(case1.getBytes());
    	scan = new Scanner(in); 
		
    	//CASE 1 valid no conflict add course
    	//pull of first student and add to id7 course
		Student tempStudent = (Student) users.get(0);
		tempStudent.addCourse(scan);
		
		//id7 course info: id7; coursename4; profname2; T; 16:30; 18:00; 10
		assertEquals(tempStudent.getUserCourses().getLast().getCourseCode(), "id7");
		assertEquals(tempStudent.getUserCourses().getLast().getCourseName(), "coursename4");
		assertEquals(tempStudent.getUserCourses().getLast().getCourseInstructor(), "profname2");
		assertEquals(tempStudent.getUserCourses().getLast().getTimeEnd(), 1800);
		assertEquals(tempStudent.getUserCourses().getLast().getTimeStart(), 1630);
		assertEquals(tempStudent.getUserCourses().getLast().getCourseCapacity(), 10);
		
		
		//CASE 2 adding course with time conflict
		//id7 and id6 share overlapping time and days
	 	String case2 = "id6\nq\r";
	 	int startSize = tempStudent.getUserCourses().size();
    
	 	in = new ByteArrayInputStream(case2.getBytes());
    	scan = new Scanner(in); 
    
		//since it fails, size remains the same
		tempStudent = (Student) users.get(0);
		tempStudent.addCourse(scan);
		assertEquals(startSize, tempStudent.getUserCourses().size());

		//CASE 3 adding course but exit with q
		//id7 and id6 share overlapping time and days
	 	String case3 = "q\r";
	 	startSize = tempStudent.getUserCourses().size();
    
	 	in = new ByteArrayInputStream(case3.getBytes());
    	scan = new Scanner(in); 
    
		//since it fails, size remains the same
		tempStudent = (Student) users.get(0);
		tempStudent.addCourse(scan);
		assertEquals(startSize, tempStudent.getUserCourses().size());
	}
	
	@Test
	public void dropCourse() {
		
		Student tempStudent = (Student) users.get(0);
	 	int startSize = tempStudent.getUserCourses().size();
	 	String case1 = "id1\nq\r";
    	in = new ByteArrayInputStream(case1.getBytes());
    	scan = new Scanner(in); 

		
    	//CASE 1 valid no conflict add course
    	//pull of first student and add to id7 course
		tempStudent.dropCourse(scan);
		assertEquals(startSize-1, tempStudent.getUserCourses().size());
		
	   	//CASE 2 try to drop course that student doesnt have
    	//pull of first student and add to id7 course
	 	String case2 = "id6\nq\r";
    	in = new ByteArrayInputStream(case2.getBytes());
    	scan = new Scanner(in); 
		tempStudent.dropCourse(scan);
		assertEquals(startSize-1, tempStudent.getUserCourses().size());
		
		
	   	//CASE 3 try to drop course and quit prematuraly
    	//pull of first student and add to id7 course
		
	 	String case3 = "q\nid6\nq\r";
    	in = new ByteArrayInputStream(case3.getBytes());
    	scan = new Scanner(in); 
		tempStudent.dropCourse(scan);
		assertEquals(startSize-1, tempStudent.getUserCourses().size());	
		
	}

//can't be tested
//	//print
//	public void viewAllCourses() {
//	public void viewGrades(Scanner scan) {
//	//getter
//	public ArrayList<Course> getUserCourses() {

}
