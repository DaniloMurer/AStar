package main.java.com.energyeet.astar.util.factory;

import main.java.com.energyeet.astar.AStarConstants;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import main.java.com.energyeet.astar.ui.GridEditor;
import main.java.com.energyeet.astar.util.json.JsonParser;
import main.java.com.energyeet.astar.util.json.JsonWriter;

import java.io.File;

/**
 * Trigger actions based on the menu item clicked on the menu bar
 */
public class MenuBarHandlerFactory implements AStarConstants {

    /**
     * Trigger actions based on the menu item
     * @param caption {@link String} of the menu item
     */
    public static void triggerAction(String caption, Stage stage, GridEditor gridEditor) {
        if (caption.equals(MENUITEM_SAVE_CAPTION)) {
            FileChooser fileChooser = new FileChooser();
            FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("ASTAR files", "*.astar");
            fileChooser.getExtensionFilters().add(extensionFilter);
            File file = fileChooser.showSaveDialog(stage);
            //If file is null exit from function
            if (file == null) return;
            //Create the file
            JsonWriter writer = new JsonWriter();
            writer = gridEditor.saveGrid(writer);
            writer.saveJsonFile(file.getPath());
        } else if (caption.equals(MENUITEM_OPEN_CAPTION)) {
            FileChooser fileChooser = new FileChooser();
            FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("ASTAR files", "*.astar");
            fileChooser.getExtensionFilters().add(extensionFilter);
            File file = fileChooser.showOpenDialog(stage);
            //If file is null exit from function
            if (file == null) return;
            JsonParser parser = new JsonParser(file);
            gridEditor.buildGrid(parser);
        }
    }
}
