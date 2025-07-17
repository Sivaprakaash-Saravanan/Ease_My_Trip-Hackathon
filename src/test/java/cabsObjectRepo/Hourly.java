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

/**
 * Page Object Model for Hourly Rental booking.
 * Allows interaction with rental menus, city selection, date/time pickers,
 * duration setup, filtering, and price retrieval.
 */
public class Hourly {
    WebDriver driver;
    WebDriverWait wait;
    JavascriptExecutor js;
    Actions actions;

    /**
     * Initializes the Hourly rental page elements and utilities.
     *
     * @param driver WebDriver instance
     */
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

    // City selection elements
    @FindBy(xpath = "//div[@id='sourceName']")
    WebElement sourceBox;

    @FindBy(xpath = "//input[@id='a_FromSector_show']")
    WebElement sourceInput;

    @FindBy(xpath = "//div[@class='auto_sugg' and @id='StartCity'/ul/li]")
    WebElement autosuggestionDiv;

    @FindBy(xpath = "//div[@class='auto_sugg_tttl']")
    List<WebElement> suggestions;

    // Calendar elements
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

    // Time selectors
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

    // Rental duration selection
    @FindBy(xpath = "//div[@id='rtimes']/parent::div[@id='timePicker']")
    WebElement rentalTimeBox;

    @FindBy(xpath = "//div[@id='addclsForRent']/ul/li")
    List<WebElement> rentHoursList;

    // Search and filtering
    @FindBy(xpath = "//div[@onclick='GetList()']")
    WebElement searchCabsBtn;

    @FindBy(xpath = "//div[contains(@class,'chk-tcnt')]/span[text()=' suv ']")
    WebElement suvFilter;

    @FindBy(xpath = "//div[@class='cabFare _f25 ']")
    WebElement lowestFare;

    /**
     * Navigates to hourly rental tab.
     */
    public void goToHourly() {
        hourlyRental.click();
    }

    /**
     * Executes the full rental cab search workflow.
     *
     * @param city       City to search cabs in
     * @param userDay    Desired day (e.g., "17")
     * @param userMonth  Desired month (e.g., "July")
     * @param userYear   Desired year (e.g., "2025")
     * @param timeRaw    Time string in HH:mm AM/PM format
     * @param rentHours  Rental duration in hours (e.g., "4")
     */
    public void searchCab(String city, String userDay, String userMonth, String userYear, String timeRaw,
            String rentHours) {

        WebElement input = wait.until(ExpectedConditions.elementToBeClickable(sourceBox));
        input.click();

        WebElement inputSearch = wait.until(ExpectedConditions.elementToBeClickable(sourceInput));
        inputSearch.sendKeys(city);

        while (true) {
            try {
                wait.until(ExpectedConditions.visibilityOfAllElements(suggestions));
                break;
            } catch (StaleElementReferenceException e) {
                continue;
            }
        }

        for (WebElement s : suggestions) {
            if (s.getText().equalsIgnoreCase(city)) {
                s.click();
                break;
            }
        }

        calendarField.click();

        while (true) {
            if (!calendarMonth.getText().equalsIgnoreCase(userMonth)
                    || !calendarYear.getText().equalsIgnoreCase(userYear)) {
                nextMonthBtn.click();
            } else {
                break;
            }
        }

        for (WebElement day : allDates) {
            if (day.getText().equalsIgnoreCase(userDay)) {
                day.click();
                break;
            }
        }

        String[] timeParts = timeRaw.split(" ");
        String[] hourMin = timeParts[0].split(":");
        String hr = hourMin[0];
        String min = hourMin[1];

        if (timeParts[1].equalsIgnoreCase("AM")) {
            AM.click();
        } else {
            PM.click();
        }

        for (WebElement h : hourOptions) {
            if (h.getText().split(" ")[0].equalsIgnoreCase(hr)) {
                js.executeScript("arguments[0].click()", h);
                break;
            }
        }

        for (WebElement m : minuteOptions) {
            if (m.getText().split(" ")[0].equalsIgnoreCase(min)) {
                js.executeScript("arguments[0].click()", m);
                break;
            }
        }

        doneBtn.click();
        rentalTimeBox.click();

        for (WebElement r : rentHoursList) {
            if (r.getText().split(" ")[0].equalsIgnoreCase(rentHours)) {
                js.executeScript("arguments[0].click()", r);
                break;
            }
        }

        searchCabsBtn.click();
    }

    /**
     * Applies filter to show SUV vehicles only.
     */
    public void filterSUV() {
        boolean chk = suvFilter.isDisplayed();
        Assert.assertTrue(chk, "SUV check box not enabled");
        suvFilter.click();
    }

    /**
     * Fetches the lowest fare from displayed SUV options.
     *
     * @return String indicating fare value
     */
    public String getLeastFare() {
        Assert.assertNotNull(lowestFare.getText(), "SUV lowest price is null");
        String fare = lowestFare.getText();
        return "Lowest fare found: " + fare;
    }
}
