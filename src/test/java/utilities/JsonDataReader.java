package utilities;

import java.io.FileReader;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * Utility class for reading data from JSON files.
 * Designed to extract test-specific data blocks based on scenario keys.
 */
public class JsonDataReader {

    /**
     * Loads a JSON file and returns the object associated with a given scenario key.
     *
     * @param scenarioKey Key that maps to the desired data block in the JSON file
     * @param fileName    Name of the JSON file located in src/test/resources/data/
     * @return JsonObject containing scenario-specific data
     * @throws RuntimeException if the file cannot be read or the key is missing
     */
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

