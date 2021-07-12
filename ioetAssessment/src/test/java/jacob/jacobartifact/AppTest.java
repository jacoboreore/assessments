package jacob.jacobartifact;

import java.util.ArrayList;

import com.jacob.config.ApplicationSettings;
import com.jacob.process.AppInitialization;
import com.jacob.process.ProcessOperations;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest extends TestCase {

	/**
	 * Create the test case
	 *
	 * @param testName name of the test case
	 */
	public AppTest(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(AppTest.class);
	}

	/**
	 * Rigourous Test
	 */
	public void testApp() {

		String[] args = { "C:\\jacob" };

		try {

			// init
			AppInitialization.validateInputs(args);
			AppInitialization.initConfig(args);

			ProcessOperations.processFile(ApplicationSettings.getFullPathInput());

			ArrayList<String> processedEmployees = ProcessOperations.getProcessedEmployees();

			TestCase.assertEquals("RENE=215.0", processedEmployees.get(processedEmployees.indexOf("RENE=215.0")));
			TestCase.assertEquals("ASTRID=85.0", processedEmployees.get(processedEmployees.indexOf("ASTRID=85.0")));

		} catch (Exception e) {
			TestCase.assertEquals(true, false);
		}

	}
}
