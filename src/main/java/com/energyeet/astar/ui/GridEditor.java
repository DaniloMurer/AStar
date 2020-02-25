package main.java.com.energyeet.astar.ui;

import javafx.scene.layout.GridPane;
import main.java.com.energyeet.astar.util.GridCellState;
import main.java.com.energyeet.astar.util.helper.EventHandlerHelper;
import main.java.com.energyeet.astar.util.GridCell;
import main.java.com.energyeet.astar.util.helper.EventHandlerHelper;
import main.java.com.energyeet.astar.util.json.JsonParser;
import main.java.com.energyeet.astar.util.json.JsonWriter;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class to display a simple grid to draw some obstacles, a start-point and an end-point
 */
public class GridEditor extends GridPane {
    public static boolean isStartPointSet = false;
    public static boolean isEndPointSet = false;

    private int numberOfRows;
    private int numberOfColumns;
    private EventHandlerHelper eventHandlerHelper;
    private GridCell[][] cellArray;

    /**
     * Constructor
     * @param numberOfRows {@link int} number of rows
     * @param numberOfColumns {@link int} number of columns
     */
    public GridEditor(int numberOfRows, int numberOfColumns) {
        this.numberOfRows = numberOfRows;
        this.numberOfColumns = numberOfColumns;
        this.eventHandlerHelper = new EventHandlerHelper(null);
        this.cellArray = new GridCell[numberOfRows][numberOfColumns];
        this.build();
    }

    /**
     * build the grid editor
     */
    private void build() {
        this.setHgap(1);
        this.setVgap(1);

        for (int i = 0; i < numberOfRows; i++) {
            for (int j = 0; j < numberOfColumns; j++) {
                GridCell gridCell = new GridCell(50, 50, j, i);
                gridCell.setOnMouseClicked(eventHandlerHelper.gridEditorMouseClick());
                this.add(gridCell, j, i);
                this.cellArray[i][j] = gridCell;
            }
        }
    }

    /**
     * gets the number of rows
     * @return {@link int} number of rows
     */
    public int getNumberOfRows() {
        return numberOfRows;
    }

    /**
     * gets the number of columns
     * @return {@link int} number of columns
     */
    public int getNumberOfColumns() {
        return numberOfColumns;
    }

    /**
     * rebuild the grid editor
     * @return {@link GridPane} Grid editor
     */
    public void rebuild() {
        isEndPointSet = false;
        isStartPointSet = false;
        build();
    }

    /**
     * Builds the grid with all the functions (create start-point, end-point, wall etc.)
     * @param parser  {@link JsonParser}
     */
    public void buildGrid(JsonParser parser) {
        //Clear field
        this.rebuild();
        //Parse file to the objects
        JSONObject startPoint = parser.getObject(parser.getRootObject().toString(), "start");
        JSONObject endPoint = parser.getObject(parser.getRootObject().toString(), "end");
        JSONArray wallsArray = parser.getArray(parser.getRootObject().toString(), "walls");
        //Set start-point
        cellArray[Integer.parseInt(startPoint.getString("y"))][Integer.parseInt(startPoint.getString("x"))].setState(GridCellState.START);
        //Set end-point
        cellArray[Integer.parseInt(endPoint.getString("y"))][Integer.parseInt(endPoint.getString("x"))].setState(GridCellState.END);
        //Set all walls
        for (int i = 0; i < wallsArray.length(); i++) {
            JSONObject wall = wallsArray.getJSONObject(i);
            cellArray[Integer.parseInt(wall.getString("y"))][Integer.parseInt(wall.getString("x"))].setState(GridCellState.WALL);
        }
    }

    /**
     * Gets all cells in the grid
     * @return all {@link GridCell} in the grid as an array
     */
    public GridCell[][] getCellArray() {
        return cellArray;
    }

    /**
     * Export the grid into the {@link JsonWriter} object
     * @param jsonWriter {@link JsonWriter}
     * @return {@link JsonWriter} with the grid into it
     */
    public JsonWriter saveGrid(JsonWriter jsonWriter) {
        List<Map<String, String>> walls = new ArrayList<>();
        for (int i = 0; i < cellArray.length; i++) {
            for (int j = 0; j < cellArray[i].length; j++) {
                Map<String, String> map = new HashMap<>();
                //Get start-point
                GridCell cell = cellArray[i][j];
                if (cell.getState() == GridCellState.START) { //Get start-point
                    map.put("x", String.valueOf(cell.getXPosition()));
                    map.put("y", String.valueOf(cell.getYPosition()));
                    jsonWriter.addObject("start", map);
                } else if (cell.getState() == GridCellState.END) { //Get end point
                    map.put("x", String.valueOf(cell.getXPosition()));
                    map.put("y", String.valueOf(cell.getYPosition()));
                    jsonWriter.addObject("end", map);
                } else if (cell.getState() == GridCellState.WALL) { //Get Wall
                    map.put("x", String.valueOf(cell.getXPosition()));
                    map.put("y", String.valueOf(cell.getYPosition()));
                    walls.add(map);
                }
            }
        }
        jsonWriter.addArray("walls", walls);
        return jsonWriter;
    }

    /**
     * Clears the solution-path
     */
    public void clearPath() {
        for (int i = 0; i < cellArray.length; i++) {
            for (int j = 0; j < cellArray[i].length; j++) {
                if (cellArray[i][j].getIsSolution() && (cellArray[i][j].getState() != GridCellState.START && cellArray[i][j].getState() != GridCellState.END)) {
                    cellArray[i][j].setIsSolution(false);
                    cellArray[i][j].setState(GridCellState.NONE);
                }
            }
        }
    }
}
