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

public class Activities {
	WebDriver driver;
	JavascriptExecutor js;
	WebDriverWait wait;

	public Activities(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		this.js = (JavascriptExecutor) driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
		System.out.println("Initialized Activities_ObjectRepository");
	}

	@FindBy(id = "txtcityname")
	WebElement city;

	@FindBy(id = "traveldateSec")
	WebElement clickDate;

	@FindBy(xpath = "//li[@class='wt600']")
	WebElement currentMonthYear;

	@FindBy(xpath = "//li[@id='traveldatenextMonth']")
	WebElement nextMonthButton;

	@FindBy(xpath = "//table//tbody//tr//td//span")
	List<WebElement> allDates;

	@FindBy(id = "srchBtn")
	WebElement searchBtn;

	@FindBy(id = "txtcityname")
	WebElement fullCity;

	@FindBy(xpath = "//div[@id='autolist']/ul")
	WebElement suggestionListContainer;

	@FindBy(xpath = "//div[@id='autolist']/ul/li")
	List<WebElement> citySuggestions;

	@FindBy(xpath = "//li[@id='plh']")
	WebElement priceLowToHigh;

	@FindBy(xpath = "//div[@id='Day_Trips-chk']//img[@class='tickImg']")
	WebElement dayTripsCheckbox;

	@FindBy(xpath = "//div[@id='Day_Trips-chk']/..")
	WebElement dayTripsImg;

	@FindBy(xpath = "//div[@class='_cityname']")
	List<WebElement> cityNames;

	public void enterCity(String partialCityName) {
//		System.out.println("Entering partial city name: " + partialCityName);
		city.sendKeys(partialCityName);
	}

	public void openCalender() {
//		System.out.println("Opening calendar");
		clickDate.click();
	}

	public void selectMonthAndYear(String targetMonthYear) {
//		System.out.println("Selecting month and year: " + targetMonthYear);

		while (true) {
			String displayed = currentMonthYear.getText().trim();
//			System.out.println("Currently displayed: " + displayed);

			if (displayed.equalsIgnoreCase(targetMonthYear)) {
//				System.out.println("Target month/year matched");
				break;
			}

//			System.out.println("Clicking next month");
			nextMonthButton.click();
		}
	}

	public void selectDate(String targetDate) {
//		System.out.println("Selecting date: " + targetDate);

		for (WebElement dateElement : allDates) {
			if (dateElement.getText().equals(targetDate)) {
//				System.out.println("Date matched, clicking: " + targetDate);
				dateElement.click();
				break;
			}
		}
	}

	public void clickSearchBtn() {
//		System.out.println("Clicking on 'Search' button");
		searchBtn.click();
	}

	public void enterFullCity(String fullCityName) {
//		System.out.println("Clearing and entering full city name: " + fullCityName);
		fullCity.clear();
		fullCity.sendKeys(fullCityName);
	}

	public void selectCity(String cityNamePartial) {
//		System.out.println("Waiting for city suggestions to appear");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='autolist']/ul/li")));
		while (true) {
			try {
				for (WebElement city : citySuggestions) {
					String text = city.getText();
//					System.out.println("Checking suggestion: " + text);

					if (text.startsWith(cityNamePartial) && text.contains(cityNamePartial)) {
//						System.out.println("Matching city found, clicking: " + text);
						city.click();
						break;
					}
				}

				break;
			} catch (StaleElementReferenceException e) {
//				System.out.println("Caught stale element for city suggestions, retrying...");
			}
		}
	}

	public void openPriceLowToHigh() {
//		System.out.println("Clicking on 'Price Low to High' filter");
		boolean chk = priceLowToHigh.isDisplayed();
		Assert.assertTrue(chk, "Sort option is not displayed");
		priceLowToHigh.click();
	}

	public void clickDayTripsCheckbox() {
//		System.out.println("Clicking on 'Day Trips' checkbox using JavaScript");
		boolean chk = dayTripsImg.isDisplayed();
		Assert.assertTrue(chk, "Day trips ckeckbox is not displayed");
		js.executeScript("arguments[0].click();", dayTripsCheckbox);
	}

	public void printCityNames() throws IOException {
//		System.out.println("Printing city names from results:");
		Assert.assertTrue(cityNames.size() > 0, "city names not displayed");
		int i = 1;
		TextFileWriter.clearFile("ActivitiesResults.txt");
		TextFileWriter.writeToTextFile("ActivitiesResults.txt", "Activities:\n-------------------------------------");
		for (WebElement city : cityNames) {
//			System.out.println(city.getText());
			if (i > 5) {
				break;
			}
			TextFileWriter.writeToTextFile("ActivitiesResults.txt", city.getText());
			i++;
		}
	}
}