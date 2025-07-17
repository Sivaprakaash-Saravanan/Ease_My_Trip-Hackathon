package utilities;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * Utility class for writing key-value data to JSON files. Supports simple flat
 * key-value writing and nested scenario-based data grouping.
 */
public class JsonDataWriter {

	/**
	 * Writes a single key-value pair into the root level of a JSON file. If the
	 * file already exists, it appends the new key-value pair; otherwise, it creates
	 * a new file.
	 *
	 * @param key      The key to write
	 * @param value    The value to associate with the key
	 * @param fileName The name of the JSON file (relative to
	 *                 /src/test/resources/data/)
	 */
	public static void writeSimpleData(String key, String value, String fileName) {
		try {
			File file = new File("./src/test/resources/data/" + fileName);

			// Load existing JSON or create a new object
			JsonObject root = file.exists() ? JsonParser.parseReader(new FileReader(file)).getAsJsonObject()
					: new JsonObject();

			// Add key-value pair
			root.addProperty(key, value);

			// Save to file with pretty formatting
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			FileWriter writer = new FileWriter(file);
			gson.toJson(root, writer);
			writer.flush();
			writer.close();
		} catch (IOException e) {
			throw new RuntimeException("Failed to write data to JSON", e);
		}
	}

	/**
	 * Writes a key-value pair inside a nested block identified by a scenario key.
	 * If the file or scenario block doesn't exist, they are created.
	 *
	 * @param scenarioKey The block/group name under which data is organized
	 * @param key         The key to write inside the scenario block
	 * @param value       The value to associate with the key
	 * @param fileName    The JSON file name (relative to /src/test/resources/data/)
	 */
	public static void writeSimpleDataWithScenario(String scenarioKey, String key, String value, String fileName) {
		try {
			File file = new File("./src/test/resources/data/" + fileName);

			// Ensure file and its parent directories exist
			if (!file.exists()) {
				file.getParentFile().mkdirs();
				file.createNewFile();
			}

			// Load existing content or create a new root object
			JsonObject root = file.length() > 0 ? JsonParser.parseReader(new FileReader(file)).getAsJsonObject()
					: new JsonObject();

			// Get existing scenario block or create a new one
			JsonObject scenarioBlock = root.has(scenarioKey) ? root.getAsJsonObject(scenarioKey) : new JsonObject();

			// Add key-value pair to scenario
			scenarioBlock.addProperty(key, value);
			root.add(scenarioKey, scenarioBlock);

			// Save JSON back to file
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			FileWriter writer = new FileWriter(file);
			gson.toJson(root, writer);
			writer.flush();
			writer.close();
		} catch (IOException e) {
			throw new RuntimeException("Failed to write data to JSON", e);
		}
	}
}
