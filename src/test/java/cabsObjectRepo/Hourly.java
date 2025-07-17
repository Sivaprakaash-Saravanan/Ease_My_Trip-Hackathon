package cabsObjectRepo;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class Hourly {
	WebDriver driver;
	WebDriverWait wait;
	JavascriptExecutor js;
	Actions actions;

	public Hourly(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		js = (JavascriptExecutor) driver;
		actions = new Actions(driver);
	}

	// Cab menu and rental option
	@FindBy(xpath = "//span[@class='meuicowidth cabmenuico']")
	WebElement cabMenu;

	@FindBy(id = "li3")
	WebElement hourlyRental;

	// City selection
	@FindBy(xpath = "//div[@id='sourceName']")
	WebElement sourceBox;

	@FindBy(xpath = "//input[@id='a_FromSector_show']")
	WebElement sourceInput;

	@FindBy(xpath = "//div[@class='auto_sugg' and @id='StartCity'/ul/li]")
	WebElement autosuggestionDiv;

	@FindBy(xpath = "//div[@class='auto_sugg_tttl']")
	List<WebElement> suggestions;

	// Calendar
	@FindBy(xpath = "//div[@class='box-dt']")
	WebElement calendarField;

	@FindBy(xpath = "//span[@class='ui-datepicker-month']")
	WebElement calendarMonth;

	@FindBy(xpath = "//span[@class='ui-datepicker-year']")
	WebElement calendarYear;

	@FindBy(xpath = "//a[@data-handler='next']")
	WebElement nextMonthBtn;

	@FindBy(xpath = "//a[@class='ui-state-default']")
	List<WebElement> allDates;

	@FindBy(xpath = "//label[@for='am']")
	WebElement AM;

	@FindBy(xpath = "//label[@for='pm']")
	WebElement PM;

	@FindBy(xpath = "//div[@id='hr']/ul/li")
	List<WebElement> hourOptions;

	@FindBy(xpath = "//div[@id='min']/ul/li")
	List<WebElement> minuteOptions;

	@FindBy(xpath = "//div[@class='done_d' and @onclick='Done()']")
	WebElement doneBtn;

	// Rental duration
	@FindBy(xpath = "//div[@id='rtimes']/parent::div[@id='timePicker']")
	WebElement rentalTimeBox;

	@FindBy(xpath = "//div[@id='addclsForRent']/ul/li")
	List<WebElement> rentHoursList;

	// Search button
	@FindBy(xpath = "//div[@onclick='GetList()']")
	WebElement searchCabsBtn;

	// filter chckbox
	@FindBy(xpath = "//div[contains(@class,'chk-tcnt')]/span[text()=' suv ']")
	WebElement suvFilter;

	// fecthing price
	@FindBy(xpath = "//div[@class='cabFare _f25 ']")
	WebElement lowestFare;

	public void goToHourly() {
//		System.out.println("Starting cab search...");
		hourlyRental.click();
//		System.out.println("Clicked on hourly rental");
	}

	public void searchCab(String city, String userDay, String userMonth, String userYear, String timeRaw,
			String rentHours) {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		WebElement input = wait.until(ExpectedConditions.elementToBeClickable(sourceBox));
		input.click();
//		System.out.println("Clicked on source box");

		WebElement inputSearch = wait.until(ExpectedConditions.elementToBeClickable(sourceInput));
		inputSearch.sendKeys(city);
//		System.out.println("Entered city: " + city);

		while (true) {
			try {
				WebDriverWait wait1 = new WebDriverWait(driver, Duration.ofSeconds(10));
				wait1.until(ExpectedConditions.visibilityOfAllElements(suggestions));
//				System.out.println("Suggestions loaded");
				break;
			} catch (StaleElementReferenceException e) {
//				System.out.println("Caught stale element, retrying...");
				continue;
			}
		}

		for (WebElement s : suggestions) {
			if (s.getText().equalsIgnoreCase(city)) {
				s.click();
//				System.out.println("Selected city from suggestions: " + city);
				break;
			}
		}

		calendarField.click();
//		System.out.println("Opened calendar");

		while (true) {
			if (!calendarMonth.getText().equalsIgnoreCase(userMonth)
					|| !calendarYear.getText().equalsIgnoreCase(userYear)) {
				nextMonthBtn.click();
//				System.out.println("Navigating to next month...");
			} else {
				break;
			}
		}

		for (WebElement day : allDates) {
			if (day.getText().equalsIgnoreCase(userDay)) {
				day.click();
//				System.out.println("Selected date: " + userDay + " " + userMonth + " " + userYear);
				break;
			}
		}

		String[] timeParts = timeRaw.split(" ");
		String[] hourMin = timeParts[0].split(":");
		String hr = hourMin[0];
		String min = hourMin[1];

		if (timeParts[1].equalsIgnoreCase("AM")) {
			AM.click();
//			System.out.println("Selected AM");
		} else {
			PM.click();
//			System.out.println("Selected PM");
		}

		for (WebElement h : hourOptions) {
			if (h.getText().split(" ")[0].equalsIgnoreCase(hr)) {
				js.executeScript("arguments[0].click()", h);
//				System.out.println("Selected hour: " + hr);
				break;
			}
		}

		for (WebElement m : minuteOptions) {
			if (m.getText().split(" ")[0].equalsIgnoreCase(min)) {
				js.executeScript("arguments[0].click()", m);
//				System.out.println("Selected minute: " + min);
				break;
			}
		}

		doneBtn.click();
//		System.out.println("Time selection done");

		rentalTimeBox.click();
//		System.out.println("Opened rental duration options");

		for (WebElement r : rentHoursList) {
			if (r.getText().split(" ")[0].equalsIgnoreCase(rentHours)) {
				js.executeScript("arguments[0].click()", r);
//				System.out.println("Selected rental duration: " + rentHours + " hours");
				break;
			}
		}

		searchCabsBtn.click();
//		System.out.println("Clicked on search cabs");
	}

	public void filterSUV() {
		boolean chk = suvFilter.isDisplayed();
		Assert.assertTrue(chk, "SUV check box not enabled");
		suvFilter.click();
//		System.out.println("Applied SUV filter");
	}

	public String getLeastFare() {
		Assert.assertNotNull(lowestFare.getText(), "SUV lowest price is null");
		String fare = lowestFare.getText();
		return "Lowest fare found: " + fare;
	}
}