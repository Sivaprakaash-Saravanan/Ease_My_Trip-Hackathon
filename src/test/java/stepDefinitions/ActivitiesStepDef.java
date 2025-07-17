package stepDefinitions;

import java.io.IOException;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import hooks.Hook;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import objectRepositories.Activities;
import objectRepositories.HomePage;
import utilities.DriverSetup;
import utilities.ReadXMLUtil;
import utilities.ScreenshotUtil;

/*
 * Step definition class for automating the Activities booking workflow
 * on EaseMyTrip using Cucumber framework.
 * 
 * Includes test cases for validating both field-level and functional-level
 * behaviors of the Activities section using XML-based test data.
 */

public class ActivitiesStepDef {

	static WebDriver driver;
	static DriverSetup setUp;
	static Activities act;
	static HomePage home;
	public static Logger log;

	@Given("The user open the browser and navigate to EaseMyTrip")
	public void the_user_open_the_browser_and_navigate_to_ease_my_trip() {
		// Write code here that turns the phrase above into concrete actions
		log = DriverSetup.getLogger();
		log.info("***** TC_06-Activities Section Field Level*****");
		driver = Hook.driver;
		Assert.assertNotNull(driver, "Driver is null");
		home = new HomePage(driver);
		act = new Activities(driver);
	}

	@When("The user click activity")
	public void the_user_click_activity() {
		// Write code here that turns the phrase above into concrete actions
		home.goToActivities();
		boolean urlCheck = driver.getCurrentUrl().contains("activities");
		Assert.assertTrue(urlCheck, "Not navigated to activities page");
		log.info("Switched to Activities");
	}

	@When("The user enters Ba as the partial city")
	public void the_user_select_as_the_partial_city() {
		// Write code here that turns the phrase above into concrete actions
		log.info("entering partial data and checking the field level functionality");
		act.enterCity(ReadXMLUtil.getTestData("city"));
	}

	@When("The user choose September 2025 and 25 as the date")
	public void the_user_choose_and_as_the_date() {
		// Write code here that turns the phrase above into concrete actions
		act.openCalender();
		act.selectMonthAndYear(ReadXMLUtil.getTestData("monthAndYear"));
		act.selectDate(ReadXMLUtil.getTestData("date"));
	}

	@When("The user click the search button")
	public void the_user_click_the_search_button() throws IOException {
		// Write code here that turns the phrase above into concrete actions
		act.clickSearchBtn();
		ScreenshotUtil.captureScreenShot(driver, "ActivitiesFieldTC");
		log.info("Activites Field level TC completed");

	}

	@Given("The user enter Delhi as full city and select New Delhi")
	public void the_user_enter_as_full_city_and_select() {
		// Write code here that turns the phrase above into concrete actions
		log.info("***** TC_07-Activities Section Functional Level*****");
		log.info("entering data...");
		act.enterFullCity(ReadXMLUtil.getTestData("fullCity"));
		act.selectCity(ReadXMLUtil.getTestData("selectNewDelhi"));
	}

	@When("The user click search button again")
	public void the_user_click_search_button_again() {
		// Write code here that turns the phrase above into concrete actions
		act.clickSearchBtn();
		log.info("entered data and clicked on search");
	}

	@Then("The user sort results by price low to high")
	public void the_user_sort_results_by_price_low_to_high() {
		// Write code here that turns the phrase above into concrete actions
		act.openPriceLowToHigh();
	}

	@Then("The user select Day Trips checkbox")
	public void the_user_select_day_trips_checkbox() {
		// Write code here that turns the phrase above into concrete actions
		act.clickDayTripsCheckbox();
	}

	@Then("The user print the list of city names displayed")
	public void the_user_print_the_list_of_city_names_displayed() throws IOException {
		// Write code here that turns the phrase above into concrete actions
		act.printCityNames();
		log.info("displayed data");
		ScreenshotUtil.captureScreenShot(driver, "ActivitiesFunctionalTC");
		log.info("Activities Functional Level TC completed");
	}
}
