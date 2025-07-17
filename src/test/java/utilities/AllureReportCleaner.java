package utilities;

import java.io.File;

public class AllureReportCleaner {

	public static void cleanAllureFolders() {
		File results = new File("target/allure-results");
		File report = new File("target/allure-report");

		deleteFolder(results);
		deleteFolder(report);
	}

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
