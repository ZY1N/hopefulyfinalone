package roles;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import courses.Course;
import files.FileInfoReader;

class ProfessorTest {

    static public ArrayList <Course> courses = new ArrayList <Course>();
	public static ArrayList<User> users = new ArrayList<User>();
	Scanner scan;
	public InputStream sysInBackup = System.in; // backup System.in to restore it later
	public ByteArrayInputStream in = new ByteArrayInputStream("My string".getBytes());
	
	@BeforeEach                                         
    void setUp() {
    	FileInfoReader reader = new FileInfoReader();
    	
    	reader.readCourse("courseTest.txt", courses);
		//Initialize user data by calling readUsers function for each user data file
		//Append all users to ArrayList users
		reader.readUsers("profTest.txt", "professor", users, courses);
    }
	
	@Test
	void  testgetUserCourses() {
		Professor a = (Professor) users.get(0);
		Professor b = (Professor) users.get(1);

		//check valid returns
		assertEquals(a.profCourses, a.getUserCourses());
		assertEquals(b.profCourses, b.getUserCourses());
		
		//check invalid
		//check valid returns
		assertNotEquals(a.profCourses, b.getUserCourses());
		assertNotEquals(b.profCourses, a.getUserCourses());

	}
	
	//can't test print funcs
	//void testViewGivenCourses()
	//void testViewStudentList(Scanner scan)
}
