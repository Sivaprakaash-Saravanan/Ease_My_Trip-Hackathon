package testRunner;

import io.cucumber.testng.CucumberOptions;
import utilities.AllureReportCleaner;
import utilities.AllureReportOpener;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import io.cucumber.testng.AbstractTestNGCucumberTests;

@CucumberOptions(features = { ".\\src\\test\\resources\\features" }, glue = { "stepDefinitions",
		"hooks" }, tags = "@sanity or @regression or @fieldLevel", plugin = { "pretty",
				"html:target/cucumber-reports/cucumber-html-report.html",
				"json:target/cucumber-reports/cucumber-report.json",
				"io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm" }, monochrome = false)
public class TestRun extends AbstractTestNGCucumberTests {

	@BeforeSuite
	public void cleanReports() {
		AllureReportCleaner.cleanAllureFolders();
	}

	@AfterSuite
	public void afterSuite() {
		AllureReportOpener.openAllureReport();
	}
}