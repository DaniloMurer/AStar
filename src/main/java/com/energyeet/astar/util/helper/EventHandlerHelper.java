package main.java.com.energyeet.astar.util.helper;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import main.java.com.energyeet.astar.ui.GridEditor;
import main.java.com.energyeet.astar.util.GridCell;
import main.java.com.energyeet.astar.util.factory.GridEditorHandlerFactory;
import main.java.com.energyeet.astar.util.factory.MenuBarHandlerFactory;

/**
 * Class for handling events
 */
public class EventHandlerHelper {

    //Stage for which the event handler should take are of
    private Stage stage;

    public EventHandlerHelper(Stage stage) {
        this.stage = stage;
    }

    /**
     * Handles navigation from the menu bar
     * @return {@link EventHandler} for the {@link MenuItem}
     */
    public EventHandler<ActionEvent> menuBarHandler(GridEditor gridEditor) {
        return event -> {
            String menuItemCaption = ((MenuItem) event.getSource()).getText();
            MenuBarHandlerFactory.triggerAction(menuItemCaption, stage, gridEditor);
        };
    }

    /**
     * Handles all mouse clicks
     * @return {@link EventHandler} event
     */
    public EventHandler<MouseEvent> gridEditorMouseClick() {
        return event -> {
            GridCell clickedCell = (GridCell) event.getSource();
            GridEditorHandlerFactory.setCellState(event, clickedCell);
        };
    }
}
