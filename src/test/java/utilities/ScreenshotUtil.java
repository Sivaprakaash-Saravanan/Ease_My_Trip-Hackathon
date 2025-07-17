package utilities;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.io.FileHandler;

/**
 * Utility class to capture screenshots from active WebDriver sessions.
 * Screenshots are saved to the ./Screenshots/ directory with timestamped
 * filenames.
 */
public class ScreenshotUtil {

	/** Base directory where screenshots will be stored */
	public static String filePath = "./Screenshots/";

	/**
	 * Captures a screenshot of the current browser view and saves it as a PNG file.
	 * Filenames include a timestamp for uniqueness.
	 *
	 * @param wd       WebDriver instance (must implement TakesScreenshot)
	 * @param fileName Desired base name for the screenshot file (timestamp will be
	 *                 appended)
	 * @return Absolute path to the saved screenshot
	 * @throws IOException If file writing or screenshot capture fails
	 */
	public static String captureScreenShot(WebDriver wd, String fileName) throws IOException {
		// Create timestamp for file uniqueness
		DateFormat df = new SimpleDateFormat("dd-MM-yy HH-mm-ss");
		Date date = new Date();

		// Ensure screenshot directory exists
		File folder = new File(filePath);
		if (!folder.exists()) {
			folder.mkdirs(); // creates the folder and any missing parent directories
		}

		// Take the screenshot
		File src = ((TakesScreenshot) wd).getScreenshotAs(OutputType.FILE);
		String dest = filePath + File.separator + fileName + "_" + df.format(date) + ".png";
		File destFile = new File(dest);

		// Save the screenshot file
		try {
			FileHandler.copy(src, destFile);
			return dest;
		} catch (IOException e) {
			throw new RuntimeException("ScreenShot Capture Failed: " + e.getMessage());
		}
	}
}
