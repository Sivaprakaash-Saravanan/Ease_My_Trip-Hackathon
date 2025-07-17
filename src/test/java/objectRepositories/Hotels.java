package objectRepositories;

import java.util.List;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import utilities.JsonDataWriter;

/**
 * Page Object Model for Hotel Booking functionality. Handles selecting city,
 * check-in/check-out dates, applying filters, sorting, and retrieving hotel
 * name and price.
 */
public class Hotels {
	WebDriver driver;
	static JavascriptExecutor jse;

	/**
	 * Initializes hotel booking page elements.
	 * 
	 * @param driver WebDriver instance for browser automation
	 */
	public Hotels(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	// Web Elements

	/** City selection dropdown/button */
	@FindBy(xpath = "//div/span[@class='hp_city']")
	WebElement cityName;

	/** Input box for typing city name */
	@FindBy(id = "txtCity")
	WebElement name;

	/** Suggested city options */
	@FindBy(xpath = "//div[@class='w_85 dest_namect']")
	List<WebElement> citynames;

	/** Click to open check-in date selector */
	@FindBy(xpath = "//div[@class=\"hp_inputBox ht-dates\"]//div[@id='htl_dates']")
	WebElement clickCI;

	/** Check-in month dropdown */
	@FindBy(xpath = "//select[@data-handler='selectMonth']")
	WebElement month;

	/** List of available check-in month options */
	@FindBy(xpath = "//select[@data-handler=\"selectMonth\"]/option")
	List<WebElement> selectCImonth;

	/** List of check-in date options */
	@FindBy(xpath = "//td[@data-handler=\"selectDay\"]")
	List<WebElement> datesForCI;

	/** Click to open check-out date selector */
	@FindBy(xpath = "//div[@class=\"hp_inputBox ht-dates\"]/span[text()=\"Check-Out\"]")
	WebElement clickCO;

	/** List of available check-out month options */
	@FindBy(xpath = "//select[@data-handler=\"selectMonth\"]/option")
	List<WebElement> selectCOmonth;

	/** List of check-out date options */
	@FindBy(xpath = "//td[@data-handler=\"selectDay\"]")
	List<WebElement> datesForCO;

	/** Button to confirm date selection */
	@FindBy(xpath = "//a[text()='Done']")
	WebElement clickdone;

	/** Search button to retrieve hotel results */
	@FindBy(id = "btnSearch")
	WebElement searchBtn;

	/** Price filter label */
	@FindBy(xpath = "//span[text()='₹ 2,001 - ₹ 4,000 ']")
	WebElement price;

	/** Corresponding checkbox for price filter */
	@FindBy(xpath = "//span[text()='₹ 2,001 - ₹ 4,000 ']/following-sibling::span[contains(@class,'checkmark')]")
	WebElement priceCheckBox;

	/** Priority sort dropdown */
	@FindBy(xpath = "//div[contains(@class,'drp-bx')]")
	WebElement priority;

	/** Price Low to High sort option */
	@FindBy(xpath = "//div[text()='Low to High']")
	WebElement priceLowToHigh;

	/** Hotel name of the first result */
	@FindBy(xpath = "(//div[contains(@class,'result-item')])[1]//span[@class='ng-star-inserted']/a")
	WebElement hotelName;

	/** Hotel price of the first result */
	@FindBy(xpath = "(//div[contains(@class,'result-item')])[1]//span[@class='CurrncyCD_INR']/following-sibling::span")
	WebElement hotelPrice;

	/**
	 * Selects a city based on given parameters.
	 * 
	 * @param cityname Full city name to type
	 * @param place    Matching location to begin with
	 * @param city     Target city to match within suggestion list
	 * @throws Exception if selection fails
	 */
	public void selectingCity(String cityname, String place, String city) throws Exception {
		cityName.click();
		name.sendKeys(cityname);

		while (true) {
			try {
				for (WebElement i : citynames) {
					String placename = i.getText();
					if (placename.startsWith(place) && placename.contains(city)) {
						i.click();
						break;
					}
				}
				break;
			} catch (StaleElementReferenceException e) {
				// Retry on stale elements
			}
		}
	}

	/**
	 * Selects check-in month and date.
	 * 
	 * @param ciMonth Desired month (e.g., "July")
	 * @param ciDate  Desired date (e.g., "15")
	 * @throws Exception if interaction fails
	 */
	public void checkInDate(String ciMonth, String ciDate) throws Exception {
		clickCI.click();
		month.click();

		for (WebElement mon : selectCImonth) {
			if (mon.getText().equals(ciMonth)) {
				mon.click();
				break;
			}
		}

		for (WebElement date : datesForCI) {
			if (date.getText().equals(ciDate)) {
				date.click();
				break;
			}
		}
	}

	/**
	 * Selects check-out month and date, then clicks 'Done'.
	 * 
	 * @param coMonth Desired check-out month
	 * @param coDate  Desired check-out date
	 */
	public void checkOutDate(String coMonth, String coDate) {
		for (WebElement mon : selectCOmonth) {
			if (mon.getText().equals(coMonth)) {
				mon.click();
				break;
			}
		}

		for (WebElement date : datesForCO) {
			if (date.getText().equals(coDate)) {
				date.click();
				break;
			}
		}
		clickdone.click();
	}

	/**
	 * Initiates hotel search.
	 * 
	 * @throws Exception if button is not clickable
	 */
	public void search() throws Exception {
		searchBtn.click();
	}

	/**
	 * Sorts hotel results by price and extracts first hotel's name and price.
	 * Writes result to a JSON file.
	 * 
	 * @throws Exception if sorting fails or data is not displayed
	 */
	public void hotelNameAndPrice() throws Exception {
		Assert.assertTrue(priority.isDisplayed(), "Sort option is not displayed");
		priority.click();
		Assert.assertTrue(priceLowToHigh.isDisplayed(), "Price Low to High Sort option is not displayed");
		priceLowToHigh.click();
		Assert.assertNotNull(hotelName.getText(), "Hotel name not displayed");
		Assert.assertNotNull(hotelPrice.getText(), "Hotel price not displayed");

		JsonDataWriter.writeSimpleData(hotelName.getText(), hotelPrice.getText(), "HotelWriting.json");
	}
}
