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

public class ScreenshotUtil {
	public static String filePath = "./Screenshots/";

	public static String captureScreenShot(WebDriver wd, String fileName) throws IOException {
		DateFormat df = new SimpleDateFormat("dd-MM-yy HH-mm-ss");
		Date date = new Date();

		File folder = new File(filePath);
		if (!folder.exists()) {
			folder.mkdirs(); // creates the folder (and any parent directories if missing)
		}

		File src = ((TakesScreenshot) wd).getScreenshotAs(OutputType.FILE);
		String dest = filePath + File.separator + fileName + "_" + df.format(date) + ".png";
		File destFile = new File(dest);
		try {
			FileHandler.copy(src, destFile);
			return dest;
		} catch (IOException e) {
			throw new RuntimeException("ScreenShot Capture Failed: " + e.getMessage());
		}

	}
}
