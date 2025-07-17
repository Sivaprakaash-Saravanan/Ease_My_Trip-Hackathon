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

public class Hook {
	public static WebDriver driver;
	public static String url;
	public static DriverSetup setUp;
	public static Airport airport;
	public static HomePage home;
	static String browser;
	static Properties p;

	@BeforeAll
	public static void setup() throws IOException {
		setUp = new DriverSetup();
		FileReader file = new FileReader(System.getProperty("user.dir") + "/src/test/resources/data/config.properties");
		p = new Properties();
		p.load(file);
		url = p.getProperty("baseURL");
		browser = Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getParameter("browser");
		driver = setUp.intializeWebDriver(browser);
		driver.get(url);
	}

	@After
	public void attachSS(Scenario scenario) {
		if (scenario.isFailed()) {
			byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
			Allure.addAttachment("Screenshot on Failure", new ByteArrayInputStream(screenshot));
		}
	}

	@AfterAll
	public static void tearDown() {
		setUp.driverTearDown();
	}
}