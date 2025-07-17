package objectRepositories;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Page Object Model for the home page navigation. Provides access to Home,
 * Cabs, Activities, and Hotels tabs.
 */
public class HomePage {
	WebDriver driver;

	/**
	 * Initializes the web elements on the home page.
	 *
	 * @param driver WebDriver instance used to interact with the browser
	 */
	public HomePage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	/** Home tab element in header */
	@FindBy(xpath = "//div[@class='emt_header']")
	WebElement homeTab;

	/** Cabs tab in navigation menu */
	@FindBy(xpath = "//*[@id='myTopnav']/div/ul/li[7]")
	WebElement cabsTab;

	/** Activities tab in navigation menu */
	@FindBy(xpath = "//*[@id='myTopnav']/div/ul/li[8]")
	WebElement activity;

	/** Hotels tab in navigation menu */
	@FindBy(xpath = "//*[@id='myTopnav']/div/ul/li[2]")
	WebElement hotels;

	/**
	 * Navigates to the home section by scrolling into view and clicking.
	 */
	public void gotoHome() {
		Actions acts = new Actions(driver);
		acts.scrollToElement(homeTab).perform();
		homeTab.click();
	}

	/**
	 * Navigates to the Cabs section.
	 */
	public void goToCabs() {
		cabsTab.click();
	}

	/**
	 * Navigates to the Activities section.
	 */
	public void goToActivities() {
		activity.click();
	}

	/**
	 * Navigates to the Hotels section.
	 */
	public void goToHotels() {
		hotels.click();
	}
}
