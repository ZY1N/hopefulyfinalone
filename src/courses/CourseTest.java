package courses;
import static org.junit.jupiter.api.Assertions.*;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import files.FileInfoReader;
import roles.Admin;
import roles.Student;
import roles.User;

class CourseTest {
	
//    static public ArrayList <Course> courses = new ArrayList <Course>();
//	public static ArrayList<User> users = new ArrayList<User>();
//	Scanner scan;
//	Admin admin;

	//public InputStream sysInBackup = System.in; // backup System.in to restore it later
	//public ByteArrayInputStream in = new ByteArrayInputStream("My string".getBytes());
	
  static public ArrayList <Course> courses;
  static public ArrayList <Student> students;
  static public ArrayList <User> users;
  static public FileInfoReader reader = new FileInfoReader();


  /*NOTE VARIABLES ARE INITED WITHIN THE TEST METHODS FOR READBILITY, IK THEY CAN BE MOVED OUT*/
  
  //static FileInfoReader reader;


	@BeforeEach
	void setup() throws Exception{
		//open up a fileinfo reader
		users = new ArrayList <User>();

		courses = new ArrayList <Course>();
		reader.readCourse("courseTest.txt", courses);
	}

	@Test
	void testCheckIfEnrolled() {
		
		//read in only the students
		reader.readUsers("studentTest.txt", "student", users, courses);
		
		//case 1 student is enrolled
		//first course object is "id1, coursename1, profname1, M, 1; 2; 1"
		Course testUnit = courses.getFirst();
		Student falseStudent = (Student) users.getFirst();
		assertTrue(testUnit.checkIfEnrolled(falseStudent));
		
		//case 2 where student isn't enrolled, 2st student is NOT enrolled in the class
		Student trueStudent = (Student) users.get(2);
		assertFalse(testUnit.checkIfEnrolled(trueStudent));
	}
	
	@Test
	void testCheckCapacity() {
		
		//read in only the students
		reader.readUsers("studentTest.txt", "student", users, courses);
		Student randomStudent = (Student) users.getLast();


		//case 1 test course 1 which is at capacity, so will not have space
		Course course1 = courses.get(0);
		assertFalse(course1.checkCapacity(randomStudent));

		
		//case 2 test course 2 which has capacity
		Course course2 = courses.get(1);
//		System.out.println(course2);
		assertTrue(course2.checkCapacity(randomStudent));
		
	}
	
	@Test void testAddStudent() {
		reader.readUsers("studentTest.txt", "student", users, courses);

		Student student1 = (Student) users.get(0);
		Student student2 = (Student) users.get(1);
		Student student3 = (Student) users.get(2);

		Course course1 = courses.get(0);
		Course course2 = courses.get(1);

		
		//case 1 try to add student 1 to course2 that they are already part of it: fail
		assertFalse(course1.checkCapacity(student1));
		assertFalse(course1.addStudent(student1));
		
		//case 2 try to add student 2 to course1, when course 1 is full: fail
		assertFalse(course1.addStudent(student2));
		
		//case 2 try to add student 3 to course 1, this will fail because of schedule conflict
		assertFalse(course2.addStudent(student3));
		
		//case 3 filling up course 2 with students
		//try to put student2 into the course
		assertTrue(course2.checkCapacity(student2));
		//add it in
		assertTrue(course2.addStudent(student2));
		//capacity should be full at 2 students
		assertFalse(course2.checkCapacity(student2));
	}

	
	@Test void testIncrementStudent() {
		reader.readUsers("studentTest.txt", "student", users, courses);

		Student student1 = (Student) users.get(0);
		Course course1 = courses.get(0);
		
		//increment student1 
		assertEquals(1, course1.getStudentNum());
		course1.incrementStudents();
		assertEquals(2, course1.getStudentNum());
		course1.incrementStudents();
		assertEquals(3, course1.getStudentNum());
		course1.incrementStudents();
		assertEquals(4, course1.getStudentNum());
		
	}


	@Test void removeStudent() {
		reader.readUsers("studentTest.txt", "student", users, courses);
		Student student1 = (Student) users.get(0);
		Student student2 = (Student) users.get(1);
		Student student3 = (Student) users.get(2);
		
		Course course1 = courses.get(0);
		
		//case 1 remove student 2 from course1, which they are not in
		assertEquals(1, course1.getStudentNum());
		assertFalse(course1.removeStudent(student2));
		assertEquals(1, course1.getStudentNum());
		
		//case 2 remove student 1 from course1, which they are in
		assertEquals(1, course1.getStudentNum());
		assertTrue(course1.removeStudent(student1));
		assertEquals(0, course1.getStudentNum());
	}


	@Test void testCheckSchedulingConflict() {
		reader.readUsers("studentTest.txt", "student", users, courses);

		Student student1 = (Student) users.get(0);
		Student student2 = (Student) users.get(1);
		Student student3 = (Student) users.get(2);
		
		Course course1 = courses.get(0);
		Course course2 = courses.get(1);
		Course course3 = courses.get(2);
		Course course4 = courses.get(3);


		System.out.println(student1.getUserCourses());
		//case 1 normal no conflict, different time, same date
		assertTrue(course2.checkSchedulingConflict(student3));
		assertFalse(course3.checkSchedulingConflict(student2));

		//case 2 adding student1 to course 2, when already registered for course 2
		//2 and 2 will collide and return true for conflict
		assertTrue(course2.checkSchedulingConflict(student1));

		//case 3 same time, different date. should be allowed
		assertFalse(course4.checkSchedulingConflict(student3));
	}


}
