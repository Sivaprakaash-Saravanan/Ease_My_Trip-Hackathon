package utilities;

import java.io.FileReader;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class JsonDataReader {
	public static JsonObject getCabData(String scenarioKey, String fileName) {
		try {
			FileReader reader = new FileReader("./src/test/resources/data/" + fileName);
			JsonObject root = JsonParser.parseReader(reader).getAsJsonObject();
			return root.getAsJsonObject(scenarioKey);
		} catch (Exception e) {
			throw new RuntimeException("Unable to read JSON data for: " + scenarioKey, e);
		}
	}
}
