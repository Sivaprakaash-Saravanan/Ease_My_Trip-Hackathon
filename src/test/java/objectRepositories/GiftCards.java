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

public class GiftCards {
	WebDriver driver;
	JavascriptExecutor js;
	Actions acts;
	static boolean emailFlag;
	static boolean mobileFlag;

	public GiftCards(WebDriver driver) {
		this.driver = driver;
		js = (JavascriptExecutor) driver;
		acts = new Actions(driver);
		PageFactory.initElements(driver, this);
		System.out.println("Initialized Cards page object");
	}

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

	public void navigateToCards() {
//		System.out.println("Navigating to Gift Card section");
		acts.moveToElement(moreIcon).build().perform();
		acts.moveToElement(giftIcon).build().perform();
		giftIcon.click();
//		System.out.println("Clicked on 'Gift Card'");
	}

	public void selectingCard() {
		try {
//		System.out.println("Scrolling to Festival tab and selecting Diwali card");
			boolean chk = festivalTab.isDisplayed();
			Assert.assertTrue(chk, "Festival cards is not displayed");
			js.executeScript("arguments[0].scrollIntoView(true)", festivalTab);
			js.executeScript("arguments[0].click()", festivalImg);
			boolean chkD = diwaliCard.isDisplayed();
			Assert.assertTrue(chkD, "Diwali card option is not displayed");
			diwaliCard.click();
//		System.out.println("Clicked on Diwali card");
		} catch (Exception e) {
			Assert.fail("Not able to interact with Cards");
		}
	}

	public void fillForm(String amnt, String qty, String name) {
//		System.out.println("Filling out the gift card form");
		js.executeScript("arguments[0].scrollIntoView(true)", formHeader);
		denominationInput.sendKeys(amnt);
//		System.out.println("Entered denomination: "+amnt);

		Select selectQty = new Select(quantityDropdown);
		selectQty.selectByVisibleText(qty);
//		System.out.println("Selected quantity: "+qty);

		js.executeScript("arguments[0].scrollIntoView(true)", scrollToBtm);
		sameAsReciever.click();
//		System.out.println("Checked 'Same as Receiver'");

		senderName.sendKeys(name);
//		System.out.println("Entered sender name: "+name);
	}

	public void validateEmail(String emailInput) throws IOException {
		emailFlag = false;
//		System.out.println("Validating email: " + emailInput);

		if (emailInput.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
			senderEmail.sendKeys(emailInput);
//			System.out.println("Entered valid email");
		} else {
			senderEmail.sendKeys(emailInput);
//			System.out.println("Entered invalid email");
			boolean chkAcc = acceptTnC.isDisplayed();
			Assert.assertTrue(chkAcc, "Accept Conditions option is not displayed");
			acceptTnC.click();
//			System.out.println("Accepted terms and conditions");
			emailFlag = true;
		}
	}

	public void validateMobileNum(String mobileInput) throws IOException {
		mobileFlag = false;
		if (mobileInput.matches("^\\d{10}$")) {
			senderMobile.sendKeys(mobileInput);
//			System.out.println("Entered valid mobile number");
			acceptTnC.click();
//			System.out.println("Accepted terms and conditions");
		} else {
			senderMobile.sendKeys(mobileInput);
//			System.out.println("Entered invalid mobile number");
			acceptTnC.click();
//			System.out.println("Accepted terms and conditions");
			mobileFlag = true;
		}
	}

	public void screenShots(ExcelWrite writer, int rowIndex) throws Exception {
		if (emailFlag == true) {
			takeScreenshot("Email");
		}
		if (mobileFlag == true) {
			takeScreenshot("MobileNo");
		}
		if (mobileFlag == false && emailFlag == false) {
			writer.setCellValue(rowIndex, 6, "Valid");
			writer.fillCellGreen(rowIndex, 6);
//			writer.setCellValue(rowIndex++,6,"Valid");

		} else {

			writer.setCellValue(rowIndex, 6, "Invalid");
			writer.fillCellRed(rowIndex, 6);

		}

	}

	public void validation(String emailInput, String mobileInput) throws IOException {
		validateEmail(emailInput);
		validateMobileNum(mobileInput);
	}

	private void takeScreenshot(String prefix) throws IOException {
//		System.out.println("Taking screenshot for: " + prefix);
		DateFormat df = new SimpleDateFormat("dd-MM-yy-hh-mm-ss-a");
		Date date = new Date();
		File dir = new File(".\\Screenshots");

		if (!dir.exists()) {
			dir.mkdirs();
//			System.out.println("Created Screenshots directory");
		}

		File srcFile = formScreenshot.getScreenshotAs(OutputType.FILE);
		FileHandler.copy(srcFile, new File(".\\Screenshots\\" + prefix + "_" + df.format(date) + ".png"));
//		System.out.println("Screenshot saved: " + prefix + "_" + df.format(date) + ".png");
	}
}
