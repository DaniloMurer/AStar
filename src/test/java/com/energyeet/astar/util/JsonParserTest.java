package test.java.com.energyeet.astar.util;

import main.java.com.energyeet.astar.util.MockData;
import main.java.com.energyeet.astar.util.json.JsonParser;
import main.java.com.energyeet.astar.util.json.JsonWriter;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

/**
 * Test class for the JsonParser class
 */
public class JsonParserTest {

    private JsonParser jsonParser;
    private JsonWriter jsonWriter;
    private File astarFile;

    @Before
    public void setUp() {
        this.jsonWriter = MockData.createWriterMockData();
        String path = System.getProperty("user.home");
        path = path + "/AStarFileTest.astar";
        jsonWriter.saveJsonFile(path);
        this.astarFile = new File(path);
        this.jsonParser = new JsonParser(this.astarFile);
    }

    @Test
    public void getPropertyTest() {
        JSONObject object = this.jsonParser.getObject(this.jsonParser.getRootObject().toString(), "settings");
        String value = this.jsonParser.getProperty(object, "height");
        Assert.assertEquals("900", value);
    }

    @Test
    public void getJsonObjectFromArrayTest() {
        JSONObject jsonObject = this.jsonParser.getObject(this.jsonParser.getRootObject().toString(), "walls", 0);
        Assert.assertEquals("4", jsonParser.getProperty(jsonObject, "x"));
        Assert.assertEquals("10", jsonParser.getProperty(jsonObject, "y"));
    }

    @Test
    public void getJsonArrayFromObjectTest() {
        JSONArray jsonArray = this.jsonParser.getArray(this.jsonParser.getRootObject().toString(), "walls");
        String data = jsonArray.getJSONObject(0).getString("x");
        Assert.assertEquals("4", data);
    }
}
