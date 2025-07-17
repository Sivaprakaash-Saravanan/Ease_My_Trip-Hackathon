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

/**
 * Airport transfer page object class.
 * Handles selection of source/destination, date/time, cab search, filtering, and result extraction.
 */
public class Airport {
    WebDriver driver;

    /**
     * Constructor for initializing web elements on the Airport page.
     * 
     * @param driver WebDriver instance
     */
    public Airport(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    // Web Elements

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
    }

    public void clickPickup() {
        pickupEle.click();
    }

    public void clickDrop() {
        dropVal.click();
    }

    public void clickSource() {
        srcClick.click();
    }

    public void sendValToSrc(String source) {
        srcTxt.sendKeys(source);
    }

    /**
     * Selects a source from auto-suggestions based on closest match.
     * 
     * @param sourceVal Desired source value
     */
    public void selectSrcVal(String sourceVal) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        while (true) {
            try {
                wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//div[@class='auto_sugg_tttl']")));
                List<WebElement> suggestions = driver.findElements(By.xpath("//div[@class='auto_sugg_tttl']"));

                WebElement bestMatch = null;
                int shortestLength = Integer.MAX_VALUE;
                String targetText = sourceVal.trim().toLowerCase();

                for (WebElement s : suggestions) {
                    String suggestionText = s.getText().trim().toLowerCase();
                    if (suggestionText.equals(targetText)) {
                        bestMatch = s;
                        break;
                    }
                    if (suggestionText.startsWith(targetText) || suggestionText.contains(targetText)) {
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
                }
                break;
            } catch (StaleElementReferenceException e) {
                // Retry loop
            }
        }
    }

    public void clickDestination() {
        destClick.click();
    }

    public void sendValToDest(String dest) {
        destTxt.sendKeys(dest);
    }

    /**
     * Selects destination from suggestions using similarity and length heuristics.
     * 
     * @param destVal Desired destination value
     */
    public void selectDestVal(String destVal) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        while (true) {
            try {
                wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//div[@class='auto_sugg_tttl']")));
                List<WebElement> suggestions = driver.findElements(By.xpath("//div[@class='auto_sugg_tttl']"));

                WebElement bestMatch = null;
                int shortestLength = Integer.MAX_VALUE;
                String targetText = destVal.trim().toLowerCase();

                for (WebElement s : suggestions) {
                    String suggestionText = s.getText().trim().toLowerCase();
                    if (suggestionText.equals(targetText)) {
                        bestMatch = s;
                        break;
                    }
                    if (suggestionText.startsWith(targetText) || suggestionText.contains(targetText)) {
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
                }
                break;
            } catch (StaleElementReferenceException e) {
                // Retry loop
            }
        }
    }

    public void clickOnCalendar() {
        calIcon.click();
    }

    /**
     * Selects a date in the calendar widget.
     * 
     * @param date Date string in format dd/MMM/yyyy (e.g., "17/Jul/2025")
     */
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
                driver.findElement(By.xpath("//table[contains(@class,'calendar')]/tbody/tr/td/*[text()='" + day + "']")).click();
            }
        } catch (Exception e) {
            System.out.println("Exception while selecting date: " + e.getMessage());
        }
    }

    /**
     * Selects a time for pickup using hour, minute, and meridian inputs.
     * 
     * @param time Time string in format HH:mm AM/PM
     */
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
        } else if (meridian.equals("PM")) {
            pmSelector.click();
        }

        driver.findElement(By.xpath("//div[@id='hr']//ul//li[text()='" + hours + " Hr.']")).click();
        driver.findElement(By.xpath("//div[@id='min']//ul//li[text()='" + minutes + " Min.']")).click();
        doneBtn.click();
    }

    public void clickSearch() {
    	searchBtn.click();
    }
    
    /**
     * Writes all displayed cab names to the provided Excel writer.
     * 
     * @param writer ExcelWrite object used to write data into Excel
     * @return List of WebElements representing cab names
     * @throws IOException if there is a failure writing to Excel
     */
    public List<WebElement> displayNames(ExcelWrite writer) throws IOException {
        int row = 1;
        writer.setCellValue(0, 0, "Cab Names");
        writer.setCellValue(0, 1, "Cab Prices");
        Assert.assertTrue(cabNamesList.size() > 0, "No cab names were displayed.");
        for (WebElement ns : cabNamesList) {
            writer.setCellValue(row++, 0, ns.getText());
        }
        return cabNamesList;
    }

    /**
     * Writes all displayed cab prices to the provided Excel writer.
     * 
     * @param writer ExcelWrite object used to write data into Excel
     * @return List of WebElements representing cab prices
     * @throws IOException if there is a failure writing to Excel
     */
    public List<WebElement> displayPrices(ExcelWrite writer) throws IOException {
        int rowIndex = 1;
        Assert.assertTrue(priceNamesList.size() > 0, "No cab prices were displayed.");
        for (WebElement ps : priceNamesList) {
            writer.setCellValue(rowIndex++, 1, ps.getText());
        }
        return priceNamesList;
    }

    /**
     * Clicks the Sedan checkbox to filter results.
     * If the checkbox is unavailable, test will fail with a descriptive error.
     */
    public void clickOnSedanChk() {
        if (sedanChkBox != null && sedanChkBox.isDisplayed() && sedanChkBox.isEnabled()) {
            sedanChkBox.click();
        } else {
            Assert.fail("❌ Sedan checkbox is not available to click!");
        }
    }

    /**
     * Retrieves the lowest sedan price and logs it to the Excel sheet.
     * 
     * @param writer ExcelWrite object used to write data into Excel
     * @return String representing the least Sedan price
     * @throws IOException if there is a failure writing to Excel
     */
    public String displayPrice(ExcelWrite writer) throws IOException {
        writer.setCellValue(0, 2, "Least Sedan Price");
        Assert.assertNotNull(priceVal.getText(), "Sedan price is null");
        writer.setCellValue(1, 2, priceVal.getText());
        return priceVal.getText();
    }
}

    
    