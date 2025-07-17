package utilities;

import java.io.File;

/**
 * Utility class for cleaning up Allure report directories. This class deletes
 * the contents of 'target/allure-results' and 'target/allure-report' folders
 * before generating a fresh Allure report during test execution.
 */
public class AllureReportCleaner {

	/**
	 * Public method to delete both allure-results and allure-report folders.
	 * Ensures the report folders are emptied before the next test run.
	 */
	public static void cleanAllureFolders() {
		File results = new File("target/allure-results");
		File report = new File("target/allure-report");

		deleteFolder(results);
		deleteFolder(report);
	}

	/**
	 * Recursively deletes all contents inside the given folder.
	 *
	 * @param folder File object representing the folder to delete
	 */
	private static void deleteFolder(File folder) {
		if (folder.exists()) {
			for (File file : folder.listFiles()) {
				if (file.isDirectory()) {
					deleteFolder(file);
				} else {
					file.delete();
				}
			}
			folder.delete();
		}
	}
}
