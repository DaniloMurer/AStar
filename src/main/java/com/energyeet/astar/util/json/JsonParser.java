package main.java.com.energyeet.astar.util.json;

import main.java.com.energyeet.astar.util.helper.FileHelper;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;

/**
 * Class for parsing JSON
 */
public class JsonParser {

    private File astarFile;
    private String jsonString;
    private FileHelper fileHelper;

    /**
     * Constructor
     * @param file {@link File} object of the ASTAR file
     */
    public JsonParser(File file) {
        this.astarFile = file;
        fileHelper = new FileHelper();
        jsonString = fileHelper.readFile(this.astarFile);
    }

    /**
     * Create the parse by a JsonString
     * @param jsonString {@link String}
     */
    public JsonParser(String jsonString) {
        this.jsonString = jsonString;
    }

    /**
     * Get the root object of the file
     * @return {@link JSONObject} root object
     */
    public JSONObject getRootObject() {
        return new JSONObject(this.jsonString);
    }

    /**
     * Get a {@link JSONObject} for a key from the json file
     * @param jsonString {@link String} jsonString
     * @param key {@link String} key for the {@link JSONObject}
     * @return {@link JSONObject} for the given key
     */
    public JSONObject getObject(String jsonString, String key) {
        JSONObject jsonObject = new JSONObject(jsonString);
        return jsonObject.getJSONObject(key);
    }

    /**
     * Get a {@link JSONObject} out of an array
     * @param jsonString {@link String} json String
     * @param arrayKey {@link String} name of the array
     * @param objectIndex {@link int} index of the object inside the array
     * @return {@link JSONObject} for the given index in the array
     */
    public JSONObject getObject(String jsonString, String arrayKey, int objectIndex) {
        JSONObject jsonObject = new JSONObject(jsonString);
        JSONArray jsonArray = jsonObject.getJSONArray(arrayKey);
        return jsonArray.getJSONObject(objectIndex);
    }

    /**
     * Get a {@link JSONArray} from a {@link JSONObject}
     * @param jsonString {@link String} the json String that will be the object
     * @param key {@link String} key for the searched array
     * @return {@link JSONArray} the array
     */
    public JSONArray getArray(String jsonString, String key) {
        JSONObject jsonObject = new JSONObject(jsonString);
        return  jsonObject.getJSONArray(key);
    }

    /**
     * Get a property from a jsonObject
     * @param jsonObject {@link JSONObject} the object from which to get the value
     * @param key {@link String} the key
     * @return {@link String} the property
     */
    public String getProperty(JSONObject jsonObject, String key) {
        return jsonObject.getString(key);
    }
}
