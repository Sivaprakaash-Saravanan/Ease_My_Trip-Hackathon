package testScenarios;

//import java.io.IOException;

//
//import org.openqa.selenium.WebDriver;
//import org.testng.annotations.AfterClass;
//import org.testng.annotations.BeforeClass;
//import org.testng.annotations.Parameters;
//import org.testng.annotations.Test;
//
//import cabsObjectRepo.Airport;
//import cabsObjectRepo.Hourly;
//import cabsObjectRepo.Outstation;
//import objectRepositories.Activities;
//import objectRepositories.GiftCards;
//import objectRepositories.HomePage;
//import objectRepositories.Hotels;
//import utilities.DriverSetup;

public class TestMain {
//	WebDriver driver;
//	String url = "https://www.easemytrip.com/";
//
//	DriverSetup objDriverSetup;
//	Airport objAirport;
//	Hourly objHourly;
//	Outstation objOutstation;
//	Activities objActivities;
//	GiftCards objGiftCards;
//	HomePage objHomePage;
//	Hotels objHotels;
//
//	@BeforeClass
//	@Parameters("browser")
//
//	public void setUp(String browser) throws IOException {
//		objDriverSetup = new DriverSetup();
//		driver = objDriverSetup.intializeWebDriver(browser);
//		driver.get(url);
//
//		objAirport = new Airport(driver);
//		objHourly = new Hourly(driver);
//		objOutstation = new Outstation(driver);
//		objActivities = new Activities(driver);
//		objGiftCards = new GiftCards(driver);
//		objHomePage = new HomePage(driver);
//		objHotels = new Hotels(driver);
//	}
//
//	@Test(priority = 1)
//
//	public void airportPickup() {
//		System.out.println("Running: airportPickup()");
//		objHomePage.goToCabs();
//		objAirport.pickup();
//	}
//
//	@Test(priority = 2)
//
//	public void airportDrop() {
//		System.out.println("Running: airportDrop()");
//		objHomePage.goToCabs();
//		objAirport.drop();
//	}
//
//	@Test(priority = 3)
//
//	public void outstationOneWayTrip() {
//		System.out.println("Running: outstationOneWayTrip()");
//		objHomePage.goToCabs();
//		objOutstation.oneWayTrip();
//	}
//
//	@Test(priority = 4)
//
//	public void outstationRoundTrip() {
//		System.out.println("Running: outstationRoundTrip()");
//		objHomePage.goToCabs();
//		objOutstation.roundTrip();
//	}
//	
//	@Test(priority = 5)
//
//	public void hourlyRental() {
//		System.out.println("Running: hourlyRental()");
//		objHomePage.goToCabs();
//		objHourly.run();
//	}
//
//	@Test(priority = 6)
//
//	public void browseActivities() {
//		System.out.println("Running: browseActivities()");
//		objHomePage.goToActivities();
//		objActivities.browsingForActivities();
//	}
//	
//	@Test(priority = 7)
//
//	public void searchHotels() throws Exception {
//		System.out.println("Running: searchHotels()");
//		objHomePage.goToHotels();
//		objHotels.searchHotel();
//	}
//
////	@Test(priority = 8)
////
////	public void browseGiftCards() throws Exception {
////		System.out.println("Running: browseGiftCards()");
////		driver.navigate().to(url);
////		objGiftCards.giftExecution();
////	}
//
//	@AfterClass
//
//	public void tearDown() {
//		objDriverSetup.driverTearDown();
//	}
}