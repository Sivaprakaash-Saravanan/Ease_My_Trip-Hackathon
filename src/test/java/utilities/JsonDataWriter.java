package utilities;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class JsonDataWriter {

	public static void writeSimpleData(String key, String value, String fileName) {
		try {
			File file = new File("./src/test/resources/data/" + fileName);

			// Load existing JSON or create a new one
			JsonObject root = file.exists() ? JsonParser.parseReader(new FileReader(file)).getAsJsonObject()
					: new JsonObject();

			// Add key-value entry
			root.addProperty(key, value);

			// Write back to file
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			FileWriter writer = new FileWriter(file);
			gson.toJson(root, writer);
			writer.flush();
			writer.close();

//            System.out.println("Saved: \"" + key + "\": " + value);

		} catch (IOException e) {
			throw new RuntimeException("Failed to write data to JSON", e);
		}
	}

	public static void writeSimpleDataWithScenario(String scenarioKey, String key, String value, String fileName) {
		try {
			File file = new File("./src/test/resources/data/" + fileName);

			// Ensure file and folder exist
			if (!file.exists()) {
				file.getParentFile().mkdirs();
				file.createNewFile();
			}

			// Load existing or start fresh
			JsonObject root = file.length() > 0 ? JsonParser.parseReader(new FileReader(file)).getAsJsonObject()
					: new JsonObject();

			// Get or create scenario block
			JsonObject scenarioBlock = root.has(scenarioKey) ? root.getAsJsonObject(scenarioKey) : new JsonObject();

			// Add key-value into scenario
			scenarioBlock.addProperty(key, value);
			root.add(scenarioKey, scenarioBlock);

			// Save to file
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			FileWriter writer = new FileWriter(file);
			gson.toJson(root, writer);
			writer.flush();
			writer.close();

//            System.out.println("Saved: \"" + key + "\": " + value + " under scenario: " + scenarioKey);

		} catch (IOException e) {
			throw new RuntimeException("Failed to write data to JSON", e);
		}
	}

}
