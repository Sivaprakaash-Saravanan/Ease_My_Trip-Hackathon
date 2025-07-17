package utilities;

import java.io.IOException;

/**
 * Utility class to automate generation and viewing of Allure test reports.
 * It first creates a fresh Allure report from test results,
 * and then opens it in the default browser.
 */
public class AllureReportOpener {

    /**
     * Generates a clean Allure report and opens it using the configured command-line path.
     * This method assumes Allure CLI is properly installed and available at the specified location.
     */
    public static void openAllureReport() {
        try {
            // Step 1: Generate Allure report from target/allure-results
            ProcessBuilder generate = new ProcessBuilder(
                    "C:\\Users\\2407037\\Downloads\\allure-commandline-2.29.0\\allure-2.29.0\\bin\\allure.bat",
                    "generate", "target/allure-results", "-o", "target/allure-report", "--clean");
            generate.inheritIO(); // Optionally shows output in the console
            Process genProcess = generate.start();
            genProcess.waitFor(); // Wait until report generation is complete

            // Step 2: Open the generated Allure report in the browser
            ProcessBuilder open = new ProcessBuilder(
                    "C:\\Users\\2407037\\Downloads\\allure-commandline-2.29.0\\allure-2.29.0\\bin\\allure.bat", 
                    "open", "target/allure-report");
            open.inheritIO();
            Process openProcess = open.start();
            openProcess.waitFor(); // Wait until browser opens

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
