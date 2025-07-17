package objectRepositories;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import utilities.ExcelWrite;

/**
 * Page Object Model for the Gift Cards section. Supports navigation to gift
 * card options, selecting a specific card, entering sender details, validating
 * inputs, and capturing screenshots.
 */
public class GiftCards {
	WebDriver driver;
	JavascriptExecutor js;
	Actions acts;

	/** Flags for validation status */
	static boolean emailFlag;
	static boolean mobileFlag;

	/**
	 * Constructor to initialize Gift Cards page elements and utilities.
	 * 
	 * @param driver WebDriver instance used for browser interaction
	 */
	public GiftCards(WebDriver driver) {
		this.driver = driver;
		js = (JavascriptExecutor) driver;
		acts = new Actions(driver);
		PageFactory.initElements(driver, this);
		System.out.println("Initialized Cards page object");
	}

	// Web Elements

	@FindBy(xpath = "//div[@class='_menurohdr']/ul/li[@class='_subheaderlink']")
	WebElement moreIcon;

	@FindBy(xpath = "//span[@class=\"fnt14\" and text()=\"Gift Card\"]")
	WebElement giftIcon;

	@FindBy(xpath = "//div[@class='tab']/button[text()='Festival']")
	WebElement festivalTab;

	@FindBy(xpath = "//img[contains(@src,'fireworks')]")
	WebElement festivalImg;

	@FindBy(xpath = "//div[@id=\"Festival\"]//div[@class=\"crdmgmn\"]/img[contains(@src,\"diwali\")]")
	WebElement diwaliCard;

	@FindBy(xpath = "//h4[text()=\"Enter Sender and Receiver Details\"]")
	WebElement formHeader;

	@FindBy(xpath = "//input[@placeholder=\"Min 1000 -  50000\"]")
	WebElement denominationInput;

	@FindBy(xpath = "//select[@ng-change=\"GetPayAmount()\"]")
	WebElement quantityDropdown;

	@FindBy(xpath = "//span[@class=\"left\" ]")
	WebElement scrollToBtm;

	@FindBy(xpath = "//input[@ng-change=\"SameAsSender()\"]")
	WebElement sameAsReciever;

	@FindBy(xpath = "//input[@ng-model=\"User.SenderName\" and @ng-change=\"SameOnCheck()\"]")
	WebElement senderName;

	@FindBy(xpath = "//input[@id=\"txtEmailId\"]")
	WebElement senderEmail;

	@FindBy(xpath = "//input[@ng-model=\"User.SenderMobile\"]")
	WebElement senderMobile;

	@FindBy(xpath = "//input[@ng-change=\"IsValidate()\"]")
	WebElement acceptTnC;

	@FindBy(xpath = "//div[@class=\"w_50 \"]")
	WebElement formScreenshot;

	/**
	 * Navigates to the Gift Cards section via mouse hover.
	 */
	public void navigateToCards() {
		acts.moveToElement(moreIcon).build().perform();
		acts.moveToElement(giftIcon).build().perform();
		try {
			giftIcon.click();
		} catch (Exception e) {
			Assert.fail("Unable to click GiftCards icon");
		}
	}

	/**
	 * Selects a festival-themed gift card such as Diwali. Asserts visibility of
	 * required components before proceeding.
	 */
	public void selectingCard() {
		try {
			Assert.assertTrue(festivalTab.isDisplayed(), "Festival cards not displayed");
			js.executeScript("arguments[0].scrollIntoView(true)", festivalTab);
			js.executeScript("arguments[0].click()", festivalImg);
			Assert.assertTrue(diwaliCard.isDisplayed(), "Diwali card not displayed");
			diwaliCard.click();
		} catch (Exception e) {
			Assert.fail("Unable to interact with Gift Cards UI");
		}
	}

	/**
	 * Fills the sender form with denomination, quantity, and sender name.
	 * 
	 * @param amnt Amount as string (e.g., "1000")
	 * @param qty  Quantity of cards (e.g., "1")
	 * @param name Sender's full name
	 */
	public void fillForm(String amnt, String qty, String name) {
		js.executeScript("arguments[0].scrollIntoView(true)", formHeader);
		denominationInput.sendKeys(amnt);
		new Select(quantityDropdown).selectByVisibleText(qty);
		js.executeScript("arguments[0].scrollIntoView(true)", scrollToBtm);
		sameAsReciever.click();
		senderName.sendKeys(name);
	}

	/**
	 * Validates and enters sender email. Triggers T&C acceptance if invalid.
	 * 
	 * @param emailInput Email string input
	 * @throws IOException on screenshot capture error
	 */
	public void validateEmail(String emailInput) throws IOException {
		emailFlag = false;
		senderEmail.sendKeys(emailInput);

		if (!emailInput.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
			Assert.assertTrue(acceptTnC.isDisplayed(), "Accept Conditions not displayed");
			acceptTnC.click();
			emailFlag = true;
		}
	}

	/**
	 * Validates and enters sender mobile number. Triggers T&C acceptance if
	 * invalid.
	 * 
	 * @param mobileInput Mobile number as string
	 * @throws IOException on screenshot capture error
	 */
	public void validateMobileNum(String mobileInput) throws IOException {
		mobileFlag = false;
		senderMobile.sendKeys(mobileInput);

		if (!mobileInput.matches("^\\d{10}$")) {
			acceptTnC.click();
			mobileFlag = true;
		} else {
			acceptTnC.click();
		}
	}

	/**
	 * Captures screenshots on validation failure and updates Excel sheet.
	 * 
	 * @param writer   ExcelWrite instance for result tracking
	 * @param rowIndex Current row to be updated
	 * @throws Exception on screenshot failure
	 */
	public void screenShots(ExcelWrite writer, int rowIndex) throws Exception {
		if (emailFlag) {
			takeScreenshot("Email");
		}
		if (mobileFlag) {
			takeScreenshot("MobileNo");
		}

		if (!emailFlag && !mobileFlag) {
			writer.setCellValue(rowIndex, 6, "Valid");
			writer.fillCellGreen(rowIndex, 6);
		} else {
			writer.setCellValue(rowIndex, 6, "Invalid");
			writer.fillCellRed(rowIndex, 6);
		}
	}

	/**
	 * Performs combined validation for email and mobile number fields.
	 * 
	 * @param emailInput  Email address string
	 * @param mobileInput Mobile number string
	 * @throws IOException on validation error
	 */
	public void validation(String emailInput, String mobileInput) throws IOException {
		validateEmail(emailInput);
		validateMobileNum(mobileInput);
	}

	/**
	 * Captures and stores screenshot of the form section for invalid inputs.
	 * 
	 * @param prefix Filename prefix (e.g., "Email")
	 * @throws IOException if file cannot be saved
	 */
	private void takeScreenshot(String prefix) throws IOException {
		DateFormat df = new SimpleDateFormat("dd-MM-yy-hh-mm-ss-a");
		String timestamp = df.format(new Date());
		File dir = new File(".\\Screenshots");
		if (!dir.exists())
			dir.mkdirs();

		File srcFile = formScreenshot.getScreenshotAs(OutputType.FILE);
		FileHandler.copy(srcFile, new File(".\\Screenshots\\" + prefix + "_" + timestamp + ".png"));
	}
}
