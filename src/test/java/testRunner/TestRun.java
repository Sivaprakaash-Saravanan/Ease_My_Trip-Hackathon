package testRunner;

import io.cucumber.testng.CucumberOptions;
import utilities.AllureReportCleaner;
import utilities.AllureReportOpener;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import io.cucumber.testng.AbstractTestNGCucumberTests;

/**
 * TestNG runner class for executing Cucumber scenarios. Configured with desired
 * feature paths, glue code, tags, and reporting plugins. Automatically cleans
 * and opens Allure reports before and after test execution.
 */
@CucumberOptions(features = { ".\\src\\test\\resources\\features" }, glue = { "stepDefinitions",
		"hooks" }, tags = "@sanity or @regression or @fieldLevel", plugin = { "pretty",
				"html:target/cucumber-reports/cucumber-html-report.html",
				"json:target/cucumber-reports/cucumber-report.json",
				"io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm" }, monochrome = false)
public class TestRun extends AbstractTestNGCucumberTests {

	/**
	 * Cleans up existing Allure report folders before the test suite begins.
	 */
	@BeforeSuite
	public void cleanReports() {
		AllureReportCleaner.cleanAllureFolders();
	}

	/**
	 * Generates and opens Allure report after the test suite completes.
	 */
	@AfterSuite
	public void afterSuite() {
		AllureReportOpener.openAllureReport();
	}
}
