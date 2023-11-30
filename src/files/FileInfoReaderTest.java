package files;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.jupiter.api.Test;

import courses.Course;

class FileInfoReaderTest {

		FileInfoReader reader = new FileInfoReader();
	
	
	@Test void testParseGrades(){
		
		String[] coursesAndGrades1 = {"CIS191: A", "CIS320: A"};
		String[] coursesAndGrades2 = {"test1: B", "test2: C"};
		String[] coursesAndGrades3 = {};
		HashMap <String, String> studentGrades;


		//case 1 testing out real data
		studentGrades = reader.parseGrades(coursesAndGrades1);		
		assertEquals( "A" , studentGrades.get("CIS191"));
		assertEquals( "A" , studentGrades.get("CIS320"));
				
		studentGrades = reader.parseGrades(coursesAndGrades2);
		assertEquals( "B" , studentGrades.get("test1"));
		assertEquals( "C" , studentGrades.get("test2"));


		//case 2 testing out blank entry
		studentGrades = reader.parseGrades(coursesAndGrades3);
		assertEquals( null , studentGrades.get("test1"));
		assertEquals( null , studentGrades.get("test2"));
		assertEquals( null , studentGrades.get("CIS191"));
		assertEquals( null , studentGrades.get("CIS320"));
	}

	//public ArrayList<Course> parseCourses (String[] coursesAndGrades, ArrayList<Course> courses){

	@Test void testparseCourses(){
		//output
		ArrayList<Course> courseOutput = null;

		//input
		ArrayList<Course> coursesInput = new ArrayList<Course>();
		FileInfoReader reader = new FileInfoReader();
		reader.readCourse("courseInfo.txt", coursesInput);
		
		String[] coursesAndGrades1 = {"CIS191: A", "CIS320: A"};
		String[] coursesAndGrades2 = {"CIT592: A", "CIT593: A-"};
		
		//CASE 1 NORMAL FUNCTIONAL, all are valid and courses exist
		courseOutput = reader.parseCourses(coursesAndGrades1, coursesInput);
		//check size
		assertEquals(2, courseOutput.size());
		//first should be CIS191, second should be CIS320
		assertEquals("CIS191", courseOutput.get(0).getCourseCode());
		assertEquals("CIS320", courseOutput.get(1).getCourseCode());
		
		courseOutput = reader.parseCourses(coursesAndGrades2, coursesInput);
		//check size
		assertEquals(2, courseOutput.size());
		//first should be CIS191, second should be CIS320
		assertEquals("CIT592", courseOutput.get(0).getCourseCode());
		assertEquals("CIT593", courseOutput.get(1).getCourseCode());


		//CASE 2 COURSES AND GRADES HAVE COURSES NOT IN ARRAYLIST<COURSE> COURSES
		String[] coursesAndGrades3 = {"noexist: A", "123: A"};
		
		courseOutput = reader.parseCourses(coursesAndGrades3, coursesInput);
		//should be empty
		assertEquals(0, courseOutput.size());

		
		//CASE 3 COURSES AND GRADES HAVE COURSES NOT IN ARRAYLIST<COURSE> COURSES 
		//1 valid 1 invalid 

		String[] coursesAndGrades4 = {"noexist: A", "CIS191: A"};
		courseOutput = reader.parseCourses(coursesAndGrades4, coursesInput);

		//should be size 1
		assertEquals(1, courseOutput.size());
		assertEquals("CIS191", courseOutput.get(0).getCourseCode());

	}	
	

}
