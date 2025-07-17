package utilities;

import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

/**
 * Utility class responsible for initializing and managing WebDriver instances.
 * Supports both local and remote browser setup using configuration properties.
 */
public class DriverSetup {
	Properties p;
	private WebDriver driver;
	static Logger logger;
	static String browser;

	/**
	 * Initializes a WebDriver instance based on execution environment and browser
	 * type. Reads configuration from config.properties.
	 *
	 * @param browser Browser name ("chrome" or "edge")
	 * @return WebDriver instance
	 * @throws IOException if configuration file fails to load or setup fails
	 */
	@SuppressWarnings("deprecation")
	public WebDriver intializeWebDriver(String browser) throws IOException {
		System.out.println("Initializing WebDriver for browser: " + browser);

		Map<String, Object> prefs = new HashMap<>();
		prefs.put("profile.default_content_setting_values.notifications", 2); // Block Notifications

		FileReader file = new FileReader(System.getProperty("user.dir") + "/src/test/resources/data/config.properties");
		p = new Properties();
		p.load(file);
		String executionEnv = p.getProperty("execution_env");
		String os = p.getProperty("os").toLowerCase();

		if (executionEnv.equalsIgnoreCase("remote")) {
			DesiredCapabilities capabilities = new DesiredCapabilities();

			switch (os) {
			case "windows":
				capabilities.setPlatform(Platform.WIN11);
				break;
			case "mac":
				capabilities.setPlatform(Platform.MAC);
				break;
			case "linux":
				capabilities.setPlatform(Platform.LINUX);
				break;
			default:
				System.out.println("No matching OS");
				return null;
			}

			switch (browser.toLowerCase()) {
			case "chrome":
				ChromeOptions chromeOptions = new ChromeOptions();
				chromeOptions.addArguments("--disable-notifications");
				capabilities.setBrowserName("chrome");
				chromeOptions.merge(capabilities);
				driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), chromeOptions);
				break;

			case "edge":
				EdgeOptions edgeOptions = new EdgeOptions();
				edgeOptions.addArguments("--disable-notifications");
				capabilities.setBrowserName("MicrosoftEdge");
				edgeOptions.merge(capabilities);
				driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), edgeOptions);
				break;

			default:
				System.out.println("No matching browser");
				return null;
			}

		} else if (executionEnv.equalsIgnoreCase("local")) {
			switch (browser.toLowerCase()) {
			case "chrome":
				ChromeOptions chromeOptions = new ChromeOptions();
				chromeOptions.setExperimentalOption("prefs", prefs);
				chromeOptions.addArguments("--disable-notifications");
				chromeOptions.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
				driver = new ChromeDriver(chromeOptions);
				break;

			case "edge":
				EdgeOptions edgeOptions = new EdgeOptions();
				edgeOptions.setExperimentalOption("prefs", prefs);
				edgeOptions.addArguments("--disable-notifications");
				edgeOptions.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
				driver = new EdgeDriver();
				break;

			default:
				throw new IllegalArgumentException("Unsupported browser: " + browser);
			}
		}

		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

		System.out.println("WebDriver setup complete for: " + browser);
		return driver;
	}

	/**
	 * Returns a logger instance using Log4j.
	 *
	 * @return Logger object for logging
	 */
	public static Logger getLogger() {
		logger = LogManager.getLogger();
		return logger;
	}

	/**
	 * Terminates the browser session and quits WebDriver.
	 */
	public void driverTearDown() {
		driver.quit();
		System.out.println("Closing the browser and quitting WebDriver.");
	}
}
