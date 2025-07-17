package hooks;

import java.io.ByteArrayInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.Reporter;

import cabsObjectRepo.Airport;
import io.cucumber.java.After;
import io.cucumber.java.AfterAll;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.Scenario;
import io.qameta.allure.Allure;
import objectRepositories.HomePage;
import utilities.DriverSetup;

/**
 * Cucumber hook class for managing test lifecycle. Handles WebDriver
 * initialization, scenario-level reporting, and teardown after all scenarios
 * are executed.
 */
public class Hook {

	public static WebDriver driver;
	public static String url;
	public static DriverSetup setUp;
	public static Airport airport;
	public static HomePage home;
	static String browser;
	static Properties p;

	/**
	 * Executed before all scenarios. Initializes the WebDriver and navigates to
	 * base URL. Browser type is retrieved from TestNG XML parameters.
	 *
	 * @throws IOException if config.properties fails to load
	 */
	@BeforeAll
	public static void setup() throws IOException {
		setUp = new DriverSetup();
		FileReader file = new FileReader(System.getProperty("user.dir") + "/src/test/resources/data/config.properties");
		p = new Properties();
		p.load(file);
		url = p.getProperty("baseURL");

		// Get browser name from TestNG XML
		browser = Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getParameter("browser");

		driver = setUp.intializeWebDriver(browser);
		driver.get(url);
	}

	/**
	 * After each scenario, attach a screenshot to Allure report if the test has
	 * failed.
	 *
	 * @param scenario Cucumber Scenario object
	 */
	@After
	public void attachSS(Scenario scenario) {
		if (scenario.isFailed()) {
			byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
			Allure.addAttachment("Screenshot on Failure", new ByteArrayInputStream(screenshot));
		}
	}

	/**
	 * Executed once after all scenarios finish. Quits the WebDriver.
	 */
	@AfterAll
	public static void tearDown() {
		setUp.driverTearDown();
	}
}
