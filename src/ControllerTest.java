import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import roles.*;

/*
 * JUnit test methods for contrroller class
 * @Author Langdon Fennell
 * @Author Yineng Zhang
 */
class ControllerTest {
	
	Controller controller;
	ArrayList<User> users;
	
	@BeforeEach
	void setUp() throws Exception {
		
		controller = new Controller();
	}

	@Test
	void testCheckValidInput() {
		
		String[] loginOptions = {"1", "2", "3", "4"};
		String testInput1 = "1";
		String testInput2 = "0";
		
		//Test Case 1: Confirm valid option returns true
		assertEquals(true, controller.checkValidInput(loginOptions, testInput1));
		
		//Test Case 2: Confirm invalid option returns false
		assertEquals(false, controller.checkValidInput(loginOptions, testInput2));

	}

	@Test
	void testFindUser() {
		
		String testUser1 = "admin01";
		String testUser2 = "student99";
		
		//Test Case 1: Confirm valid option returns true
		assertEquals(true, controller.findUser(testUser1));
		
		//Test Case 2: Confirm invalid option returns false
		assertEquals(false, controller.findUser(testUser2));
		
		
	}


}
