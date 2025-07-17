package utilities;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Utility class for writing and clearing text files. Primarily used for logging
 * or storing test data in a readable format. Operates on files within the
 * src/test/resources/data/ directory.
 */
public class TextFileWriter {

	/**
	 * Appends content to a text file. If the file does not exist, it will be
	 * created. Each entry is written on a new line.
	 *
	 * @param fileName Name of the file (e.g., "output.txt")
	 * @param content  String content to write into the file
	 */
	public static void writeToTextFile(String fileName, String content) {
		try {
			File file = new File("./src/test/resources/data/" + fileName);

			// Create file and parent directory if they don't exist
			if (!file.exists()) {
				file.getParentFile().mkdirs();
				file.createNewFile();
				System.out.println("Created new file: " + fileName);
			}

			// Write content in append mode
			FileWriter writer = new FileWriter(file, true);
			writer.write(content + System.lineSeparator());
			writer.close();
		} catch (IOException e) {
			System.out.println("Error writing to file: " + e.getMessage());
		}
	}

	/**
	 * Clears all existing content from the specified text file. Creates the file if
	 * it does not already exist.
	 *
	 * @param fileName Name of the file to clear (e.g., "output.txt")
	 * @throws IOException if file writing or creation fails
	 */
	public static void clearFile(String fileName) throws IOException {
		File file = new File("./src/test/resources/data/" + fileName);
		file.getParentFile().mkdirs(); // Create parent directories if missing
		file.createNewFile(); // Create file if missing

		// Overwrite mode with empty string
		FileWriter writer = new FileWriter(file, false);
		writer.write("");
		writer.close();
	}
}
