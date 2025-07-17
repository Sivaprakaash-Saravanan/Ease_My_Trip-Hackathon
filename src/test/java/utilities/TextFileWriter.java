package utilities;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class TextFileWriter {

	public static void writeToTextFile(String fileName, String content) {
		try {
			File file = new File("./src/test/resources/data/" + fileName);

			// ✅ Create the file if it doesn't exist
			if (!file.exists()) {
				file.getParentFile().mkdirs(); // if folder structure doesn't exist
				file.createNewFile();
				System.out.println("Created new file: " + fileName);
			}

			// ✅ Write to the file
			FileWriter writer = new FileWriter(file, true); // true = append mode
			writer.write(content + System.lineSeparator());
			writer.close();

//			System.out.println("Successfully wrote to file.");
		} catch (IOException e) {
			System.out.println("Error writing to file: " + e.getMessage());
		}
	}

	public static void clearFile(String fileName) throws IOException {
		File file = new File("./src/test/resources/data/" + fileName);
		file.getParentFile().mkdirs(); // Create folder structure if missing
		file.createNewFile(); // Create file if missing

		FileWriter writer = new FileWriter(file, false); // false = overwrite
		writer.write(""); // clear content
		writer.close();
	}
}
