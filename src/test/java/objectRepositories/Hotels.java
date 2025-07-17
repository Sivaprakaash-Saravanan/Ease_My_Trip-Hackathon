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

public class Hotels {
	WebDriver driver;
	static JavascriptExecutor jse;

	public Hotels(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//div/span[@class='hp_city']")
	WebElement cityName;

	@FindBy(id = "txtCity")
	WebElement name;

	@FindBy(xpath = "//div[@class='w_85 dest_namect']")
	List<WebElement> citynames;

	@FindBy(xpath = "//div[@class=\"hp_inputBox ht-dates\"]//div[@id='htl_dates']")
	WebElement clickCI;

	@FindBy(xpath = "//select[@data-handler='selectMonth']")
	WebElement month;

	@FindBy(xpath = "//select[@data-handler=\"selectMonth\"]/option")
	List<WebElement> selectCImonth;

	@FindBy(xpath = "//td[@data-handler=\"selectDay\"]")
	List<WebElement> datesForCI;

	@FindBy(xpath = "//div[@class=\"hp_inputBox ht-dates\"]/span[text()=\"Check-Out\"]")
	WebElement clickCO;

	@FindBy(xpath = "//select[@data-handler=\"selectMonth\"]/option")
	List<WebElement> selectCOmonth;

	@FindBy(xpath = "//td[@data-handler=\"selectDay\"]")
	List<WebElement> datesForCO;

	@FindBy(xpath = "//a[text()='Done']")
	WebElement clickdone;

	@FindBy(id = "btnSearch")
	WebElement searchBtn;

	@FindBy(xpath = "//span[text()='₹ 2,001 - ₹ 4,000 ']")
	WebElement price;

	@FindBy(xpath = "//span[text()='₹ 2,001 - ₹ 4,000 ']/following-sibling::span[contains(@class,'checkmark')]")
	WebElement priceCheckBox;

	@FindBy(xpath = "//div[contains(@class,'drp-bx')]")
	WebElement priority;

	@FindBy(xpath = "//div[text()='Low to High']")
	WebElement priceLowToHigh;

	@FindBy(xpath = "(//div[contains(@class,'result-item')])[1]//span[@class='ng-star-inserted']/a")
	WebElement hotelName;

	@FindBy(xpath = "(//div[contains(@class,'result-item')])[1]//span[@class='CurrncyCD_INR']/following-sibling::span")
	WebElement hotelPrice;

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
//				System.out.println("Stale Element Retrying...");
			}
		}
	}

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
				// System.out.println("COdate Selected");
				break;
			}
		}
		clickdone.click();
	}

	public void search() throws Exception {
		searchBtn.click();
	}

	public void hotelNameAndPrice() throws Exception {
		boolean chk = priority.isDisplayed();
		Assert.assertTrue(chk, "Sort option is not displayed");
		priority.click();
		boolean priceOptChk = priceLowToHigh.isDisplayed();
		Assert.assertTrue(priceOptChk, "Price Low to High Sort option is not displayed");
		priceLowToHigh.click();
		Assert.assertNotNull(hotelName.getText(), "Hotel names not displayed");
		Assert.assertNotNull(hotelPrice.getText(), "Hotel prices not displayed");
		JsonDataWriter.writeSimpleData(hotelName.getText(), hotelPrice.getText(), "HotelWriting.json");
//		System.out.println(hotelName.getText());
//		System.out.println(hotelPrice.getText());
	}
}