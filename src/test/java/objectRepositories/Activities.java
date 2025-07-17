package objectRepositories;

import java.io.IOException;
import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import utilities.TextFileWriter;

/**
 * Page Object Model for the Activities booking section. Handles city search,
 * calendar selection, filtering, and result extraction.
 */
public class Activities {
	WebDriver driver;
	JavascriptExecutor js;
	WebDriverWait wait;

	/**
	 * Constructor that initializes web elements and supporting utilities.
	 * 
	 * @param driver WebDriver instance used to interact with browser
	 */
	public Activities(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		this.js = (JavascriptExecutor) driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
		System.out.println("Initialized Activities_ObjectRepository");
	}

	/** Input field for partial city name */
	@FindBy(id = "txtcityname")
	WebElement city;

	/** Calendar field for selecting travel date */
	@FindBy(id = "traveldateSec")
	WebElement clickDate;

	/** Element displaying current month and year in calendar */
	@FindBy(xpath = "//li[@class='wt600']")
	WebElement currentMonthYear;

	/** Button for navigating to next month */
	@FindBy(xpath = "//li[@id='traveldatenextMonth']")
	WebElement nextMonthButton;

	/** All date elements in calendar */
	@FindBy(xpath = "//table//tbody//tr//td//span")
	List<WebElement> allDates;

	/** Search button to find activities */
	@FindBy(id = "srchBtn")
	WebElement searchBtn;

	/** Input field for full city name (cleared before search) */
	@FindBy(id = "txtcityname")
	WebElement fullCity;

	/** Suggestion list container */
	@FindBy(xpath = "//div[@id='autolist']/ul")
	WebElement suggestionListContainer;

	/** List of auto-suggested cities */
	@FindBy(xpath = "//div[@id='autolist']/ul/li")
	List<WebElement> citySuggestions;

	/** Sort option: Price Low to High */
	@FindBy(xpath = "//li[@id='plh']")
	WebElement priceLowToHigh;

	/** Checkbox image for filtering day trips */
	@FindBy(xpath = "//div[@id='Day_Trips-chk']//img[@class='tickImg']")
	WebElement dayTripsCheckbox;

	/** Container element for day trips checkbox */
	@FindBy(xpath = "//div[@id='Day_Trips-chk']/..")
	WebElement dayTripsImg;

	/** List of activity city names in search results */
	@FindBy(xpath = "//div[@class='_cityname']")
	List<WebElement> cityNames;

	/**
	 * Sends partial city name to auto-suggestion field.
	 * 
	 * @param partialCityName Partial input for auto-suggestion
	 */
	public void enterCity(String partialCityName) {
		city.sendKeys(partialCityName);
	}

	/**
	 * Opens the calendar widget for date selection.
	 */
	public void openCalender() {
		clickDate.click();
	}

	/**
	 * Navigates the calendar until the target month and year are displayed.
	 * 
	 * @param targetMonthYear Desired month and year (e.g., "July 2025")
	 */
	public void selectMonthAndYear(String targetMonthYear) {
		while (true) {
			String displayed = currentMonthYear.getText().trim();
			if (displayed.equalsIgnoreCase(targetMonthYear)) {
				break;
			}
			nextMonthButton.click();
		}
	}

	/**
	 * Selects the desired date from the calendar.
	 * 
	 * @param targetDate Day of the month to select (e.g., "15")
	 */
	public void selectDate(String targetDate) {
		for (WebElement dateElement : allDates) {
			if (dateElement.getText().equals(targetDate)) {
				dateElement.click();
				break;
			}
		}
	}

	/**
	 * Clicks the search button to load activity results.
	 */
	public void clickSearchBtn() {
		searchBtn.click();
	}

	/**
	 * Clears the city input field and enters full city name.
	 * 
	 * @param fullCityName Complete name of the city to search
	 */
	public void enterFullCity(String fullCityName) {
		fullCity.clear();
		fullCity.sendKeys(fullCityName);
	}

	/**
	 * Selects city from auto-suggestions using partial match.
	 * 
	 * @param cityNamePartial Partial city name used to locate suggestions
	 */
	public void selectCity(String cityNamePartial) {
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='autolist']/ul/li")));
		while (true) {
			try {
				for (WebElement city : citySuggestions) {
					String text = city.getText();
					if (text.startsWith(cityNamePartial) && text.contains(cityNamePartial)) {
						city.click();
						break;
					}
				}
				break;
			} catch (StaleElementReferenceException e) {
			}
		}
	}

	/**
	 * Opens the sort menu and selects "Price Low to High".
	 */
	public void openPriceLowToHigh() {
		boolean chk = priceLowToHigh.isDisplayed();
		Assert.assertTrue(chk, "Sort option is not displayed");
		priceLowToHigh.click();
	}

	/**
	 * Applies the Day Trips filter using JavaScript click for reliability.
	 */
	public void clickDayTripsCheckbox() {
		boolean chk = dayTripsImg.isDisplayed();
		Assert.assertTrue(chk, "Day trips checkbox is not displayed");
		js.executeScript("arguments[0].click();", dayTripsCheckbox);
	}

	/**
	 * Prints up to five city names from activity results into a text file.
	 * 
	 * @throws IOException if file writing fails
	 */
	public void printCityNames() throws IOException {
		Assert.assertTrue(cityNames.size() > 0, "City names not displayed");
		int i = 1;
		TextFileWriter.clearFile("ActivitiesResults.txt");
		TextFileWriter.writeToTextFile("ActivitiesResults.txt", "Activities:\n-------------------------------------");
		for (WebElement city : cityNames) {
			if (i > 5) {
				break;
			}
			TextFileWriter.writeToTextFile("ActivitiesResults.txt", city.getText());
			i++;
		}
	}
}
