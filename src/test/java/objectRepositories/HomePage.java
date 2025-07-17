package objectRepositories;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage {
	WebDriver driver;

	public HomePage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//div[@class='emt_header']")
	WebElement homeTab;

	@FindBy(xpath = "//*[@id='myTopnav']/div/ul/li[7]")
	WebElement cabsTab;

	@FindBy(xpath = "//*[@id='myTopnav']/div/ul/li[8]")
	WebElement activity;

	@FindBy(xpath = "//*[@id='myTopnav']/div/ul/li[2]")
	WebElement hotels;

	public void gotoHome() {
		Actions acts = new Actions(driver);
		acts.scrollToElement(homeTab).perform();
		homeTab.click();
	}

	public void goToCabs() {
		cabsTab.click();
	}

	public void goToActivities() {
		activity.click();
	}

	public void goToHotels() {
		hotels.click();
	}
}