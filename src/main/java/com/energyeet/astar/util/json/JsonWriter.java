package main.java.com.energyeet.astar.util.json;

import main.java.com.energyeet.astar.util.helper.FileHelper;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Class for creating json files
 */
public class JsonWriter {

    //Root Object where all other things are stored
    private JSONObject rootObject;
    private FileHelper fileHelper;

    public JsonWriter() {
        this.rootObject = new JSONObject();
        this.fileHelper = new FileHelper();
    }

    /**
     * Add JsonObject with data to the file
     * @param name {@link String} identifier
     * @param data {@link Map} data pairs
     */
    public void addObject(String name, Map<String, String> data) {
        JSONObject newObject = new JSONObject();
        Iterator iterator = data.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry pair = (Map.Entry) iterator.next();
            newObject.put((String) pair.getKey(), pair.getValue());
        }
        rootObject.put(name, newObject);
    }

    /**
     * Add an array with data to the file
     * @param name {@link String} name of the array
     * @param data {@link Map} array of data
     */
    public void addArray(String name, List<Map<String, String>> data) {
        JSONArray newArray = new JSONArray();
        for (Map<String, String> map : data) {
            Iterator iterator = map.entrySet().iterator();
            JSONObject newObject = new JSONObject();
            while (iterator.hasNext()) {
                Map.Entry pair = (Map.Entry) iterator.next();
                newObject.put((String) pair.getKey(), pair.getValue());
            }
            newArray.put(newObject);
        }
        rootObject.put(name, newArray);
    }

    /**
     * Write the current Json file
     */
    public void saveJsonFile(String path) {
        this.fileHelper.writeFile(new File(path), this.rootObject.toString());
    }
}
