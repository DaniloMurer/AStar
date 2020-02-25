package test.java.com.energyeet.astar.util;

import main.java.com.energyeet.astar.util.MockData;
import main.java.com.energyeet.astar.util.json.JsonWriter;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.io.File;

/**
 * Test class for the JsonWriter class
 */
public class JsonWriterTest {

    private JsonWriter jsonWriter;

    @Before
    public void setUp() {
        this.jsonWriter = MockData.createWriterMockData();
    }

    @Test
    public void createAStarFileTest() {
        String path = System.getProperty("user.home");
        path = path + "/AStarFileTest.astar";
        jsonWriter.saveJsonFile(path);
        Assert.assertTrue(new File(path).exists());
    }
}
