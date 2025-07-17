package cabsObjectRepo;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Calendar;
import java.util.Date;
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

import utilities.ExcelWrite;

public class Airport {
	WebDriver driver;

	public Airport(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	// Elements
	@FindBy(xpath = "//*[@id='airportdiv']")
	WebElement airportBox;

	@FindBy(xpath = "//*[@id='pickup']")
	WebElement pickupEle;

	@FindBy(xpath = "//*[@id='drop']")
	WebElement dropVal;

	@FindBy(xpath = "//*[@id='hrlysrc']")
	WebElement srcClick;

	@FindBy(xpath = "//*[@id='a_FromSector_show']")
	static WebElement srcTxt;

	@FindBy(xpath = "//*[@id='to']")
	WebElement destClick;

	@FindBy(xpath = "//*[@id='a_ToSector_show']")
	WebElement destTxt;

	@FindBy(xpath = "//div[@id='pickCalender']//input[@id='datepicker']")
	WebElement calIcon;

	@FindBy(xpath = "//label[@for='am']")
	WebElement amSelector;

	@FindBy(xpath = "//label[@for='pm']")
	WebElement pmSelector;

	@FindBy(xpath = "//div[@onclick='Done()']")
	WebElement doneBtn;

	@FindBy(xpath = "//div[@id='CommonSearch']//div[text()='SEARCH']")
	WebElement searchBtn;

	@FindBy(xpath = "//div[@class='_listflx']//div[@class='list-dtl']/div[@class='_pro_ttl']")
	List<WebElement> cabNamesList;

	@FindBy(xpath = "//div[@class='_listflx']//div[@class='nw_price']/div[not(contains(@class,'red'))]")
	List<WebElement> priceNamesList;

	@FindBy(xpath = "//div[contains(@class,'chk-tcnt')]/span[text()=' sedan ']")
	WebElement sedanChkBox;

	@FindBy(xpath = "//div[@class='_listflx']//div[@class='nw_price']/div")
	WebElement priceVal;

	// Action Methods
	public void clickOnAirportTransfer() {
		airportBox.click();
//		System.out.println("Clicked on Airport Transfer");
	}

	public void clickPickup() {
		pickupEle.click();
//		System.out.println("Clicked on Pickup");
	}

	public void clickDrop() {
		dropVal.click();
//		System.out.println("Clicked on Drop");
	}

	public void clickSource() {
		srcClick.click();
//		System.out.println("Clicked on Source field");
	}

	public void sendValToSrc(String source) {
		srcTxt.sendKeys(source);
//		System.out.println("Entered source: " + source);
	}

	public void selectSrcVal(String sourceVal) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		while (true) {
			try {
				// Wait for suggestions to be visible
				wait.until(ExpectedConditions
						.visibilityOfAllElementsLocatedBy(By.xpath("//div[@class='auto_sugg_tttl']")));

				// Re-fetch suggestions to avoid stale elements
				List<WebElement> suggestions = driver.findElements(By.xpath("//div[@class='auto_sugg_tttl']"));

//		        System.out.println("Suggestions loaded");

				WebElement bestMatch = null;
				int shortestLength = Integer.MAX_VALUE;
				String targetText = sourceVal.trim().toLowerCase();

				for (WebElement s : suggestions) {
					String suggestionText = s.getText().trim().toLowerCase();

					// 1. Exact match wins instantly
					if (suggestionText.equals(targetText)) {
						bestMatch = s;
						break;
					}

					// 2. Starts with is stronger than contains
					if (suggestionText.startsWith(targetText)) {
						if (suggestionText.length() < shortestLength) {
							bestMatch = s;
							shortestLength = suggestionText.length();
						}
					} else if (suggestionText.contains(targetText)) {
						if (suggestionText.length() < shortestLength) {
							bestMatch = s;
							shortestLength = suggestionText.length();
						}
					}
				}

				if (bestMatch != null) {
					((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", bestMatch);
					wait.until(ExpectedConditions.elementToBeClickable(bestMatch));
					bestMatch.click();
//		            System.out.println("Selected city from suggestions: " + sourceVal);
				} else {
//		            System.out.println("No matching suggestion found for: " + sourceVal);
				}

				break; // Exit after one clean pass
			} catch (StaleElementReferenceException e) {
//		        System.out.println("Caught stale element, retrying...");
				// Loop retries until stable elements are loaded
			}
		}
	}

	public void clickDestination() {
		destClick.click();
//		System.out.println("Clicked on Destination field");
	}

	public void sendValToDest(String dest) {
		destTxt.sendKeys(dest);
//		System.out.println("Entered destination: " + dest);
	}

	public void selectDestVal(String destVal) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		while (true) {
			try {
				// Wait for suggestions to be visible
				wait.until(ExpectedConditions
						.visibilityOfAllElementsLocatedBy(By.xpath("//div[@class='auto_sugg_tttl']")));

				// Re-fetch suggestions to avoid stale elements
				List<WebElement> suggestions = driver.findElements(By.xpath("//div[@class='auto_sugg_tttl']"));

//		        System.out.println("Suggestions loaded");

				WebElement bestMatch = null;
				int shortestLength = Integer.MAX_VALUE;
				String targetText = destVal.trim().toLowerCase();

				for (WebElement s : suggestions) {
					String suggestionText = s.getText().trim().toLowerCase();

					// 1. Exact match wins instantly
					if (suggestionText.equals(targetText)) {
						bestMatch = s;
						break;
					}

					// 2. Starts with is stronger than contains
					if (suggestionText.startsWith(targetText)) {
						if (suggestionText.length() < shortestLength) {
							bestMatch = s;
							shortestLength = suggestionText.length();
						}
					} else if (suggestionText.contains(targetText)) {
						if (suggestionText.length() < shortestLength) {
							bestMatch = s;
							shortestLength = suggestionText.length();
						}
					}
				}

				if (bestMatch != null) {
					((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", bestMatch);
					wait.until(ExpectedConditions.elementToBeClickable(bestMatch));
					bestMatch.click();
//		            System.out.println("Selected city from suggestions: " + destVal);
				} else {
//		            System.out.println("No matching suggestion found for: " + destVal);
				}

				break; // Exit after one clean pass
			} catch (StaleElementReferenceException e) {
//		        System.out.println("Caught stale element, retrying...");
				// Loop retries until stable elements are loaded
			}
		}
	}

	public void clickOnCalendar() {
		calIcon.click();
//		System.out.println("Clicked on Calendar");
	}

	public void selectDate(String date) {
		Calendar cal = Calendar.getInstance();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM/yyyy");
			sdf.setLenient(false);
			Date formattedDate = sdf.parse(date);
			cal.setTime(formattedDate);

			int month = cal.get(Calendar.MONTH);
			int year = cal.get(Calendar.YEAR);
			int day = cal.get(Calendar.DAY_OF_MONTH);

			String monYearText = driver.findElement(By.className("ui-datepicker-title")).getText();
			SimpleDateFormat currSdf = new SimpleDateFormat("MMM yyyy");
			Date currFDate = currSdf.parse(monYearText);
			cal.setTime(currFDate);

			int currMonth = cal.get(Calendar.MONTH);
			int currYear = cal.get(Calendar.YEAR);

			while (currMonth < month || currYear < year) {
				driver.findElement(By.xpath("//*[contains(@class,'next') and @title='Next']")).click();
				monYearText = driver.findElement(By.className("ui-datepicker-title")).getText();
				currFDate = currSdf.parse(monYearText);
				cal.setTime(currFDate);
				currMonth = cal.get(Calendar.MONTH);
				currYear = cal.get(Calendar.YEAR);
			}

			if (currMonth == month && currYear == year) {
				driver.findElement(By.xpath("//table[contains(@class,'calendar')]/tbody/tr/td/*[text()='" + day + "']"))
						.click();
//				System.out.println("Selected date: " + date);
			} else {
//				System.out.println("Unable to fetch the given date");
			}
		} catch (Exception e) {
			System.out.println("Exception while selecting date: " + e.getMessage());
		}
	}

	public void selectTime(String time) {
		String[] timeParts = time.split("[: ]");

		String hours = timeParts[0];
		String minutes = timeParts[1];
		String meridian = timeParts[2];

		if (hours.charAt(0) == '0') {
			hours = Character.toString(hours.charAt(1));
		}

		if (meridian.equals("AM")) {
			amSelector.click();
//			System.out.println("Selected 'AM' as meridian.");
		} else if (meridian.equals("PM")) {
			pmSelector.click();
//			System.out.println("Selected 'PM' as meridian.");
		}

		driver.findElement(By.xpath("//div[@id='hr']//ul//li[text()='" + hours + " Hr.']")).click();
		driver.findElement(By.xpath("//div[@id='min']//ul//li[text()='" + minutes + " Min.']")).click();
		doneBtn.click();
	}

	public void clickSearch() {
		searchBtn.click();
//		System.out.println("Clicked on Search");
	}

	public List<WebElement> displayNames(ExcelWrite writer) throws IOException {
		int row = 1;
		writer.setCellValue(0, 0, "Cab Names");
		writer.setCellValue(0, 1, "Cab Prices");
		Assert.assertTrue(cabNamesList.size() > 0, "No cab names were displayed.");
		for (WebElement ns : cabNamesList) {
//			ExcelUtils.setCellValue(filePath,"CabNames",row++,0,ns.getText());
			writer.setCellValue(row++, 0, ns.getText());

//			System.out.println("Cab Name: " + ns.getText());
		}
		return cabNamesList;
	}

	public List<WebElement> displayPrices(ExcelWrite writer) throws IOException {
		int rowIndex = 1;
		Assert.assertTrue(priceNamesList.size() > 0, "No cab prices were displayed.");
		for (WebElement ps : priceNamesList) {
//			ExcelUtils.setCellValue(filePath,"CabPrices",rowIndex++,0,ps.getText());
			writer.setCellValue(rowIndex++, 1, ps.getText());
//			System.out.println("Cab Price: " + ps.getText());
		}
		return priceNamesList;
	}

	public void clickOnSedanChk() {
		if (sedanChkBox != null && sedanChkBox.isDisplayed() && sedanChkBox.isEnabled()) {
			sedanChkBox.click();
//		    System.out.println("✅ Clicked on Sedan checkbox");
		} else {
			Assert.fail("❌ Sedan checkbox is not available to click!");
		}
	}

	public String displayPrice(ExcelWrite writer) throws IOException {
		writer.setCellValue(0, 2, "Least Sedan Price");
		Assert.assertNotNull(priceVal.getText(), "Sedan price is null");
		writer.setCellValue(1, 2, priceVal.getText());
//		System.out.println("Least Price: " + priceVal.getText());
		return priceVal.getText();
	}

}
