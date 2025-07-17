package stepDefinitions;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import hooks.Hook;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import objectRepositories.HomePage;
import objectRepositories.Hotels;
import utilities.DriverSetup;
import utilities.ReadXMLUtil;
import utilities.ScreenshotUtil;

/*
 * Step definition class for automating Hotel booking functionality on EaseMyTrip.
 * 
 * This script validates the hotel search flow—from selecting city and dates to retrieving hotel details—
 * using Cucumber and Page Object Model. Test data is pulled dynamically from XML files.
 */

public class HotelsStepDef {
	static WebDriver driver;
	static DriverSetup setUp;
	static Hotels hotels;
	static HomePage home;
	public static Logger log;

	@Given("the user clicks the hotels tab")
	public void the_user_clicks_the_hotels_tab() {
		// Write code here that turns the phrase above into concrete actions
		log = DriverSetup.getLogger();
		log.info("***** TC_08-Hotels Functionality Check*****");
		driver = Hook.driver;
		Assert.assertNotNull(driver, "Driver is null");
		home = new HomePage(driver);
		hotels = new Hotels(driver);
		home.goToHotels();
		boolean urlCheck = driver.getCurrentUrl().contains("hotels");
		Assert.assertTrue(urlCheck, "Not navigated to hotels page");
		log.info("Swiched to Hotels Section");
	}

	@Given("the user enters city name as Delhi and selects Dwarka with city as Delhi")
	public void the_user_enters_city_name_as_and_selects_with_city_as() throws Exception {
		// Write code here that turns the phrase above into concrete actions
		log.info("entering data...");
		hotels.selectingCity(ReadXMLUtil.getTestData("cityName"), ReadXMLUtil.getTestData("place"),
				ReadXMLUtil.getTestData("City"));
	}

	@Given("the user selects the check-in date as Aug and 15")
	public void the_user_selects_the_check_in_date_as_and() throws Exception {
		// Write code here that turns the phrase above into concrete actions
		hotels.checkInDate(ReadXMLUtil.getTestData("ciMonth"), ReadXMLUtil.getTestData("ciDate"));
	}

	@Given("the user selects the check-out date as Aug and 20 with number of rooms")
	public void the_user_selects_the_check_out_date_as_and_with_number_of_rooms() {
		// Write code here that turns the phrase above into concrete actions
		hotels.checkOutDate(ReadXMLUtil.getTestData("coMonth"), ReadXMLUtil.getTestData("coDate"));
	}

	@When("the user clicks the  Search button")
	public void the_user_clicks_the_search_button() throws Exception {
		// Write code here that turns the phrase above into concrete actions
		hotels.search();
		log.info("entered data and clicked on search");
	}

	@Then("the filtered hotel names and prices should be displayed")
	public void the_filtered_hotel_names_and_prices_should_be_displayed() throws Exception {
		// Write code here that turns the phrase above into concrete actions
		hotels.hotelNameAndPrice();
		log.info("displayed data");
		ScreenshotUtil.captureScreenShot(driver, "HotelsTC");
		log.info("Hotels TC completed");
	}
}
