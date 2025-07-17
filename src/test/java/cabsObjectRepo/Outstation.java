package cabsObjectRepo;

import java.time.Duration;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import utilities.JsonDataWriter;

/**
 * Page Object Model for Outstation Cab Booking. Handles user interaction for
 * selecting source, destination, date, time, filtering, and extracting results
 * into JSON.
 */
public class Outstation {
	WebDriver driver;
	WebDriverWait wait;

	/**
	 * Initializes the Outstation booking page with driver and wait configuration.
	 * 
	 * @param driver WebDriver instance to interact with browser
	 */
	public Outstation(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		PageFactory.initElements(driver, this);
	}

	// Web elements for booking workflow

	@FindBy(id = "li2")
	WebElement outstation;

	@FindBy(id = "sourceName")
	WebElement from;

	@FindBy(id = "a_FromSector_show")
	WebElement fromCity;

	@FindBy(id = "destinationName")
	WebElement to;

	@FindBy(id = "a_ToSector_show")
	WebElement toCity;

	@FindBy(id = "datepicker")
	WebElement pickupDate;

	@FindBy(id = "rtag")
	WebElement returnDateSelection;

	@FindBy(id = "rdatepicker")
	WebElement returnDate;

	@FindBy(xpath = "//span[@class='ui-datepicker-month']")
	WebElement displayedMonth;

	@FindBy(xpath = "//span[@class='ui-datepicker-year']")
	WebElement displayedYear;

	@FindBy(xpath = "//span[@class='ui-icon ui-icon-circle-triangle-e']")
	WebElement nextMonthBtn;

	@FindBy(xpath = "//span[@class='ui-icon ui-icon-circle-triangle-w']")
	WebElement prevMonthBtn;

	@FindBy(xpath = "//label[@for='am']")
	WebElement amSelectorPickup;

	@FindBy(xpath = "//label[@for='pm']")
	WebElement pmSelectorPickup;

	@FindBy(xpath = "//div[@id='rap']//label[@for='ram']")
	WebElement amSelectorReturn;

	@FindBy(xpath = "//div[@id='rap']//label[@for='rpm']")
	WebElement pmSelectorReturn;

	@FindBy(xpath = "//div[@onclick='Done()']")
	WebElement pickupDoneBtn;

	@FindBy(xpath = "//div[@onclick='rDone()']")
	WebElement returnDoneBtn;

	@FindBy(xpath = "//div[@onclick='GetList()']")
	WebElement searchBtn;

	@FindBy(xpath = "//label[3]//div[1]//span[2]")
	WebElement suvFilter;

	@FindBy(xpath = "//div[@class='blue-link']//a")
	WebElement options;

	@FindBy(xpath = "//span[@class='close']")
	WebElement closeBtn;

	@FindBy(xpath = "//div[@id='StartCity']//ul//li")
	List<WebElement> citySuggestions_from;

	@FindBy(xpath = "//div[@id='EndCity']//ul//li")
	List<WebElement> citySuggestions_to;

	@FindBy(xpath = "//label[contains(@class, 'fare')]")
	List<WebElement> vehicleBlocks;

	/**
	 * Switches to the outstation booking section.
	 */
	public void switchToOutstation() {
		outstation.click();
	}

	/**
	 * Enters and selects the source city.
	 * 
	 * @param osFromCity Source city name
	 */
	public void fromField(String osFromCity) {
		from.click();
		fromCity.click();
		fromCity.clear();
		fromCity.sendKeys(osFromCity);

		while (true) {
			try {
				wait.until(ExpectedConditions.visibilityOfAllElements(citySuggestions_from));
				for (WebElement suggestion : citySuggestions_from) {
					String text = suggestion.getText();
					if (text.startsWith(osFromCity) && text.contains(osFromCity)) {
						suggestion.click();
						break;
					}
				}
				break;
			} catch (StaleElementReferenceException e) {
			}
		}
	}

	/**
	 * Enters and selects the destination city.
	 * 
	 * @param osToCity Destination city name
	 */
	public void toField(String osToCity) {
		to.click();
		toCity.click();
		toCity.clear();
		toCity.sendKeys(osToCity);

		while (true) {
			try {
				wait.until(ExpectedConditions.visibilityOfAllElements(citySuggestions_to));
				for (WebElement suggestion : citySuggestions_to) {
					String text = suggestion.getText();
					if (text.startsWith(osToCity) && text.contains(osToCity)) {
						suggestion.click();
						break;
					}
				}
				break;
			} catch (StaleElementReferenceException e) {
			}
		}
	}

	/**
	 * Selects pickup date from calendar widget.
	 * 
	 * @param date Date string in dd/MM/yyyy format
	 */
	public void pickupDate(String date) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate targetDate = LocalDate.parse(date, formatter);
		String targetDay = String.valueOf(targetDate.getDayOfMonth());
		YearMonth targetYearMonth = YearMonth.from(targetDate);

		pickupDate.click();

		while (true) {
			String month = displayedMonth.getText();
			String year = displayedYear.getText();

			DateTimeFormatter monthyearFormatter = DateTimeFormatter.ofPattern("MMMM yyyy", Locale.ENGLISH);
			YearMonth displayedYearMonth = YearMonth.parse(month + " " + year, monthyearFormatter);

			if (displayedYearMonth.equals(targetYearMonth)) {
				break;
			} else if (displayedYearMonth.isBefore(targetYearMonth)) {
				nextMonthBtn.click();
			} else {
				try {
					prevMonthBtn.click();
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
			}
		}

		driver.findElement(By.xpath("//a[normalize-space()='" + targetDay + "']")).click();
	}

	/**
	 * Selects return date from calendar widget.
	 * 
	 * @param date Date string in dd/MM/yyyy format
	 */
	public void returnDate(String date) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate targetDate = LocalDate.parse(date, formatter);
		String targetDay = String.valueOf(targetDate.getDayOfMonth());
		YearMonth targetYearMonth = YearMonth.from(targetDate);

		returnDateSelection.click();
		returnDate.click();

		while (true) {
			String month = displayedMonth.getText();
			String year = displayedYear.getText();

			DateTimeFormatter monthyearFormatter = DateTimeFormatter.ofPattern("MMMM yyyy", Locale.ENGLISH);
			YearMonth displayedYearMonth = YearMonth.parse(month + " " + year, monthyearFormatter);

			if (displayedYearMonth.equals(targetYearMonth)) {
				break;
			} else if (displayedYearMonth.isBefore(targetYearMonth)) {
				nextMonthBtn.click();
			} else {
				try {
					prevMonthBtn.click();
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
			}
		}

		driver.findElement(By.xpath("//a[normalize-space()='" + targetDay + "']")).click();
	}

	/**
	 * Selects pickup time from time picker.
	 * 
	 * @param time Time string in HH:mm AM/PM format
	 */
	public void pickupTime(String time) {
		String[] timeParts = time.split("[: ]");
		String hours = timeParts[0];
		String minutes = timeParts[1];
		String meridian = timeParts[2];

		if (meridian.equals("AM")) {
			amSelectorPickup.click();
		} else if (meridian.equals("PM")) {
			pmSelectorPickup.click();
		}

		driver.findElement(By.xpath("//div[@id='hr']//ul//li[normalize-space()='" + hours + " Hr.']")).click();
		driver.findElement(By.xpath("//div[@id='min']//ul//li[normalize-space()='" + minutes + " Min.']")).click();
		pickupDoneBtn.click();
	}

	/**
	 * Clicks to activate return time picker.
	 */
	public void clickReturnTime() {
		returnDateSelection.click();
	}

	/**
	 * Selects return time from time picker.
	 * 
	 * @param time Time string in HH:mm AM/PM format
	 */
	public void returnTime(String time) {
		String[] timeParts = time.split("[: ]");
		String hours = timeParts[0];
		String minutes = timeParts[1];
		String meridian = timeParts[2];

		if (meridian.equals("AM")) {
			amSelectorReturn.click();
		} else if (meridian.equals("PM")) {
			pmSelectorReturn.click();
		}

		driver.findElement(By.xpath("//div[@id='rhr']//ul//li[normalize-space()='" + hours + " Hr.']")).click();
		driver.findElement(By.xpath("//div[@id='rmin']//ul//li[normalize-space()='" + minutes + " Min.']")).click();
		returnDoneBtn.click();
	}

	/**
	 * Initiates search for outstation cabs.
	 */
	public void search() {
		searchBtn.click();
	}

	/**
	 * Applies SUV filter, expands options, and extracts vehicle names and prices.
	 * Stores result data in JSON with scenario context.
	 * 
	 * @param scenario Scenario name used for JSON organization
	 */
	public void results(String scenario) {
		boolean chk = suvFilter.isDisplayed();
		Assert.assertTrue(chk, "SUV checkbox not enabled");
		suvFilter.click();

		boolean optChk = options.isDisplayed();
		Assert.assertTrue(optChk, "Options link not visible");
		options.click();

		wait.until(ExpectedConditions.visibilityOfAllElements(vehicleBlocks));
		Assert.assertTrue(vehicleBlocks.size() > 0, "No cab prices were displayed.");

		for (WebElement block : vehicleBlocks) {
			try {
				WebElement nameElement = block
						.findElement(By.xpath(".//div[contains(@class, 'checkbox-container')]/h6"));
				String vehicleName = nameElement.getText().trim();

				WebElement priceElement = block.findElement(By.xpath(".//div[contains(@class, 'ruppes')]//h6"));
				String price = priceElement.getText().trim();

				JsonDataWriter.writeSimpleDataWithScenario(scenario, vehicleName, price, "OutstationWrite.json");
			} catch (Exception e) {
				System.out.println("[Vehicle name or price not found]");
			}
		}

		closeBtn.click();
	}
}