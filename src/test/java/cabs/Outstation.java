package cabs;

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


public class Outstation {
	WebDriver driver;
	WebDriverWait wait;

	public Outstation(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		PageFactory.initElements(driver, this);
	}

	// outstation
	@FindBy(id = "li2")
	WebElement outstation;

	// from location
	@FindBy(id = "sourceName")
	WebElement from;

	@FindBy(id = "a_FromSector_show")
	WebElement fromCity;

	// to location
	@FindBy(id = "destinationName")
	WebElement to;

	@FindBy(id = "a_ToSector_show")
	WebElement toCity;

	// date and time
	@FindBy(id = "datepicker")
	WebElement pickupDate;

	@FindBy(id = "rtag")
	WebElement returnDateSelection;

	@FindBy(id = "rdatepicker")
	WebElement returnDate;

	// calendar navigation
	@FindBy(xpath = "//span[@class='ui-datepicker-month']")
	WebElement displayedMonth;

	@FindBy(xpath = "//span[@class='ui-datepicker-year']")
	WebElement displayedYear;

	@FindBy(xpath = "//span[@class='ui-icon ui-icon-circle-triangle-e']")
	WebElement nextMonthBtn;

	@FindBy(xpath = "//span[@class='ui-icon ui-icon-circle-triangle-w']")
	WebElement prevMonthBtn;

	// AM/PM selectors
	@FindBy(xpath = "//label[@for='am']")
	WebElement amSelectorPickup;

	@FindBy(xpath = "//label[@for='pm']")
	WebElement pmSelectorPickup;

	@FindBy(xpath = "//div[@id='rap']//label[@for='ram']")
	WebElement amSelectorReturn;

	@FindBy(xpath = "//div[@id='rap']//label[@for='rpm']")
	WebElement pmSelectorReturn;

	// done btn
	@FindBy(xpath = "//div[@onclick='Done()']")
	WebElement pickupDoneBtn;

	@FindBy(xpath = "//div[@onclick='rDone()']")
	WebElement returnDoneBtn;

	// search
	@FindBy(xpath = "//div[@onclick='GetList()']")
	WebElement searchBtn;

	// SUV filter
	@FindBy(xpath = "//label[3]//div[1]//span[2]")
	WebElement suvFilter;

	// click for more options
	@FindBy(xpath = "//div[@class='blue-link']//a")
	WebElement options;

	// close btn
	@FindBy(xpath = "//span[@class='close']")
	WebElement closeBtn;

	// lists
	@FindBy(xpath = "//div[@id='StartCity']//ul//li")
	List<WebElement> citySuggestions_from;

	@FindBy(xpath = "//div[@id='EndCity']//ul//li")
	List<WebElement> citySuggestions_to;

	@FindBy(xpath = "//label[contains(@class, 'fare')]")
	List<WebElement> vehicleBlocks;

	// one-way trip
	public void oneWayTrip() {
		switchToOutstation();
		fromField();
		toField();
		pickupDate("10/10/2025");
		pickupTime("10:00 AM");
		search();
		results();
	}

	// round trip
	public void roundTrip() {
		switchToOutstation();
		fromField();
		toField();
		pickupDate("10/10/2025");
		pickupTime("10:00 AM");
		returnDate("11/10/2025");
		returnTime("10:00 PM");
		search();
		results();
	}

	// switching to outstation
	public void switchToOutstation() {
		outstation.click();
		System.out.println("Clicked on outstation");
	}

	// from field
	public void fromField() {
		from.click();
		System.out.println("Clicked on from field");

		fromCity.click();
		System.out.println("Clicked on city field");

		fromCity.clear();
		System.out.println("Cleared city field");

		fromCity.sendKeys("Delhi");
		System.out.println("Entered value to city field");

		while (true) {
			try {
				wait.until(ExpectedConditions.visibilityOfAllElements(citySuggestions_from));

				for (WebElement suggestion: citySuggestions_from) {
					String text = suggestion.getText();

					if (text.startsWith("Delhi") && text.contains("Delhi")) {
						suggestion.click();
						System.out.println("City selected");
						break;
					}
				}

				break;
			} catch (StaleElementReferenceException e) {
				System.out.println("Caught stale element for 'from' city, retrying...");
			}
		}
	}

	// to field
	public void toField() {
		to.click();
		System.out.println("Clicked on to field");

		toCity.click();
		System.out.println("Clicked on city field");

		toCity.clear();
		System.out.println("Cleared city field");

		toCity.sendKeys("Manali");
		System.out.println("Entered value to city field");

		while (true) {
			try {
				wait.until(ExpectedConditions.visibilityOfAllElements(citySuggestions_to));

				for (WebElement suggestion: citySuggestions_to) {
					String text = suggestion.getText();

					if (text.startsWith("Manali") && text.contains("Kullu")) {
						suggestion.click();
						System.out.println("City selected");
						break;
					}
				}

				break;
			} catch (StaleElementReferenceException e) {
				System.out.println("Caught stale element for 'to' city, retrying...");
			}
		}
	}

	// pickup date
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

	// return date
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

	// pickup time
	public void pickupTime(String time) {
		String[] timeParts = time.split("[: ]");

		String hours = timeParts[0];
		String minutes = timeParts[1];
		String meridian = timeParts[2];

		if (meridian.equals("AM")) {
			amSelectorPickup.click();
			System.out.println("Selected 'AM' as meridian.");
		} else if (meridian.equals("PM")) {
			pmSelectorPickup.click();
			System.out.println("Selected 'PM' as meridian.");
		}

		driver.findElement(By.xpath("//div[@id='hr']//ul//li[normalize-space()='" + hours + " Hr.']")).click();
		driver.findElement(By.xpath("//div[@id='min']//ul//li[normalize-space()='" + minutes + " Min.']")).click();
		pickupDoneBtn.click();
	}

	// return time
	public void returnTime(String time) {
		String[] timeParts = time.split("[: ]");

		String hours = timeParts[0];
		String minutes = timeParts[1];
		String meridian = timeParts[2];

		if (meridian.equals("AM")) {
			amSelectorReturn.click();
			System.out.println("Selected 'AM' as meridian.");
		} else if (meridian.equals("PM")) {
			pmSelectorReturn.click();
			System.out.println("Selected 'PM' as meridian.");
		}

		driver.findElement(By.xpath("//div[@id='rhr']//ul//li[normalize-space()='" + hours + " Hr.']")).click();
		driver.findElement(By.xpath("//div[@id='rmin']//ul//li[normalize-space()='" + minutes + " Min.']")).click();
		returnDoneBtn.click();
	}

	// searching for results
	public void search() {
		searchBtn.click();
		System.out.println("Clicked on search");
	}

	// fetching the results
	public void results() {
		suvFilter.click();
		System.out.println("Clicked 'SUV' for filters");
		options.click();
		System.out.println("Clicked for more options");

		wait.until(ExpectedConditions.visibilityOfAllElements(vehicleBlocks));

		for (WebElement block: vehicleBlocks) {
			try {
				WebElement nameElement = block
					.findElement(By.xpath(".//div[contains(@class, 'checkbox-container')]/h6"));
				String vehicleName = nameElement.getText().trim();

				WebElement priceElement = block.findElement(By.xpath(".//div[contains(@class, 'ruppes')]//h6"));
				String price = priceElement.getText().trim();

				System.out.println(vehicleName + " - " + price);
			} catch (Exception e) {
				System.out.println("[Vehicle name or price not found]");
			}
		}

		closeBtn.click();
	}
}