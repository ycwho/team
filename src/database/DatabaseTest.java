package database;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * 
 */

/**
 * @author Ning Wei
 *
 */
class TestDatabase {

	private Database testDatabase;

	@BeforeEach
	public void test() {
		testDatabase = new Database();
	}

	// test checkExistUserTest
	@Test
	public void checkExistUserTest1() {
		boolean expectedResult = true;
		boolean actualResult = testDatabase.checkExistUser("Henry", "123");
		assertEquals(expectedResult, actualResult);
	}

	// test checkExistUserTest
	@Test
	public void checkExistUserTest2() {
		boolean expectedResult = false;
		boolean actualResult = testDatabase.checkExistUser("Tina", "pass");
		assertEquals(expectedResult, actualResult);
	}

	// test checkExistUserTest
	@Test
	public void checkExistUserTest3() {
		boolean expectedResult = false;
		boolean actualResult = testDatabase.checkExistUser("", "");
		assertEquals(expectedResult, actualResult);
	}

	// test checkExistUserTest
	@Test
	public void checkExistUserTest4() {
		boolean expectedResult = false;
		boolean actualResult = testDatabase.checkExistUser("Bilk", "");
		assertEquals(expectedResult, actualResult);
	}

	// test checkExistUserTest
	@Test
	public void checkExistUserTest5() {
		boolean expectedResult = true;
		boolean actualResult = testDatabase.checkExistUser("Tom", "Password");
		assertEquals(expectedResult, actualResult);
	}

	// test checkPassword
	@Test
	public void checkPassword1() {
		boolean expectedResult = false;
		boolean actualResult = testDatabase.checkPassword("Henry", "");
		assertEquals(expectedResult, actualResult);
	}

	// test checkPassword
	@Test
	public void checkPassword2() {
		boolean expectedResult = true;
		boolean actualResult = testDatabase.checkPassword("Henry", "123");
		assertEquals(expectedResult, actualResult);
	}

	// test checkPassword
	@Test
	public void checkPassword3() {
		boolean expectedResult = false;
		boolean actualResult = testDatabase.checkPassword("", "123");
		assertEquals(expectedResult, actualResult);
	}

	// test checkPassword
	@Test
	public void checkPassword4() {
		boolean expectedResult = false;
		boolean actualResult = testDatabase.checkPassword("Tom", "123");
		assertEquals(expectedResult, actualResult);
	}

	// test saveShipPosition
	@Test
	public void saveShipPosition1() {
		int expectedResult = 0;
		int actualResult = testDatabase.saveShipPosition("Jim", "ships1",
				"46@56@66@76@86-87@77@67@57-24@23@22-42@52@62-53@43");
		assertEquals(expectedResult, actualResult);
	}

	// test saveShipPosition
	@Test
	public void saveShipPosition2() {
		int expectedResult = 1;
		int actualResult = testDatabase.saveShipPosition("Tina", "ships1",
				"46@56@66@76@86-87@77@67@57-24@23@22-42@52@62-53@43");
		assertEquals(expectedResult, actualResult);
	}

	// test saveShipPosition
	@Test
	public void saveShipPosition3() {
		int expectedResult = 0;
		int actualResult = testDatabase.saveShipPosition("John", "S1",
				"43@44@45@46@47-65@66@67@68-84@83@82-50@40@30-13@14");
		assertEquals(expectedResult, actualResult);
	}

	// test loadShipPosition
	@Test
	public void loadShipPosition1() {
		String expectedResult = "51@52@53@54@55-27@37@47@57-86@85@84-13@12@11-9@19";
		String actualResult = testDatabase.loadShipPosition("John", "ships");
		assertEquals(expectedResult, actualResult);
	}

	// test loadShipPosition
	@Test
	public void loadShipPosition2() {
		String expectedResult = "EMPTY";
		String actualResult = testDatabase.loadShipPosition("John", "ships1");
		assertEquals(expectedResult, actualResult);
	}

	// test loadShipPosition
	@Test
	public void loadShipPosition3() {
		String expectedResult = "EMPTY";
		String actualResult = testDatabase.loadShipPosition("", "ships1");
		assertEquals(expectedResult, actualResult);
	}

	// test deleteOption
	@Test
	public void deleteOption1() {
		boolean expectedResult = true;
		boolean actualResult = testDatabase.deleteOption("Jim", "ship");
		assertEquals(expectedResult, actualResult);
	}

	// test deleteOption
	@Test
	public void deleteOption2() {
		boolean expectedResult = true;
		boolean actualResult = testDatabase.deleteOption("j", "ships1");
		assertEquals(expectedResult, actualResult);
	}

	// test recordScore
	@Test
	public void recordScore1() {
		int expectedResult = 0;
		int actualResult = testDatabase.recordScore("Jim", 3);
		assertEquals(expectedResult, actualResult);
	}

	// test recordScore
	@Test
	public void recordScore2() {
		int expectedResult = 1;
		int actualResult = testDatabase.recordScore("", 3);
		assertEquals(expectedResult, actualResult);
	}
	
	// test recordScore
		@Test
		public void recordScore3() {
			int expectedResult = 0;
			int actualResult = testDatabase.recordScore("Tom", -1);
			assertEquals(expectedResult, actualResult);
		}

}

