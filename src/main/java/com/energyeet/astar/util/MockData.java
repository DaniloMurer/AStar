package main.java.com.energyeet.astar.util;

import main.java.com.energyeet.astar.util.json.JsonWriter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class for test mock data
 */
public class MockData {

    /**
     * Create Mock data for the {@link JsonWriter}
     * @return {@link JsonWriter} with the mock data
     */
    public static JsonWriter createWriterMockData() {
        JsonWriter jsonWriter = new JsonWriter();
        Map<String, String> settingData = new HashMap<>();
        settingData.put("height", "900");
        settingData.put("width", "600");
        Map<String, String> startData = new HashMap<>();
        startData.put("x", "6");
        startData.put("y", "1");
        Map<String, String> endData = new HashMap<>();
        endData.put("x", "12");
        endData.put("y", "5");
        jsonWriter.addObject("settings", settingData);
        jsonWriter.addObject("start", startData);
        jsonWriter.addObject("end", endData);
        List<Map<String, String>> walls = new ArrayList<>();
        Map<String, String> wall1 = new HashMap<>();
        wall1.put("x", "4");
        wall1.put("y", "10");
        walls.add(wall1);
        Map<String, String> wall2= new HashMap<>();
        wall2.put("x", "8");
        wall2.put("y", "3");
        walls.add(wall2);
        Map<String, String> wall3= new HashMap<>();
        wall3.put("x", "7");
        wall3.put("y", "4");
        walls.add(wall3);
        jsonWriter.addArray("walls", walls);
        return jsonWriter;
    }
}
