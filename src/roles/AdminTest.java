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

class AdminTest {
	
    static public ArrayList <Course> courses = new ArrayList <Course>();
	public static ArrayList<User> users = new ArrayList<User>();
	Admin admin;
	Scanner scan;
	public InputStream sysInBackup = System.in; // backup System.in to restore it later
	public ByteArrayInputStream in = new ByteArrayInputStream("My string".getBytes());
	
	@BeforeEach                                         
    void setUp() {
    	FileInfoReader reader = new FileInfoReader();
    	
    	reader.readCourse("courseTest.txt", courses);
		//Initialize user data by calling readUsers function for each user data file
		//Append all users to ArrayList users
		reader.readUsers("adminTest.txt", "admin", users, courses);
		reader.readUsers("studentTest.txt", "student", users, courses);
		reader.readUsers("profTest.txt", "professor", users, courses);

    	admin = new Admin("username", "password", "name", "id", courses, users);
    }

    
    @Test
	void testAddNewCourse() {
    	
       	ByteArrayInputStream in = new ByteArrayInputStream("q\n".getBytes());
    	scan = new Scanner(in);    	
    	int sizeStart = courses.size();
    	
    	// CASE 1 "Q"out immediately
    	//nothing is created so size remains the same
    	assertEquals(sizeStart, User.courses.size());
    	admin.addNewCourse(scan);
    	assertEquals(sizeStart, User.courses.size());
    	
    	/**************************************/

    	//CASE 2 Valid entry with existing prof
    	String case2 = "testid\nname\nprofname1\nM\n1:00\n2:00\n1\nq\n";
    	in = new ByteArrayInputStream(case2.getBytes());
    	scan = new Scanner(in); 
    	admin.addNewCourse(scan);
    	
    	//find it
    	Course tempCourse = User.courses.getLast();
    	assertEquals("testid", tempCourse.getCourseCode());
    	assertEquals("profname1", tempCourse.getCourseInstructor());
    	assertEquals('M', tempCourse.getDaysList().getFirst());
    	assertEquals(100, tempCourse.getTimeStart());
    	assertEquals(200, tempCourse.getTimeEnd());
    	

    	/**************************************/

    	//CASE 3 Valid entry with new professor
    	String case3 = "testid87\nname\ndoesn'texist1\nM\n1:00\n2:00\n1\nprofid\ndoesn'texist\npassword590\ntestid87\nname\nprofid\nM\n1:00\n2:00\n1\nq\nq\n";
    			//\ntestid2\nname\ndoesn'texist\nM\n1:00\n2:00\n1\nq\n";
    	in = new ByteArrayInputStream(case3.getBytes());
    	scan = new Scanner(in); 
    	//scan = new Scanner(System.in);
    	admin.addNewCourse(scan);
    	
    	//find it
    	tempCourse = User.courses.getLast();
    	assertEquals("testid", tempCourse.getCourseCode());
    	assertEquals("profname1", tempCourse.getCourseInstructor());
    	assertEquals('M', tempCourse.getDaysList().getFirst());
    	assertEquals(100, tempCourse.getTimeStart());
    	assertEquals(200, tempCourse.getTimeEnd());
    	
    	
    	//CASE 4 Valid entry but exit out early
    	String case4 = "testid99\nname\ndoesn'texist1\nM\n1:00\n2:00\n1\nprofid\ndoesn'texist\nq\nq\ntestidoo\nname\nprofid\nM\n1:00\n2:00\n1\nq\nq\n";
    	in = new ByteArrayInputStream(case4.getBytes());
    	scan = new Scanner(in); 
    	//scan = new Scanner(System.in);
    	admin.addNewCourse(scan);
    	assertEquals(sizeStart + 2, User.courses.size());

    }

    @Test
	void testDeleteCourse() {
    	
       	ByteArrayInputStream in = new ByteArrayInputStream("q\n".getBytes());
    	scan = new Scanner(in); 
    	int startSize = courses.size();
    	
    	// CASE 1 "Q"out immediately
    	//nothing is created so size remains the same
    	assertEquals(startSize, User.courses.size());
    	admin.deleteCourse(scan);
    	assertEquals(startSize, User.courses.size());
    	

    	//Case 2, delete one id1; coursename4; profname2; F; 16:30; 18:00; 1
    	in = new ByteArrayInputStream("id1\nq\n".getBytes());
    	scan = new Scanner(in);  
    	assertEquals(startSize, User.courses.size());
    	admin.deleteCourse(scan);
    	//deletion successful so size decreases
    	assertEquals(startSize - 1, User.courses.size());

    	System.out.println(User.courses.size());

    	//Case 3, delete one id4; coursename4; profname2; F; 16:30; 18:00; 1
    	in = new ByteArrayInputStream("id3\nq\n".getBytes());
    	scan = new Scanner(in);  
    	assertEquals(startSize-1, User.courses.size());
    	
    	System.out.println(User.courses.size());
    	
    	admin.deleteCourse(scan);
    	//deletion successful so size decreases
    	assertEquals(startSize-2, User.courses.size());
    }


    @Test
	void addProfessor() {
       	ByteArrayInputStream in = new ByteArrayInputStream("q\n".getBytes());
    	scan = new Scanner(in);  
    	int startSize = User.courses.size();
    	
    	// CASE 1 "Q"out immediately
    	//nothing is created so size remains the same
    	assertEquals(startSize, User.courses.size());
    	admin.addProfessor(scan);
    	assertEquals(startSize, User.courses.size());
    	
    	//CASE 2 Valid entry with prof: name; id; username; pword
    	String case2 = "name\nid\nusername\npword\n";
    	in = new ByteArrayInputStream(case2.getBytes());
    	scan = new Scanner(in); 
    	admin.addProfessor(scan);
    	
    	//find it
    	User tempProf = users.getLast();
    	assertEquals("name", tempProf.getName());
    	assertEquals("id", tempProf.getId());
    	assertEquals("username", tempProf.getUserName());
    	assertEquals("pword", tempProf.getPassword());
    	
    	
    	//CASE 3 Valid entry with prof: name; id; username; pword, quit halfway
    	String case3 = "name\nid\nusername\nq\npword\n";
    	int sizeStart = users.size();
    	
    	in = new ByteArrayInputStream(case3.getBytes());
    	scan = new Scanner(in); 
    	admin.addProfessor(scan);
    	
    	//find it
    	assertEquals(sizeStart, users.size());
    }
    
    
    @Test
	void deleteProfessor() {
       	ByteArrayInputStream in = new ByteArrayInputStream("q\n".getBytes());
    	scan = new Scanner(in);   
    	int sizeStart = users.size();

    	
    	// CASE 1 "Q"out immediately
    	//nothing is created so size remains the same
    	assertEquals(sizeStart, users.size());
    	admin.deleteProfessor(scan);
    	assertEquals(sizeStart, users.size());
    	
    	//CASE 2 Valid entry with punmae1
    	String case2 = "puname1\nq\n";
//    	int sizeStart = users.size();
    	
    	in = new ByteArrayInputStream(case2.getBytes());
    	scan = new Scanner(in); 
    	admin.deleteProfessor(scan);
    	
    	//validate it
    	assertFalse(sizeStart == users.size());
    	System.out.println(sizeStart + " " + users.size());
    	
    	
    	//CASE 3 Invalid name and exit out    	
    	String case3 = "punamdde1\nq\nq\n";
    	sizeStart = users.size();
    	
    	in = new ByteArrayInputStream(case3.getBytes());
    	scan = new Scanner(in); 
    	admin.deleteProfessor(scan);
    	
    	//validate it
    	assertTrue(sizeStart == users.size());
    }
    
    @Test
	void addStudent() {
       	ByteArrayInputStream in = new ByteArrayInputStream("q\n".getBytes());
    	scan = new Scanner(in);  
    	int startSize = User.courses.size();
    	
    	// CASE 1 "Q"out immediately
    	//nothing is created so size remains the same
    	admin.addStudent(scan);
    	assertEquals(startSize, User.courses.size());
    	
    	//CASE 2 Valid entry with prof: name; id; username; pword
    	String case2 = "tests\nid\nuname\npword\nn\nq\n";
    	in = new ByteArrayInputStream(case2.getBytes());
    	scan = new Scanner(in); 
    	admin.addStudent(scan);
    	
    	//validate
    	User tempStudent = users.getLast();
    	assertEquals("tests", tempStudent.getName());
    	assertEquals("id", tempStudent.getId());
    	assertEquals("uname", tempStudent.getUserName());
    	assertEquals("pword", tempStudent.getPassword());
    	
    	
    	//CASE 3 Valid entry with duplicate name
    	int sizeStart = users.size();
    	
    	in = new ByteArrayInputStream(case2.getBytes());
    	scan = new Scanner(in); 
    	admin.addStudent(scan);

    	//duplicates not allowed
    	assertEquals(sizeStart, users.size());
    	
    	
    }
    
    @Test
	void deleteStudent() {
       	ByteArrayInputStream in = new ByteArrayInputStream("q\nq\n".getBytes());
    	scan = new Scanner(in); 
    	int startSize = User.courses.size();
    	
    	// CASE 1 "Q"out immediately
    	//nothing is created so size remains the same
    	assertEquals(startSize, User.courses.size());
    	admin.deleteStudent(scan);
    	assertEquals(startSize, User.courses.size());
    	
    	//CASE 2 Valid entry with  testStudent01
    	String case2 = "testStudent01\nq\nq\n";
    	int sizeStart = users.size();
    	
    	in = new ByteArrayInputStream(case2.getBytes());
    	scan = new Scanner(in); 
    	admin.deleteStudent(scan);
    	
    	//validate it
    	assertFalse(sizeStart == users.size());    	
    	
    	//CASE 3 Invalid name and exit out    	
    	String case3 = "punamdde1\nq\nq\n";
    	sizeStart = users.size();
    	
    	in = new ByteArrayInputStream(case3.getBytes());
    	scan = new Scanner(in); 
    	admin.deleteProfessor(scan);
    	
    	//validate it
    	assertTrue(sizeStart == users.size());
    	
    	
    }

      
//    addStudent(Scanner scan)
    
//    deleteStudent(Scanner scan)

	
	
	// NOT TESTABLE
	//printActionMenu() {
	//public void performAction(Scanner scan) {
    //void viewAllCourses() {





}
